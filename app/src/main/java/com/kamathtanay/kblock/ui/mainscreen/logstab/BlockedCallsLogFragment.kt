package com.kamathtanay.kblock.ui.mainscreen.logstab

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.CallLogRepository
import com.kamathtanay.kblock.databinding.FragmentBlockedCallsLogBinding
import com.kamathtanay.kblock.model.CallLogItem
import com.kamathtanay.kblock.ui.mainscreen.ConstantsMain
import com.kamathtanay.kblock.ui.mainscreen.adapters.CallLogsRecyclerViewAdapter
import com.kamathtanay.kblock.util.diffutil.hide
import com.kamathtanay.kblock.util.diffutil.show
import com.kamathtanay.kblock.viewmodel.CallLogViewModel
import com.kamathtanay.kblock.viewmodel.CallLogViewModelFactory

class BlockedCallLogFragment : Fragment() {
    private var _binding: FragmentBlockedCallsLogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CallLogViewModel
    private lateinit var callLogsAdapter:CallLogsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = AppDatabase(requireContext())
        val repository = CallLogRepository(db)
        val factory = CallLogViewModelFactory(repository)
        viewModel= ViewModelProvider(this, factory).get(CallLogViewModel::class.java)
        callLogsAdapter= CallLogsRecyclerViewAdapter()

        _binding = FragmentBlockedCallsLogBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logsRecyclerView.setHasFixedSize(true)
        binding.logsRecyclerView.adapter=callLogsAdapter

        viewModel.getAllCallLogs().observe(viewLifecycleOwner,{
            Log.e("Observing","updated call logs!")
            val callLogsList: MutableList<CallLogItem> = viewModel.prepareDataForCallLogsRecyclerView(it)
            if(callLogsList.isNotEmpty()){
                binding.callLogViewContainer.hide()
                binding.logsRecyclerView.show()
                callLogsAdapter.submitList(callLogsList)
            }else{
                binding.logsRecyclerView.hide()
                binding.callLogViewContainer.show()
            }
        })
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