package com.example.project_p3l_mobile.pegawai

import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.DetailLaporan5Cust
import com.example.project_p3l_mobile.data_api.model.DetailLaporanCustBaru
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.ActivityLaporan5CustPemesanTerbanyakBinding
import com.example.project_p3l_mobile.databinding.ActivityLaporanCustBaruPerBulanBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Laporan5CustPemesanTerbanyakActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private lateinit var binding : ActivityLaporan5CustPemesanTerbanyakBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporan5CustPemesanTerbanyakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences("myKey", android.content.Context.MODE_PRIVATE)

        tableLayout = binding.tableLayout5Cust

        // Merujuk ke Spinner
        val spinner: Spinner = binding.spinner
        // Mendefinisikan array item untuk Spinner
        val items = arrayOf("2023", "2022", "2021")
        // Membuat ArrayAdapter menggunakan array string dan tata letak Spinner default
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        // Menentukan tata letak yang akan digunakan saat daftar pilihan muncul
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Menerapkan adapter ke Spinner
        spinner.adapter = adapter
        // Secara opsional, atur listener untuk menanggapi peristiwa pemilihan item
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Panggil fungsi getLaporanCustomerBaru dengan item yang dipilih sebagai parameter
                val selectedItem = spinner.selectedItem.toString()
                getLaporan5Customer(selectedItem)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                getLaporan5Customer("2023")
            }
        })
    }

    private fun displayDataInTable(dataList: List<DetailLaporan5Cust>) {
        // Membersihkan tampilan tabel sebelum menambahkan data baru
        tableLayout.removeAllViews()

        // Baris Header
        val headerRow = TableRow(this)

        val headerNoTextView = TextView(this)
        headerNoTextView.text = "Nama Customer"
        headerNoTextView.setPadding(8, 8, 8, 8)
        headerNoTextView.width = 350
        headerNoTextView.setTypeface(null, Typeface.BOLD)
        headerNoTextView.textSize = 16F

        val headerBulanTextView = TextView(this)
        headerBulanTextView.text = "Jumlah Reservasi"
        headerBulanTextView.setPadding(8, 8, 8, 8)
        headerBulanTextView.width = 210
        headerBulanTextView.setTypeface(null, Typeface.BOLD)
        headerBulanTextView.textSize = 16F

        val headerJumlahTextView = TextView(this)
        headerJumlahTextView.text = "Total Pembayaran"
        headerJumlahTextView.setPadding(8, 8, 8, 8)
        headerJumlahTextView.width = 350
        headerJumlahTextView.setTypeface(null, Typeface.BOLD)
        headerJumlahTextView.textSize = 16F

        headerRow.addView(headerNoTextView)
        headerRow.addView(headerBulanTextView)
        headerRow.addView(headerJumlahTextView)

        tableLayout.addView(headerRow)

        // Baris Data
        for (data in dataList) {
            val tableRow = TableRow(this)

            val noTextView = TextView(this)
            noTextView.text = data.nama_customer
            noTextView.setPadding(8, 8, 8, 8)

            val bulanTextView = TextView(this)
            bulanTextView.text = data.jumlah_reservasi.toString()
            bulanTextView.setPadding(8, 8, 8, 8)

            val jumlahTextView = TextView(this)
            jumlahTextView.text = data.total_harga
            jumlahTextView.setPadding(8, 8, 8, 8)

            tableRow.addView(noTextView)
            tableRow.addView(bulanTextView)
            tableRow.addView(jumlahTextView)

            tableLayout.addView(tableRow)
        }
    }


    fun getLaporan5Customer(tahun: String) {
        val token = sharedPreferences!!.getString("token", null)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getLaporan5CustomerPemesanTerbanyak(tahun, "Bearer $token")
                val data = response.data;

//                Toast.makeText(requireActivity(), "Berhasil Menampilkan Kamar", Toast.LENGTH_LONG).show()
                displayDataInTable(data)
            } catch (e: Exception) {
                Toast.makeText(this@Laporan5CustPemesanTerbanyakActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}