package com.himorfosis.kelolabelanja.category

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.adapter.AssetsGroupAdapter
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_assets_category_list.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AssetsCategory : AppCompatActivity() {

    lateinit var viewModel: CategoryViewModel
    lateinit var adapterAssets: AssetsGroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_category_list)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        initializeUI()
    }

    private fun initializeUI() {
        setToolbar()
        setAdapter()
        fetchAssets()
    }

    private fun fetchAssets() {

        viewModel.assetsFetch()
        viewModel.assetsResponse.observe(this, Observer {
            isLoadingStop()
            when (it) {
                is StateNetwork.OnSuccess -> {
                    if (it.data.isNotEmpty()) {

                        var listData : MutableList<AssetsModel> = ArrayList()
                        for(item in it.data) {
                            if (item.assets.isNotEmpty()) {
                                listData.add(item)
                            }
                        }
                        adapterAssets.addAll(listData)

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

    private fun setAdapter() {
        adapterAssets = AssetsGroupAdapter()
        assets_recycler.apply {
            layoutManager = LinearLayoutManager(this@AssetsCategory)
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

        titleBar_tv.text = "Assets"

    }

    private fun isLoadingStop() {
//        loading_category_shimmer.visibility = View.GONE
    }

    private fun onFailure(title: String, message: String) {
        isLoadingStop()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun dialogInfo(title: String?, message: String?) {
        DialogInfo(this@AssetsCategory, title.toString(), message.toString()).show()
    }

    fun isLog(message: String) {
        Util.log("Category Income Fragment", message)
    }

}
