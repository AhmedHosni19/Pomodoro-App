package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.countdowntimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel:PomodoroTimerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener{
            viewModel.startTimer()
        }
        binding.resetTv.setOnClickListener{
            viewModel.resetTimer()
        }
        viewModel.remainingTime.observe(this){remainingTime->
            val minute =remainingTime.div(1000).div(60)
            val second =remainingTime.div(1000)%60
            val formatedTime=String.format("%02d:%02d",minute,second)
            binding.timerTv.text=formatedTime
        }
        viewModel.isTimeRunning.observe(this){isRunning->
            binding.startBtn.isEnabled=!isRunning

        }
        viewModel.titleText.observe(this){title->
            binding.titleTv.text=title

        }
        viewModel.progress.observe(this){progress->
            binding.progressBar.progress=(progress*100).toInt()

        }
        if (savedInstanceState!=null){
            val savedTime=savedInstanceState.getLong("remainingTime")
            if (savedTime.toInt() != 25 * 60 * 1000){
                viewModel.startTimer()
            }
        }
   }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("remainingTime",viewModel.remainingTime.value!!)
    }

}

