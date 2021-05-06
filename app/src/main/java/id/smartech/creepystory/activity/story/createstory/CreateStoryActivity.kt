package id.smartech.creepystory.activity.story.createstory

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import id.smartech.creepystory.R
import id.smartech.creepystory.base.BaseActivity
import id.smartech.creepystory.databinding.ActivityCreateStoryBinding
import id.smartech.creepystory.util.FileHelper.Companion.createPartFromFile
import id.smartech.creepystory.util.FileHelper.Companion.createPartFromString
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreateStoryActivity : BaseActivity<ActivityCreateStoryBinding>() {
    private lateinit var viewModel: CreateStoryViewModel
    private var category: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_create_story
        super.onCreate(savedInstanceState)

        setOnClick()
        setSpinner()
        setViewModel()

    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(CreateStoryViewModel::class.java)
        viewModel.setService(createApi(this))
    }

    private fun setSpinner() {
        val categoryOption = arrayOf("Creepy Pasta", "Riddle", "Urban Legend", "Real Story")
        bind.spinnerListProject.adapter = ArrayAdapter<String>(this, R.layout.style_spinner, categoryOption)
        bind.spinnerListProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Toast.makeText(this@CreateStoryActivity, categoryOption[position], Toast.LENGTH_SHORT).show()

                when {
                    categoryOption[position] == "Creepy Pasta" -> {
                        category = 1
//                        noticeToast(category.toString())
                    }
                    categoryOption[position] == "Riddle" -> {
                        category = 2
//                        noticeToast(category.toString())
                    }
                    categoryOption[position] == "Urban Legend" -> {
                        category = 3
//                        noticeToast(category.toString())
                    }
                    categoryOption[position] == "Real Story" -> {
                        category = 4
//                        noticeToast(category.toString())
                    }
                    else -> {
                        category = 1
//                        noticeToast(category.toString())
                    }
                }

            }
        }
    }

    private fun setOnClick() {
        bind.ivImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions,
                            PERMISSION_CODE
                    );
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        bind.btnCreateStory.setOnClickListener {
            val ctId = createPartFromString(category.toString())
            val meId = createPartFromString(sharedPref.getMemberId().toString())
            val title = createPartFromString(bind.etTitle.text.toString())
            val content = createPartFromString(bind.etContent.text.toString())

            noticeToast("dor")
            viewModel.createStoryWithoutImage(ctId, meId, title, content)

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            bind.ivImage.setImageURI(data?.data)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)
            Log.d("FileName", file.name)

            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("photo", file.name, it1)
            }

        }
    }

    private fun getPath(context: Context, contentUri: Uri) : String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }
}