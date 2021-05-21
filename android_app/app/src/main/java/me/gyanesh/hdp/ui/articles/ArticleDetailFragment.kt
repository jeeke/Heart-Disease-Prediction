package me.gyanesh.hdp.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.view_toolbar_detail_top.*
import me.gyanesh.hdp.databinding.FragmentBlogDetailBinding
import me.gyanesh.hdp.ui.BaseFragment

class ArticleDetailFragment : BaseFragment() {

    lateinit var binding: FragmentBlogDetailBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlogDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args by navArgs<ArticleDetailFragmentArgs>()
        binding.article = args.article
        setupToolbarTop()
        setupScrollingBehaviour()
    }

    private fun setupToolbarTop() {
        navController = findNavController()
        btnBack.setOnClickListener { navController.navigateUp() }
    }

    private fun setupScrollingBehaviour() {
        binding.blogScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val totalScrollLength =
                binding.blogScrollView.getChildAt(0).height - binding.blogScrollView.height
            val titleContentLength = binding.textView19.height + binding.textView14.height

            binding.include.pbBlogScroll.apply {
                max =
                    ((totalScrollLength.toFloat() / binding.blogScrollView.getChildAt(0).height.toFloat()) * titleContentLength).toInt()
                progress = scrollY
            }
        }
    }

}