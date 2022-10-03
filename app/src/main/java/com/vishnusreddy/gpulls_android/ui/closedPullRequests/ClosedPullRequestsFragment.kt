package com.vishnusreddy.gpulls_android.ui.closedPullRequests

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.databinding.FragmentClosedPullRequestsBinding
import com.vishnusreddy.gpulls_android.ui.common.LoaderStateAdapter
import com.vishnusreddy.gpulls_android.utils.ui.UIUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException

class ClosedPullRequestsFragment : Fragment() {

    lateinit var binding: FragmentClosedPullRequestsBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity())[ClosedPullRequestsViewModel::class.java] }
    private lateinit var adapter: ClosedPullRequestsAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter
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
        adapter = ClosedPullRequestsAdapter(requireContext())
        loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        arguments?.let {
            userName = it.getString(USERNAME, "")
            repoName = it.getString(REPO_NAME, "")
        }
    }

    private fun setObservers() {
        if (userName.isNotEmpty()) {
            viewModel.fetchClosedPullRequestsLiveData(userName, repoName).observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.addLoadStateListener { loadState ->
                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                if (loadState.append.endOfPaginationReached) {
                    if (adapter.itemCount < 1) {
                        binding.emptyImageView.visibility = View.VISIBLE
                    } else {
                        binding.emptyImageView.visibility = View.GONE
                    }
                } else if (errorState?.error is IOException) {
                    UIUtils.showSnackbar(binding.root, R.string.please_check_your_internet_and_try_again)
                }
            }
        }
    }

    private fun setupViews() {
        binding.prRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.prRecyclerView.adapter = adapter
        binding.prRecyclerView.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

}