package com.vishnusreddy.gpulls_android.ui.publicRepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.data.model.GithubRepository

class PublicReposAdapter(var listener: ItemClickListener):
    PagingDataAdapter<GithubRepository, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface ItemClickListener {
        fun onItemClicked(repo: GithubRepository)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
        (holder as? GitHubRepoViewHolder)?.bind(item = item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GitHubRepoViewHolder.getInstance(parent)
    }

    class GitHubRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun getInstance(parent: ViewGroup): GitHubRepoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.repo_list_item, parent, false)
                return GitHubRepoViewHolder(view)
            }
        }

        var title: TextView = view.findViewById(R.id.list_item_title_textView)
        var description: TextView = view.findViewById(R.id.list_item_description_textView)
        var stars: TextView = view.findViewById(R.id.list_item_stars_textView)
        var watchers: TextView = view.findViewById(R.id.list_item_watchers_textView)
        var language: TextView = view.findViewById(R.id.list_item_language_textView)

        fun bind(item: GithubRepository?) {
            if (item != null) {
                title.text = item.name
                description.text = item.description
                stars.text = item.startCount.toString()
                watchers.text = item.watchersCount.toString()
                language.text = item.language
            }
        }

    }

}