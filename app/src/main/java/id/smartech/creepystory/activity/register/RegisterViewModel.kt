package id.smartech.creepystory.activity.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.model.account.LoginResponse
import id.smartech.creepystory.services.AccountApiService
import id.smartech.creepystory.util.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountApiService
    private lateinit var sharedPref: SharedPreference

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountApiService) {
        this.service = service
    }

    fun setSharedPref(sharedPref: SharedPreference) {
        this.sharedPref = sharedPref
    }

    fun RegisterRequest(email: String, password: String) {
        launch {
            isLoadingLiveData.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service.RegisterAccount(email, password)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        onSuccessLiveData.value = false
                        onFailLiveData.value = "Gagal Membuat Account"
                    }
                }
            }
            if (result is GeneralResponse) {
                isLoadingLiveData.value = false
                if(result.success) {
                    onSuccessLiveData.value = true
                } else {
                    onFailLiveData.value = "Gagal Membuat Account\""
                }
            }
        }
    }


}