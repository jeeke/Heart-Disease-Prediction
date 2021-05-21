package me.gyanesh.hdp.data.network

import androidx.lifecycle.LiveData
import me.gyanesh.hdp.data.model.Article
import me.gyanesh.hdp.data.model.TestReport
import me.gyanesh.hdp.data.model.Video
import me.gyanesh.hdp.data.Result
import me.gyanesh.hdp.data.db.HDPDb
import me.gyanesh.hdp.data.model.TestInput
import me.gyanesh.hdp.ui.BaseActivity
import me.gyanesh.hdp.util.ApiException

class HDPRepo(
    private val api: HDPApi,
    private val db: HDPDb
) : SafeApiRequest() {

    suspend fun getAllArticles(): Result<List<Article>> {
        return apiRequest {
            api.getAllArticles(BaseActivity.currentLanguage)
        }
    }

    suspend fun getAllVideos(): Result<List<Video>> {
        return apiRequest {
            api.getAllVideos(BaseActivity.currentLanguage)
        }
    }

    suspend fun predictRisk(testInput: TestInput): Result<TestReport> {
        val r = apiRequest {
            api.predictRisk(testInput)
        }
        if (r is Result.Success) {
            if (r.data.message?.isNotBlank() == true) return Result.Error(ApiException(r.data.message.toString()))
            db.testReportDao().insert(listOf(r.data))
        }
        return r
    }

    fun getAllReports(): LiveData<List<TestReport>> = db.testReportDao().reports()

}