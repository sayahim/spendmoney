package com.himorfosis.kelolabelanja.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.profile.repo.ProfileViewModel
import com.himorfosis.kelolabelanja.service.ImageService
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.IOException
import java.text.DateFormatSymbols
import java.util.*
import androidx.lifecycle.Observer
import com.himorfosis.kelolabelanja.auth.Login
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.profile.model.UserModel
import com.himorfosis.kelolabelanja.profile.model.UserUpdateRequest
import com.himorfosis.kelolabelanja.state.HomeState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_edit.name_ed
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class ProfileEdit : AppCompatActivity() {

    companion object {
        private lateinit var viewModel: ProfileViewModel
        val calendar: Calendar = Calendar.getInstance()
        private var bitmap: Bitmap? = null
        private lateinit var loadingDialog: DialogLoading

        // gender
        var SELECTED_GENDER = ""
        var MALE = "M"
        var FEMALE = "F"

        // date born selected
        var DATE_SELECTED = "-"
        var MONTH_SELECTED = "-"
        var YEAR_SELECTED = "-"
        var CAL_YEAR_POS = 0
        var CAL_MONTH_POS = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        setToolbar()
        initUI()

    }

    private fun initUI() {

        val name = DataPreferences.account.getString(AccountPref.NAME)
        val email = DataPreferences.account.getString(AccountPref.EMAIL)
        val phone = DataPreferences.account.getString(AccountPref.PHONE_NUMBER)
        val image = DataPreferences.account.getString(AccountPref.IMAGE)
        val born = DataPreferences.account.getString(AccountPref.BORN)
        val gender = DataPreferences.account.getString(AccountPref.GENDER)

        isLog("born data $born")
        isLog("gender : $gender")
        if (born!!.isEmpty()) {
            setBornDate(DateSet.getDateToday())
        } else {
            isLog("born date ${DateSet.convertTimestampDate(born!!.toLong())}")
            setBornDate(DateSet.convertTimestampDate(born!!.toLong()))
        }

        name_ed.setText(name)
        phone_ed.setText(phone)
        email_tv.text = email
        gender_tv.text = gender
        name_ed.setSelection(name!!.length)
        onSelectGender(gender)

        if (image!!.isNotEmpty()) {
            Picasso.with(this)
                    .load(image)
                    .error(R.drawable.ic_broken_image)
                    .into(profile_img)
        } else {
            profile_img.setImageResource(R.drawable.ic_broken_image)
        }

        save_btn.onClick {
            updateProfileUser()
        }

        male_rb.onClick {
            onSelectGender(MALE)
        }

        female_rb.onClick {
            onSelectGender(FEMALE)
        }

        change_profile_photo_tv.onClick {
            if (Build.VERSION.SDK_INT >= 23) {
                ImageService.isWriteStoragePermissionGranted(this@ProfileEdit)
                ImageService.isReadStoragePermissionGranted(this@ProfileEdit)
            } else {
                ImageService.requestStoragePermission(this@ProfileEdit)
            }
        }

    }

    private fun setBornDate(born: String) {
        DATE_SELECTED = DateSet.selectedDate(born)
        MONTH_SELECTED = DateSet.selectedMonth(born)
        YEAR_SELECTED = DateSet.selectedYear(born)

        addYearToSpinner()
        addMonthToSpinner()
        month_spinner.setSelection(MONTH_SELECTED.toInt() - 1)
        date_spinner.setSelection(DATE_SELECTED.toInt() - 1)
    }


    private fun onSelectGender(gender: String?) {

        if (gender!!.isEmpty()) {
            gender_frame.visibility = View.VISIBLE
            gender_tv.visibility = View.GONE
            if (gender == MALE) {
                male_rb.isChecked = true
                female_rb.isChecked = false
                SELECTED_GENDER = MALE
            } else {
                male_rb.isChecked = false
                female_rb.isChecked = true
                SELECTED_GENDER = FEMALE
            }
        } else {
            gender_frame.visibility = View.GONE
            gender_tv.visibility = View.VISIBLE
            gender_tv.text = if (gender == MALE) getString(R.string.male) else getString(R.string.female)
            SELECTED_GENDER = gender
        }

    }

    private fun updateProfileUser() {

        val name = name_ed.text.toString()
        val phone = phone_ed.text.toString()
        val born = "$YEAR_SELECTED-$MONTH_SELECTED-$DATE_SELECTED"
        val id = DataPreferences.account.getString(AccountPref.ID)

        isLog("id : $name")
        isLog("name : $name")
        isLog("phone : $phone")
        isLog("born : $born")
        isLog("SELECTED_GENDER : $SELECTED_GENDER")
        isLog("bitmap : $bitmap")

        if (name.isNotEmpty() && phone.isNotEmpty() && YEAR_SELECTED.isNotEmpty() &&
                MONTH_SELECTED.isNotEmpty() && DATE_SELECTED.isNotEmpty() && SELECTED_GENDER.isNotEmpty()) {

            val map = HashMap<String, RequestBody>()
            map["id"] = Util.createRequestString(id.toString())
            map["name"] = Util.createRequestString(name)
            map["gender"] = Util.createRequestString(SELECTED_GENDER)
            map["phone"] = Util.createRequestString(phone)
            map["born"] = Util.createRequestString(born)

            if (bitmap == null) {
                isLoading()
                pushUpdateProfileWithoutImage(map)
            } else {
                isLoading()
                val fileImage = ImageService.createImageFileJpg(bitmap!!)
                val requestBody = RequestBody.create(MediaType.parse("image/*"), fileImage)
                val bodyImage = MultipartBody.Part.createFormData("image", fileImage!!.name, requestBody)

                pushUpateProfileUser(UserUpdateRequest(bodyImage, map))
            }

        } else {
            dialogInfo(getString(R.string.please_complete_data), getString(R.string.please_complete_data_message))
        }

    }

    private fun pushUpateProfileUser(item: UserUpdateRequest) {
        viewModel.updateUserProfile(item)
        viewModel.responseUpdateProfile.observe(this, Observer {
            loadingDialog.dismiss()
            when (it) {
                is StateNetwork.OnSuccess -> onSuccessUpdateProfile(it.data)
                is StateNetwork.OnError ->
                    dialogInfo(it.error, it.message)
                is StateNetwork.OnFailure ->
                    dialogInfo(getString(R.string.failed_server_connection),
                            getString(R.string.failed_server_connection_message))
            }
        })
    }

    private fun pushUpdateProfileWithoutImage(data: Map<String, RequestBody>) {

        viewModel.updateProfileWithoutImage(data)
        viewModel.responseUpdateProfile.observe(this, Observer {
            loadingDialog.dismiss()
            when (it) {
                is StateNetwork.OnSuccess -> onSuccessUpdateProfile(it.data)
                is StateNetwork.OnError ->
                    dialogInfo(it.error, it.message)
                is StateNetwork.OnFailure ->
                    dialogInfo(getString(R.string.failed_server_connection),
                            getString(R.string.failed_server_connection_message))
            }
        })
    }

    private fun onSuccessUpdateProfile(model: UserModel) {

        toast("Update Profile Success")
        DataPreferences.account.saveString(AccountPref.NAME, model.name)
        DataPreferences.account.saveString(AccountPref.EMAIL, model.email)
        DataPreferences.account.saveString(AccountPref.PHONE_NUMBER, model.phone_number)
        DataPreferences.account.saveString(AccountPref.IMAGE, model.image_url)
        DataPreferences.account.saveString(AccountPref.BORN, model.born)
        DataPreferences.account.saveString(AccountPref.GENDER, model.gender)

        startActivity(intentFor<HomepageActivity>(
                "from" to HomeState.PROFILE
        ))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Checking the request code of our request
        if (requestCode == ImageService.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // show image from galery
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, ImageService.GALLERY)

//                showDialogChooseMediaPicture()
            }
        } else {
            isLog("Anda baru saja menolak izin")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageService.GALLERY) {
            isLog("GALERY result")
            if (data != null) {
                try {

                    val contentURI = data.data
                    val bitmapValue = MediaStore.Images.Media.getBitmap(contentResolver, contentURI)
                    val selectedImagePath = ImageService.getPath(this@ProfileEdit, contentURI)
                    var bitmapRotate: Bitmap? = null
                    val ei = ExifInterface(selectedImagePath)
                    val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> bitmapRotate = ImageService.rotateImage(bitmapValue!!, 90f)
                        ExifInterface.ORIENTATION_ROTATE_180 -> bitmapRotate = ImageService.rotateImage(bitmapValue!!, 180f)
                        ExifInterface.ORIENTATION_ROTATE_270 -> bitmapRotate = ImageService.rotateImage(bitmapValue!!, 270f)
                        else -> bitmapRotate = bitmapValue
                    }

                    isLog("selectedImagePath : $selectedImagePath")

                    if (selectedImagePath != null) {
                        // Selected image is local image
                        val src = BitmapDrawable(resources, bitmapRotate).bitmap
                        val height = (src.height * (512.0 / src.width)).toInt()
                        bitmapRotate = Bitmap.createScaledBitmap(src, 512, height, true)
                    } else {
                        // selected image is picassso image
//                        loadPicasaImageFromGallery(contentURI)
                        val bitmapData = ImageService.loadResizeImageFromGallery(this@ProfileEdit, contentURI)
                        isLog("bitmap data 1 : $bitmapData ")
//                        bitmap = loadResizeImageFromGallery(contentURI)
                        isLog("bitmap data 2 : $bitmap ")

                    }

                    // check rotate image
                    bitmap = bitmapRotate
                    isLog("bitmap get : " + bitmap!!)
                    isLog("bitmap rotate : " + bitmapRotate!!)
                    profile_img.setImageBitmap(bitmapRotate)

                } catch (e: IOException) {
                    e.printStackTrace()
                    isLog("Failed load image")
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == ImageService.CAMERA) {
            isLog("CAMERA result")
            if (resultCode == Activity.RESULT_OK) {
                bitmap = data!!.extras!!.get("data") as Bitmap
                isLog("bitmap get : " + bitmap!!)
                profile_img.setImageBitmap(bitmap)
            }

        }

    }

    // resize image
