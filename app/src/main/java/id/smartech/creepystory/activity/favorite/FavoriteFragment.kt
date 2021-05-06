package id.smartech.creepystory.activity.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.home.adapter.HomeFavoritedAdapter
import id.smartech.creepystory.activity.story.detail.DetailStoryActivity
import id.smartech.creepystory.base.BaseFragment
import id.smartech.creepystory.databinding.FragmentFavoriteBinding
import id.smartech.creepystory.model.favorite.FavoriteModel
import id.smartech.creepystory.model.story.StoryModel


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listFavorite = ArrayList<FavoriteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_favorite
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getFavoriteByMemberId(sharedPref.getMemberId())
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
                adapter.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                bind.tvDataNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvFavorite.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvFavorite.layoutManager = layoutManager
        adapter =
            FavoriteAdapter(
                listFavorite
            )
        bind.rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object: FavoriteAdapter.OnItemClickCallback {
            override fun onClickItem(data: FavoriteModel) {
                val intent = Intent(activity, DetailStoryActivity::class.java)
                intent.putExtra("st_id", data.stId)
                intent.putExtra("me_name", data.meName)
                intent.putExtra("me_photo_profile", data.mePhotoProfile)
                intent.putExtra("me_photo_cover", data.mePhotoCover)
                intent.putExtra("me_domicile", data.meDomicile)
                intent.putExtra("me_description", data.meDescription)
                intent.putExtra("me_role", data.meRole)
                intent.putExtra("st_title", data.stTitle)
                intent.putExtra("st_content", data.stContent)
                intent.putExtra("st_photo_cover", data.stPhotoCover)
                intent.putExtra("st_created", data.stCreated)
                intent.putExtra("st_favorite_length", data.stFavorited)
                startActivity(intent)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavoriteByMemberId(sharedPref.getMemberId())
    }

    override fun onStop() {
        super.onStop()
        viewModel.getFavoriteByMemberId(sharedPref.getMemberId())
    }


}