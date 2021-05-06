package id.smartech.creepystory.activity.story.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.favorite.FavoriteAdapter
import id.smartech.creepystory.activity.favorite.FavoriteViewModel
import id.smartech.creepystory.activity.label.LabelAdapter
import id.smartech.creepystory.activity.maincontent.MainActivity
import id.smartech.creepystory.activity.onboard.OnBoardActivity
import id.smartech.creepystory.activity.profile.detailprofile.DetailProfileActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityDetailStoryBinding
import id.smartech.creepystory.model.favorite.FavoriteModel
import id.smartech.creepystory.model.label.LabelModel
import id.smartech.creepystory.model.story.DetailStoryModel
import id.smartech.creepystory.model.story.StoryModel
import id.smartech.creepystory.remote.ApiClient.Companion.BASE_URL_IMAGE

class DetailStoryActivity : BaseActivity<ActivityDetailStoryBinding>() {

    private lateinit var viewModel: DetailStoryViewModel
    private lateinit var adapter: LabelAdapter
    private lateinit var actionMenu: Menu
    private var listLabel = ArrayList<LabelModel>()

    private var stId: Int? = 0
    private var meId: Int? = 0
    private var ctName: String? = null
    private var meName: String? = null
    private var stTitle: String? = null
    private var stContent: String? = null
    private var mePhotoProfile: String? = null
    private var stPhotoCover: String? = null
    private var stCreated: String? = null
    private var stFavoritedLength: String? = null
    private var meRole: String? = null
    private var meDescription: String? = null
    private var mePhotoCover: String? = null
    private var meDomicile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_story
        super.onCreate(savedInstanceState)

        setDataFromIntent()
        setOnClick()
        setRecyclerView()
        setViewModel()
        subscribeLiveData()
        setToolbarActionBar()

    }

    private fun setOnClick(){


        bind.tvName.setOnClickListener {
            var memberId = intent.getIntExtra("me_id", 0)
            if (memberId == 0) {
                Log.d("empty", "member id empty")
            } else {
                val intent = Intent(this, DetailProfileActivity::class.java)
                intent.putExtra("me_name", meName)
                intent.putExtra("me_id", meId)
                intent.putExtra("me_photo_profile", mePhotoProfile)
                intent.putExtra("me_photo_cover", mePhotoCover)
                intent.putExtra("me_domicile", meDomicile)
                intent.putExtra("me_description", meDescription)
                intent.putExtra("me_role", meRole)
                startActivity(intent)
            }

        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("st_title")

        bind.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_unfavorite -> {
                noticeToast("unfavorite clicked!")
            }

            R.id.nav_favorite -> {
                noticeToast("favorite clicked!")
            }

            R.id.nav_edit -> {
                noticeToast("edit clicked!")
            }
            R.id.nav_delete -> {
                showDeleteDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_story_menu, menu)
        actionMenu = menu!!

        val memberId = sharedPref.getMemberId()
        val memberIdStories = intent.getIntExtra("me_id", 0)

        noticeToast("Member id = $memberIdStories")

        if (memberId == memberIdStories) {
            actionMenu.findItem(R.id.nav_delete).isVisible = true
            actionMenu.findItem(R.id.nav_edit).isVisible = true

            viewModel.onSuccessCheckFavorite.observe(this) {
                if(it) {
                    actionMenu.findItem(R.id.nav_favorite).isVisible = true
                    actionMenu.findItem(R.id.nav_unfavorite).isVisible = false
                } else {
                    actionMenu.findItem(R.id.nav_favorite).isVisible = false
                    actionMenu.findItem(R.id.nav_unfavorite).isVisible = true
                }
            }

        } else {
            actionMenu.findItem(R.id.nav_delete).isVisible = false
            actionMenu.findItem(R.id.nav_edit).isVisible = false

            viewModel.onSuccessCheckFavorite.observe(this) {
                if(it) {
                    actionMenu.findItem(R.id.nav_favorite).isVisible = true
                    actionMenu.findItem(R.id.nav_unfavorite).isVisible = false
                } else {
                    actionMenu.findItem(R.id.nav_favorite).isVisible = false
                    actionMenu.findItem(R.id.nav_unfavorite).isVisible = true
                }
            }
        }


        return true
    }

    private fun setDataFromIntent() {
        stId = intent.getIntExtra("st_id", 0)
        meId = intent.getIntExtra("me_id", 0)
        ctName = intent.getStringExtra("ct_name")
        meName = intent.getStringExtra("me_name")
        stTitle = intent.getStringExtra("st_title")
        stContent = intent.getStringExtra("st_content")
        mePhotoProfile = BASE_URL_IMAGE + intent.getStringExtra("me_photo_profile")
        stPhotoCover = BASE_URL_IMAGE + intent.getStringExtra("st_photo_cover")
        stCreated = intent.getStringExtra("st_created").split("T")[0]
        stFavoritedLength = intent.getStringExtra("st_favorite_length")
        meRole = intent.getStringExtra("me_role")
        meDescription = intent.getStringExtra("me_description")
        meDomicile = intent.getStringExtra("me_domicile")
        mePhotoCover = intent.getStringExtra("me_photo_cover")

        bind.story = DetailStoryModel(
            stId = stId!!,
            meId = meId!!,
            meName = meName!!,
            stTitle = stTitle!!,
            stContent = stContent!!,
            mePhotoProfile = mePhotoProfile!!,
            stPhotoCover = stPhotoCover!!,
            stCreated = stCreated!!,
            stFavorited = stFavoritedLength!!,
            meDomicile = meDomicile!!,
            mePhotoCover = mePhotoCover!!,
            meRole = meRole!!,
            meDescription = meDescription!!
        )

        Glide.with(applicationContext)
            .load(stPhotoCover)
            .placeholder(R.drawable.white)
            .error(R.drawable.white)
            .into(bind.ivCover)

        Glide.with(applicationContext)
            .load(mePhotoProfile)
            .placeholder(R.drawable.white)
            .error(R.drawable.white)
            .into(bind.profilePhoto)

    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(DetailStoryViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.setServiceFavorite(createApi(this))
        viewModel.serviceCheckApi(
                meId = sharedPref.getMemberId(),
                stId = intent.getIntExtra("st_id", 0)
        )
        viewModel.getLabelByStory(intent.getIntExtra("st_id", 0))
    }

    private fun subscribeLiveData() {
        this?.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        this?.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        this?.let {
            viewModel.onFailLiveData.observe(it) { message ->
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvLabel.isNestedScrollingEnabled = false
        adapter = LabelAdapter(listLabel)
        bind.rvLabel.adapter = adapter

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        bind.rvLabel.layoutManager = layoutManager

    }

    override fun onStart() {
        super.onStart()
        viewModel.getLabelByStory(intent.getIntExtra("st_id", 0))
    }

    override fun onStop() {
        super.onStop()
        viewModel.getLabelByStory(intent.getIntExtra("st_id", 0))
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to delete this story?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            noticeToast("Cerita berhasil dihapus")
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }


}