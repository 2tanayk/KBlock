package com.kamathtanay.kblock.ui.mainscreen.contactstab

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.databinding.FragmentBlockedContactsBinding
import com.kamathtanay.kblock.databinding.FragmentUserContactsBinding
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.blockedtab.BlockedContactsFragment

class UserContactsFragment : Fragment() {
    private var _binding: FragmentUserContactsBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(ConstantsMain.ITEM_DELETE_ALL_LOGS)
        menu.removeItem(ConstantsMain.ITEM_UNBLOCK_ALL)
        menu.add(Menu.NONE, ConstantsMain.ITEM_REFRESH_CONTACTS, Menu.NONE, "Refresh")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            ConstantsMain.ITEM_REFRESH_CONTACTS -> {
                Log.e("Item: ${ConstantsMain.ITEM_REFRESH_CONTACTS}", "clicked!")
            }
        }
        return false
    }
}