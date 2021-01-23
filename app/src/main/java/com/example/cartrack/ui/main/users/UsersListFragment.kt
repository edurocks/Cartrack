package com.example.cartrack.ui.main.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.adapter.UsersAdapter
import com.example.cartrack.model.UsersResponse
import com.example.cartrack.repository.Resource
import com.example.cartrack.ui.main.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersListFragment : Fragment(), UsersAdapter.userRowClickListener{

    private val mViewModel: UsersViewModel by viewModels()
    private lateinit var usersRecyclerView : RecyclerView
    private lateinit var usersAdapter : UsersAdapter
    private var mLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        usersRecyclerView = view.findViewById(R.id.users_recycler_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        mViewModel.getUsers()
        observeResult()
    }

    private fun observeResult() {
        mViewModel.mUsersResponse.observe(viewLifecycleOwner, { usersResponse ->
            usersResponse.let { usersData ->
                when (usersResponse.status) {
                    Resource.Status.SUCCESS -> {
                        usersAdapter = UsersAdapter(
                            usersData.data!!,
                            this@UsersListFragment
                        )
                        usersRecyclerView.adapter = usersAdapter
                    }

                    Resource.Status.ERROR -> {
                        Snackbar.make(
                            requireView(), "Can't load users list",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    else -> Log.e("Erro", "Erro getting users list")
                }
            }
        })
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        usersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (mLayoutManager as LinearLayoutManager).orientation
            )
        )
        usersRecyclerView.layoutManager = mLayoutManager
    }

    override fun onUserClickListener(users: UsersResponse) {
        val bundle = bundleOf("userData" to users)
        findNavController().navigate(R.id.action_usersListFragment_to_usersDetailFragment, bundle)

    }
}