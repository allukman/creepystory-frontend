package id.smartech.creepystory.activity.profile.detailprofile

import android.os.Bundle
import com.bumptech.glide.Glide
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.story.liststory.ListStoryActivity
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityDetailProfileBinding
import id.smartech.creepystory.model.profile.ProfileModel
import id.smartech.creepystory.remote.ApiClient
import id.smartech.creepystory.remote.ApiClient.Companion.BASE_URL_IMAGE

class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding>() {

    private var meId: Int? = 0
    private var meName: String? = null
    private var mePhotoProfile: String? = null
    private var meRole: String? = null
    private var meDescription: String? = null
    private var mePhotoCover: String? = null
    private var meDomicile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_profile
        super.onCreate(savedInstanceState)

        setDataFromIntent()
        onClick()
    }

    private fun setDataFromIntent() {
        meId = intent.getIntExtra("me_id", 0)
        meName = intent.getStringExtra("me_name")
        mePhotoProfile = intent.getStringExtra("me_photo_profile")
        meRole = intent.getStringExtra("me_role")
        meDescription = intent.getStringExtra("me_description")
        meDomicile = intent.getStringExtra("me_domicile")
        mePhotoCover = BASE_URL_IMAGE + intent.getStringExtra("me_photo_cover")

        bind.profile = ProfileModel(
                meId = meId!!,
                meName = meName!!,
                meDescription = meDescription!!,
                meRole = meRole!!,
                mePhotoCover = mePhotoCover,
                meDomicile = meDomicile!!,
                mePhotoProfile = mePhotoProfile
        )

        Glide.with(applicationContext)
                .load(mePhotoCover)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.imageCover)

        Glide.with(applicationContext)
                .load(mePhotoProfile)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.imageProfile)

    }

    private fun onClick() {
        bind.btnMyStory.setOnClickListener {
            val memberId = intent.getIntExtra("me_id", 0)
            val memberName = intent.getStringExtra("me_name")
            sharedPref.createMyStories(memberId, memberName)
            noticeToast(sharedPref.getMemberIdClick().toString())
            intents<ListStoryActivity>(this)
        }
    }
}