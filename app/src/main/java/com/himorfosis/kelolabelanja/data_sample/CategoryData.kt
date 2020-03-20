package com.himorfosis.kelolabelanja.data_sample

import com.himorfosis.kelolabelanja.database.entity.CategoryEntity

object CategoryData {

    fun getDataCategoryIncome(): List<CategoryEntity> {

        var data: List<CategoryEntity> = ArrayList<CategoryEntity>()

        data = listOf(

                CategoryEntity(1, "Gaji", "ic_money"),
                CategoryEntity(2, "Persewaan", "ic_store"),
                CategoryEntity(3, "Penjualan", "ic_payment"),
                CategoryEntity(4, "Hadiah", "ic_trophy")
        )

        return data

    }

    fun getDataCategorySpending(): List<CategoryEntity> {

        var data: List<CategoryEntity> = ArrayList<CategoryEntity>()

        data = listOf(

                CategoryEntity(1, "Makanan", "ic_eat"),
                CategoryEntity(2, "Belanja", "ic_belanja"),
                CategoryEntity(3, "Pakaian", "ic_hanger"),
                CategoryEntity(4, "Dapur", "ic_baker"),

                CategoryEntity(5, "Kendaraan", "ic_scooter"),
                CategoryEntity(6, "Rumah", "ic_house"),
                CategoryEntity(7, "Kesehatan", "ic_medical"),
                CategoryEntity(8, "Pendidikan", "ic_toga"),

                CategoryEntity(9, "Tiket", "ic_ticket"),
                CategoryEntity(10, "Rekreasi", "ic_mountains"),
                CategoryEntity(11, "Furnitur", "ic_sofa"),
                CategoryEntity(12, "Elektronik", "ic_laptop"),

                CategoryEntity(13, "Sedekah", "ic_payment"),
                CategoryEntity(14, "Pajak", "ic_tax"),
                CategoryEntity(15, "Peliharaan", "ic_cat"),
                CategoryEntity(16, "Tanaman", "ic_plant"),


                CategoryEntity(17, "Utilitas", "ic_utilities"),
                CategoryEntity(18, "Keluarga", "ic_family"),
                CategoryEntity(19, "Buku", "ic_open_book"),
                CategoryEntity(20, "Kopi", "ic_coffee")

        )

        return data

    }

}