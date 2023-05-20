package com.sample.rearrangewords

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sample.rearrangewords.databinding.ItemWordBinding

class AnswerWordsAdapter(
    private val itemSelectedListener: (WordModel) -> Unit,
) : ListAdapter<WordModel, AnswerWordsAdapter.VHAnswerWords>(
    object : DiffUtil.ItemCallback<WordModel>() {
        override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    private val answerWorks: MutableList<WordModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHAnswerWords {
        return VHAnswerWords(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { model ->
            selectedWord(model)
            itemSelectedListener.invoke(model)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectedWord(it: WordModel) {
        val index = answerWorks.indexOf(it)
        answerWorks[index] = it.copy(isSelected = !it.isSelected)
        this.submitList(answerWorks)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(answerWorks: List<WordModel>) {
        this.answerWorks.clear()
        this.answerWorks.addAll(answerWorks)
        this.submitList(answerWorks)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VHAnswerWords, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class VHAnswerWords(
        private val binding: ItemWordBinding,
        private val itemSelectedListener: (WordModel) -> Unit,
    ) : ViewHolder(binding.root) {
        fun bind(model: WordModel) {
            binding.tvWord.text = model.word
            binding.tvWord.visibility = if (!model.isSelected) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            if (model.isSelected) {
                binding.rlRoot.setBackgroundResource(R.color.gray)
            } else {
                binding.rlRoot.setBackgroundResource(R.color.black)
            }

            binding.rlRoot.setOnClickListener {
                if (!model.isSelected) itemSelectedListener.invoke(model)
            }
        }
    }
}
