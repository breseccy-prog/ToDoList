package com.example.todolist

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FocusActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStartTimer: Button
    private lateinit var btnResetTimer: Button
    private lateinit var btnKembaliFocus: Button

    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 25 * 60 * 1000
    private var timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus)

        tvTimer = findViewById(R.id.tvTimer)
        btnStartTimer = findViewById(R.id.btnStartTimer)
        btnResetTimer = findViewById(R.id.btnResetTimer)
        btnKembaliFocus = findViewById(R.id.btnKembaliFocus)

        updateTimerText()

        btnStartTimer.setOnClickListener {
            if (timerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        btnResetTimer.setOnClickListener {
            resetTimer()
        }

        btnKembaliFocus.setOnClickListener {
            finish()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                timerRunning = false
                btnStartTimer.text = "Mulai"
                Toast.makeText(this@FocusActivity, "Sesi fokus selesai!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        timerRunning = true
        btnStartTimer.text = "Pause"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        timerRunning = false
        btnStartTimer.text = "Mulai"
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        timeLeftInMillis = 25 * 60 * 1000
        timerRunning = false
        btnStartTimer.text = "Mulai"
        updateTimerText()
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }
}