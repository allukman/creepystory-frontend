package id.smartech.creepystory.util

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FileHelper {
    companion object {
        fun createPartFromString(descriptionString: String): RequestBody {
            return descriptionString.toRequestBody(MultipartBody.FORM)
        }

        fun createPartFromFile(path: String): MultipartBody.Part {
            val file = File(path)
            val reqFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

            return MultipartBody.Part.createFormData("image", file.name, reqFile)
        }
    }
}