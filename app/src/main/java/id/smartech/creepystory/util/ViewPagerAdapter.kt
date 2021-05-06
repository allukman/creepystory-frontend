package id.smartech.creepystory.util

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.smartech.creepystory.activity.home.favorited.HomeFavoritedFragment
import id.smartech.creepystory.activity.home.new.HomeNewFragment

class ViewPagerAdapter(fragment: FragmentManager) : FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(
        HomeNewFragment(),
        HomeFavoritedFragment()
    )

    override fun getCount(): Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Cerita Terbaru"
            1 -> "Cerita Terfavorit"
            else -> ""
        }
    }

    override fun saveState(): Parcelable? {
        return null
    }
}