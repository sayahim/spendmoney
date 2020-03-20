package com.himorfosis.kelolabelanja.financial

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.dialog.InputFinanceBottomDialog
import com.himorfosis.kelolabelanja.financial.model.InputDataModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_input_financial.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import kotlin.collections.ArrayList


class InputFinancial : AppCompatActivity() {

    var TAG = "InputFinancial"

    lateinit var financialCategoryAdapter: FinancialCategoryAdapter

    var getTypeInputData = "income"

    // data
    var listCategory: List<CategoryEntity> = ArrayList<CategoryEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_financial)

        setToolbar()

        setTabLayout()

        setHandleBackpressed()

        setDataCategorySpending()

        setAdapterCategory()

        setActionClick()

    }

    private fun showInputDataFinance(category: CategoryEntity) {

        val inputDataDialog = InputFinanceBottomDialog(this@InputFinancial)
        inputDataDialog.show()
        InputFinanceBottomDialog.setOnclick(object : InputFinanceBottomDialog.Companion.OnClickItemDialog {
            override fun onItemClicked(data: InputDataModel) {

                Util.log(TAG, "nominal : ${data.nominal}")
                Util.log(TAG, "note : ${data.note}")
                Util.log(TAG, "date : ${data.date}")
                Util.log(TAG, "id cat : ${category.id}")

                startActivity(Intent(this@InputFinancial, HomepageActivity::class.java))
            }
        })

    }

    private fun setTabLayout() {

        spend_ll.setOnClickListener {

            // set data category to list
            setDataCategorySpending()

        }

        income_ll.setOnClickListener {

            // set data category to list
            setDataCategoryIncome()

        }

    }

    private fun deleteTextSearch() {

        search_category_et.setText("")
        delete_search_btn.visibility = View.INVISIBLE

    }

    private fun setActionClick() {

        delete_search_btn.setOnClickListener {

            deleteTextSearch()
        }

    }


    private fun insertIntoDatabase(data: FinancialEntitiy) {

        toast("Data Berhasil Tersimpan")

        startActivity(Intent(this, HomepageActivity::class.java))

    }

    private fun setDataCategorySpending() {

        getTypeInputData = "spend"

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        spend_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
        income_tv.setTextColor(resources.getColor(R.color.text_second))

        // set indicator
        income_indicator.visibility = View.INVISIBLE
        spend_indicator.visibility = View.VISIBLE

        search_category_et.setText("")
        delete_search_btn.visibility = View.INVISIBLE

        // delete cache data
        Util.saveData("category", "selected", "0", this)

        deleteTextSearch()

        listCategory = CategoryData.getDataCategorySpending()

        setAdapterCategory()

    }

    private fun setDataCategoryIncome() {

        getTypeInputData = "income"

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        income_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
        spend_tv.setTextColor(resources.getColor(R.color.text_second))

        // set indicator
        income_indicator.visibility = View.VISIBLE
        spend_indicator.visibility = View.INVISIBLE

        // delete cache data
        Util.saveData("category", "selected", "0", this)

        deleteTextSearch()

        listCategory = CategoryData.getDataCategoryIncome()

        setAdapterCategory()

    }

    private fun setAdapterCategory() {

        Util.log(TAG, "adapter")

        financialCategoryAdapter = FinancialCategoryAdapter()

        recycler_category.apply {

            financialCategoryAdapter.addAll(listCategory)
            layoutManager = GridLayoutManager(this@InputFinancial, 3)
            adapter = financialCategoryAdapter

        }
        if (listCategory.isEmpty()) {

            status_data_tv.visibility = View.VISIBLE

        } else {

            status_data_tv.visibility = View.INVISIBLE
        }

        // adapter callback
        financialCategoryAdapter.setOnclick(object : FinancialCategoryAdapter.AdapterOnClickItem {
            override fun onItemClicked(data: CategoryEntity) {

                Util.saveData("category", "selected", data.id.toString(), this@InputFinancial)

                showInputDataFinance(data)

                reloadDataAdapter()

            }
        })

    }

    private fun reloadDataAdapter() {

        financialCategoryAdapter.removeAllData()
        financialCategoryAdapter.addAll(listCategory)

    }


    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        titleBar_tv.text = "Input Data"

        backBar_btn.setOnClickListener {

            actionBackpressed()
        }

    }

    private fun setHandleBackpressed() {

        Util.saveData("category", "selected", "0", this)

    }

    override fun onBackPressed() {

        actionBackpressed()

    }

    private fun actionBackpressed() {

        if (getTypeInputData == "income") {

            setDataCategorySpending()
        } else {

            startActivity(Intent(this, HomepageActivity::class.java))
        }
    }

}
