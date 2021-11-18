package com.kamathtanay.kblock.ui.mainscreen.blockedtab

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Menu.NONE
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.databinding.FragmentBlockedContactsBinding
import com.kamathtanay.kblock.model.ContactItem
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.MainActivity
import com.kamathtanay.kblock.ui.mainscreen.adapters.BlockedRecyclerViewAdapter
import com.kamathtanay.kblock.ui.mainscreen.blockedtab.dialog.AddNumberDialog
import com.kamathtanay.kblock.util.PermissionUtil.hasPermission
import com.kamathtanay.kblock.util.PermissionUtil.requestPermission
import com.kamathtanay.kblock.util.diffutil.hide
import com.kamathtanay.kblock.util.diffutil.show
import com.kamathtanay.kblock.viewmodel.BlockedViewModel
import com.kamathtanay.kblock.viewmodel.BlockedViewModelFactory


class BlockedContactsFragment : Fragment(), BlockedRecyclerViewAdapter.OnItemClickListener,MainActivity.DataListener {
    private var _binding: FragmentBlockedContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BlockedViewModel
    private lateinit var blockedAdapter: BlockedRecyclerViewAdapter
    private lateinit var blockedSearchView: SearchView
    private lateinit var checkCallingPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCallingPermission = requestPermission(this)

        val mainActivity = activity as MainActivity
        mainActivity.setDataListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockedContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        val db = AppDatabase(requireContext())
        val repository = BlockedRepository(db)
        val factory = BlockedViewModelFactory(repository)
        blockedAdapter = BlockedRecyclerViewAdapter(this)

        viewModel = ViewModelProvider(this, factory).get(BlockedViewModel::class.java)
        setHasOptionsMenu(true)

        if (!hasPermission(requireContext(), Manifest.permission.READ_PHONE_STATE)) {
            checkCallingPermission.launch(Manifest.permission.READ_PHONE_STATE)
        }

        if (!hasPermission(requireContext(), Manifest.permission.CALL_PHONE)) {
            checkCallingPermission.launch(Manifest.permission.CALL_PHONE)
        }

        if (!hasPermission(requireContext(), Manifest.permission.READ_CALL_LOG)) {
            checkCallingPermission.launch(Manifest.permission.READ_CALL_LOG)
        }


        if (!hasPermission(requireContext(), Manifest.permission.ANSWER_PHONE_CALLS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                checkCallingPermission.launch(Manifest.permission.ANSWER_PHONE_CALLS)
            }
        }

//        if (!hasPermission(requireContext(), Manifest.permission.BIND_SCREENING_SERVICE)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                checkCallingPermission.launch(Manifest.permission.BIND_SCREENING_SERVICE)
//            }
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.blockedRecyclerView.setHasFixedSize(true)
        binding.blockedRecyclerView.adapter = blockedAdapter

        viewModel.getAllBlockedContacts().observe(viewLifecycleOwner, {
            Log.e("blocked contacts", it.toString())
            val blockedContacts: MutableList<ContactItem> =
                viewModel.prepareDataForBlockedRecyclerView(it)
            if (blockedContacts.size > 0) {
                binding.blockedContactsViewContainer.hide()
                binding.blockedRecyclerView.show()
                blockedAdapter.submitList(blockedContacts)
                binding.searchFab.show()
            } else {
                binding.blockedRecyclerView.hide()
                binding.searchFab.hide()
                binding.blockedContactsViewContainer.show()
            }
        })

        binding.searchFab.setOnClickListener {
            (activity as MainActivity).openAddNumberDialog()
        }
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("QuerySubmitted", query)
                val result = viewModel.filterBlockedList(query)
                blockedAdapter.submitList(result)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                Log.e("Query", query)
                val result = viewModel.filterBlockedList(query)
                blockedAdapter.submitList(result)
                return false
            }
        })
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

    override fun onItemClick(position: Int) {
        Log.e("Item", "$position clicked")
    }

    override fun onBlockUnblockClick(position: Int, contactItem: ContactItem) {
        viewModel.unblockContact(contactPhoneNo = contactItem.contactNumber)
    }

    override fun newBlockedNumberListener(phoneNumber: String) {
        Log.e("Data recd.", phoneNumber)
    }
}