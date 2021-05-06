package id.smartech.creepystory.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {
    companion object {
        const val PREF_NAME = "LOGIN"
        const val LOGIN = "IS_LOGIN"
        const val TOKEN = "TOKEN"
        const val AC_ID = "AC_ID"
        const val ME_NAME = "ME_NAME"
        const val AC_EMAIL = "AC_EMAIL"
        const val AC_PHONE = "AC_PHONE"
        const val ME_ID = "ME_ID"
        const val ME_DOMICILE = "ME_DOMICILE"
        const val ME_DESCRIPTION = "ME_DESCRIPTION"
        const val ME_ROLE = "ME_ROLE"
        const val ME_DOB = "ME_DOB"
        const val ME_GENDER = "ME_GENDER"
        const val ME_PHOTO_PROFILE = "ME_PIC_IMAGE"
        const val ME_PHOTO_COVER = "ME_PIC_COVER_IMAGE"
        const val ST_LENGTH = "ST_LENGTH"

        const val ME_ID_CLICK = "ME_ID_CLICK"
        const val ME_NAME_CLICK = "ME_NAME_CLICK"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

//    GET DATA

    fun getToken(): String {
        return sharedPreferences.getString(TOKEN, "")!!
    }

    fun getMemberId(): Int {
        return sharedPreferences.getInt(ME_ID, 0)!!
    }

    fun getMemberIdClick(): Int {
        return sharedPreferences.getInt(ME_ID_CLICK, 0)!!
    }

    fun getName(): String {
        return sharedPreferences.getString(ME_NAME, "")!!
    }

    fun getNameClick(): String {
        return sharedPreferences.getString(ME_NAME_CLICK, "")!!
    }

    fun getEmail(): String {
        return sharedPreferences.getString(AC_EMAIL, "")!!
    }

    fun getDomicile(): String {
        return sharedPreferences.getString(ME_DOMICILE, "")!!
    }

    fun getRole(): String {
        return sharedPreferences.getString(ME_ROLE, "")!!
    }

    fun getDecs(): String {
        return sharedPreferences.getString(ME_DESCRIPTION, "")!!
    }

    fun getPhotoProfile(): String {
        return sharedPreferences.getString(ME_PHOTO_PROFILE, "")!!
    }

    fun getPhotoCover(): String {
        return sharedPreferences.getString(ME_PHOTO_COVER, "")!!
    }

    fun getStoryLength(): Int {
        return sharedPreferences.getInt(ST_LENGTH, 0)!!
    }

//    LOGOUT

    fun accountLogout() {
        editor.clear()
        editor.commit()
    }

//    LOGIN

    fun createAccount(
            acId: Int,
            meName: String,
            acEmail: String,
            acPhone: String,
            meId: Int,
            meRole: String,
            meDomicile: String? = null,
            meDescription: String? = null,
            meDob: String? = null,
            meGender: String? = null,
            mePhotoProfile: String? = null,
            mePhotoCover: String? = null,
            token: String
    ) {
        editor.putBoolean(LOGIN, true)
        editor.putString(TOKEN, token)
        editor.putInt(AC_ID, acId)
        editor.putString(ME_NAME, meName)
        editor.putString(AC_EMAIL, acEmail)
        editor.putString(AC_PHONE, acPhone)
        editor.putInt(ME_ID, meId)
        editor.putString(ME_ROLE, meRole)
        editor.putString(ME_DOMICILE, meDomicile)
        editor.putString(ME_DESCRIPTION, meDescription)
        editor.putString(ME_DOB, meDob)
        editor.putString(ME_GENDER, meGender)
        editor.putString(ME_PHOTO_PROFILE, mePhotoProfile)
        editor.putString(ME_PHOTO_COVER, mePhotoCover)
        editor.commit()
    }

    fun saveLength(storyLength: Int) {
        editor.putInt(ST_LENGTH, storyLength)
        editor.commit()
    }

    fun createMyStories(meId: Int, meName: String) {
        editor.putInt(ME_ID_CLICK, meId)
        editor.putString(ME_NAME_CLICK, meName)
        editor.commit()
    }
}