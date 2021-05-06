package id.smartech.creepystory.activity.maincontent


import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.favorite.FavoriteFragment
import id.smartech.creepystory.activity.home.HomeFragment
import id.smartech.creepystory.activity.profile.ProfileFragment
import id.smartech.creepystory.activity.search.SearchFragment
import id.smartech.creepystory.activity.story.createstory.CreateStoryActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)


        bind.bottomNavigationView.background = null
        bind.bottomNavigationView.menu.getItem(2).isEnabled = false

        val profileFragment = ProfileFragment()
        val favoriteFragment = FavoriteFragment()
        val searchFragment = SearchFragment()
        val homeFragment =
            HomeFragment()

        currentFragment(homeFragment)
        bind.toolbarSubtitle.text = "Cerita"

        bind.fab.setOnClickListener {
            intents<CreateStoryActivity>(this)
        }

        bind.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    currentFragment(homeFragment)
                    bind.toolbarSubtitle.text = "Cerita"
                }
                R.id.item_search -> {
                    currentFragment(searchFragment)
                    bind.toolbarSubtitle.text = "Telusuri"
                }
                R.id.item_favorite -> {
                    currentFragment(favoriteFragment)
                    bind.toolbarSubtitle.text = "Favorite"
                }
                R.id.item_profile -> {
                    currentFragment(profileFragment)
                    bind.toolbarSubtitle.text = "Profile"
                }
            }
            true
        }
    }

    private fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment).commit()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onBackPressed() {

    }
}