package com.example.pusatara_app.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityProfileBinding
import com.example.pusatara_app.view.welcome.WelcomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var logoutButton: Button
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.profile)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(applicationContext)

        logoutButton = binding.logoutButton
        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout_confirmation))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton("OK") { _, _ ->
                    lifecycleScope.launch {
                        userPreferences.clearDataStore()

                        val intent = Intent(this@ProfileActivity, WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton(getString(R.string.logout_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        lifecycleScope.launch {
            val userName = userPreferences.getUsername().first()
            val email = userPreferences.getEmail().first()
            if (!userName.isNullOrBlank()) {
                binding.profileName.text = "$userName"
                binding.profileEmail.text = "$email"
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}