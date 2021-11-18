package com.kamathtanay.kblock.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kamathtanay.kblock.model.CallLogItem

class CallLogDiffUtil : DiffUtil.ItemCallback<CallLogItem>() {
    override fun areItemsTheSame(oldItem: CallLogItem, newItem: CallLogItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CallLogItem, newItem: CallLogItem): Boolean {
        return oldItem.contactNumber.equals(newItem.contactNumber) &&
                oldItem.contactName.equals(newItem.contactName) &&
                oldItem.time.equals(newItem.time)
    }
}