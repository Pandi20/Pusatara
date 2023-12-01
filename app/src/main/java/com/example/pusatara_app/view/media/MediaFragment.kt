package com.example.pusatara_app.view.media

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pusatara_app.databinding.FragmentMediaBinding

class MediaFragment : Fragment() {
    private lateinit var binding : FragmentMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaBinding.inflate(inflater, container, false)


        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddMediaActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}