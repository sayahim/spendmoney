package com.himorfosis.kelolabelanja.homepage.statistict.model;

public class ChartModel {

    private Integer id_category;
    private String category_image;
    private String category_name;
    private Integer total_nominal_category;

    public Integer getId_category() {
        return id_category;
    }

    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
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
