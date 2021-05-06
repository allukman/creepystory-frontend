package id.smartech.creepystory.activity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.viewpager.widget.ViewPager
import id.smartech.creepystory.R
import id.smartech.creepystory.base.BaseFragment
import id.smartech.creepystory.databinding.FragmentHomeBinding
import id.smartech.creepystory.util.ViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        bind.viewPager.adapter = pagerAdapter
        bind.tabLayout.setupWithViewPager(bind.viewPager)

    }


}