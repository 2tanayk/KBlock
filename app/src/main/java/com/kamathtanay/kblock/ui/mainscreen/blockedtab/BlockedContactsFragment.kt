package com.kamathtanay.kblock.ui.mainscreen.blockedtab

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Menu.NONE
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.databinding.FragmentBlockedContactsBinding
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.adapters.BlockedRecyclerViewAdapter
import com.kamathtanay.kblock.viewmodel.BlockedViewModel
import com.kamathtanay.kblock.viewmodel.BlockedViewModelFactory
import com.kamathtanay.kblock.viewmodel.ContactsViewModel


class BlockedContactsFragment : Fragment() {
    private var _binding: FragmentBlockedContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BlockedViewModel
    private lateinit var blockedAdapter: BlockedRecyclerViewAdapter
    private lateinit var blockedSearchView:SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBlockedContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        val db = AppDatabase(requireContext())
        val repository = BlockedRepository(db)
        val factory = BlockedViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(BlockedViewModel::class.java)
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

        val searchItem = menu.findItem(R.id.main_search_bar)
        val searchView: SearchView = searchItem.actionView as SearchView
        blockedSearchView = searchView
        searchView.queryHint = "Search Blocked Contacts"
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