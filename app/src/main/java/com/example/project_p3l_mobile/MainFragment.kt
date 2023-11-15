package com.example.project_p3l_mobile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.project_p3l_mobile.adapter.RV_JenisKamarAdapter
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.FragmentMainBinding
import com.example.project_p3l_mobile.utils.Utils
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class MainFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    private var binding: FragmentMainBinding? = null
    private val viewModel = MyViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("myKey", android.content.Context.MODE_PRIVATE)
        getJenisKamar()

        binding?.btnLoginInHomePage?.setOnClickListener{
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding?.btnLogout?.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("Konfirmasi Logout")
                .setMessage("Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, which ->
                    logout()
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.clear()
                    editor.apply()
                    Toast.makeText(requireActivity(), "Berhasil Logout", Toast.LENGTH_LONG).show()
                    val intent = Intent(requireActivity(), NavigationActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Batal") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        if(sharedPreferences!!.getString("token", null) != null) {
            binding?.btnLoginInHomePage?.visibility = View.GONE
            binding?.tvNamaUser?.visibility = View.VISIBLE
            binding?.btnLogout?.visibility = View.VISIBLE
//            Toast.makeText(requireActivity(), "Selamat Datang ${sharedPreferences!!.getString("nama", null)}", Toast.LENGTH_LONG).show()
            binding?.tvNamaUser?.setText(sharedPreferences!!.getString("nama", null))
        } else {
            binding?.tvNamaUser?.visibility = View.GONE
            binding?.btnLogout?.visibility = View.GONE
            binding?.btnLoginInHomePage?.visibility = View.VISIBLE
        }

        binding?.editTextTanggal?.setOnClickListener {
            showDatePicker()
        }

        binding?.btnCariKetersediaan?.setOnClickListener {
            val intent = Intent(requireActivity(), KetersediaanKamarActivity::class.java)
            val jumlahDewasa = binding?.editTextJumlahDewasa?.text.toString()
            val jumlahAnak = binding?.editTextJumlahAnak?.text.toString()
            val jumlahKamar = binding?.editTextJumlahKamar?.text.toString()

            val cekInCek = if (viewModel.tglCheckIn == 0L || viewModel.tglCheckOut == 0L) {
                0
            } else {
                1
            }
            val jumlahDewasaCek = try {
                binding?.editTextJumlahDewasa?.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            val jumlahAnakCek = try {
                binding?.editTextJumlahAnak?.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            val jumlahKamarCek = try {
                binding?.editTextJumlahKamar?.text.toString().toInt()
            } catch (e: Exception) {
                0
            }

            binding?.editTextTanggal?.error = null
            binding?.editTextJumlahDewasa?.error = null
            binding?.editTextJumlahKamar?.error = null
            if(jumlahDewasaCek == 0 || cekInCek == 0 || jumlahKamarCek == 0) {
                if(jumlahDewasaCek == 0 && cekInCek == 0 && jumlahKamarCek == 0) {
                    binding?.editTextJumlahDewasa?.error = "Jumlah Dewasa tidak boleh kosong"
                    binding?.editTextTanggal?.error = "Tanggal tidak boleh kosong"
                    binding?.editTextJumlahKamar?.error = " Jumlah Kamartidak boleh kosong"
                }else if (jumlahDewasaCek == 0 && cekInCek == 0) {
                    binding?.editTextJumlahDewasa?.error = "Jumlah Dewasa tidak boleh kosong"
                    binding?.editTextTanggal?.error = "Tanggal tidak boleh kosong"
                }else if (jumlahDewasaCek == 0 && jumlahKamarCek == 0) {
                    binding?.editTextJumlahDewasa?.error = "Jumlah Dewasa tidak boleh kosong"
                    binding?.editTextJumlahKamar?.error = "Jumlah Kamar tidak boleh kosong"
                }else if (cekInCek == 0 && jumlahKamarCek == 0) {
                    binding?.editTextTanggal?.error = "Tanggal tidak boleh kosong"
                    binding?.editTextJumlahKamar?.error = "Jumlah Kamartidak boleh kosong"
                }else if (jumlahDewasaCek == 0) {
                    binding?.editTextJumlahDewasa?.error = "Jumlah Dewasa tidak boleh kosong"
                }else if (cekInCek == 0) {
                    binding?.editTextTanggal?.error = "Tanggal tidak boleh kosong"
                }else if (jumlahKamarCek == 0) {
                    binding?.editTextJumlahKamar?.error = "Jumlah Kamar tidak boleh kosong"
                }
            }else{
                intent.putExtra("tglCheckIn", viewModel.tglCheckIn)
                intent.putExtra("tglCheckOut", viewModel.tglCheckOut)
                intent.putExtra("jumlahDewasa", jumlahDewasa)
                intent.putExtra("jumlahAnak", jumlahAnak)
                intent.putExtra("jumlahKamar", jumlahKamar)
                startActivity(intent)
            }
        }

    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Check In - Check Out")
            .setSelection(
                androidx.core.util.Pair(viewModel.tglCheckIn, viewModel.tglCheckOut)
            )
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(MaterialDatePicker.todayInUtcMilliseconds())
                    .setEnd(MaterialDatePicker.todayInUtcMilliseconds() + 31536000000)
                    .build()
            )
            .build()

        datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            val startDate = Date(it.first ?: 0)
            val endDate = Date(it.second ?: 0)
            val startDateString = Utils.formatDate(startDate, Utils.DF_DATE_SHORT)
            val endDateString = Utils.formatDate(endDate, Utils.DF_DATE_SHORT)
            binding?.editTextTanggal?.setText("$startDateString - $endDateString")
            viewModel.tglCheckIn = it.first ?: 0
            viewModel.tglCheckOut = it.second ?: 0
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().logout( "Bearer ${sharedPreferences!!.getString("token", null)}")
                val data = response.message

                Log.d("Logout", data)

                Toast.makeText(requireActivity(), data, Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.fragment_main)
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getJenisKamar() {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getJenisKamar()
                val data = response.data

                Log.d("Jenis Kamar", data.toString())

//                Toast.makeText(requireActivity(), "Berhasil Menampilkan Kamar", Toast.LENGTH_LONG).show()
                displayRecyclerView(data)
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayRecyclerView(data: List<JenisKamarData>) {
        val recyclerView = binding?.rvJenisKamar
        recyclerView?.adapter = RV_JenisKamarAdapter(data)
    }
}