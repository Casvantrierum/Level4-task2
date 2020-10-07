package com.example.level4_task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.level4_task2.R
import com.example.level4_task2.model.Game
import com.example.level4_task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private val possibilities = listOf("Rock", "Paper", "Scissors")

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_PlayFragment_to_HistoryFragment)
//        }

        gameRepository = GameRepository(requireContext())

        view.findViewById<ImageButton>(R.id.ibRock).setOnClickListener {
            play(possibilities[0])
        }
        view.findViewById<ImageButton>(R.id.ibPaper).setOnClickListener {
            play(possibilities[1])
        }
        view.findViewById<ImageButton>(R.id.ibScissors).setOnClickListener {
            play(possibilities[2])
        }
    }

    private fun play(playerHand: String){
        val computerHand = generateRandom()
        val result : String

        if(computerHand == playerHand) result = "draw"
        else if(possibilities.indexOf(computerHand) -1 == possibilities.indexOf(playerHand)) result = "loss"
        else if(possibilities.indexOf(computerHand) == 0 &&  possibilities.indexOf(playerHand) == 2) result = "loss"
        else result = "win"

        Log.i(result , "$computerHand - $playerHand")

        addGame(playerHand, computerHand, result)

        findNavController().navigate(R.id.action_PlayFragment_to_HistoryFragment)
    }

    private fun generateRandom(): String{
        return possibilities.random();
    }

    private fun addGame(playerHand: String, computerHand: String, result: String) {
        mainScope.launch {
            val game = Game(
                player = playerHand,
                computer = computerHand,
                result = result
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }
}