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
import com.example.pusatara_app.R
import com.example.pusatara_app.databinding.FragmentHomeBinding
import com.example.pusatara_app.view.profile.ProfileActivity

@Suppress("DEPRECATION")
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

        val mainActivity = activity as AppCompatActivity
        val toolbar = mainActivity.supportActionBar?.apply {
            title = "Home"
        }
        setHasOptionsMenu(true)

        binding.fabScan.setOnClickListener {
            val intent = Intent(requireActivity(), UploadScanActivity::class.java)
            startActivity(intent)
        }
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
                // Handle the settings action
                // Example: show a toast
                // Toast.makeText(requireContext(), "Settings clicked", Toast.LENGTH_SHORT).show()
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