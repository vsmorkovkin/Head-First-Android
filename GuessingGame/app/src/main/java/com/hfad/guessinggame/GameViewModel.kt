package com.hfad.guessinggame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment")
    private val secretWord = words.random().uppercase()

    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay

    private var correctGuesses = ""

    private val _incorrectGuesses = MutableLiveData("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses

    private val _livesLeft = MutableLiveData(8)
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    private val _gameOver = MutableLiveData(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver


    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
        Log.i("ViewModel", "GameViewModel created")
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) = when(correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = livesLeft.value?.minus(1)
            }
        }
        if (isWon() || isLost()) _gameOver.value = true
    }

    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    private fun isLost() = (livesLeft.value ?: 0) <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += "The word was $secretWord"
        return message
    }

    fun finishGame() {
        _gameOver.value = true
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "GameViewModel cleared")
    }

}