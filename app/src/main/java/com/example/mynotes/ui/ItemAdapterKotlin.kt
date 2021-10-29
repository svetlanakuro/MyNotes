package com.example.mynotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.NoteDataKotlin
import com.example.mynotes.domain.NoteSource
import java.text.DateFormat.getDateInstance
import java.util.*

class ItemAdapterKotlin(private val noteSource: NoteSource) :
    RecyclerView.Adapter<ItemAdapterKotlin.ItemViewHolder>() {
    private var listener: OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(noteSource.getNoteData(position))
    }

    override fun getItemCount(): Int {
        return noteSource.size()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val executed: CheckBox = itemView.findViewById(R.id.executed)
        private val date: TextView = itemView.findViewById(R.id.date)
        fun bind(noteData: NoteDataKotlin) {
            title.text = noteData.title
            description.text = noteData.description
            executed.isChecked = noteData.isExecuted
            date.text = getDateInstance()
                .format(Objects.requireNonNull(noteData.date))
        }

        init {
            title.setOnClickListener {
                listener!!.onItemClick(
                    description,
                    layoutPosition
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}