package com.example.project_p3l_mobile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.project_p3l_mobile.adapter.RV_JenisKamarAdapter
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponse
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponseMessageAsString
import com.example.project_p3l_mobile.data_api.model.CustomerData
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.FragmentMainBinding
import com.example.project_p3l_mobile.databinding.FragmentProfileBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("myKey", android.content.Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("token", null) == null) {
            binding?.ifLogin?.visibility = View.GONE
            Log.d("Masuk", "Masuk")
            binding?.ifNotLogin?.visibility = View.VISIBLE

            binding?.btnLoginInProfile?.setOnClickListener {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            binding?.ifLogin?.visibility = View.VISIBLE
            binding?.ifNotLogin?.visibility = View.GONE
            binding?.btnSimpanBatalLayout?.visibility = View.GONE
            getProfile()

            binding?.btnUbah?.setOnClickListener{
                binding?.btnUbahLayout?.visibility = View.GONE
                binding?.btnSimpanBatalLayout?.visibility = View.VISIBLE

                binding?.editNama?.isEnabled = true
                binding?.editEmail?.isEnabled = true
                binding?.editJenisIdentitas?.isEnabled = true
                binding?.editNomorIdentitas?.isEnabled = true
                binding?.editNomorTelp?.isEnabled = true
                binding?.editAlamat?.isEnabled = true

            }

            binding?.btnSave?.setOnClickListener {
                updateProfile()
            }
            binding?.btnCancel?.setOnClickListener {
                binding?.btnUbahLayout?.visibility = View.VISIBLE
                binding?.btnSimpanBatalLayout?.visibility = View.GONE
            }

            binding?.btnUbahPassword?.setOnClickListener {
                val intent = Intent(requireActivity(), UbahPasswordActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun getProfile() {
        val token = sharedPreferences!!.getString("token", null)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getProfile("Bearer $token")
                val data = response.data

                Log.d("Profile", data.toString())

                Toast.makeText(requireActivity(), "Berhasil Ambil Profile", Toast.LENGTH_LONG).show()
                displayData(data)
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayData(data: CustomerData) {
        val nama_customer = binding?.editNama
        val email = binding?.editEmail
        val jenis_identitas = binding?.editJenisIdentitas
        val no_identitas = binding?.editNomorIdentitas
        val no_telp = binding?.editNomorTelp
        val alamat = binding?.editAlamat

        fun EditText.setReadOnlyText(text: String) {
            setText(text)
            isEnabled = false
        }

        nama_customer?.setReadOnlyText(data.nama_customer)
        email?.setReadOnlyText(data.email)
        jenis_identitas?.setReadOnlyText(data.jenis_identitas)
        no_identitas?.setReadOnlyText(data.no_identitas)
        no_telp?.setReadOnlyText(data.no_telp)
        alamat?.setReadOnlyText(data.alamat)
    }

    fun updateProfile() {
        val token = sharedPreferences!!.getString("token", null)
        val nama_customer = binding?.editNama
        val email = binding?.editEmail
        val jenis_identitas = binding?.editJenisIdentitas
        val no_identitas = binding?.editNomorIdentitas
        val no_telp = binding?.editNomorTelp
        val alamat = binding?.editAlamat

        CoroutineScope(Dispatchers.Main).launch {
            try {
                nama_customer?.error = null
                email?.error = null
                jenis_identitas?.error = null
                no_identitas?.error = null
                no_telp?.error = null
                alamat?.error = null

                val response = ApiConfig.getApiService().ubahProfile(
                    token = "Bearer $token",
                    jenis_customer = "P",
                    nama_customer = nama_customer?.text.toString(),
                    no_identitas = no_identitas?.text.toString(),
                    jenis_identitas = jenis_identitas?.text.toString(),
                    no_telp = no_telp?.text.toString(),
                    email = email?.text.toString(),
                    alamat = alamat?.text.toString(),
                    flag_stat = "1"
                )
                val data = response.data

                Log.d("Update Profile", data.toString())
                sharedPreferences!!.edit().putString("nama", data.nama_customer).apply()
                Toast.makeText(requireActivity(), "Berhasil Ubah Profile", Toast.LENGTH_LONG).show()
                displayData(data)
                binding?.btnUbahLayout?.visibility = View.VISIBLE
                binding?.btnSimpanBatalLayout?.visibility = View.GONE
            } catch (e: HttpException) {
                if(e.code() == 400){
                    val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)

                    errorResponse.message?.forEach { (key, value) ->
                        when (key) {
                            "nama_customer" -> {
                                nama_customer?.error = value[0]
                            }
                            "email" -> {
                                email?.error = value[0]
                            }
                            "jenis_identitas" -> {
                                jenis_identitas?.error = value[0]
                            }
                            "no_identitas" -> {
                                no_identitas?.error = value[0]
                            }
                            "no_telp" -> {
                                no_telp?.error = value[0]
                            }
                            "alamat" -> {
                                alamat?.error = value[0]
                            }
                        }
                    }
                }else if(e.code() == 404 || e.code() == 401) {
                    val errorResponse = Gson().fromJson(
                        e.response()?.errorBody()?.charStream(),
                        ApiErrorResponseMessageAsString::class.java
                    )
                    Toast.makeText(
                        requireActivity(),
                        errorResponse.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                }
            } catch(e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}