package me.gyanesh.hdp.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reports.*
import me.gyanesh.hdp.ui.BaseFragment
import me.gyanesh.hdp.R
import me.gyanesh.hdp.ui.RootViewModel
import me.gyanesh.hdp.util.createViewModel
import org.kodein.di.direct
import org.kodein.di.generic.instance


class ReportsFragment : BaseFragment() {

    private val viewModel by lazy {
        requireActivity().createViewModel {
            RootViewModel(kodein.direct.instance())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val reportsAdapter = ReportsAdapter()
        viewModel.reports.observe(
            viewLifecycleOwner, {
                reportsAdapter.submitList(it)
            }
        )

        recyclerView.apply {
            this.adapter = reportsAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
}