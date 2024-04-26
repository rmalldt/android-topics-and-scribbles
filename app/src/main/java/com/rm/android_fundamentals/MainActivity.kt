package com.rm.android_fundamentals

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.base.Topic
import com.rm.android_fundamentals.base.TopicActivity
import com.rm.android_fundamentals.base.TopicAdapter
import com.rm.android_fundamentals.base.topics
import com.rm.android_fundamentals.databinding.ActivityMainBinding
import com.rm.android_fundamentals.topics.t0.MainFragmentDirections
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment, R.id.drawerMainFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // navView not setup with NavController because recyclerView used instead of navView menu

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvMain.apply {
            adapter = TopicAdapter(topics) { topic ->
                val bundle = Bundle()
                bundle.putParcelable ("topic", topic)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                navController.navigate(R.id.drawerMainFragment, bundle)
            }

            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(binding.drawerLayout) || super.onSupportNavigateUp()
    }
}