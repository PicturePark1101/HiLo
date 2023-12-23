package ddwucom.moblie.hilo

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import ddwucom.moblie.hilo.base.BaseActivity
import ddwucom.moblie.hilo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var homeFragment: HomeFragment
    lateinit var calendarTotalFragment: CalendarFragment
    lateinit var mapFragment: MapFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragment = HomeFragment()
        calendarTotalFragment = CalendarFragment()
        mapFragment = MapFragment()

        replaceFragment(homeFragment)
        binding.bottomNavi.setOnItemSelectedListener(
            object : NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    var selectedFragment: Fragment? = null
                    when (item.itemId) {
                        R.id.page_1 -> {
                            replaceFragment(homeFragment)
                        }
                        R.id.page_2 -> {
                            replaceFragment(mapFragment)
                        }
                        R.id.page_3 -> {
                            replaceFragment(calendarTotalFragment)
                        }
                        else -> return false
                    }
                    return true
                }
            },
        )
    }

    private fun replaceFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cl_main, fragment)
                .commit()
        }
    }
}
