package me.gyanesh.hdp.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.android.synthetic.main.fragment_videos.recyclerView
import kotlinx.android.synthetic.main.fragment_videos.swipeRefreshLayout
import me.gyanesh.hdp.ui.BaseFragment
import me.gyanesh.hdp.R
import me.gyanesh.hdp.data.Result
import me.gyanesh.hdp.ui.RootViewModel
import me.gyanesh.hdp.util.createViewModel
import me.gyanesh.hdp.util.hide
import me.gyanesh.hdp.util.show
import me.gyanesh.hdp.util.toastError
import org.kodein.di.direct
import org.kodein.di.generic.instance


class VideosFragment : BaseFragment() {

    private val viewModel by lazy {
        requireActivity().createViewModel {
            RootViewModel(kodein.direct.instance())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val videosAdapter = VideosAdapter()
        viewModel.videos.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    videosAdapter.submitList(it.data)
                }
                is Result.Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is Result.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    context.toastError(it.exception.message)
                }
            }
        })

        recyclerView.apply {
            this.adapter = videosAdapter
            layoutManager = LinearLayoutManager(context)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchVideos()
        }

    }
}