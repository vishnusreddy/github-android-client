package com.vishnusreddy.gpulls_android.ui.publicRepos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.data.model.GithubRepository
import com.vishnusreddy.gpulls_android.databinding.FragmentPublicReposBinding
import com.vishnusreddy.gpulls_android.ui.closedPullRequests.ClosedPullRequestsFragment
import com.vishnusreddy.gpulls_android.ui.common.LoaderStateAdapter
import kotlinx.coroutines.launch

class PublicReposFragment : Fragment() {

    lateinit var binding: FragmentPublicReposBinding

    private val viewModel by lazy { ViewModelProvider(requireActivity())[PublicReposViewModel::class.java] }
    private lateinit var adapter: PublicReposAdapter
    private lateinit var loaderStateAdapter: LoaderStateAdapter
    private lateinit var userName: String


    companion object {
        private const val USERNAME = "USERNAME"

        /** Use this function in the companion object to pass the username
         * entered by the user on login screen to this Fragment. **/
        fun newInstance(userName: String): PublicReposFragment {
            return PublicReposFragment().apply {
                val bundle = Bundle()
                bundle.putString(USERNAME, userName)
                arguments = bundle
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicReposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupViews()
        setObservers()
    }

    private fun init() {
        adapter = PublicReposAdapter(
            object : PublicReposAdapter.ItemClickListener {
                override fun onItemClicked(repo: GithubRepository) {
                    goToClosedPrFragment(userName, repo.name)
                }
            }
        )
        loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        arguments?.let {
            userName = it.getString(USERNAME, "")
        }
    }

    private fun goToClosedPrFragment(userName: String, name: String) {
        this.parentFragmentManager.beginTransaction().add(R.id.fragmentContainerMain, ClosedPullRequestsFragment.newInstance(userName, name)).addToBackStack(null).commit()
    }

    private fun setObservers() {
        if (!userName.isNullOrEmpty()) {
            viewModel.fetchPublicReposLiveData(userName).observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setupViews() {
        binding.reposRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reposRecyclerView.adapter = adapter
        binding.reposRecyclerView.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    private fun showSnackbar(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

}