package com.dicoding.asclepius.view.adapter

import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.databinding.HistoryItemBinding
import com.dicoding.asclepius.view.result.ResultActivity
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryListAdapter(private val historyList: List<History>) :
    RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            // Todo: Bind data to view
            if (history.label.trim() == "Non Cancer") {
                binding.tvLabel.setBackgroundResource(R.drawable.custom_label_green)
                binding.tvLabel.text = history.label
            }
            val formattedScore = NumberFormat.getPercentInstance().format(history.score).trim()
            val dateFormat = SimpleDateFormat("EEE, d MMM, yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(history.dateTime)


            binding.apply {
                percetage.progress = (history.score * 100).toInt()
                tvScore.text = formattedScore
                tvDate.text = formattedDate
                savedImg.setImageURI(Uri.parse(history.imgUri))
            }

            val intent = Intent(itemView.context,ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_HISTORY,history)
            binding.root.setOnClickListener {
                itemView.context.startActivity(intent)
            }

            // Todo: add Delete Modal
            binding.btnDelete.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }
}