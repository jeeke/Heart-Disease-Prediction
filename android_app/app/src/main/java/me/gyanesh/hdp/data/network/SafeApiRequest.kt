package me.gyanesh.hdp.data.network

import me.gyanesh.hdp.R
import me.gyanesh.hdp.util.ApiException
import me.gyanesh.hdp.util.getString
import me.gyanesh.hdp.data.Result
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                response.body()?.let { return Result.Success(it) }
                return Result.EmptySuccess
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                val errorCode = response.code()
                error?.let {
                    try {
//                        if (errorCode == 406) {
//                            NblikApp.logout(true)
//                        }
                        if (JSONObject(it).has("error")) {
                            message.append(JSONObject(it).get("error"))
                        }
                        if (JSONObject(it).has("errors")) {
                            val obj = JSONObject(it).get("errors")
                            if (obj is JSONArray && obj.length() > 0) {
                                message.append(obj.get(0))
                            } else if (obj is String) {
                                message.append(obj)
                            }
                        }
                        if (JSONObject(it).has("notice")) {
                            message.append(JSONObject(it).get("notice"))
                        }
//                        if (message.toString() == "You are not Authorized") {
//                            NblikApp.logout()
//                        }
                        if (message.toString().isBlank()) {
                            message.append(getString(R.string.something_wrong))
                        }
                    } catch (e: JSONException) {
                        throw ApiException(getString(R.string.something_wrong))
                    }
                }
                throw ApiException(message.toString())
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}

