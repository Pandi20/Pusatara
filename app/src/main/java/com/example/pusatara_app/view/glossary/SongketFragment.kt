package com.example.pusatara_app.view.glossary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.FragmentSongketBinding
import com.example.pusatara_app.view.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SongketFragment : Fragment() {
    private lateinit var binding: FragmentSongketBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: GlossarySongketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        adapter = GlossarySongketAdapter()
        binding.rvSongketGlossary.adapter = adapter
        binding.rvSongketGlossary.layoutManager = LinearLayoutManager(requireContext())

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            showToast("Error: $error")
        }

        viewModel.songketPatternItemList.observe(viewLifecycleOwner) { songketPatterns ->
            adapter.submitList(songketPatterns)
        }

        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            viewModel.getAllSongketPatterns("Bearer $token", 2)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}