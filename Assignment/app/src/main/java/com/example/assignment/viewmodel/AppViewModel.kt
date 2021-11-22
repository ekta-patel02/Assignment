package com.example.assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.model.AppData
import com.example.assignment.model.ListData
import com.example.assignment.repository.AppDataApi
import com.example.assignment.repository.AppRepository
import com.example.assignment.utils.NetworkUtils
import com.example.assignment.utils.Urls
import com.example.assignment.utils.WebServiceState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val listData: MutableLiveData<List<ListData?>?> = MutableLiveData()
    val errorMsg = MutableLiveData<String>()
    val isInternetNotAvailable = MutableLiveData<Boolean>()
    val webserviceState = MutableLiveData<WebServiceState>()
    val appTitle = MutableLiveData<String>()

    private val appRepository: AppRepository = AppRepository(application)

    init {
        val dbList = appRepository.getAllListData?.value
        if (!dbList.isNullOrEmpty()) {
            listData.postValue(dbList)
        } else {
            getAllAppData()
        }
    }

    fun getAllAppData() {
        if (!NetworkUtils.hasNetwork(getApplication())) {
            isInternetNotAvailable.postValue(true)
        } else {
            isInternetNotAvailable.postValue(false)
            makeRequest()
        }
    }

    private fun makeRequest() {
        webserviceState.postValue(WebServiceState.PROCESSING)
        val retrofit = Retrofit.Builder()
            .baseUrl(Urls.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: AppDataApi = retrofit.create(AppDataApi::class.java)
        val call: Call<AppData?>? = api.getAssignmentData()
        call?.enqueue(object : Callback<AppData?> {
            override fun onResponse(
                call: Call<AppData?>,
                response: Response<AppData?>
            ) {
                if (response.isSuccessful) {
                    val res: AppData? = response.body()
                    appRepository.deleteAndInsertData(res?.listData ?: ArrayList())
                    appTitle.postValue(res?.title.orEmpty())
                    webserviceState.postValue(WebServiceState.SUCCESS)
                }
            }

            override fun onFailure(call: Call<AppData?>, t: Throwable) {
                errorMsg.postValue(t.message)
                webserviceState.postValue(WebServiceState.FAILED)
            }
        })
    }
}