package com.tenkovskaya.testprogect.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.testprogect.databinding.ActivityMainBinding
import com.tenkovskaya.testprogect.game.GameLogic

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val gameLogic = GameLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameLogic.startGame(binding)
    }
}

