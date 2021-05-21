package me.gyanesh.hdp.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import me.gyanesh.hdp.R
import me.gyanesh.hdp.databinding.ActivityHomeBinding
import me.gyanesh.hdp.util.gone
import me.gyanesh.hdp.util.show

class HomeActivity : BaseActivity() {

    private val homeIds = hashSetOf(
        R.id.navigation_home,
        R.id.navigation_reports,
        R.id.navigation_articles,
        R.id.navigation_videos
    )

    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
        setupDrawer()
        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, bundle: Bundle? ->
            when {
//                destination.id == R.id.navigation_profile -> {
//                    val username = bundle?.getString("username")
//                    val currentUserName = NblikApp.currentUser()?.username
//                    content_main.show(username == null || username == currentUserName)
//                }
                homeIds.contains(destination.id) -> binding.contentMain.show(true)
                else -> binding.contentMain.gone()
            }
        }
    }

    fun setupActionBar(toolbar: Toolbar) {
//        val v: View? = toolbar.findViewById(R.id.btn_menu)
//        setSupportActionBar(toolbar)
//        v?.setOnClickListener { binding.drawerLayout.open() }
//
//        toolbar.btn_search?.setOnClickListener {
//            navController.navigate(R.id.searchNavGraph)
//        }
//
//        toolbar.btn_notifications?.setOnClickListener {
//            navController.navigate(R.id.notificationFragment)
//        }
//        toolbar.btn_messaging?.setOnClickListener {
//            PreferenceProvider.onChatIconClick()
//            navController.navigate(R.id.chatListFragment)
//        }
    }

    private fun setupDrawer() {

    }

}
