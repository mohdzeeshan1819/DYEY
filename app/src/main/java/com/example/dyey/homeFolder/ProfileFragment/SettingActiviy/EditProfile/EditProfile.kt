package com.example.dyey.homeFolder.ProfileFragment.EditProfile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import com.example.dyey.R
import com.example.dyey.apiInterfaces.ApiServices
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.apiInterfaces.RealPathUtil
import com.example.dyey.apiInterfaces.RetrofitInstance
import com.example.dyey.apiInterfaces.UploadImage.UploadImageInstance
import com.example.dyey.apiInterfaces.UploadImage.UploadImageResponse
import com.example.dyey.authentication.SignIn.SignIn
import com.example.dyey.authentication.SignUp.SignupResponse
import com.example.dyey.databinding.ActivityEditProfileBinding
import com.example.dyey.homeFolder.HomeActivity
import com.example.dyey.homeFolder.HomeFragment.Users
import com.example.dyey.homeFolder.OfferFragment.OfferDetails
import com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.SettingActivity
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

class EditProfile : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding:ActivityEditProfileBinding
    private val retrofitInstance = RetrofitInstance()
    private val retrofitInstanceUpload = UploadImageInstance()
    private val apiService: ApiServices? = retrofitInstance.apiService
    private val apiServiceUpload: ApiServices? = retrofitInstanceUpload.apiService
    private var userDetails: ArrayList<Users> = ArrayList()

    private lateinit var genderArray: Array<String>
    private val REQUEST_CAMERA = 100
    private val REQUEST_GALLERY = 101
    var SELECT_PICTURE = 200
    private val CAMERA_PERMISSION_CODE = 100
    private val STORAGE_PERMISSION_CODE = 101
    private var cameraPermession = false
    private val REQUEST_EXTERNAL_STORAGE = 1
    var image: Uri?=null
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private var deviceToken = ""
    private var deviceId = ""
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
    private var lastClickedImageView: ImageView? = null
    private var lastclickedvisible: ImageView? = null
    private var camera: ImageView? = null




    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAdvertisingId { advertisingId ->
            if (advertisingId.isNotEmpty()) {
                println("Advertising ID: $advertisingId")
                deviceId = advertisingId
            } else {
                println("Failed to retrieve Advertising ID")
            }
        }
        Log.d("checkingData1", AppInfo.getToken())


        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token1 = task.result
                Log.d("checkingData1", token1)
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




    @RequiresApi(34)
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
            startActivity(Intent(this, SettingActivity::class.java))
        }

        binding.dobtext.setOnClickListener() {
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

        binding.plus1.setOnClickListener {
            lastClickedImageView = binding.id1
            lastclickedvisible=binding.plus1
            camera=binding.camera2
            verifyStoragePermissions(this)
            checkPermission(
                Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE
            )
            imageChooser()
        }
        binding.plus2.setOnClickListener {
            lastClickedImageView = binding.id2
            lastclickedvisible=binding.plus2
            camera=binding.camera3

            verifyStoragePermissions(this)
            checkPermission(
                Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE
            )
            imageChooser()
        }
        binding.image1.setOnClickListener {
            lastClickedImageView = binding.image1
            lastclickedvisible=binding.image
            camera=binding.image2

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
                    Log.d("checkignResponse", "camera selected")
                    image = data?.extras?.get("data") as Uri
                    setImageForId(image!!)
                    uploadPhotoFunction(image!!)

                }
                REQUEST_GALLERY -> {
                    Log.d("checkignResponse", "gallery selected")
                   image = data?.data!!
                    setImageForId(image!!)
                    uploadPhotoFunction(image!!)

                }
            }
        }
    }

    private fun setImageForId(imageUri: Uri) {
        lastClickedImageView?.setImageURI(imageUri)
        lastclickedvisible?.visibility=View.GONE
        camera?.visibility=View.VISIBLE
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
            this@EditProfile,
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
        binding.dobtext.setText(formattedDate)
    }

    private fun saveUserDataToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val userDataJson = gson.toJson(editor)
        editor.putString("user_data", userDataJson)
        editor.apply()
    }

    @RequiresApi(34)
    private fun postdataapi() {

        val request=EditProfileRequest()
        request.firstName=first_name
        request.lastName=last_name
        request.education=education
        request.profession=profession
        request.about=about
        request.birthday=dob
        request.favoriteFood=fav_food
        request.height=height
        request.state="Punjab"
        request.gender=gender
        request.latitude=0
        request.longitude=0
        request.profileVisibility=0
        request.profileImageUrl=image.toString()
        request.idProofFrontImg=image.toString()
        request.idProofBackImg=image.toString()

        apiService?.editProfile("Bearer ${AppInfo.getToken()}",request)?.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val model= EditProfileRequest()
                    val responsebody=response.body()
                    if (responsebody?.status == true) {
                        try {
                            AppInfo.setFirstName(model.firstName.toString())
                            AppInfo.setLastName(model.lastName.toString())
                            AppInfo.setGender(model.gender.toString())
                            AppInfo.setDateOfBirth(model.birthday.toString())
                            AppInfo.setEducation(model.education.toString())
                            AppInfo.setHeight(model.height.toString())
                            AppInfo.setProfession(model.profession.toString())
                            AppInfo.setFavoriteFood(model.favoriteFood.toString())
                            if (model.profileImageUrl != "null" && model.profileImageUrl != "") {
                                AppInfo.setUserImage(model.profileImageUrl.toString())
                            }
                        } catch (e: Exception) {
                            Log.d("crashHandle", e.toString())
                        }
                    }
                } else {
                    Toast.makeText(this@EditProfile, response.toString(), Toast.LENGTH_SHORT).show()
                }
                Log.d("SignUpresponce", response.toString())
                Log.d("SignUpresponce", response.toString())
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                // Handle network failure
                Log.d("SignUp", t.toString())
                Toast.makeText(this@EditProfile, "something went wrong in failure", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    @RequiresApi(34)
    private fun validation() {
        first_name = binding.fName.text.trim().toString()
        last_name = binding.lastName.text.trim().toString()
        gender = binding.gender.text.toString().trim()
        dob = binding.dobtext.text.toString().trim()
        education = binding.education.text.toString().trim()
        height = binding.height.text.toString().trim()
        profession = binding.profession.text.trim().toString()
        fav_food = binding.food.text.trim().toString()
        email = binding.gmail.text.trim().toString()
        pass = binding.password.text.trim().toString()
        confirm_pass = binding.confirmPass.text.trim().toString()
        about = binding.aboutme.text.trim().toString()


        if (binding.fName.length() <= 3) {
            Toast.makeText(this, "Please enter a valid first name", Toast.LENGTH_SHORT).show()
        } else if (binding.lastName.length() <= 3) {
            Toast.makeText(this, "Please enter a valid last name", Toast.LENGTH_SHORT).show()
        } else if (gender.isEmpty()) {
            Toast.makeText(
                this,
                "Please enter a Male or Female with same writing",
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.dobtext.length() <= 8) {
            Toast.makeText(this, "Please enter correct DOB", Toast.LENGTH_SHORT).show()
        } else if (education.isEmpty()) {
            Toast.makeText(this, "Please enter correct education", Toast.LENGTH_SHORT).show()
        } else if (height.isEmpty()) {
            Toast.makeText(this, "Please enter correct height", Toast.LENGTH_SHORT).show()
        } else if (binding.profession.length() <= 2) {
            Toast.makeText(this, "Please enter correct profession", Toast.LENGTH_SHORT).show()
        } else if (binding.food.length() <= 2) {
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
                startActivity(Intent(this@EditProfile, SettingActivity::class.java))
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