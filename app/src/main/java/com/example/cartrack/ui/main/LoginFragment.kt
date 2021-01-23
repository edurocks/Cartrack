package com.example.cartrack.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cartrack.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private val mViewModel: UsersViewModel by viewModels()
    private var mUsername: TextInputLayout? = null
    private var mPassword: TextInputLayout? = null
    private var mCountry: TextInputLayout? = null
    private var mLogin: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       mUsername = view.findViewById(R.id.username)
       mPassword = view.findViewById(R.id.password)
       mCountry = view.findViewById(R.id.country)
       mLogin = view.findViewById(R.id.login)
       mLogin?.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO: insert only once in database
        mViewModel.insertUser()
        mViewModel.insertCountry()
        populateCountries()
        observeResult()
    }

    private fun populateCountries() {
        mViewModel.mCountriesName.observe(viewLifecycleOwner, { resourceCountry ->
            resourceCountry.let { countryData ->

                        val listCountries = arrayListOf<String>()
                        countryData.forEach { country ->
                            listCountries.add(country.name)
                        }

                        val adapter = ArrayAdapter(requireContext(), R.layout.country_list_row,
                                                  listCountries)
                        (mCountry?.editText as? AutoCompleteTextView)?.setAdapter(adapter)

                }
            })
        }

    private fun observeResult() {
        mViewModel.mUser.observe(viewLifecycleOwner, { user ->
            if (user != null){
                findNavController().navigate(R.id.action_loginFragment_to_usersListFragment)
            }else{
                Snackbar.make(requireView(), "User don't exist in the database",
                        Snackbar.LENGTH_LONG).show()
            }
        })

        mViewModel.mErrorMessage.observe(viewLifecycleOwner, {
            if (it.contains("Username")){
                mUsername?.error = it
            }

            if (it.contains("Password")){
                mPassword?.error = it
            }

            if (it.contains("Country")){
                mCountry?.error = it
            }
        })
    }

    override fun onClick(v: View?) {
        mViewModel.getAndValidateUser(mUsername?.editText?.text?.trim().toString(),
                                      mPassword?.editText?.text?.trim().toString(),
                                      mCountry?.editText?.text.toString())

    }
}