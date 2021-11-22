package com.example.assignment.ui

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.databinding.FragmentAssignmentBinding
import com.example.assignment.model.ListData
import com.example.assignment.repository.AppRepository
import com.example.assignment.ui.adapter.ListDataAdapter
import com.example.assignment.utils.WebServiceState
import com.example.assignment.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_assignment.*

class AssignmentFragment : Fragment() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var adapter: ListDataAdapter
    private lateinit var appRepository: AppRepository

    private val listDataObserver = Observer<List<ListData?>?> {
        //ui update
        rvList.adapter = adapter
        adapter.setListData(it)
        if (it?.isNullOrEmpty() == true) {
            txtNoData.visibility = View.VISIBLE
        } else
            txtNoData.visibility = View.GONE
    }

    private val webServiceObserver = Observer<WebServiceState> {
        if (it == WebServiceState.PROCESSING) {
            if (!swipeRefresh.isRefreshing)
                progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
            if (swipeRefresh.isRefreshing)
                swipeRefresh.isRefreshing = false
        }
    }

    private val errorMsgObserver = Observer<String> {
        //show offline : if loaded from cache and network call fails then show a message
        //If no Data available then show a message below actionBar
        Snackbar.make(rvList, it, Snackbar.LENGTH_LONG).show()
    }

    private val internetNotAvailableObserver = Observer<Boolean> {
        if (it)
            txtOfflineStatus.visibility = View.VISIBLE
        else
            txtOfflineStatus.visibility = View.GONE
    }

    private val appTitleObserver = Observer<String> {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        val myEdit: SharedPreferences.Editor? = sharedPreferences?.edit()
        myEdit?.putString(getString(R.string.pref_title), it)
        myEdit?.apply()

        (activity as? AppCompatActivity)?.supportActionBar?.title = it
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
        appRepository = AppRepository(requireActivity().application)
        setViewModel()
        observerSetUp()
        viewSetUp()
    }

    private fun viewSetUp() {
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.setHasFixedSize(true)
        adapter = ListDataAdapter(appViewModel.listData.value)

        updateTitle()
        setSwipeRefresh()
    }

    private fun setSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            appViewModel.getAllAppData()
        }
    }

    private fun updateTitle() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        val title = sh.getString(getString(R.string.pref_title), getString(R.string.app_name))
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    private fun observerSetUp() {
        appViewModel.webserviceState.observe(viewLifecycleOwner, webServiceObserver)
        appViewModel.errorMsg.observe(viewLifecycleOwner, errorMsgObserver)
        appViewModel.isInternetNotAvailable.observe(this, internetNotAvailableObserver)
        appViewModel.appTitle.observe(viewLifecycleOwner, appTitleObserver)
        appViewModel.listData.observe(viewLifecycleOwner, listDataObserver)
        appRepository.listDataDao?.getAllData()?.observe(this,
            {
                //onchange
                appViewModel.listData.postValue(it)
            })
    }

    private fun setViewModel() {
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
    }
}