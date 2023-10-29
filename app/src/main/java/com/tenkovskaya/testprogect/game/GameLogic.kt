package com.tenkovskaya.testprogect.game

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Path
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.testprogect.ui.StartActivity
import com.tenkovskaya.testprogect.databinding.ActivityMainBinding
import java.util.Random

class GameLogic {
    private var counter = 0
    private var timer: CountDownTimer? = null
    private var isBallMoving = false

    fun startGame(binding: ActivityMainBinding) {
        binding.ballImg.setOnClickListener {
            if (!isBallMoving) {
                startBallMovement(binding)
            }
            counter++
            binding.counterTx.text = counter.toString()
        }

        startTimer(30000, binding)
        runDelayed(30000) {
            goToFinishActivity(binding)
        }
    }

    private fun runDelayed(delayMillis: Long, action: () -> Unit) {
        Handler().postDelayed(action, delayMillis)
    }

    private fun startTimer(time: Long, binding: ActivityMainBinding) {
        timer?.cancel()
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                binding.timerTx.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun goToFinishActivity(binding: ActivityMainBinding) {
        val intent = Intent(binding.root.context, StartActivity::class.java)
        intent.putExtra("counter", counter.toString())
        binding.root.context.startActivity(intent)
        (binding.root.context as AppCompatActivity).finish()
    }

    private fun startBallMovement(binding: ActivityMainBinding) {
        isBallMoving = true
        val screenWidth = binding.root.resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = binding.root.resources.displayMetrics.heightPixels.toFloat()
        val ballWidth = binding.ballImg.width.toFloat()
        val ballHeight = binding.ballImg.height.toFloat()
        val maxX = screenWidth - ballWidth
        val maxY = screenHeight - ballHeight

        val ballStartPositionX = binding.ballImg.x
        val ballStartPositionY = binding.ballImg.y

        val random = Random()
        val destinationX = random.nextFloat() * maxX
        val destinationY = random.nextFloat() * maxY

        val path = Path()
        path.moveTo(ballStartPositionX, ballStartPositionY)
        path.lineTo(destinationX, destinationY)

        val anim = ObjectAnimator.ofFloat(binding.ballImg, View.X, View.Y, path)
        anim.duration = 600
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isBallMoving = false
            }
        })
        anim.start()
    }

}
