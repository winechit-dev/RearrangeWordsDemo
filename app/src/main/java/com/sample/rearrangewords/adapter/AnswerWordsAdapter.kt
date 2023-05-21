package com.sample.rearrangewords.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sample.rearrangewords.R
import com.sample.rearrangewords.model.WordModel
import com.sample.rearrangewords.databinding.ItemWordBinding

class AnswerWordsAdapter(
    private val itemSelectedListener: (WordModel) -> Unit,
) : ListAdapter<WordModel, AnswerWordsAdapter.VHAnswerWords>(
    object : DiffUtil.ItemCallback<WordModel>() {
        override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

        override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHAnswerWords {
        return VHAnswerWords(
            binding = ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemSelectedListener = itemSelectedListener
        )
    }


    fun setData(answerWorks: List<WordModel>) {
        this.submitList(ArrayList(answerWorks))
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
