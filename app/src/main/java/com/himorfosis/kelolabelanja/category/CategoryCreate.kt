package com.himorfosis.kelolabelanja.category

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.adapter.AssetsGroupAdapter
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.category.model.CategoryCreateRequest
import com.himorfosis.kelolabelanja.category.repo.AssetsCallback
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.state.HomeState
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.activity_category_create.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast


class CategoryCreate : AppCompatActivity(), AssetsCallback.OnClickItemAssets {

    lateinit var viewModel: CategoryViewModel
    lateinit var adapterAssets: AssetsGroupAdapter
    lateinit var animScale: ScaleAnimation
    lateinit var animRotate: ScaleAnimation
    private var listData: MutableList<AssetsModel> = ArrayList()
    lateinit var loadingDialog: DialogLoading

    private var assetSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_create)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        initializeUI()

    }

    override fun onStart() {
        super.onStart()
        DataPreferences.category.saveString(CategoryPref.SELECTED, "")
    }

    private fun initializeUI() {
        setToolbar()
        setAdapter()
        fetchAssets()
        setEffectImage()
        save_category_btn.onClick {
            isLoading()
            pushCreateCategory()
        }

    }

    private fun fetchAssets() {

        viewModel.assetsFetch()
        viewModel.assetsResponse.observe(this, Observer {
            isLoadingStop()
            when (it) {
                is StateNetwork.OnSuccess -> {
                    if (it.data.isNotEmpty()) {
                        listData.addAll(it.data)
                        adapterAssets.addAll(it.data)
                    } else {
                        onFailure(
                                getString(R.string.data_not_available),
                                getString(R.string.data_not_available_message))
                    }
                }

                is StateNetwork.OnError -> {
                    onFailure(it.error, it.message)
                }
                is StateNetwork.OnFailure -> {
                    onFailure(
                            getString(R.string.check_connection),
                            getString(R.string.check_connection_message))
                }
            }
        })

    }

    private fun pushCreateCategory() {
        val title = title_category_ed.text.toString()
        if (title.isNotEmpty() && assetSelected.isNotEmpty()) {
            viewModel.userCreateCategoryPush(CategoryCreateRequest(title, assetSelected))
            viewModel.userCreateCategoryResponse.observe(this, Observer {
                loadingDialog.dismiss()
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        toast("Berhasil Menambah Kategori")
                        startActivity(intentFor<HomepageActivity>("from" to HomeState.CATEGORY))
                    }
                    is StateNetwork.OnError -> {
                        dialogInfo(it.error, it.message)
                    }
                    else -> {
                        toast("Gagal Menambah Kategori")
                    }
                }
            })
        } else {
            loadingDialog.dismiss()
            dialogInfo(getString(R.string.please_complete_data), getString(R.string.please_complete_data_message))
        }

    }

    private fun setEffectImage() {
        //        anim = RotateAnimation(0f, 350f, 15f, 15f)
        animScale = ScaleAnimation(0f, 350f, 15f, 15f)
        animScale.interpolator = LinearInterpolator()
        animScale.repeatCount = Animation.ZORDER_TOP
        animScale.duration = 100
    }

    private fun setAdapter() {
        adapterAssets = AssetsGroupAdapter()
        assets_recycler.apply {
            layoutManager = LinearLayoutManager(this@CategoryCreate)
            adapter = adapterAssets
        }

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.onClick {
            finish()
        }

        titleBar_tv.text = "Tambah Kategori"

    }

    private fun isLoading() {
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun isLoadingStop() {
        assets_category_progress.visibility = View.GONE
    }

    private fun onFailure(title: String, message: String) {
        isLoadingStop()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun dialogInfo(title: String?, message: String?) {
        DialogInfo(this@CategoryCreate, title.toString(), message.toString()).show()
    }

    fun isLog(message: String) {
        Util.log("Category Income Fragment", message)
    }

    override fun onItemClicked(data: AssetsModel.Asset) {

        Glide.with(this@CategoryCreate)
                .load(data.image_assets_url)
                .thumbnail(0.1f)
                .error(R.drawable.ic_broken_image)
                .into(assets_selected_img)

        assets_selected_img.startAnimation(animScale)

        assetSelected = data.image_assets

        val type = DataPreferences.category.getString(CategoryPref.TYPE)
        isLog("type $type")

        val checkData = DataPreferences.category.getString(CategoryPref.SELECTED)
        if (checkData!!.isNotEmpty()) {
            isLog("id assets : ${data.id}")
            DataPreferences.category.saveString(CategoryPref.SELECTED, data.image_assets)
            // reffresh adapter
            adapterAssets.notifyDataSetChanged()
            adapterAssets.addAll(listData)
        } else {
            DataPreferences.category.saveString(CategoryPref.SELECTED, data.image_assets)
        }

    }

}
