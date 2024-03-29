package com.packy.data.remote.photo

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import com.packy.common.authenticator.ext.removeQueryParameters
import com.packy.data.model.photo.UploadPhotoUrl
import com.packy.lib.utils.Resource
import com.packy.lib.utils.Resource.Success
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class PhotoService @Inject constructor(
    private val httpClient: HttpClient,
    private val contentResolver: ContentResolver,
    private val context: Context
) {

    suspend fun getUploadUrl(fileName: String): Resource<UploadPhotoUrl> = safeRequest {
        httpClient.get("/api/v1/file/presigned-url/${fileName}.jpg")
    }

    @OptIn(InternalAPI::class)
    suspend fun uploadPhoto(
        fileName: String,
        uploadPhotoUrl: String,
        uri: String
    ): Resource<String> = withContext(Dispatchers.IO) {

        val uri = Uri.parse(uri)
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val bitmap = rotateBitmapIfRequired(
                BitmapFactory.decodeStream(inputStream),
                uri
            )
            val tempFile = File(
                context.cacheDir,
                "${fileName}.jpg"
            )
            try {
                FileOutputStream(tempFile).use { stream ->
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        50,
                        stream
                    )
                    val response = awsHttpClient.put(uploadPhotoUrl) {
                        body = tempFile.readBytes()
                        headers.append(
                            "Content-Type",
                            "application/octet-stream"
                        )
                    }
                    tempFile.delete()
                    bitmap.recycle()
                    if (response.status.value == 200) {
                        val imageUrl = response.request.url.toString().removeQueryParameters()
                        return@withContext Success(
                            imageUrl,
                            code = "200",
                            message = "Success Image Upload"
                        )
                    } else {
                        return@withContext Resource.NetworkError(Exception("Image Upload Error"))
                    }
                }
            } catch (e: Exception) {
                tempFile.delete()
                bitmap.recycle()
                return@withContext Resource.NetworkError(e)
            }
        }
        return@withContext Resource.NetworkError(Exception("Network Error"))
    }


    private val awsHttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i(
                        "Ktor",
                        message
                    )
                }
            }
            level = LogLevel.ALL
        }
    }

    private suspend fun rotateBitmapIfRequired(
        bitmap: Bitmap,
        uri: Uri
    ): Bitmap {
        return withContext(Dispatchers.Default) {
            val exif = try {
                if (uri.scheme == "content") {
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        ExifInterface(input)
                    }
                } else {
                    ExifInterface(uri.path!!)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }

            exif?.let {

                val rotation = when (it.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
                if (rotation != 0) {
                    val matrix = android.graphics.Matrix()
                    matrix.postRotate(rotation.toFloat())
                    Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        matrix,
                        true
                    )
                } else {
                    bitmap
                }
            } ?: bitmap
        }
    }
}
