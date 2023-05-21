package com.sample.rearrangewords.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sample.rearrangewords.model.WordModel
import com.sample.rearrangewords.databinding.ItemWordBinding

class RearrangeWordsAdapter(
    private val itemSelectedListener: (WordModel) -> Unit,
) : ListAdapter<WordModel, RearrangeWordsAdapter.VHRearrangeWords>(
    object : DiffUtil.ItemCallback<WordModel>() {
        override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    private val rearrangeWords: MutableList<WordModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHRearrangeWords {
        return VHRearrangeWords(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            removeWord(it)
            itemSelectedListener.invoke(it.copy(isSelected = !it.isSelected))
        }
    }

    private fun removeWord(it: WordModel) {
        rearrangeWords.remove(it)
        this.submitList(ArrayList(rearrangeWords))
    }

    fun setData(rearrangeWord: WordModel) {
        this.rearrangeWords.add(rearrangeWord)
        this.submitList(ArrayList(rearrangeWords))
    }

    override fun onBindViewHolder(holder: VHRearrangeWords, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class VHRearrangeWords(
        private val binding: ItemWordBinding,
        private val itemSelectedListener: (WordModel) -> Unit,
    ) : ViewHolder(binding.root) {
        fun bind(model: WordModel) {
            binding.tvWord.text = model.word

            binding.rlRoot.setOnClickListener {
                itemSelectedListener.invoke(model)
            }
        }
    }
}
