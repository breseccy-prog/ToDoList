package com.example.todolist

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FocusTimerActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private var countDownTimer: CountDownTimer? = null
    private var remainingMillis = DEFAULT_DURATION
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_timer)

        tvTimer = findViewById(R.id.tvTimer)
        updateTimerText()

        findViewById<Button>(R.id.btnStartPause).setOnClickListener {
            if (isRunning) pauseTimer() else startTimer()
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener { resetTimer() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(remainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                remainingMillis = 0
                isRunning = false
                findViewById<Button>(R.id.btnStartPause).text = "Mulai"
                updateTimerText()
            }
        }.start()

        isRunning = true
        findViewById<Button>(R.id.btnStartPause).text = "Jeda"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isRunning = false
        findViewById<Button>(R.id.btnStartPause).text = "Lanjut"
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        remainingMillis = DEFAULT_DURATION
        isRunning = false
        findViewById<Button>(R.id.btnStartPause).text = "Mulai"
        updateTimerText()
    }

    private fun updateTimerText() {
        val minutes = (remainingMillis / 1000) / 60
        val seconds = (remainingMillis / 1000) % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val DEFAULT_DURATION = 25 * 60 * 1000L
    }
}
