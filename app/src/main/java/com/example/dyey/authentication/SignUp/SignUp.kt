package com.example.dyey.authentication.SignUp

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RealPathUtil
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.apiInterfaces.UploadImage.UploadImageInstance
import com.example.dyey.apiInterfaces.UploadImage.UploadImageResponse
import com.example.dyey.authentication.SignIn.SignIn
import com.example.dyey.databinding.ActivitySignUpBinding
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.loginFolder.IntroductionActivity
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SignUp : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivitySignUpBinding
    private val retrofitInstance = RetrofitInstance()
    private val retrofitInstanceUpload = UploadImageInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private val apiServiceUpload: ApiServices? = retrofitInstanceUpload.apiService
    private lateinit var genderArray: Array<String>
    private val REQUEST_CAMERA = 100
    private val REQUEST_GALLERY = 101
    var SELECT_PICTURE = 200
    private val CAMERA_PERMISSION_CODE = 100
    private val STORAGE_PERMISSION_CODE = 101
    private var cameraPermession = false
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private var deviceToken = ""
    private var deviceId = ""
    var image: Uri?=null
    var first_name = ""
    var last_name = ""
    var gender = ""
    var dob = ""
    var education = ""
    var height = ""
    var profession = ""
    var fav_food = ""
    var about = ""
    var email = ""
    var pass = ""
    var confirm_pass = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        checkLoginState()

        getAdvertisingId { advertisingId ->
            if (advertisingId.isNotEmpty()) {
                println("Advertising ID: $advertisingId")
                deviceId = advertisingId
            } else {
                println("Failed to retrieve Advertising ID")
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token1 = task.result
                Log.d("checkingData1", token1.toString())
                deviceToken = token1
                // Use the token as needed (e.g., send it to your server)
            } else {
                Log.d("checkingData0", "error ")
                // Handle token generation error
            }
        }
        init()

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform your file operation here
                verifyStoragePermissions(this)

                checkPermission(
                    Manifest.permission.CAMERA,
                    CAMERA_PERMISSION_CODE
                )
                imageChooser()
            } else {
                // Permission denied, handle accordingly (show a message, disable functionality, etc.)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkLoginState() {
        val isLoggedIn = AppInfo.getIsLoggedIn()
        if (isLoggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    fun init() {
        try {
            binding.signin.setOnClickListener {
                startActivity(Intent(this, SignIn::class.java))
            }
        } catch (e: Exception) {
            Log.d("exception", e.toString())
        }

        binding.login.setOnClickListener {
            validation()
        }
        binding.back.setOnClickListener {
            startActivity(Intent(this, IntroductionActivity::class.java))
        }

        binding.dob.setOnClickListener() {
            showDatePickerDialog()
        }
        binding.dropdownBirthday.setOnClickListener() {
            showDatePickerDialog()
        }
        binding.height.setOnClickListener() {
            showDropdownMenu(binding.height, R.array.height)
        }
        binding.dropdownHeight.setOnClickListener() {
            showDropdownMenu(binding.height, R.array.height)
        }
        binding.gender.setOnClickListener() {
            showDropdownMenu(binding.gender, R.array.gender_array)
        }
        binding.dropdownGender.setOnClickListener() {
            showDropdownMenu(binding.gender, R.array.gender_array)
        }
        binding.education.setOnClickListener() {
            showDropdownMenu(binding.education, R.array.education)
        }
        binding.dropdownEducation.setOnClickListener() {
            showDropdownMenu(binding.education, R.array.education)
        }
        binding.image.setOnClickListener() {
//            openImagePicker()
            verifyStoragePermissions(this)

            checkPermission(
                Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE
            )
            imageChooser()
        }
    }

//    private fun openImagePicker() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
//        startActivityForResult(chooserIntent, REQUEST_GALLERY)
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    Log.d("checkignResponse","camera selected")
                    image = (data?.extras?.get("data") as Uri)
                    binding.image1.setImageURI(image)
                    binding.image.visibility = View.GONE
                    binding.image2.visibility = View.VISIBLE
                    binding.image2.setOnClickListener() {
                        verifyStoragePermissions(this)

                        checkPermission(
                            Manifest.permission.CAMERA,
                            CAMERA_PERMISSION_CODE
                        )
                        imageChooser()
                    }
                    try {
                        uploadPhotoFunction(image!!)

                    }catch (E:Exception){
                        Log.d("checkignResponse1", "camera selected$E")


                    }

                }
                REQUEST_GALLERY -> {
                    Log.d("checkignResponse","gallery selected")
                    image = data?.data!!
                    val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image)
                    binding.image1.setImageBitmap(imageBitmap)
                    binding.image.visibility = View.GONE
                    binding.image2.visibility = View.VISIBLE
                    binding.image2.setOnClickListener() {
                        verifyStoragePermissions(this)

                        checkPermission(
                            Manifest.permission.CAMERA,
                            CAMERA_PERMISSION_CODE
                        )
                        imageChooser()
                    }
                    try {
                        uploadPhotoFunction(image!!)

                    }catch (E:Exception){
                        Log.d("checkignResponse2", "gallery selected$E")


                    }

                }
            }
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

    fun imageChooser() {
        verifyStoragePermissions(this)
        // create an instance of the
        // intent of the type image
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        startActivityForResult(chooserIntent, REQUEST_GALLERY)
    }



    fun verifyStoragePermissions(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            Log.d("camera permission", "else statement 1026")
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                requestCode
            )
        } else {
            cameraPermession = true
        }
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
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        binding.dob.setText(formattedDate)
    }

    private fun saveUserDataToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val userDataJson = gson.toJson(editor)
        editor.putString("user_data", userDataJson)
        editor.apply()
    }

    private fun postdataapi() {




        apiService?.signUp(
            about = about,
            birthday = dob,
            education = education,
            email = email,
            favoriteFood = fav_food,
            firstName = first_name,
            gender = gender,
            height = height,
            lastName = last_name,
            profession = profession,
            profileImageUrl = image.toString(),
            sign = "true",
            city = "Ropad",
            deviceId = deviceId,
            firebaseToken = deviceToken,
            deviceType = "A",
            latitude = 0.0,
            longitude = 0.0,
            state = "Some state",
            password = pass,
            age = "30"
        )?.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if (response.isSuccessful) {
                    val model: SignupResponse? = response.body()
                    if (model!!.status == true) {
                        AppInfo.setUserDeviceId(deviceId)
                        AppInfo.setFcmToken(deviceToken)
                        try {
                            AppInfo.setFirstName(model.data!!.firstName.toString())
                            AppInfo.setLastName(model.data!!.lastName.toString())
                            AppInfo.setGender(model.data!!.gender.toString())
                            AppInfo.setDateOfBirth(model.data!!.birthday.toString())
                            AppInfo.setEducation(model.data!!.education.toString())
                            AppInfo.setHeight(model.data!!.height.toString())
                            AppInfo.setProfession(model.data!!.profession.toString())
                            AppInfo.setFavoriteFood(model.data!!.favoriteFood.toString())
                            AppInfo.setEmail(model.data!!.email.toString())
                            AppInfo.setPassword(model.data!!.password.toString())
                            if (model.data?.profileImageUrl != "null" && model.data?.profileImageUrl != "") {
                                AppInfo.setUserImage(model.data?.profileImageUrl.toString())
                            }
                        } catch (e: Exception) {
                            Log.d("crashHandle", e.toString())
                        }
                    }
                } else {
                    Toast.makeText(this@SignUp, response.toString(), Toast.LENGTH_SHORT).show()
                }
                Log.d("SignUpresponce", response.toString())
                Log.d("SignUpresponce", response.toString())
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                // Handle network failure
                Log.d("SignUp", t.toString())
                Toast.makeText(this@SignUp, "something went wrong in failure", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun validation() {
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
        if (first_name.length <= 3) {
            Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show()
        } else if (last_name.length <= 3) {
            Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show()
        } else if (gender.length <= 3) {
            Toast.makeText(
                this,
                "Please enter a Male or Female with same writing",
                Toast.LENGTH_SHORT
            ).show()
        } else if (dob.length <= 8) {
            Toast.makeText(this, "Please enter correct DOB", Toast.LENGTH_SHORT).show()
        } else if (education.length <= 2) {
            Toast.makeText(this, "Please enter correct education", Toast.LENGTH_SHORT).show()
        } else if (height <= "2") {
            Toast.makeText(this, "Please enter correct height", Toast.LENGTH_SHORT).show()
        } else if (profession.length <= 2) {
            Toast.makeText(this, "Please enter correct profession", Toast.LENGTH_SHORT).show()
        } else if (fav_food.length <= 2) {
            Toast.makeText(this, "Please enter valid food name", Toast.LENGTH_SHORT).show()
        } else if (!email.contains("@") || !email.contains(".com")) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
        } else if (!pass.contains("@") || pass.length <= 5) {
            Toast.makeText(
                this,
                "Please enter minimum 6 latter and atleast 1 @ in password field",
                Toast.LENGTH_SHORT
            ).show()
        } else if (confirm_pass != pass) {
            Toast.makeText(this, "Please enter same password", Toast.LENGTH_SHORT).show()
        } else {
            try{
                saveUserDataToSharedPreferences()
                postdataapi()
                startActivity(Intent(this@SignUp, HomeActivity::class.java))
                finish()
                Toast.makeText(this, "all authentication rules are verified", Toast.LENGTH_SHORT)
                    .show()
            }catch (e:Exception){
                Log.d("signin",e.toString())
            }

        }
    }

    fun getAdvertisingId(callback: (String) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val advertisingInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
                val id = advertisingInfo.id
                callback(id.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                callback("")
            }
        }
    }


    private fun uploadPhotoFunction(imageUri: Uri) {
        val filePath: String? = RealPathUtil.getRealPath(this, imageUri)
        val imageFile = File(filePath.toString())
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("files", imageFile.name, requestFile)
        Log.d("xeeshu1", filePath.toString())
        Log.d("xeeshu2", imageFile.toString())
        Log.d("xeeshu3", imagePart.toString())

        val call = apiServiceUpload?.uploadImage(imagePart)
        call?.enqueue(object : Callback<UploadImageResponse> {
            override fun onResponse(call: Call<UploadImageResponse>, response: Response<UploadImageResponse>) {
                val responsebody=response.body()
                if (response.isSuccessful) {
                    if(responsebody?.status==true){
                        Log.d("xeeshu", responsebody.toString())

                    }else{
                        Log.d("xeeshu", responsebody.toString())
                    }
                } else {
                    Log.d("shobhit_check", response.toString())
                    Log.d("shobhit_check", responsebody.toString())

                }
            }
            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                // Network request failed, handle error
                Log.d("onFalior", t.toString())
            }
        })
    }


}
