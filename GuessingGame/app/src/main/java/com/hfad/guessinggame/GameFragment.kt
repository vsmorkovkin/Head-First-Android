package com.hfad.guessinggame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel                   // viewModel property

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        // set the viewModel property
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // use live data to update values on screen
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
        })

        viewModel.livesLeft.observe(viewLifecycleOwner) { newValue ->
            binding.lives.text = "You have $newValue lives left"
        }

        viewModel.secretWordDisplay.observe(viewLifecycleOwner) { newValue ->
            binding.word.text = newValue
        }

        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        Log.i("ViewModel", "GF onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ViewModel", "GF onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ViewModel", "GF onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i("ViewModel", "GF onDestroyView")
    }

}