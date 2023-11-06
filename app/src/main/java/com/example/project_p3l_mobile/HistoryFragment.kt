package com.example.project_p3l_mobile

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_p3l_mobile.adapter.RV_HistoryAdapter
import com.example.project_p3l_mobile.adapter.RV_JenisKamarAdapter
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiReservasiData
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.FragmentHistoryBinding
import com.example.project_p3l_mobile.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    private var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("myKey", android.content.Context.MODE_PRIVATE)
        getTransaksiByIdCustomer()
        val recyclerView = binding?.rvHistory
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView?.layoutManager = layoutManager
    }

    fun getTransaksiByIdCustomer() {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiConfig.getApiService().getTransaksi(
                    token = "Bearer ${sharedPreferences!!.getString("token", null)!!}"
                )
                val data = response.data

                Log.d("Transaksi by id cust", data.toString())

                Toast.makeText(requireActivity(), "Berhasil Menampilkan Data Transaksi", Toast.LENGTH_LONG).show()
                if (data.trx_reservasis == null || data.trx_reservasis.isEmpty()) {
                    Toast.makeText(requireActivity(), "Data Kosong", Toast.LENGTH_LONG).show()
                } else {
                    displayRecyclerView(data.trx_reservasis)
                }
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayRecyclerView(data: List<TransaksiReservasiData>) {
        val recyclerView = binding?.rvHistory
        recyclerView?.adapter = RV_HistoryAdapter(data)
    }

}