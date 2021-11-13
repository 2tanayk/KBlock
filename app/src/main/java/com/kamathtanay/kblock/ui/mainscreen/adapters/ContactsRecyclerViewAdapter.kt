package com.kamathtanay.kblock.ui.mainscreen.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.databinding.ContactItemBinding
import com.kamathtanay.kblock.model.ContactItem
import com.kamathtanay.kblock.util.diffutil.ContactDiffUtil

class ContactsRecyclerViewAdapter(val listener: OnItemClickListener) :
    ListAdapter<ContactItem, ContactsRecyclerViewAdapter.ContactViewHolder>(ContactDiffUtil()) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onBlockUnblockClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    }


    inner class ContactViewHolder(private val b: ContactItemBinding) :
        RecyclerView.ViewHolder(b.root), View.OnClickListener {

        init {
            b.root.setOnClickListener(this)

            b.blockUnblockBtn.setOnClickListener {
                Log.e("Block Btn", "clicked!")
            }
        }

        override fun onClick(view: View?) {
            Log.e("ContactViewHolder", "clicked!")
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}