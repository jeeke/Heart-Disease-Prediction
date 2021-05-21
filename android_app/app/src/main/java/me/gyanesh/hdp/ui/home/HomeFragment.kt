package me.gyanesh.hdp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.gyanesh.hdp.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import me.gyanesh.hdp.R
import me.gyanesh.hdp.ui.ChooseLanguageActivity

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.navigation_change_lang) {
                val i = Intent(context, ChooseLanguageActivity::class.java)
                ChooseLanguageActivity.fromMainActivity = true
                startActivity(i)
            }
            true
        }

        btn_test.setOnClickListener {
            findNavController().navigate(R.id.testFragment)
        }

    }
}