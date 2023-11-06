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
import com.example.project_p3l_mobile.databinding.ActivityLoginPegawaiBinding
import com.example.project_p3l_mobile.pegawai.NavigationPegawaiActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginPegawaiActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: ActivityLoginPegawaiBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("myKeyPegawai", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            loginPegawai()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
        binding.textLupaPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    fun loginPegawai() {
        val email = binding.inputEmail.text.toString()
        val emailInputLayout = binding.inputEmail
        val password = binding.inputPassword.text.toString()
        val passwordInputLayout = binding.inputPassword

        CoroutineScope(Dispatchers.Main).launch {
            try {
                emailInputLayout.error = null
                passwordInputLayout.error = null

                val response = ApiConfig.getApiService().loginPegawai(email = email, password = password)
                val data = response.data

                Log.d("Login Pegawai", data.toString())


                if (data.user.role?.nama_role == "Owner" || data.user.role?.nama_role == "General Manager") {
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString("token", data.authorization.token)
                    editor.putString("nama", data.user.nama_pegawai)
                    editor.putString("id", data.user.id.toString())
                    editor.apply()

                    Toast.makeText(this@LoginPegawaiActivity, response.message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginPegawaiActivity, NavigationPegawaiActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@LoginPegawaiActivity, "Anda tidak memiliki akses", Toast.LENGTH_LONG).show()
                }
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
                    Toast.makeText(this@LoginPegawaiActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginPegawaiActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }catch(e: Exception) {
                Toast.makeText(this@LoginPegawaiActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}