package id.smartech.creepystory.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.creepystory.model.account.LoginResponse
import id.smartech.creepystory.services.AccountApiService
import id.smartech.creepystory.util.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountApiService
    private lateinit var sharedPref: SharedPreference

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountApiService) {
        this@LoginViewModel.service = service
    }

    fun setSharedPref(sharedPref: SharedPreference) {
        this@LoginViewModel.sharedPref = sharedPref
    }

    fun loginRequest(email: String, password: String) {
        launch {
            isLoadingLiveData.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(email, password)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        onSuccessLiveData.value = false
                        onFailLiveData.value = "Username / Password salah"
                    }
                }
            }
            if (result is LoginResponse) {
                isLoadingLiveData.value = false
                if(result.success) {
                    val data = result.data
                    sharedPref.createAccount(
                            acId = data.acId,
                            meName = data.meName,
                            acEmail = data.acEmail,
                            acPhone = data.acPhone,
                            meId = data.meId,
                            meRole = data.meRole,
                            meDomicile = data.meDomicile,
                            meDescription = data.meDescription,
                            meDob = data.meDob,
                            meGender = data.meGender,
                            mePhotoProfile = data.mePhotoProfile,
                            mePhotoCover = data.mePhotoCover,
                            token = data.token
                    )
                    onSuccessLiveData.value = true
                } else {
                    onFailLiveData.value = "Password Anda Salah"
                }
            }
        }
    }
}