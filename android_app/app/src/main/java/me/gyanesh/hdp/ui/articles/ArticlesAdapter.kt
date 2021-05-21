package me.gyanesh.hdp.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.gyanesh.hdp.R
import me.gyanesh.hdp.data.model.Article
import me.gyanesh.hdp.databinding.HolderArticleBinding
import me.gyanesh.hdp.util.*


class ArticlesAdapter :
    ListAdapter<Article, ArticlesAdapter.CommunityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommunityViewHolder(
            HolderArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CommunityViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.article = item

        holder.binding.root.setOnClickListener {
            it.findNavController().navigate(
                R.id.articleDetailFragment,
                bundleOf("article" to item)
            )
        }
    }

    inner class CommunityViewHolder(
        val binding: HolderArticleBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
