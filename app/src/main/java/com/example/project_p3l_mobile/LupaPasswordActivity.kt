package com.example.project_p3l_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponse
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponseMessageAsString
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityLoginBinding
import com.example.project_p3l_mobile.databinding.ActivityLupaPasswordBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LupaPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnKirim.setOnClickListener {
            binding.btnKirim.setText("Tunggu Sebentar...")
            lupaPassword()
        }

    }

    fun lupaPassword() {
        val email = binding.inputEmail.text.toString()
        val emailInputLayout = binding.inputEmail

        CoroutineScope(Dispatchers.Main).launch {
            try {
                emailInputLayout.error = null

                val response = ApiConfig.getApiService().lupaPassword(email = email, role = "customer")
                val data = response

                Log.d("LoginActivity", data.message)

                Toast.makeText(this@LupaPasswordActivity, response.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@LupaPasswordActivity, LoginActivity::class.java)
                startActivity(intent)
            } catch (e: HttpException) {
//                untuk response selain 200
                if(e.code() == 400) {
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)

                    errorResponse.message?.forEach { (key, value) ->
                        when (key) {
                            "email" -> emailInputLayout.error = value[0]
                        }
                    }
                }else if(e.code() == 404 || e.code() == 401) {
                    val errorResponse = Gson().fromJson(
                        e.response()?.errorBody()?.charStream(),
                        ApiErrorResponseMessageAsString::class.java
                    )
                    Toast.makeText(this@LupaPasswordActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LupaPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }catch(e: Exception) {
                Toast.makeText(this@LupaPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
            }finally {
                binding.btnKirim.setText("KIRIM")
            }
        }
    }
}