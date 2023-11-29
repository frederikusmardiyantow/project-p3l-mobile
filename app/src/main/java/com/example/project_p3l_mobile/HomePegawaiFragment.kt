package com.example.project_p3l_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintAttributes.MediaSize
import android.print.PrintManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.project_p3l_mobile.data_api.service.ApiConfig
import com.example.project_p3l_mobile.databinding.FragmentHomePegawaiBinding
import com.example.project_p3l_mobile.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePegawaiFragment : Fragment() {
    var sharedPreferences: SharedPreferences? = null
    private var binding: FragmentHomePegawaiBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePegawaiBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("myKeyPegawai", android.content.Context.MODE_PRIVATE)

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
            Toast.makeText(requireActivity(), "Selamat Datang ${sharedPreferences!!.getString("nama", null)}", Toast.LENGTH_LONG).show()
            binding?.tvNamaUser?.setText(sharedPreferences!!.getString("nama", null))
        } else {
            binding?.tvNamaUser?.visibility = View.GONE
            binding?.btnLogout?.visibility = View.GONE
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

}