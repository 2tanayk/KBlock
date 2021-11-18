package com.kamathtanay.kblock.ui.mainscreen.blockedtab.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.kamathtanay.kblock.databinding.DialogAddNumberBinding
import java.lang.ClassCastException

class AddNumberDialog : AppCompatDialogFragment() {

    private var _binding: DialogAddNumberBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: AddNumberDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as AddNumberDialogListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw ClassCastException(context.toString() + "must implement ExampleDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

        _binding = DialogAddNumberBinding.inflate(layoutInflater)

        builder.setView(binding.root)
            .setTitle("Add Number to the Blocked List")
            .setNegativeButton("Cancel") { dialogInterface, i ->
            }
            .setPositiveButton("Add") { dialogInterface, i ->
                val phoneNumber=binding.phoneNumberEditText.text.toString()
                listener.addNumber(phoneNumber)
            }

        return builder.create()
    }

    interface AddNumberDialogListener {
        fun addNumber(phoneNumber: String)
    }
}