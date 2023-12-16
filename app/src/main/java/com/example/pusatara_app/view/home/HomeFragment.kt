package com.example.pusatara_app.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

        binding.fabScan.setOnClickListener {
            val intent = Intent(requireActivity(), UploadScanActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val userName = userPreferences.getUsername().first()
            if (!userName.isNullOrBlank()) {
                binding.usernameHome.text = "$userName"
            }
        }

        binding.navToBatikGlossary.setOnClickListener {
            glossaryFragment()
        }

        binding.navToSongketGlossary.setOnClickListener {
            glossaryFragment()
        }
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