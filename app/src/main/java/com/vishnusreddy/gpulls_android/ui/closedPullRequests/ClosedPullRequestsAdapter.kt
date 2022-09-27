package com.vishnusreddy.gpulls_android.ui.closedPullRequests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.data.model.GithubPullRequest

class ClosedPullRequestsAdapter(val context: Context) :
    PagingDataAdapter<GithubPullRequest, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface ItemClickListener {
        fun onItemClicked(repo: GithubPullRequest)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubPullRequest>() {
            override fun areItemsTheSame(
                oldItem: GithubPullRequest,
                newItem: GithubPullRequest
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: GithubPullRequest,
                newItem: GithubPullRequest
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? GithubPullRequestViewHolder)?.bind(item = getItem(position), context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GithubPullRequestViewHolder.getInstance(parent)
    }

    class GithubPullRequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun getInstance(parent: ViewGroup): GithubPullRequestViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.pull_request_list_item, parent, false)
                return GithubPullRequestViewHolder(view)
            }
        }

        var userImage: ImageView = view.findViewById(R.id.pr_user_image)
        var title: TextView = view.findViewById(R.id.pr_title)
        var userName: TextView = view.findViewById(R.id.pr_username)
        var number: TextView = view.findViewById(R.id.pr_number)
        var created: TextView = view.findViewById(R.id.created_on_textview)
        var closed: TextView = view.findViewById(R.id.closed_on_textview)

        fun bind(item: GithubPullRequest?, context: Context) {
            if (item != null) {
                if (item.user != null && !item.user!!.displayPicture.isNullOrEmpty()) {
                    Glide.with(context).load(item.user!!.displayPicture).into(userImage)
                }
                title.text = item.title
                userName.text = item.user?.userName ?: ""
                number.text = "#${item.pullRequestId.toString()}"
                created.text = item.createdDate.toString()
                closed.text = item.closedDate.toString()
            }
        }

    }
}