package id.smartech.creepystory.activity.story.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.model.favorite.FavoriteResponse
import id.smartech.creepystory.model.label.LabelModel
import id.smartech.creepystory.model.label.LabelResponse
import id.smartech.creepystory.services.FavoriteApiService
import id.smartech.creepystory.services.LabelApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DetailStoryViewModel: ViewModel(), CoroutineScope {
    private lateinit var labelService: LabelApiService
    private lateinit var favoriteService: FavoriteApiService

    val onSuccessLiveData = MutableLiveData<List<LabelModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val onSuccessFavorite = MutableLiveData<String>()
    val onSuccessCheckFavorite = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job () + Dispatchers.Main

    fun setService(service: LabelApiService) {
        labelService = service
    }

    fun setServiceFavorite(service: FavoriteApiService) {
        favoriteService = service
    }

    fun getLabelByStory(storyId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    labelService.getLabelByStoryId(storyId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is LabelResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    val list = response.data.map {
                        LabelModel(
                            laId = it.laId,
                            stId = it.stId,
                            laName = it.laName
                        )
                    }
                    onSuccessLiveData.value = list
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceCheckApi(meId: Int, stId: Int) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    favoriteService.checkIsFavorite(
                            meId = meId ,
                            stId = stId
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is GeneralResponse) {
                onSuccessCheckFavorite.value = response.success
            }
        }
    }
}