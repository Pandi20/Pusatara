package com.example.pusatara_app.view.glossary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.pusatara_app.databinding.FragmentGlossaryBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GlossaryFragment : Fragment() {
    private lateinit var binding: FragmentGlossaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlossaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = binding.glossaryViewPager
        val tabLayout: TabLayout = binding.glossaryTabLayout

        val fragments = listOf(BatikFragment(), SongketFragment())
        val adapter = GlossaryPagerAdapter(requireActivity(), fragments)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Batik"
                1 -> tab.text = "Songket"
            }
        }.attach()
    }
}