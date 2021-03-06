package com.example.level4_task2.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.level4_task2.R
import com.example.level4_task2.model.Game
import com.example.level4_task2.repository.GameRepository
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_play.*
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private lateinit var lastGame: Game
    private lateinit var stats: List<Int>

    private val possibilities = listOf("Rock", "Paper", "Scissors")

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_history -> {
            NavHostFragment.findNavController(nav_host_fragment)
                .navigate(R.id.action_PlayFragment_to_HistoryFragment)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onStart() {
        super.onStart()
        getLastGame()
        getStats()
        getActivity()?.setTitle(getString(R.string.app_name));
    }

    private fun play(playerHand: String){
        val computerHand = generateRandom()
        val result : String

        if(computerHand == playerHand) result = "draw"
        else if(possibilities.indexOf(computerHand) -1 == possibilities.indexOf(playerHand)) result = "loss"
        else if(possibilities.indexOf(computerHand) == 0 &&  possibilities.indexOf(playerHand) == 2) result = "loss"
        else result = "win"

        addGame(playerHand, computerHand, result)
    }

    private fun generateRandom(): String{
        return possibilities.random();
    }

    private fun addGame(playerHand: String, computerHand: String, result: String) {
        mainScope.launch {
            val game = Game(
                player = playerHand,
                computer = computerHand,
                result = result,
                date = Date(System.currentTimeMillis())
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
            lastGame = game

            setUI()
            getStats()
        }
    }

    private fun getStats(){
        mainScope.launch {
            stats = withContext(Dispatchers.IO) {
                gameRepository.getStats()
            }
            setStats()
        }
    }

    private fun getLastGame(){
        mainScope.launch {
            val lastGame = withContext(Dispatchers.IO) {
                gameRepository.getLatestGame()
            }
            if (lastGame.isNotEmpty()) {
                this@PlayFragment.lastGame = lastGame.first()
                setUI()
            }
        }
    }

    private fun setStats(){
        tvStatsIntro.text = getString(R.string.stats_intro, stats.sum())

        tvStats.text = getString(R.string.stats, stats.elementAt(0), stats.elementAt(1), stats.elementAt(2))
    }

    private fun setUI() {

            when (lastGame.result){
                "win" -> tvWinner.text = getString(R.string.win)
                "draw" -> tvWinner.text = getString(R.string.draw)
                "loss" -> tvWinner.text = getString(R.string.loss)
            }
            when (lastGame.player){
                possibilities[0] -> ivPlayer.setImageResource(R.drawable.rock)
                possibilities[1] -> ivPlayer.setImageResource(R.drawable.paper)
                possibilities[2] -> ivPlayer.setImageResource(R.drawable.scissors)
            }
            when (lastGame.computer){
                possibilities[0] -> ivComputer.setImageResource(R.drawable.rock)
                possibilities[1] -> ivComputer.setImageResource(R.drawable.paper)
                possibilities[2] -> ivComputer.setImageResource(R.drawable.scissors)
            }

    }
}