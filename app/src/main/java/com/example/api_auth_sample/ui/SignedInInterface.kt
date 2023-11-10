package com.example.api_auth_sample.ui

import CardAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api_auth_sample.MainActivity
import com.example.api_auth_sample.R
import com.example.api_auth_sample.api.DataSource
import com.example.api_auth_sample.databinding.ActivitySignedInInterfaceBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SignedInInterface : AppCompatActivity() {

    private lateinit var binding: ActivitySignedInInterfaceBinding
    private lateinit var signoutButton: ImageButton
    private lateinit var doctorsRecyclerView: RecyclerView
    private lateinit var pharmacysRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeComponents()

        setDoctorsCardAdapter()
        setPharmacysCardAdapter()

        signoutButton.setOnClickListener{
            GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut()
            val intent = Intent(this@SignedInInterface, MainActivity::class.java);
            startActivity(intent)
        }
    }

    private fun initializeComponents() {
        binding = ActivitySignedInInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signoutButton = findViewById(R.id.signOutButton);
        doctorsRecyclerView = findViewById(R.id.doctorsRecyledView);
        pharmacysRecyclerView = findViewById(R.id.pharmacysRecylerView);
    }

    private fun setDoctorsCardAdapter() {
        val cardAdapter = CardAdapter(DataSource.getDummyDoctors())
        doctorsRecyclerView.layoutManager = LinearLayoutManager(this)
        doctorsRecyclerView.adapter = cardAdapter
    }

    private fun setPharmacysCardAdapter() {
        val cardAdapter = CardAdapter(DataSource.getDummyPharmacies())
        pharmacysRecyclerView.layoutManager = LinearLayoutManager(this)
        pharmacysRecyclerView.adapter = cardAdapter
    }
}