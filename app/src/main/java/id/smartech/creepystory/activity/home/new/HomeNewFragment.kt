package id.smartech.creepystory.activity.home.new

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.home.adapter.HomeNewAdapter
import id.smartech.creepystory.activity.story.detail.DetailStoryActivity
import id.smartech.creepystory.base.BaseFragment
import id.smartech.creepystory.databinding.FragmentHomeNewBinding
import id.smartech.creepystory.model.story.StoryModel


class HomeNewFragment : BaseFragment<FragmentHomeNewBinding>() {
    private lateinit var viewModel: HomeNewViewModel
    private lateinit var newAdapter: HomeNewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listStory = ArrayList<StoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout= R.layout.fragment_home_new
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(HomeNewViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getStoryByNew()
    }

    private fun subscribeLiveData() {
        activity?.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        activity?.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                bind.tvDataNotFound.visibility = View.GONE
                newAdapter.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                bind.tvDataNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvStory.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvStory.layoutManager = layoutManager
        newAdapter =
            HomeNewAdapter(
                listStory
            )
        bind.rvStory.adapter = newAdapter

        newAdapter.setOnItemClickCallback(object: HomeNewAdapter.OnItemClickCallback {
            override fun onClickItem(storyModel: StoryModel) {
                val intent = Intent(activity, DetailStoryActivity::class.java)
                intent.putExtra("st_id", storyModel.stId)
                intent.putExtra("ct_id", storyModel.ctId)
                intent.putExtra("me_id", storyModel.meId)
                intent.putExtra("ct_name", storyModel.ctName)
                intent.putExtra("me_name", storyModel.meName)
                intent.putExtra("st_title", storyModel.stTitle)
                intent.putExtra("st_content", storyModel.stContent)
                intent.putExtra("me_photo_profile", storyModel.mePhotoProfile)
                intent.putExtra("st_photo_cover", storyModel.stPhotoCover)
                intent.putExtra("st_created", storyModel.stCreated)
                intent.putExtra("st_updated", storyModel.stUpdated)
                intent.putExtra("st_favorite_length", storyModel.stFavorited)
                intent.putExtra("me_photo_cover", storyModel.mePhotoCover)
                intent.putExtra("me_domicile", storyModel.meDomicile)
                intent.putExtra("me_description", storyModel.meDescription)
                intent.putExtra("me_role", storyModel.meRole)
                startActivity(intent)
            }

        })


    }

    override fun onStart() {
        super.onStart()
        viewModel.getStoryByNew()
    }

}