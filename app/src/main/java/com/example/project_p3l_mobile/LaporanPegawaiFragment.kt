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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.FragmentHomePegawaiBinding
import com.example.project_p3l_mobile.databinding.FragmentLaporanPegawaiBinding
import com.example.project_p3l_mobile.pegawai.Laporan5CustPemesanTerbanyakActivity
import com.example.project_p3l_mobile.pegawai.LaporanCustBaruPerBulanActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LaporanPegawaiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaporanPegawaiFragment : Fragment() {
    private var binding: FragmentLaporanPegawaiBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini menggunakan View Binding
        binding = FragmentLaporanPegawaiBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.cvLaporanCustomerBaruPerBulan?.setOnClickListener {
            val intent = Intent(requireActivity(), LaporanCustBaruPerBulanActivity::class.java)
            startActivity(intent)
        }

        binding?.cvLaporan5CustPemesanTerbanyak?.setOnClickListener {
            val intent = Intent(requireActivity(), Laporan5CustPemesanTerbanyakActivity::class.java)
            startActivity(intent)
        }
    }

    // Pastikan untuk mengatur binding ke null di onDestroyView untuk menghindari kebocoran memori
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
