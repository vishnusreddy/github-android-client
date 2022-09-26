package com.vishnusreddy.gpulls_android.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.databinding.FragmentLoginBinding
import com.vishnusreddy.gpulls_android.ui.publicRepos.PublicReposFragment
import com.vishnusreddy.gpulls_android.utils.ui.UIUtils

class LoginFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity())[LoginViewModel::class.java] }
    lateinit var binding: FragmentLoginBinding
    var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.userNameValidLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showSnackbar(R.string.proceeding)
                    this.parentFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerMain, PublicReposFragment.newInstance(username))
                        .addToBackStack(null).commit()
                } else {
                    showSnackbar(R.string.wrong_username)
                }
            }
        }
    }

    private fun showSnackbar(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun setListeners() {
        binding.submitButton.setOnClickListener {
            UIUtils().hideKeyboard(activity)
            username = binding.etUsername.text.toString()
            if (username.isNullOrEmpty()) {
                showSnackbar(R.string.wrong_username)
            } else {
                viewModel.checkIfUserExists(username)
            }
        }
    }
}