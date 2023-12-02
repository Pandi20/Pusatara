package com.example.pusatara_app.view.media

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import com.example.pusatara_app.data.di.MediaRepository
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.FragmentMediaBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MediaFragment : Fragment() {
    private lateinit var binding : FragmentMediaBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private lateinit var userPreferences: UserPreferences
    private lateinit var mediaViewModel: MediaViewModel
    private var token: String? = null
    private var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaBinding.inflate(inflater, container, false)

        val userPreferences = UserPreferences.getInstance(requireContext())

        val activityIntent = requireActivity().intent
        token = activityIntent.getStringExtra("TOKEN")
        lifecycleScope.launch {
            token = userPreferences.getToken().first()
        }
        if (token != null) {
            Log.d("MainActivity", "Received Token: $token")
        } else {
            Log.e("MainActivity", "Token kosong atau tidak ada.")
        }

        recyclerView = binding.rvMedia
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mediaAdapter = MediaAdapter()
        recyclerView.adapter = mediaAdapter


        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddMediaActivity::class.java)
            startActivity(intent)
        }

        val mediaRepository = MediaRepository(ApiConfig.getApiService(), token?: "")
        mediaViewModel = ViewModelProvider(this, MediaViewModelFactory(mediaRepository))[MediaViewModel::class.java]
        mediaViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MediaViewModel::class.java]

        mediaViewModel.mediaList.observe(viewLifecycleOwner) { mediaList ->
            mediaAdapter.submitData(lifecycle, mediaList)
        }

        return binding.root
    }
}