package com.example.assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.model.AppData
import com.example.assignment.model.ListData
import com.example.assignment.repository.AppDataApi
import com.example.assignment.repository.AppRepository
import com.example.assignment.utils.Urls
import com.example.assignment.utils.WebServiceState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val listData: MutableLiveData<List<ListData?>?> = MutableLiveData()
    val errormsg = MutableLiveData<String>()
    val snackbarMsgStr = MutableLiveData<String>()
    val webserviceState = MutableLiveData<WebServiceState>()

    val appRepository: AppRepository = AppRepository(application)

    init {
        val dbList = appRepository.getAllListData?.value
        if (!dbList.isNullOrEmpty()) {
            listData.postValue(dbList)
        }
    }

    fun getAllAppData() {
        makeRequest()
    }

    private fun makeRequest() {
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
                    Timber.d("Success: " + res?.title + " Size: ${res?.listData?.size}")
                    //insert list in DB
                    appRepository.insert(res?.listData ?: ArrayList())
                    //Save title in sharedPref
                }
            }

            override fun onFailure(call: Call<AppData?>, t: Throwable) {
                Timber.e("ApiCall onFailure: ${t.message}")
                errormsg.postValue(t.message)
            }
        })
    }

}