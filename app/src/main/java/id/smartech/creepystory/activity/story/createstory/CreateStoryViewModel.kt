package id.smartech.creepystory.activity.story.createstory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.services.StoryApiService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.coroutines.CoroutineContext

class CreateStoryViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: StoryApiService

    val onSuccessLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: StoryApiService) {
        this.service = service
    }

    fun createStory(ctId: RequestBody,
                    meId: RequestBody,
                    title: RequestBody,
                    content: RequestBody,
                    image: MultipartBody.Part? = null) {

        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.createStory(ctId, meId, title, content, image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is GeneralResponse) {
                Log.d("responseSuccess", result.toString())
            }
        }
    }

    fun createStoryWithoutImage(ctId: RequestBody,
                    meId: RequestBody,
                    title: RequestBody,
                    content: RequestBody) {

        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.createStoryWithoutImage(ctId, meId, title, content)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is GeneralResponse) {
                Log.d("responseSuccess", result.toString())
            }
        }
    }
}