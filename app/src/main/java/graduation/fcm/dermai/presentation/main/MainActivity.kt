package graduation.fcm.dermai.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.R
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarrConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarrConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navController, appBarrConfiguration)
        observeDestinations()
    }

    private fun observeDestinations() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.questionsFragment -> {
                    hideBottomNav()
                    hideToolBar()
                }

                R.id.resultFragment -> {
                    hideBottomNav()
                    showToolBar()
                }

                R.id.scanFragment, R.id.detailsFragment, R.id.doctorFragment -> {
                    hideBottomNav()
                    showToolBar()
                }

                else -> {
                    showBottomNav()
                    showToolBar()
                }
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.show()
    }

    private fun showToolBar() {
        binding.toolbar.show()
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.gone()
        binding.toolbar.gone()
    }

    private fun hideToolBar() {
        binding.toolbar.gone()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarrConfiguration) || super.onSupportNavigateUp()
    }

}