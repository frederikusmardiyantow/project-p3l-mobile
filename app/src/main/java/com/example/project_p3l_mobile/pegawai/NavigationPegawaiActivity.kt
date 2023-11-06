package com.example.project_p3l_mobile.pegawai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.databinding.ActivityNavigationBinding
import com.example.project_p3l_mobile.databinding.ActivityNavigationPegawaiBinding

class NavigationPegawaiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNavigationPegawaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_pegawai)
        binding.navView.setupWithNavController(navController)
    }
}