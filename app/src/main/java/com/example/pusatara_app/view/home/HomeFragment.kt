package com.example.pusatara_app.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.FragmentHomeBinding
import com.example.pusatara_app.view.profile.ProfileActivity
import com.example.pusatara_app.view.scan.UploadScanActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapterBatik: BatikAdapter
    private lateinit var adapterSongket: SongketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences.getInstance(requireContext())

        val mainActivity = activity as AppCompatActivity
        val toolbar = mainActivity.supportActionBar?.apply {
            title = getString(R.string.bottombar_home)
        }
        setHasOptionsMenu(true)

        adapterBatik = BatikAdapter()
        val layoutManagerBatik = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBatik.layoutManager = layoutManagerBatik
        binding.rvBatik.adapter = adapterBatik

        adapterSongket = SongketAdapter()
        val layoutManagerSongket = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSongket.layoutManager = layoutManagerSongket
        binding.rvSongket.adapter = adapterSongket

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            showToast("Error: $error")
        }

        viewModel.batikPatternItemList.observe(viewLifecycleOwner) { batikPatterns ->
            adapterBatik.submitList(batikPatterns)
        }

        viewModel.songketPatternItemList.observe(viewLifecycleOwner) { songketPatterns ->
            adapterSongket.submitList(songketPatterns)
        }


        binding.fabScan.setOnClickListener {
            val intent = Intent(requireActivity(), UploadScanActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            val userName = userPreferences.getUsername().first()
            if (!userName.isNullOrBlank()) {
                binding.usernameHome.text = "$userName"
            }
            viewModel.getAllBatikPatterns("Bearer $token", 1)
            viewModel.getAllSongketPatterns("Bearer $token", 2)
        }

        binding.navToBatikGlossary.setOnClickListener {
            glossaryFragment()
        }

        binding.navToSongketGlossary.setOnClickListener {
            glossaryFragment()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToGlossaryFragment() {
        findNavController().navigate(R.id.action_home_to_glossary)
    }

    private fun glossaryFragment() {
        navigateToGlossaryFragment()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_btn -> {
                val intent = Intent(requireActivity(), SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.profile_btn -> {
                val intent = Intent(requireActivity(), ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}