package com.kamathtanay.kblock.ui.mainscreen.blockedtab

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Menu.NONE
import androidx.fragment.app.Fragment
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.databinding.FragmentBlockedContactsBinding


class BlockedContactsFragment : Fragment() {
    private var _binding: FragmentBlockedContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockedContactsBinding.inflate(inflater, container, false)
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
        menu.removeItem(ConstantsMain.ITEM_REFRESH_CONTACTS)
        menu.add(NONE, ConstantsMain.ITEM_UNBLOCK_ALL, NONE, "Unblock All")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            ConstantsMain.ITEM_UNBLOCK_ALL -> {
                Log.e("Item: ${ConstantsMain.ITEM_UNBLOCK_ALL}", "clicked!")
            }
        }
        return false
    }
}