//    fun loadResizeImageFromGallery(uri: Uri?): Bitmap? {
//
////        var bitmap: Bitmap? = null
//
//        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
//        val cursor = contentResolver.query(uri!!, projection, null, null, null)
//        if (cursor != null) {
//            cursor.moveToFirst()
//
//            val columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
//            if (columnIndex != -1) {
//                Thread(Runnable {
//                    try {
//                        val dataBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//                        val file = (dataBitmap.height * (512.0 / dataBitmap.width)).toInt()
//                        bitmap = Bitmap.createScaledBitmap(dataBitmap, 512, file, true)
//                        isLog("data bitmap resize : $bitmap")
//                        // THIS IS THE BITMAP IMAGE WE ARE LOOKING FOR
//                    } catch (ex: Exception) {
//                        ex.printStackTrace()
//                    }
//                }).start()
//            }
//        }
//        cursor!!.close()
//
//        return bitmap
//    }

    private fun showDialogChooseMediaPicture() {

        val pictureDialog = AlertDialog.Builder(this@ProfileEdit)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, ImageService.GALLERY)
                }
                1 -> {

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, ImageService.CAMERA)
                }
            }
        }

        pictureDialog.show()

    }

    private fun addDateToSpinner(month: Int, year: Int) {
        val position = date_spinner.selectedItemPosition
        val arrayList: ArrayList<String> = ArrayList()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        for (i in 0 until maxDate) {
            arrayList.add(i.plus(1).toString())
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        date_spinner.adapter = arrayAdapter
        date_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val data = parent!!.getItemAtPosition(position).toString()
                DATE_SELECTED = if (data.toInt() < 10) "0$data" else data
                isLog("selected date : $DATE_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        if (position > arrayList.size - 1) {
            date_spinner.setSelection(arrayList.size - 1)
        } else {
            if (position >= 1) {
                date_spinner.setSelection(position)
                DATE_SELECTED = if (position < 10) "0$position" else position.toString()
            } else {
                date_spinner.setSelection(DATE_SELECTED.toInt() - 1)
            }
            isLog("addDateToSpinner selected : $DATE_SELECTED")
        }
    }

    private fun addMonthToSpinner() {

        val month = DateFormatSymbols.getInstance().months
        val arrayMonth: ArrayList<String> = ArrayList()
        for (i in month.indices) {
            arrayMonth.add(month[i])
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayMonth)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        month_spinner.adapter = arrayAdapter
        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val monthSelected = position + 1
                MONTH_SELECTED = if (monthSelected < 10) "0$monthSelected" else monthSelected.toString()
                CAL_MONTH_POS = position
                addDateToSpinner(position, CAL_YEAR_POS)
                isLog("addMonthToSpinner selected $MONTH_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ga usah ada aksi
            }
        }

    }

    private fun addYearToSpinner() {

        val listYear: MutableList<String> = ArrayList()
        val startYear = 1940
        val totalYear = 110
        for (position in 0 until totalYear) {
            val year = startYear + position
            listYear.add(year.toString())
        }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listYear)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        year_spinner.adapter = arrayAdapter
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val data = parent!!.getItemAtPosition(position).toString()
                YEAR_SELECTED = data
                CAL_YEAR_POS = position
                addDateToSpinner(CAL_MONTH_POS, position)
                isLog("year selected : $YEAR_SELECTED")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ga usah ada aksi
            }
        }

        for (item in 0 until listYear.size) {
            if (YEAR_SELECTED.toInt() == listYear[item].toInt()) {
                val yearAdapter = arrayAdapter.getPosition(listYear[item])
                year_spinner.setSelection(yearAdapter)
                break
            }
        }

    }

    private fun dialogInfo(title: String?, message: String) {
        DialogInfo(this, title.toString(), message).show()
    }


    private fun isLoading() {
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun isLog(msg: String) {
        Log.e("ProfileEdit", msg)
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        titleBar_tv.text = "Edit Profil"
        backBar_btn.onClick {
            finish()
        }

    }

}
