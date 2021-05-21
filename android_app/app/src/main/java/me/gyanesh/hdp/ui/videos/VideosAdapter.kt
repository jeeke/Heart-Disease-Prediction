package me.gyanesh.hdp.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.gyanesh.hdp.R
import me.gyanesh.hdp.data.model.Video
import me.gyanesh.hdp.databinding.HolderVideoBinding


class VideosAdapter :
    ListAdapter<Video, VideosAdapter.CommunityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Video> =
            object : DiffUtil.ItemCallback<Video>() {
                override fun areItemsTheSame(
                    oldItem: Video,
                    newItem: Video
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Video,
                    newItem: Video
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommunityViewHolder(
            HolderVideoBinding.inflate(
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
        holder.binding.video = item
        holder.binding.btnPlay.setOnClickListener {
            it.findNavController().navigate(
                R.id.videoPlayerFragment,
                bundleOf("videoId" to item.videoId)
            )
        }
    }

    inner class CommunityViewHolder(
        val binding: HolderVideoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
