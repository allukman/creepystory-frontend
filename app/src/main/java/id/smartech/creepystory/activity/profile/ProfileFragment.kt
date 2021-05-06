package id.smartech.creepystory.activity.profile

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import id.smartech.creepystory.R
import id.smartech.creepystory.activity.onboard.OnBoardActivity
import id.smartech.creepystory.activity.profile.editprofile.EditProfileActivity
import id.smartech.creepystory.activity.story.liststory.ListStoryActivity
import id.smartech.creepystory.base.BaseFragment
import id.smartech.creepystory.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setSharedPref()
        setOnClick()
    }

    override fun onStart() {
        super.onStart()
        setSharedPref()
    }

    override fun onResume() {
        super.onResume()
        setSharedPref()
    }

    private fun setSharedPref() {

        Log.d("role", sharedPref.getDomicile())
        

        bind.tvName.text = sharedPref.getName()
        bind.tvEmail.text = sharedPref.getEmail()
        bind.tvLocation.text = sharedPref.getDomicile()
        bind.tvRole.text = sharedPref.getRole()
        bind.tvDesc.text = sharedPref.getDecs()

        val imgProfile = BASE_URL_IMAGE + sharedPref.getPhotoProfile()
        val imgCover = BASE_URL_IMAGE + sharedPref.getPhotoCover()

        Glide.with(requireContext())
                .load(imgProfile)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(bind.imageProfile)

        Glide.with(requireContext())
                .load(imgCover)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(bind.imageCover)

    }

    private fun setOnClick() {
        bind.btnEditProfile.setOnClickListener {
            intents<EditProfileActivity>(activity)
        }

        bind.btnLogout.setOnClickListener {
            showDialog()
            noticeToast("Logout Clicked")
        }

        bind.btnMyStory.setOnClickListener {
            sharedPref.createMyStories(
                    sharedPref.getMemberId(),
                    sharedPref.getName()
            )

            noticeToast(sharedPref.getMemberIdClick().toString())
            intents<ListStoryActivity>(activity)
        }

        bind.btnEditPassword.setOnClickListener {
            noticeToast("Clicked!")
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to logout?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            sharedPref.accountLogout()
            intents<OnBoardActivity>(activity)
            requireActivity().finish()

        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }

}