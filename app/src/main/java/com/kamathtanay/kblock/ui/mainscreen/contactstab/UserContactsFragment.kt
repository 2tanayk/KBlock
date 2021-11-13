package com.kamathtanay.kblock.ui.mainscreen.contactstab


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.data.contacts.KBlockContactsApi
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.ContactsRepository
import com.kamathtanay.kblock.databinding.FragmentUserContactsBinding
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.adapters.ContactsRecyclerViewAdapter
import com.kamathtanay.kblock.util.PermissionUtil.hasPermission
import com.kamathtanay.kblock.util.PermissionUtil.requestPermission
import com.kamathtanay.kblock.viewmodel.ContactsViewModel
import com.kamathtanay.kblock.viewmodel.ContactsViewModelFactory

class UserContactsFragment : Fragment(),ContactsRecyclerViewAdapter.OnItemClickListener{
    private var _binding: FragmentUserContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactsViewModel
    private lateinit var checkPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission = requestPermission(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val db = AppDatabase(requireContext())
        val contactsApi = KBlockContactsApi(requireContext())
        val repository = ContactsRepository(db, contactsApi)
        val factory = ContactsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ContactsViewModel::class.java)

        _binding = FragmentUserContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.importContactsBtn.setOnClickListener {
            if (hasPermission(requireContext(), Manifest.permission.READ_CONTACTS)) {
//                Log.e("getAllUserContacts()", "${viewModel.getAllUserContacts()} value: ${viewModel.getAllUserContacts().value}")
                viewModel.getAllUserContacts().observe(viewLifecycleOwner, {
                    Log.e("observing...","updated value $it")
                    it?.forEach { contact ->
                        Log.e(contact.contactName, contact.contactPhoneNumber)
                    }
                })
            } else {
                checkPermission.launch(Manifest.permission.READ_CONTACTS)
            }
        }
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

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onBlockUnblockClick(position: Int) {
        TODO("Not yet implemented")
    }
}

