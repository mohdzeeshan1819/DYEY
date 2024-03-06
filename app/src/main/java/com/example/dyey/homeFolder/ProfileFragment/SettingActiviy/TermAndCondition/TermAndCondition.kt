package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.databinding.ActivityTermAndConditionBinding
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.SettingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermAndCondition : AppCompatActivity() {
    private lateinit var binding: ActivityTermAndConditionBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private lateinit var adapter: TermAndConditionAdapter
    private var offerList: ArrayList<Details> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTermAndConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name=intent.getStringExtra("pageName")
        binding.pageName.text=name.toString()
        termAndCondition()
        binding.back.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }


    private fun termAndCondition(){
        val request=TermAndConditionRequest(
            userId = AppInfo.getUserDeviceId()?.toInt()
        )
        apiService?.termAndCondition(request)?.enqueue(object : Callback<TermAndConditionResponse> {
            override fun onResponse(
                call: Call<TermAndConditionResponse>,
                response: Response<TermAndConditionResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody: TermAndConditionResponse? =response.body()
                    if(responseBody?.status==true){
//                        startActivity(Intent(this@TermAndCondition, SignUp::class.java))
//                        finish()
                        setRecyclerView(offerList)


                    } else{
                        Log.d("notdelete",responseBody.toString())
                    }
                } else{
                    Log.d("notdelete2",response.toString())
                }
            }

            override fun onFailure(call: Call<TermAndConditionResponse>, t: Throwable) {
                Log.d("notdelete3",t.toString())

            }

        })
    }

    private fun setRecyclerView(offerList: ArrayList<Details>){
        binding.term.layoutManager = LinearLayoutManager(this)
        adapter = TermAndConditionAdapter(offerList)
        binding.term.adapter = adapter
        Log.d("recycler","data is coming")

    }

}