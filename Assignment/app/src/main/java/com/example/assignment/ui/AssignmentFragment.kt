package com.example.assignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.databinding.FragmentAssignmentBinding
import com.example.assignment.model.ListData
import com.example.assignment.ui.adapter.ListDataAdapter
import com.example.assignment.utils.NetworkUtils
import com.example.assignment.utils.WebServiceState
import com.example.assignment.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_assignment.*

class AssignmentFragment : Fragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var adapter: ListDataAdapter

    private val listDataObserver = Observer<List<ListData?>?> {
        //CancelProgress
        //ui update
        Log.e("===Ass.Frag", "AssignmentData: " + it.size)
        rvList.adapter = adapter
        adapter.setListData(it)
    }

    private val webServiceObserver = Observer<WebServiceState> {
        if (it == WebServiceState.PROCESSING) {
            //show Progress
            Log.e("===Ass.Frag", "show Progress")
        } else {
            //cancel Progress
            Log.e("===Ass.Frag", "end Progress")
        }
    }

    private val snackbarMsgStrObserver = Observer<String> {
        //show toast/snackbar message
        Log.e("===Ass.Frag", "ToastMsg = $it")
    }

    private val errorMsgObserver = Observer<String> {
        //show offline : if loaded from cache and network call fails then show a message
        //If no Data available then show a message below actionBar
        Log.e("===Ass.Frag", "errorMsg = $it")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentAssignmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_assignment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setViewModel()
        observerSetUp()
        viewSetUp()
    }

    private fun viewSetUp() {
        adapter =
            ListDataAdapter(
                appViewModel.listData.value,
                this.context
            )
        rvList.setHasFixedSize(true)
        rvList.layoutManager = LinearLayoutManager(requireContext())

        fetchListData()
    }

    private fun fetchListData() {
        //check if data in Database then load or else call app
        if (!NetworkUtils.hasNetwork(requireActivity())) {
            appViewModel.snackbarMsgStr.postValue(getString(R.string.please_check_your_internet_connection))
        } else {
            appViewModel.getAllAppData()
        }
    }

    private fun observerSetUp() {
        appViewModel.webserviceState.observe(viewLifecycleOwner, webServiceObserver)
        appViewModel.snackbarMsgStr.observe(viewLifecycleOwner, snackbarMsgStrObserver)
        appViewModel.errormsg.observe(viewLifecycleOwner, errorMsgObserver)
        appViewModel.listData.observe(viewLifecycleOwner, listDataObserver)
        appViewModel.appRepository.listDataDao?.getAllData()?.observe(this,
            Observer<List<ListData?>?> {
                //onchange
                appViewModel.listData.postValue(it)
            })
    }

    private fun setViewModel() {
        appViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
    }
}