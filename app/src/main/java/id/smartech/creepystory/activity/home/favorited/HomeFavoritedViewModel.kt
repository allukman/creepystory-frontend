package id.smartech.creepystory.activity.home.favorited

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.story.StoryModel
import id.smartech.creepystory.model.story.StoryResponse
import id.smartech.creepystory.services.StoryApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeFavoritedViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: StoryApiService

    val onSuccessLiveData = MutableLiveData<List<StoryModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job () + Dispatchers.Main

    fun setService(service: StoryApiService) {
        this.service = service
    }

    fun getStoryByFavorite() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllStoryByFavorite()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is StoryResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    val list = response.data.map {
                        StoryModel(
                            stId = it.stId,
                            ctId = it.ctId,
                            meId = it.meId,
                            ctName = it.ctName,
                            meName = it.meName,
                            stTitle = it.stTitle,
                            stContent = it.stContent,
                            mePhotoProfile = it.mePhotoProfile,
                            stPhotoCover = it.stPhotoCover,
                            stCreated = it.stCreated,
                            stUpdated = it.stUpdated,
                            stFavorited = it.stFavorited,
                            meDescription = it.meDescription,
                            meRole = it.meRole,
                            mePhotoCover = it.mePhotoCover,
                            meDomicile = it.meDomicile
                        )
                    }
                    onSuccessLiveData.value = list
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }
}