package com.example.level4_task2.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level4_task2.R
import com.example.level4_task2.model.Game
import com.example.level4_task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val history = arrayListOf<Game>()
    private val historyAdapter = HistoryAdapter(history)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_delete -> {
            removeAllGames()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameRepository = GameRepository(requireContext())

        getHistoryFromDatabase()

        initRv()
    }

    override fun onStart() {
        Log.i("OK", "onstart history fragment")
        super.onStart()
        getHistoryFromDatabase()

        getActivity()?.setTitle(getString(R.string.your_history));
    }

    private fun initRv() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistory.adapter = historyAdapter
        rvHistory.setHasFixedSize(true)
        rvHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getHistoryFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryFragment.history.clear()
            this@HistoryFragment.history.addAll(shoppingList)
            this@HistoryFragment.history.reverse()
            this@HistoryFragment.historyAdapter.notifyDataSetChanged()
        }
    }

    private fun removeAllGames() {
        gameRepository = GameRepository(requireContext())

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getHistoryFromDatabase()
        }
    }
}