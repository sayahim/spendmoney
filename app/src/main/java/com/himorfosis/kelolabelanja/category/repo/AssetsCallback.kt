package com.himorfosis.kelolabelanja.category.repo

import com.himorfosis.kelolabelanja.category.model.AssetsModel

object AssetsCallback {

    lateinit var onClickItem: OnClickItemAssets

    interface OnClickItemAssets {
        fun onItemClicked(data: AssetsModel.Asset)
    }

    fun setOnclick(onClickItem: OnClickItemAssets) {
        this.onClickItem = onClickItem
    }

}