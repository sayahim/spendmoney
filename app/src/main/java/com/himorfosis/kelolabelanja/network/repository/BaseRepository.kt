package com.himorfosis.kelolabelanja.network.repository

import com.himorfosis.kelolabelanja.network.state.StateNetwork
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

open class BaseRepository {

    fun <T> errorResponse(e: Throwable): StateNetwork<T> {
        return when (e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                try {
                    val result = JSONObject(errorBody.toString())
                    val status = result.getInt("status")
                    val message = result.getString("message")
                    val error = result.getString("error")
                    StateNetwork.OnError(status, error, message)
                } catch (e: Exception) {
                    StateNetwork.OnFailure(JSONException("Something Wrong!"))
                }
            }
            is TimeoutException -> StateNetwork.OnFailure(TimeoutException("Connection Timeout!"))
            is IOException -> StateNetwork.OnFailure(IOException("Connection Problem"))
            else -> StateNetwork.OnFailure(e)
        }
    }

    fun messageStatus(response: String): String {
        val data: String
        data = try {
            val result = JSONObject(response)
            val status = result.getJSONObject("status")
            val message = status.getString("message")
            message
        } catch (e: JSONException) {
            "Invalid JSON format"
        } catch (e: IOException) {
            "Something Wrong!"
        }
        return data
    }

}