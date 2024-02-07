package com.example.dyey.authentication.SignUp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.authentication.UploadID
import com.example.dyey.databinding.ActivitySignUpBinding
import com.example.dyey.homeFolder.ChatActivity
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.loginFolder.IntroductionActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SignUp : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    private lateinit var binding :ActivitySignUpBinding
    private val retrofitInstance = RetrofitInstance()
    private val apiService: ApiServices = retrofitInstance.apiService
    private lateinit var genderArray: Array<String>
    private val REQUEST_CAMERA = 100
    private val REQUEST_GALLERY = 101

    var image=""
    var first_name=""
    var last_name=""
    var gender=""
    var dob=""
    var education=""
    var height=""
    var profession=""
    var fav_food=""
    var about=""
    var email=""
    var pass=""
    var confirm_pass=""
    private var deviceId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        binding.back.setOnClickListener {
            startActivity(Intent(this, IntroductionActivity::class.java))
        }
        binding.login.setOnClickListener {
            validation()
            saveUserDataToSharedPreferences()
            postdataapi()
            checkIfUserSignedIn()

        }
        init()
    }

    fun init(){

        binding.dobLayout.setOnClickListener(){
            showDatePickerDialog()
        }
        binding.height.setOnClickListener(){
            showDropdownMenu(binding.height, R.array.height)
        }
        binding.gender.setOnClickListener(){
            showDropdownMenu(binding.gender, R.array.gender_array)
        }
        binding.education.setOnClickListener(){
            showDropdownMenu(binding.education, R.array.education)
        }
        binding.image.setOnClickListener(){
            openImagePicker()
        }

    }

    private fun openImagePicker() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        startActivityForResult(chooserIntent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    binding.image1.setImageBitmap(imageBitmap)
                    binding.image.visibility = View.GONE
                    binding.image2.visibility=View.VISIBLE
                    binding.image2.setOnClickListener(){
                        openImagePicker()
                    }

                }
                REQUEST_GALLERY -> {
                    val selectedImageUri = data?.data
                    val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    binding.image1.setImageBitmap(imageBitmap)
                    binding.image.visibility = View.GONE
                    binding.image2.visibility=View.VISIBLE
                    binding.image2.setOnClickListener(){
                        openImagePicker()
                    }

                }
            }
        }
    }


    private fun checkIfUserSignedIn() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            // User is signed in, navigate to main activity
            startActivity(Intent(this, ChatActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, ChatActivity::class.java))
            finish()
        }
    }



    private fun showDropdownMenu(view: View, arrayResourceId: Int) {
        val genderArray = resources.getStringArray(arrayResourceId)
        val popupMenu = PopupMenu(this, view)
        for (item in genderArray) {
            popupMenu.menu.add(item)
        }
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            (view as? TextView)?.text = menuItem.title
            true
        }
        popupMenu.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog and set this activity as the listener
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Set the selected date to the EditText
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        binding.dob.setText(formattedDate)
    }

    private fun saveUserDataToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Convert UserData object to JSON string
        val gson = Gson()
        val userDataJson = gson.toJson(editor)
        editor.putString("user_data", userDataJson)
        editor.apply()
    }

    private fun postdataapi() {

        val userData = SignUpDataClass()
        userData.email=email
        userData.password=pass
        userData.firstName=first_name
        userData.lastName=last_name
        userData.gender=gender
        userData.age=dob
        userData.education=education
        userData.height=height
        userData.profession=profession
        userData.favoriteFood=fav_food
        userData.password = pass
        userData.email = email
        userData.deviceType="A"
        userData.deviceId=deviceId


        val call: Call<SignupResponse> = apiService.postData(userData)

        call.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    // API call successful
                    saveUserDataToSharedPreferences()

                    // Navigate to HomeActivity
                    startActivity(Intent(this@SignUp, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@SignUp,"something went wrong in else block",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                // Handle network failure
                Toast.makeText(this@SignUp,"something went wrong in failure",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validation(){
        first_name = binding.fName.text.trim().toString()
        last_name = binding.lastName.text.trim().toString()
        gender = binding.gender.text.toString().trim()
        dob = binding.dob.text.toString().trim()
        education = binding.education.text.toString().trim()
        height = binding.height.text.toString().trim()
        profession = binding.profession.text.trim().toString()
        fav_food = binding.food.text.trim().toString()
        email = binding.gmail.text.trim().toString()
        pass = binding.password.text.trim().toString()
        confirm_pass = binding.confirmPass.text.trim().toString()
        about = binding.aboutme.text.trim().toString()
        if(first_name.length<=3){
            Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show()
        } else if(last_name.length<=3){
            Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show()
        }else if(gender.length<=3){
            Toast.makeText(this, "Please enter a Male or Female with same writing", Toast.LENGTH_SHORT).show()
        } else if(dob.length<=8){
            Toast.makeText(this, "Please enter correct DOB", Toast.LENGTH_SHORT).show()
        }else if(education.length<=2){
            Toast.makeText(this, "Please enter correct education", Toast.LENGTH_SHORT).show()
        } else if(height<="2"){
            Toast.makeText(this, "Please enter correct height", Toast.LENGTH_SHORT).show()
        }else if(profession.length<=2){
            Toast.makeText(this, "Please enter correct profession", Toast.LENGTH_SHORT).show()
        }else if(fav_food.length<=2){
            Toast.makeText(this, "Please enter valid food name", Toast.LENGTH_SHORT).show()
        } else if ( !email.contains("@") || !email.contains(".com") ) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
        }else if (!pass.contains("@") || pass.length<=5) {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show()
        }else if ( confirm_pass!=pass) {
            Toast.makeText(this, "Please enter same password", Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(this, UploadID::class.java))
            finish()
        }

    }
}