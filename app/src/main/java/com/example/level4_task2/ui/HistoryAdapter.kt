package com.example.level4_task2.ui


import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.level4_task2.R
import com.example.level4_task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class HistoryAdapter(private val history: List<Game>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            when (game.result){
                "win" -> itemView.tvWinner.text = "You won!"
                "draw" -> itemView.tvWinner.text = "Draw"
                "loss" -> itemView.tvWinner.text = "Computer won!"
            }
            when (game.player){
                "Rock" -> itemView.ivPlayer.setImageResource(R.drawable.rock)
                "Paper" -> itemView.ivPlayer.setImageResource(R.drawable.paper)
                "Scissors" -> itemView.ivPlayer.setImageResource(R.drawable.scissors)
            }
            when (game.computer){
                "Rock" -> itemView.ivComputer.setImageResource(R.drawable.rock)
                "Paper" -> itemView.ivComputer.setImageResource(R.drawable.paper)
                "Scissors" -> itemView.ivComputer.setImageResource(R.drawable.scissors)
            }
            itemView.tvDate.text = game.date.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.databind(history[position])
    }
}