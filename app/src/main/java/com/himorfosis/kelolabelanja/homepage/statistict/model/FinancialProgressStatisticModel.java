package com.himorfosis.kelolabelanja.homepage.statistict.model;

public class FinancialProgressModel {

    private String category_name;
    private Integer total_nominal_category;

    public FinancialProgressModel(String category_name, Integer total_nominal_category) {
        this.category_name = category_name;
        this.total_nominal_category = total_nominal_category;

    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getTotal_nominal_category() {
        return total_nominal_category;
    }

    public void setTotal_nominal_category(Integer total_nominal_category) {
        this.total_nominal_category = total_nominal_category;
    }

}