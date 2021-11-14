package com.kamathtanay.kblock.ui.mainscreen.contactstab


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.contacts.KBlockContactsApi
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.ContactsRepository
import com.kamathtanay.kblock.databinding.FragmentUserContactsBinding
import com.kamathtanay.kblock.model.ContactItem
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain.MAIN_IS_CONTACTS_IMPORTED
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain.MAIN_SHARED_PREFS
import com.kamathtanay.kblock.ui.mainscreen.adapters.ContactsRecyclerViewAdapter
import com.kamathtanay.kblock.util.PermissionUtil.hasPermission
import com.kamathtanay.kblock.util.PermissionUtil.requestPermission
import com.kamathtanay.kblock.util.SharedPrefsUtil
import com.kamathtanay.kblock.util.SharedPrefsUtil.createAndEditSharedPrefs
import com.kamathtanay.kblock.util.SharedPrefsUtil.retrieveSharedPrefs
import com.kamathtanay.kblock.util.diffutil.hide
import com.kamathtanay.kblock.util.diffutil.show
import com.kamathtanay.kblock.viewmodel.ContactsViewModel
import com.kamathtanay.kblock.viewmodel.ContactsViewModelFactory
import java.util.concurrent.CancellationException

class UserContactsFragment : Fragment(), ContactsRecyclerViewAdapter.OnItemClickListener {
    private var _binding: FragmentUserContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactsViewModel
    private lateinit var checkPermission: ActivityResultLauncher<String>
    private lateinit var contactsAdapter: ContactsRecyclerViewAdapter

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
        contactsAdapter = ContactsRecyclerViewAdapter(this)

        viewModel = ViewModelProvider(this, factory).get(ContactsViewModel::class.java)
        _binding = FragmentUserContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactsRecyclerView.setHasFixedSize(true)
        binding.contactsRecyclerView.adapter = contactsAdapter

        (binding.contactsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.getAllUserContacts().observe(viewLifecycleOwner, {
            Log.e("observing...", "updated value $it")
            if (retrieveSharedPrefs(requireActivity(), MAIN_SHARED_PREFS).getBoolean(MAIN_IS_CONTACTS_IMPORTED, false)) {
                binding.userContactsViewContainer.hide()
                binding.contactsProgressBar.show()
                val recyclerViewContacts: MutableList<ContactItem> = it.map { contact ->
                    ContactItem(contact.id, contact.contactName, contact.contactPhoneNumber,
                        if (contact.isBlocked) R.drawable.ic_baseline_block_24 else R.drawable.ic_baseline_unblock_24)
                }.toMutableList()
                binding.contactsRecyclerView.show()
                contactsAdapter.submitList(recyclerViewContacts)
                binding.contactsProgressBar.hide()
                binding.searchFab.show()
            } else {
                Log.e("View Model Observe", "contacts not imported")
            }
        })


        binding.importContactsBtn.setOnClickListener {
            if (hasPermission(requireContext(), Manifest.permission.READ_CONTACTS)) {
                try {
                    viewModel.importAllContacts()
                    binding.userContactsViewContainer.hide()
                    binding.contactsProgressBar.show()
                    val editor = createAndEditSharedPrefs(requireActivity(), MAIN_SHARED_PREFS)
                    editor.putBoolean(MAIN_IS_CONTACTS_IMPORTED, true)
                    editor.apply()
                } catch (e: CancellationException) {
                    Log.e("import failed", e.printStackTrace().toString())
                } catch (e: Exception) {
                    Log.e("Exception", e.printStackTrace().toString())
                }
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
        Log.e("Item", "$position clicked")
    }

    override fun onBlockUnblockClick(position: Int, contactItem: ContactItem) {
        Log.e("Item icon", "$position clicked")

        if (contactItem.iconId == R.drawable.ic_baseline_unblock_24) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Block ${contactItem.contactName}? incoming calls from this number will be disconnected")
                .setNegativeButton("Cancel") { dialog, which ->
                }
                .setPositiveButton("Ok") { dialog, which ->
                    viewModel.updateOnContactBlockedUnblocked(true, contactItem.contactNumber)
                }.show()
        } else {
            viewModel.updateOnContactBlockedUnblocked(false, contactItem.contactNumber)
        }
    }
}

