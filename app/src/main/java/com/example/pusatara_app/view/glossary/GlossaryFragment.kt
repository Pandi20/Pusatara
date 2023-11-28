package com.example.pusatara_app.view.glossary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pusatara_app.databinding.FragmentGlossaryBinding

class GlossaryFragment : Fragment() {
    private lateinit var binding: FragmentGlossaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlossaryBinding.inflate(inflater, container, false)
        return binding.root
    }

}