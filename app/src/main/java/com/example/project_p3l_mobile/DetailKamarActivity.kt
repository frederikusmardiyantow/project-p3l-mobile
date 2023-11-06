package com.example.project_p3l_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityDetailKamarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailKamarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailKamarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKamarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        getDataJenisKamarById(id)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    fun getDataJenisKamarById(id: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getJenisKamarById(id)
                val data = response.data

                Log.d("Detail Jenis Kamar", data.toString())

                Toast.makeText(
                    this@DetailKamarActivity,
                    "Berhasil Menampilkan Detail Kamar",
                    Toast.LENGTH_LONG
                ).show()
                displayDetail(data)
            } catch (e: Exception) {
                Toast.makeText(this@DetailKamarActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayDetail(data: JenisKamarData) {
        binding.tvJudulKamar.setText(data.jenis_kamar)
        Glide.with(this)
            .load(data.gambar)
            .into(binding.imageKamar)
        Glide.with(this)
            .load(data.gambar)
            .into(binding.imageKamar2)
        binding.tvKapasitas.setText( "${data.kapasitas.toString()} Orang")
        binding.tvUkuran.setText("${data.ukuran_kamar.toString()} m²")
        binding.tvFasilitasKamar.setText(data.fasilitas_kamar)
        binding.tvRincianKamar.setText(data.deskripsi)
        binding.tvPenjelasanKamar.setText(
            "Kamar ${data.jenis_kamar} seluas ${data.ukuran_kamar} meter persegi yang baru direnovasi " +
                    "didesain ulang dengan indah dan didekorasi dengan perabotan penuh gaya. Semua kamar " +
                    "memiliki balkon ekstra luas dengan pemandangan kolam renang yang dikelilingi oleh " +
                    "Taman Kerajaan yang rimbun, dan situs warisan Istana Kerajaan. Fitur khusus lainnya " +
                    "termasuk akses Internet nirkabel gratis, Smart LED TV, fasilitas kopi dan teh, brankas " +
                    "dalam kamar seukuran laptop, dan tempat tidur premium dengan kasur dengan bantalan ekstra lembut."
        )
    }
}