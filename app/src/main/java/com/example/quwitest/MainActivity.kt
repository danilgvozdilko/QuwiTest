package com.example.quwitest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quwitest.ui.authorization.AuthorizationFragment
import com.example.quwitest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authorizationFragment()
    }

    private fun authorizationFragment() {
        val fragment = AuthorizationFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
    }

}