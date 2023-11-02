package com.example.api_auth_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.api_auth_sample.api.APICall
import com.example.api_auth_sample.ui.Factor
import com.example.api_auth_sample.util.UiUtil
import com.fasterxml.jackson.databind.JsonNode

class MainActivity : AppCompatActivity() {

    private lateinit var signInLoader: ProgressBar
    private lateinit var retrySiginButton: Button
    lateinit var layout: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        initializeComponents();

        // hide the action bar in the initial screen
        UiUtil.hideActionBar(supportActionBar!!)

        retrySignInButtonOnClick()
    }

    override fun onStart() {
        super.onStart()

        // set on-click listener
        APICall.authorize(
            applicationContext,
            ::whenAuthentication,
            ::finallyAuthentication,
            ::onAuthenticationSuccess,
            ::onAuthenticationFail
        )
    }

    override fun onRestart() {
        super.onRestart()

        retrySiginButton.visibility = View.VISIBLE;
    }

    private fun initializeComponents() {
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout)
        retrySiginButton = findViewById(R.id.retrySigin)
        signInLoader = findViewById(R.id.signinLoader)
    }

    private fun retrySignInButtonOnClick() {
        retrySiginButton.setOnClickListener {
            APICall.authorize(
                applicationContext,
                ::whenAuthentication,
                ::finallyAuthentication,
                ::onAuthenticationSuccess,
                ::onAuthenticationFail
            );
        }
    }

    private fun onAuthenticationSuccess(authorizeObj: JsonNode) {
        val intent = Intent(this@MainActivity, Factor::class.java);
        intent.putExtra(
            "flowId",
            authorizeObj["flowId"].toString()
        );
        intent.putExtra(
            "step",
            authorizeObj["nextStep"].toString()
        );
        startActivity(intent)
    }

    private fun onAuthenticationFail() {
        UiUtil.showSnackBar(layout, "Sign in Failure");
        runOnUiThread {
            retrySiginButton.visibility = View.VISIBLE;
        }
    }

    private fun whenAuthentication() {
        runOnUiThread {
            signInLoader.visibility = View.VISIBLE;
        }
    }

    private fun finallyAuthentication() {
        runOnUiThread {
            signInLoader.visibility = View.INVISIBLE;
        }
    }
}