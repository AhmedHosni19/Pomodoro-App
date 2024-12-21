package com.example.countdowntimer


import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PomodoroTimerViewModel: ViewModel() {

    private val _remainingTime=MutableLiveData<Long>(25*60*1000)
    var remainingTime:LiveData<Long> =_remainingTime

    private val _isTimeRunning=MutableLiveData<Boolean>(false)
    val isTimeRunning:LiveData<Boolean> =_isTimeRunning

    private val _titleText=MutableLiveData<String>("Take Pomodoro")
    val titleText:LiveData<String> =_titleText

    private val _progress=MutableLiveData<Float>(1.0f)
    val progress:LiveData<Float> =_progress

    private var timer: CountDownTimer?=null

     fun startTimer(){
        if(!isTimeRunning.value!!){
            timer?.cancel()
            timer=object :CountDownTimer(_remainingTime.value!!,1000){
                override fun onTick(millisUntilFinished: Long) {
                    _remainingTime.value=millisUntilFinished
                    _progress.value=millisUntilFinished.toFloat()/(25*60*1000).toFloat()
                }

                override fun onFinish() {
                    _isTimeRunning.value=false
                    _titleText.value="Finished"

                }
            }.start()
            _isTimeRunning.value=true
            _titleText.value="Keep Going"

        }
    }
    fun resetTimer(){
        timer?.cancel()
        _remainingTime.value=25*60*1000
        _progress.value=1.0f
        _isTimeRunning.value=false
        _titleText.value="Take Pomodoro"
    }

}