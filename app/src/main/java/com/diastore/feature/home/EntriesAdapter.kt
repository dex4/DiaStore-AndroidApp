package com.diastore.feature.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.diastore.model.Entry

typealias EntryClickListener = ((Entry) -> Unit)
typealias DeleteEntryClickListener = ((Entry) -> Unit)

class EntryAdapter : ListAdapter<Entry, EntryViewHolder>(EntriesDiffUtilCallback) {

    private var entryClickListener: EntryClickListener? = null
    private var deleteEntryClickListener: DeleteEntryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder =
        EntryViewHolder(parent, entryClickListener, deleteEntryClickListener)

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnEntryClickListener(entryClickListener: EntryClickListener) {
        this.entryClickListener = entryClickListener
    }

    fun setOnDeleteEntryClickListener(deleteEntryClickListener: DeleteEntryClickListener) {
        this.deleteEntryClickListener = deleteEntryClickListener
    }

    companion object {
        object EntriesDiffUtilCallback : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean = oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean = oldItem === newItem
        }
    }
}