package id.smartech.creepystory.activity.story.liststory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.story.detail.DetailStoryActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityListStoryBinding
import id.smartech.creepystory.model.story.StoryModel

class ListStoryActivity : BaseActivity<ActivityListStoryBinding>() {
    private lateinit var viewModel: ListStoryViewModel
    private lateinit var adapter: ListStoryAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listStory = ArrayList<StoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_list_story
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
        setData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(ListStoryViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getStoryByMember(sharedPref.getMemberIdClick())
        viewModel.setSharedPref(sharedPref)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
                val length = sharedPref.getStoryLength()
                val text = "All Stories ($length)"
                bind.tvStory.text = text

            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast("Data not found")
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvStory.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        bind.rvStory.layoutManager = layoutManager
        adapter =
            ListStoryAdapter(
                listStory
            )
        bind.rvStory.adapter = adapter

        adapter.setOnItemClickCallback(object: ListStoryAdapter.OnItemClickCallback {
            override fun onClickItem(storyModel: StoryModel) {
                val intent = Intent(this@ListStoryActivity, DetailStoryActivity::class.java)
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

    private fun setData() {
        val name = sharedPref.getNameClick()
        val title = "$name Stories"
        bind.toolbarSubtitle.text = title
    }

    override fun onStart() {
        super.onStart()
        viewModel.getStoryByMember(sharedPref.getMemberIdClick())
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStoryByMember(sharedPref.getMemberIdClick())
    }


}