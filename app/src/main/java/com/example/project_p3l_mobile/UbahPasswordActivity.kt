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
import com.example.project_p3l_mobile.databinding.ActivityUbahPasswordBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class UbahPasswordActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: ActivityUbahPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE)

        binding.btnSave.setOnClickListener {
            ubahPassword()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    fun ubahPassword() {
        val token = sharedPreferences?.getString("token", null)
        val password_lama = binding.inputPasswordLama
        val password_baru = binding.inputPasswordBaru
        val password_confirmation = binding.inputKonfPasswordBaru

        CoroutineScope(Dispatchers.Main).launch {
            try {
                password_lama.error = null
                password_baru.error = null

                val response = ApiConfig.getApiService().ubahPassword(
                    token = "Bearer $token",
                    password_lama = password_lama.text.toString(),
                    password = password_baru.text.toString(),
                    password_confirmation = password_confirmation.text.toString()
                )
                val data = response

                Log.d("UbahPasswordActivity", data.toString())

                Toast.makeText(this@UbahPasswordActivity, data.message, Toast.LENGTH_LONG).show()
                finish()
            } catch (e: HttpException) {
                if(e.code() == 400){
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)

                    errorResponse.message?.forEach { (key, value) ->
                        when (key) {
                            "password_lama" -> password_lama.error = value[0]
                            "password" -> password_baru.error = value[0]
                        }
                    }
                }else if(e.code() == 404 || e.code() == 401) {
                    val errorResponse = Gson().fromJson(
                        e.response()?.errorBody()?.charStream(),
                        ApiErrorResponseMessageAsString::class.java
                    )
                    Toast.makeText(
                        this@UbahPasswordActivity,
                        errorResponse.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(this@UbahPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Throwable) {
                Toast.makeText(this@UbahPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
            }catch(e: Exception) {
                Toast.makeText(this@UbahPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
