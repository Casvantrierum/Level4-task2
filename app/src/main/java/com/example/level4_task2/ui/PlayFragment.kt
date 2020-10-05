package com.example.level4_task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.NavController
import com.example.level4_task2.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {


    val posibilities = listOf("Rock", "Paper", "Scissors")

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

        view.findViewById<ImageButton>(R.id.ibRock).setOnClickListener {
            play("Rock")
        }
        view.findViewById<ImageButton>(R.id.ibPaper).setOnClickListener {
            play("Paper")
        }
        view.findViewById<ImageButton>(R.id.ibScissors).setOnClickListener {
            play("Scissors")
        }
    }

    private fun play(playerHand: String){
        val computerHand = generateRandom()
        val result : String

        if(computerHand.equals(playerHand)) result = "draw"
        else if(posibilities.indexOf(computerHand) -1 == posibilities.indexOf(playerHand)) result = "loss"
        else if(posibilities.indexOf(computerHand) == 0 &&  posibilities.indexOf(playerHand) == 2) result = "loss"
        else result = "win"

        Log.i(result , "$computerHand - $playerHand")

        //addGame(playerHand, computerHand, result)
    }

    private fun generateRandom(): String{
        return posibilities.random();
    }
}