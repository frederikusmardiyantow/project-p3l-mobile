package com.example.project_p3l_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_p3l_mobile.adapter.RV_JenisKamarAdapter
import com.example.project_p3l_mobile.adapter.RV_KetersediaanKamarAdapter
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponse
import com.example.project_p3l_mobile.data_api.model.ApiErrorResponseMessageAsString
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.model.KetersediaanKamarData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityKetersediaanKamarBinding
import com.example.project_p3l_mobile.databinding.ActivityLoginBinding
import com.example.project_p3l_mobile.databinding.FragmentMainBinding
import com.example.project_p3l_mobile.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class KetersediaanKamarActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    private var binding: ActivityKetersediaanKamarBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKetersediaanKamarBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE)

        // Mendapatkan intent yang memulai aktivitas ini
        val intent = intent

//        // Mendapatkan data yang dikirimkan dari intent
//        val tglCheckIn = intent.getStringExtra("tglCheckIn")
//        val tglCheckOut = intent.getStringExtra("tglCheckOut")
//        val jumlahDewasa = intent.getStringExtra("jumlahDewasa")
//        val jumlahAnak = intent.getStringExtra("jumlahAnak")
//        val jumlahKamar = intent.getStringExtra("jumlahKamar")

        getKetersediaanKamar()
        val recyclerView = binding?.rvKetersediaanKamar
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
    }

    fun getKetersediaanKamar() {
        val formatter = SimpleDateFormat("${Utils.DF_YMD} HH:mm:ss", Locale.getDefault())
        val tglCheckInDate = Date(intent.getLongExtra("tglCheckIn", System.currentTimeMillis()))
        val tglCheckOutDate = Date(intent.getLongExtra("tglCheckOut", System.currentTimeMillis() + (24 * 60 * 60 * 1000)))

        // Menambahkan waktu 14:00:00
        val calendarCI = Calendar.getInstance()
        calendarCI.time = tglCheckInDate
        calendarCI.set(Calendar.HOUR_OF_DAY, 14)
        calendarCI.set(Calendar.MINUTE, 0)
        calendarCI.set(Calendar.SECOND, 0)

        val calendarCO = Calendar.getInstance()
        calendarCO.time = tglCheckOutDate
        calendarCO.set(Calendar.HOUR_OF_DAY, 12)
        calendarCO.set(Calendar.MINUTE, 0)
        calendarCO.set(Calendar.SECOND, 0)

        val tglCheckInFormatted = formatter.format(calendarCI.time)
        val tglCheckOutFormatted = formatter.format(calendarCO.time)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().postKetersediaanKamar(
                    tgl_check_in = tglCheckInFormatted,
                    tgl_check_out = tglCheckOutFormatted,
                    jumlah_dewasa = intent.getIntExtra("jumlahDewasa", 2),
                    jumlah_anak = intent.getIntExtra("jumlahAnak", 0),
                    jumlah_kamar = intent.getIntExtra("jumlahKamar", 1),
                )
                val data = response.data

                Log.d("KetersediaanKamarActivity", data.toString())
                displayRecyclerView(data)

                Toast.makeText(this@KetersediaanKamarActivity, response.message, Toast.LENGTH_LONG).show()
            }catch(e: Exception) {
                Toast.makeText(this@KetersediaanKamarActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayRecyclerView(data: List<KetersediaanKamarData>) {
        val recyclerView = binding?.rvKetersediaanKamar
        recyclerView?.adapter = RV_KetersediaanKamarAdapter(data)
    }

}