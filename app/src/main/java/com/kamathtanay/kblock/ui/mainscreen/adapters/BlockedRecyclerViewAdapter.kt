package com.kamathtanay.kblock.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.databinding.ContactItemBinding
import com.kamathtanay.kblock.model.ContactItem
import com.kamathtanay.kblock.util.diffutil.ContactDiffUtil

class BlockedRecyclerViewAdapter(val listener: OnItemClickListener) :
        ListAdapter<ContactItem, BlockedRecyclerViewAdapter.BlockedContactViewHolder>(ContactDiffUtil()) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onBlockUnblockClick(position: Int, contactItem: ContactItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedContactViewHolder {
        return BlockedContactViewHolder(ContactItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BlockedContactViewHolder, position: Int) {
        val contact = getItem(position)
        with(contact) {
            with(holder.b) {
                nameTextView.text = contactName
                numberTextView.text = contactNumber
                blockUnblockBtn.setImageResource(iconId)
                blockUnblockLabel.text = if (iconId == R.drawable.ic_baseline_block_24) {
                    blockUnblockLabel.setTextColor(ContextCompat.getColor(blockUnblockLabel.context, R.color.red_orange))
                    "Unblock"
                } else {
                    blockUnblockLabel.setTextColor(ContextCompat.getColor(blockUnblockLabel.context, R.color.prussian_blue))
                    "Block"
                }
            }
        }
    }

    inner class BlockedContactViewHolder(val b: ContactItemBinding) :
        RecyclerView.ViewHolder(b.root), View.OnClickListener {

        init {
            b.root.setOnClickListener(this)
            b.blockUnblockBtn.setOnClickListener {
                val position: Int = adapterPosition
                val contactData = getItem(position)
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBlockUnblockClick(position, contactData)
                }
            }
        }

        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}