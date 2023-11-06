package com.example.project_p3l_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponse
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponseMessageAsString
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityLoginBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private val myKeyLogin = "myKey"
    var sharedPreferences: SharedPreferences? = null
    private lateinit var binding:ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(myKeyLogin, Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
        }

        binding.textLupaPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoginPegawai.setOnClickListener {
            val intent = Intent(this, LoginPegawaiActivity::class.java)
            startActivity(intent)
        }

    }

    fun login() {
        val email = binding.inputEmail.text.toString()
        val emailInputLayout = binding.inputEmail
        val password = binding.inputPassword.text.toString()
        val passwordInputLayout = binding.inputPassword

        CoroutineScope(Dispatchers.Main).launch {
            try {
                emailInputLayout.error = null
                passwordInputLayout.error = null

                val response = ApiConfig.getApiService().loginCustomer(email = email, password = password)
                val data = response.data

                Log.d("LoginActivity", data.toString())
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("token", data.authorization.token)
                editor.putString("nama", data.user.nama_customer)
                editor.putString("id", data.user.id.toString())
                editor.apply()

                Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                startActivity(intent)
            } catch (e: HttpException) {
//                untuk response selain 200
                if(e.code() == 400) {
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)

                    errorResponse.message?.forEach { (key, value) ->
                        when (key) {
                            "email" -> emailInputLayout.error = value[0]
                            "password" -> passwordInputLayout.error = value[0]
                        }
                    }
                }else if(e.code() == 404 || e.code() == 401) {
                    val errorResponse = Gson().fromJson(
                        e.response()?.errorBody()?.charStream(),
                        ApiErrorResponseMessageAsString::class.java
                    )
                    Toast.makeText(this@LoginActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }catch(e: Exception) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}