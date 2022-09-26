package com.vishnusreddy.gpulls_android.ui.closedPullRequests

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import com.vishnusreddy.gpulls_android.databinding.FragmentClosedPullRequestsBinding
import com.vishnusreddy.gpulls_android.ui.publicRepos.PublicReposAdapter
import com.vishnusreddy.gpulls_android.ui.publicRepos.PublicReposFragment
import com.vishnusreddy.gpulls_android.ui.publicRepos.PublicReposViewModel
import kotlinx.coroutines.launch

class ClosedPullRequestsFragment : Fragment() {

    lateinit var binding: FragmentClosedPullRequestsBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity())[ClosedPullRequestsViewModel::class.java] }
    private lateinit var adapter: ClosedPullRequestsAdapter
    private lateinit var userName: String
    private lateinit var repoName: String


    companion object {
        const val USERNAME = "USERNAME"
        const val REPO_NAME = "REPO_NAME"

        fun newInstance(userName: String, repoName: String): ClosedPullRequestsFragment {
            return ClosedPullRequestsFragment().apply {
                val bundle = Bundle()
                bundle.putString(USERNAME, userName)
                bundle.putString(REPO_NAME, repoName)
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClosedPullRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupViews()
        setObservers()
    }

    private fun init() {
        adapter = ClosedPullRequestsAdapter()
        arguments?.let {
            userName = it.getString(USERNAME, "")
            repoName = it.getString(REPO_NAME, "")
        }
    }

    private fun setObservers() {
        if (userName.isNotEmpty()) {
            viewModel.fetchClosedReposLiveData(userName, repoName).observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setupViews() {
        binding.prRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.prRecyclerView.adapter = adapter
    }

}