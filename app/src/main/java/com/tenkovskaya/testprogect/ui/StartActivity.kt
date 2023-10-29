package com.tenkovskaya.testprogect.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.testprogect.R
import com.tenkovskaya.testprogect.databinding.ActivityStartBinding
import com.tenkovskaya.testprogect.game.AlertDialogHelper

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    private val alertDialogHelper by lazy { AlertDialogHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.rulesBt.setOnClickListener {
            showRulesDialog()
        }
    }

    private fun showRulesDialog() {
        val dialogText = getString(R.string.rules)
        alertDialogHelper.showAlertDialog("Rules", dialogText)
    }
}
