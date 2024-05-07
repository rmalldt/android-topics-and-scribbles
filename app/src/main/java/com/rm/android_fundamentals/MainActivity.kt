package com.rm.android_fundamentals

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.base.ExpandableTopicAdapter
import com.rm.android_fundamentals.base.model.NavDest
import com.rm.android_fundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.drawerMainFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // navView not setup with NavController because recyclerView used instead of navView menu

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvTopics.apply {
            adapter = ExpandableTopicAdapter(NavDest.drawerTopicList, {

                // TODO: Replace all Topic activities with fragments
                if (it.targetFragmentId == 0) {
                    startActivity(Intent(this@MainActivity, it.targetActivity))
                } else {
                    navController.navigate(it.targetFragmentId)
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            })

            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(binding.drawerLayout) || super.onSupportNavigateUp()
    }
}
