package com.example.project_p3l_mobile

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_p3l_mobile.adapter.RV_KamarDiPesan
import com.example.project_p3l_mobile.adapter.RV_KetersediaanKamarAdapter
import com.example.project_p3l_mobile.adapter.RV_LayananDiPesan
import com.example.project_p3l_mobile.data_api.model.DetailTrxReservasiData
import com.example.project_p3l_mobile.data_api.model.KetersediaanKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiLayananData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityDetailHistoryBinding
import com.example.project_p3l_mobile.databinding.ActivityDetailKamarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("myKey", android.content.Context.MODE_PRIVATE)
        val id = intent.getIntExtra("id", 0)
        getDetailTrx(id)

        val recyclerView = binding.rvKamarDiPesan
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val recyclerView2 = binding.rvLayananDiPesan
        val layoutManager2 = LinearLayoutManager(this)
        recyclerView2.layoutManager = layoutManager2

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnBatalkanPesanan.setOnClickListener {
            showDialogBatal(id)
        }


    }

    fun postBatalPesanan(id: Int) {
        val token = sharedPreferences!!.getString("token", null)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().postBatalPesanan(id, "Bearer $token")
                val message = response.message

                Log.d("Batal Pesanan", message)

                Toast.makeText(
                    this@DetailHistoryActivity,
                    "Berhasil Membatalkan Pesanan",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this@DetailHistoryActivity, NavigationActivity::class.java)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@DetailHistoryActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getDetailTrx(id: Int) {
        val token = sharedPreferences!!.getString("token", null)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getDetailTransaksi(id, "Bearer $token")
                val data = response.data

                Log.d("DetailHistory", data.toString())

//                Toast.makeText(
//                    this@DetailHistoryActivity,
//                    "Berhasil Menampilkan Detail Transaksi",
//                    Toast.LENGTH_LONG
//                ).show()

                // Tambahkan baris berikut untuk menampilkan atau menyembunyikan tombol
                if (data.status == "Menunggu Pembayaran" || data.status == "Terkonfirmasi") {
                    binding.btnBatalkanPesanan.visibility = View.VISIBLE
                    binding.tvPesanPembatalan.visibility = View.GONE
                    binding.tvPesanPembatalan2.visibility = View.GONE
                    if (data.status == "Terkonfirmasi"){
                        binding.tvPesanPembatalan.visibility = View.VISIBLE
                        binding.tvPesanPembatalan2.visibility = View.VISIBLE
                    }
                } else {
                    binding.btnBatalkanPesanan.visibility = View.GONE
                }

                myTabel1(data)
                myTabel2(data)
                displayRecyclerView(data.trx_kamars!!)
                displayRecyclerView2(data.trx_layanans!!)
            } catch (e: Exception) {
                Toast.makeText(this@DetailHistoryActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun myTabel1(data : DetailTrxReservasiData){
        val tableLayout1 = binding.myTableLayout1

// Contoh data
        val dataIsiTablenPemesan = listOf(
            arrayOf("Nama Pemesan", data.customers?.nama_customer),
            arrayOf("Nomor Identitas", data.customers?.jenis_identitas+'/'+data.customers?.no_identitas),
            arrayOf("Nomor Telepon", data.customers?.no_telp),
            arrayOf("Email", data.customers?.email),
            arrayOf("Alamat", data.customers?.alamat),
        )

// Tambahkan data ke dalam tabel
        for (item in dataIsiTablenPemesan) {
            val newRow = TableRow(this)

            for ((index, value) in item.withIndex()) {
                val newCell = TextView(this)
                val textValue = value.toString()

                newCell.text = textValue
                newCell.textSize = 16F
                if (index == 1) {
                    newCell.setTypeface(null, Typeface.BOLD)
                }
                newRow.addView(newCell)
            }

            tableLayout1.addView(newRow)
        }
    }

    fun myTabel2(data : DetailTrxReservasiData){
        val tableLayout2 = binding.myTableLayout2

// Contoh data
        val dataIsiTablePesanan = listOf(
            arrayOf("ID Booking", data.id_booking),
            arrayOf("Status", data.status),
            arrayOf("Waktu Reservasi", data.waktu_reservasi),
            arrayOf("Jumlah Dewasa", data.jumlah_dewasa),
            arrayOf("Jumlah Anak anak", data.jumlah_anak_anak),
            arrayOf("Waktu Check In", data.waktu_check_in),
            arrayOf("Waktu Check Out", data.waktu_check_out),
            arrayOf("Waktu Pembayaran", data.waktu_pembayaran),
            arrayOf("Total Pembayaran", "Rp " +data.total_harga),
        )

// Tambahkan data ke dalam tabel
        for (item in dataIsiTablePesanan) {
            val newRow = TableRow(this)

            for ((index, value) in item.withIndex()) {
                val newCell = TextView(this)
                val textValue = value.toString()

                newCell.text = textValue
                newCell.textSize = 16F
                if (index == 1) {
                    newCell.setTypeface(null, Typeface.BOLD)
                }
                newRow.addView(newCell)
            }

            tableLayout2.addView(newRow)
        }

    }

    private fun showDialogBatal(id: Int) {
        // Membuat objek AlertDialog
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Konfirmasi Pembatalan")
        builder.setMessage("Apakah Yakin ingin membatalkan transaksi ini?")

        // Menambahkan tombol positif (tombol OK)
        builder.setPositiveButton("OK") { dialog, which ->
            postBatalPesanan(id)
            dialog.dismiss()
        }

        // Menambahkan tombol negatif (tombol Batal)
        builder.setNegativeButton("Batal") { dialog, which ->
//            Toast.makeText(this, "Tombol Batal ditekan", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Membuat dan menampilkan AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun displayRecyclerView(data: List<TransaksiKamarData>) {
        val recyclerView = binding.rvKamarDiPesan
        recyclerView.adapter = RV_KamarDiPesan(data)
    }
    fun displayRecyclerView2(data: List<TransaksiLayananData>) {
        val recyclerView = binding.rvLayananDiPesan
        recyclerView.adapter = RV_LayananDiPesan(data)
    }
}