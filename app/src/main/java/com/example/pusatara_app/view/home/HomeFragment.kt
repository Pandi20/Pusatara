package com.example.pusatara_app.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pusatara_app.databinding.FragmentHomeBinding
import com.example.pusatara_app.view.profile.ProfileActivity

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileHome.setOnClickListener {
             val intent = Intent(requireActivity(), ProfileActivity::class.java)
             startActivity(intent)
        }

        binding.fabScan.setOnClickListener {
            val intent = Intent(requireActivity(), UploadScanActivity::class.java)
            startActivity(intent)
        }
    }
}