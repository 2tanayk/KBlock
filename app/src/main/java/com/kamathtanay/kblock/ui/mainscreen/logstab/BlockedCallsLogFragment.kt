package com.kamathtanay.kblock.ui.mainscreen.logstab

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.databinding.FragmentBlockedCallsLogBinding
import com.kamathtanay.kblock.databinding.FragmentBlockedContactsBinding
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain

class BlockedCallLogFragment : Fragment() {
    private var _binding: FragmentBlockedCallsLogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlockedCallsLogBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(ConstantsMain.ITEM_UNBLOCK_ALL)
        menu.removeItem(ConstantsMain.ITEM_REFRESH_CONTACTS)
        menu.add(Menu.NONE, ConstantsMain.ITEM_DELETE_ALL_LOGS, Menu.NONE, "Delete All")

        val searchItem = menu.findItem(R.id.main_search_bar)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint="Search Logs"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            ConstantsMain.ITEM_DELETE_ALL_LOGS -> {
                Log.e("Item: ${ConstantsMain.ITEM_DELETE_ALL_LOGS}", "clicked!")
            }
        }
        return false
    }
}