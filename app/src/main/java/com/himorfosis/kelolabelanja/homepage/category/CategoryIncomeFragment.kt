package com.himorfosis.kelolabelanja.homepage.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.CategoryCreate
import com.himorfosis.kelolabelanja.category.CategoryEdit
import com.himorfosis.kelolabelanja.homepage.category.adapter.CategoryListAdapter
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.category_fragment.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class CategoryIncomeFragment : Fragment() {

    var adapterCategory = CategoryListAdapter()
    lateinit var viewModel: CategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        initializeUI()
        setAdapter()
        getDataCategory()

    }

    private fun initializeUI() {

        loading_category_shimmer.startShimmerAnimation()
        fab_add_category.setOnClickListener {
            DataPreferences.category.saveString(CategoryPref.TYPE, CategoryData.INCOME)
            startActivity(intentFor<CategoryCreate>())
        }

        recycler_category.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy<0 && !fab_add_category.isShown)
                    fab_add_category.show()
                else if(dy>0 && fab_add_category.isShown)
                    fab_add_category.hide()
            }
        })


    }

    private fun getDataCategory() {

        if (ConnectionDetector.isConnectingToInternet(requireContext())) {
            viewModel.categoryTypeFinancePush(CategoryData.INCOME)
            viewModel.categoryTypeFinanceResponse.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        onSuccess(it.data)
                    }
                    is StateNetwork.OnError -> {
                        onFailure(
                                it.error,
                                it.message)
                    }
                    is StateNetwork.OnFailure -> {
                        onFailure(
                                getString(R.string.failed_server_connection),
                                getString(R.string.failed_server_connection_message))
                    }
                }
            })

        } else {
            onDisconnect()
        }


    }

    private fun onSuccess(listData: List<CategoryResponse>) {
        loading_category_shimmer.visibility = View.GONE
        fab_add_category.show()
        if (listData.isNotEmpty()) {
            isLog("list data : " + listData.size)
            adapterCategory.addAll(listData)
        } else {
            onFailure(
                    getString(R.string.data_not_available),
                    getString(R.string.data_not_available_message)
            )
        }

    }

    private fun setAdapter() {

        adapterCategory = CategoryListAdapter()
        recycler_category.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterCategory
        }
        adapterCategory.setOnclick(object : CategoryListAdapter.OnClickItem {
            override fun onItemClicked(data: CategoryResponse) {
                startActivity(intentFor<CategoryEdit>(
                        "id" to data.id,
                        "assets_url" to data.image_category_url,
                        "assets" to data.image_category,
                        "title" to data.title
                ))
            }

            override fun onItemHide(data: CategoryResponse) {
                toast("Deleted category " + data.title)
            }
        })

    }

    private fun isLoadingStop() {
        loading_category_shimmer.visibility = View.GONE
    }

    private fun onDisconnect() {
        fab_add_category.hide()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = getString(R.string.disconnect)
        description_status_tv.text = getString(R.string.disconnect_message)
    }

    private fun onFailure(title: String, message: String) {
        fab_add_category.show()
        isLoadingStop()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun dialogInfo(title: String?, message: String?) {
        DialogInfo(requireContext(), title.toString(), message.toString()).show()
    }

    fun isLog(message: String) {
        Util.log("Category Income Fragment", message)
    }

}