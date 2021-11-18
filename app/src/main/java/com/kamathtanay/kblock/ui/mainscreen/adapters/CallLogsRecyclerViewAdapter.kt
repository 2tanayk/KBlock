package com.kamathtanay.kblock.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kamathtanay.kblock.databinding.LogItemBinding
import com.kamathtanay.kblock.model.CallLogItem
import com.kamathtanay.kblock.util.diffutil.CallLogDiffUtil

class CallLogsRecyclerViewAdapter :
    ListAdapter<CallLogItem, CallLogsRecyclerViewAdapter.CallLogViewHolder>(CallLogDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        return CallLogViewHolder(
            LogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        val callLog = getItem(position)

        with(callLog) {
            with(holder.b) {
                logContactNameTxt.text = contactName
                logContactPhoneNoTxt.text = contactNumber
                timeTxt.text = time
            }
        }
    }

    inner class CallLogViewHolder(val b: LogItemBinding) : RecyclerView.ViewHolder(b.root)
}