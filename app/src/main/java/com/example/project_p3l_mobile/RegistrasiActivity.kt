package com.example.project_p3l_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponse
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponseMessageAsString
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityRegistrasiBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            registrasiAkun()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun registrasiAkun() {
        val nama = binding.inputNama.text.toString()
        val namaInputLayout = binding.namaInputLayout
        val email = binding.inputEmail.text.toString()
        val emailInputLayout = binding.emailInputLayout
        val jenis = binding.inputJenisIdentitas.text.toString()
        val jenisInputLayout = binding.jenisIdentitasInputLayout
        val noIden = binding.inputNoIdentitas.text.toString()
        val noIdenInputLayout = binding.noIdentitasInputLayout
        val noTelp = binding.inputTelp.text.toString()
        val noTelpInputLayout = binding.noTelpInputLayout
        val alamat = binding.inputAlamat.text.toString()
        val alamatInputLayout = binding.alamatInputLayout
        val password = binding.inputPassword.text.toString()
        val passwordInputLayout = binding.passwordInputLayout
        val konfPass = binding.inputKonfPassword.text.toString()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                namaInputLayout.error = null
                emailInputLayout.error = null
                jenisInputLayout.error = null
                noIdenInputLayout.error = null
                noTelpInputLayout.error = null
                alamatInputLayout.error = null
                passwordInputLayout.error = null

                val response = ApiConfig.getApiService().register(
                    jenis_customer = "P",
                    nama_customer = nama,
                    no_identitas = noIden,
                    jenis_identitas = jenis,
                    no_telp = noTelp,
                    email = email,
                    alamat = alamat,
                    password = password,
                    password_confirmation = konfPass,
                    flag_stat = "1"
                )
                val data = response.user

                Log.d("Registrasi", data.toString())

                Toast.makeText(this@RegistrasiActivity, response.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegistrasiActivity, LoginActivity::class.java)
                startActivity(intent)
            } catch (e: HttpException) {
                Log.d("Registrasi Error", e.toString())
                if(e.code() == 400){
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)

                    errorResponse.message?.forEach { (key, value) ->
                        when (key) {
                            "nama_customer" -> {
                                namaInputLayout.error = value[0]
                            }

                            "email" -> {
                                emailInputLayout.error = value[0]
                            }

                            "jenis_identitas" -> {
                                jenisInputLayout.error = value[0]
                            }

                            "no_identitas" -> {
                                noIdenInputLayout.error = value[0]
                            }

                            "no_telp" -> {
                                noTelpInputLayout.error = value[0]
                            }

                            "alamat" -> {
                                alamatInputLayout.error = value[0]
                            }

                            "password" -> {
                                passwordInputLayout.error = value[0]
                            }
                        }
                    }
                }
            } catch(e: Exception) {
                Toast.makeText(this@RegistrasiActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}