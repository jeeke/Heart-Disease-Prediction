package me.gyanesh.hdp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.gyanesh.hdp.data.model.Article
import me.gyanesh.hdp.data.network.HDPRepo
import me.gyanesh.hdp.data.Result
import me.gyanesh.hdp.data.model.TestInput
import me.gyanesh.hdp.data.model.TestReport
import me.gyanesh.hdp.data.model.Video
import me.gyanesh.hdp.util.ApiException
import me.gyanesh.hdp.util.io
import java.lang.Exception

class RootViewModel(
    private val repository: HDPRepo
) : ViewModel() {

    //numerical values
    var age: String? = null
    var trestbps: String? = null
    var chol: String? = null
    var thalach: String? = null
    var oldpeak: String? = null

    val testInput = TestInput()

    private val _articles = MutableLiveData<Result<List<Article>>>(Result.Idle)
    val articles: LiveData<Result<List<Article>>>
        get() = _articles


    private val _videos = MutableLiveData<Result<List<Video>>>(Result.Idle)
    val videos: LiveData<Result<List<Video>>>
        get() = _videos

    private val _testReport = MutableLiveData<Result<TestReport>>(Result.Idle)
    val testReport: LiveData<Result<TestReport>>
        get() = _testReport

    fun fetchArticles() {
        _articles.postValue(Result.Loading)
        io {
            _articles.postValue(repository.getAllArticles())
        }
    }

    fun fetchVideos() {
        _videos.postValue(Result.Loading)
        io {
            _videos.postValue(repository.getAllVideos())
        }
    }

    fun predictRisk() {
        testInput.age = age?.toIntOrNull()
        testInput.trestbps = trestbps?.toFloatOrNull()
        testInput.chol = chol?.toFloatOrNull()
        testInput.thalach = thalach?.toFloatOrNull()
        testInput.oldpeak = oldpeak?.toFloatOrNull()
        if (testInput.isValid()) {
            _testReport.postValue(Result.Loading)
            io {
                _testReport.postValue(repository.predictRisk(testInput))
            }
        } else _testReport.postValue(Result.Error(ApiException("Please enter valid values!")))

    }

    val reports = repository.getAllReports()

    init {
        fetchArticles()
        fetchVideos()
    }

}