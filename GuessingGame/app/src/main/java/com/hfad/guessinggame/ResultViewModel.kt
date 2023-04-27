package com.hfad.guessinggame

import android.util.Log
import androidx.lifecycle.ViewModel

class ResultViewModel(finalResult: String) : ViewModel() {
    val result = finalResult

    init {
        Log.i("ViewModel", "ResultViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "ResultViewModel cleared")
    }
}