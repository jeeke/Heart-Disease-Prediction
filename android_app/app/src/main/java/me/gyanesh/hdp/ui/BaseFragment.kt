package me.gyanesh.hdp.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    val progressBar: ProgressDialog by lazy { ProgressDialog(context) }

    private var originalMode: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originalMode = activity?.window?.attributes?.softInputMode
    }

    fun setSoftMode(mode: Int) {
        activity?.window?.setSoftInputMode(mode)
    }

    override fun onDestroy() {
        super.onDestroy()
        originalMode?.let { activity?.window?.setSoftInputMode(it) }
    }

    fun setupDrawerToolbar(binding: ViewDataBinding) {
//        binding.lifecycleOwner = this
//        val activity = activity as? HomeActivity
//        activity?.setupActionBar(binding.root.toolbar)
    }
}