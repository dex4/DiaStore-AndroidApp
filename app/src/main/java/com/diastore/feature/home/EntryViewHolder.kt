package com.diastore.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.diastore.EntryBinding
import com.diastore.R
import com.diastore.model.Entry

class EntryViewHolder(
    private val parent: ViewGroup,
    private val entryClickListener: EntryClickListener?,
    private val deleteEntryClickListener: DeleteEntryClickListener?,
    private val binding: EntryBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.item_entry,
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(entry: Entry) {
        binding.entry = entry
        binding.root.setOnClickListener {
            entryClickListener?.invoke(entry)
        }
        binding.buttonClose.setOnClickListener {
            deleteEntryClickListener?.invoke(entry)
        }
    }
}