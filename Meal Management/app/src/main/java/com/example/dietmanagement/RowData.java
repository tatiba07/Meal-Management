package com.example.dietmanagement;

public class RowData {
    private boolean radioBtn;

    public String isRadioBtn() {
        return String.valueOf(radioBtn);
    }

    public void setRadioBtn(boolean radioBtn) {
        this.radioBtn = radioBtn;
    }

    private String food;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    private Double per;

    public Double getPer() {
        return per;
    }

    public void setPer(Double per) {
        this.per = per;
    }

    private Double calorie;

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    private Double carbon;

    public Double getCarbon() {
        return carbon;
    }

    public void setCarbon(Double carbon) {
        this.carbon = carbon;
    }

    private Double protein;

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    private Double fat;

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }
}
