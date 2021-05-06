package id.smartech.creepystory.activity.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.favorite.FavoriteModel
import id.smartech.creepystory.model.favorite.FavoriteResponse
import id.smartech.creepystory.model.story.StoryModel
import id.smartech.creepystory.model.story.StoryResponse
import id.smartech.creepystory.services.FavoriteApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: FavoriteApiService

    val onSuccessLiveData = MutableLiveData<List<FavoriteModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job () + Dispatchers.Main

    fun setService(service: FavoriteApiService) {
        this.service = service
    }

    fun getFavoriteByMemberId(meId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getFavoriteByMeId(meId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is FavoriteResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    val list = response.data.map {
                        FavoriteModel(
                            faId = it.faId,
                            meId = it.meId,
                            stId = it.stId,
                            faCreated = it.faCreated,
                            meName = it.meName,
                            mePhotoProfile = it.mePhotoProfile,
                            meDomicile = it.meDomicile,
                            mePhotoCover = it.mePhotoCover,
                            meRole = it.meRole,
                            meDescription = it.meDescription,
                            stTitle = it.stTitle,
                            stPhotoCover = it.stPhotoCover,
                            stContent = it.stContent,
                            stFavorited = it.stFavorited,
                            stCreated = it.stCreated
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