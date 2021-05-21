package me.gyanesh.hdp.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.gyanesh.hdp.data.model.TestReport
import me.gyanesh.hdp.databinding.HolderReportsBinding
import me.gyanesh.hdp.util.*


class ReportsAdapter :
    ListAdapter<TestReport, ReportsAdapter.CommunityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TestReport> =
            object : DiffUtil.ItemCallback<TestReport>() {
                override fun areItemsTheSame(
                    oldItem: TestReport,
                    newItem: TestReport
                ): Boolean {
                    return oldItem.roomId == newItem.roomId
                }

                override fun areContentsTheSame(
                    oldItem: TestReport,
                    newItem: TestReport
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommunityViewHolder(
            HolderReportsBinding.inflate(
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
        holder.binding.report = item
    }

    inner class CommunityViewHolder(
        val binding: HolderReportsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
