package com.pingwinno.cityinfoapp.models;

public class City {
    private long id;
    private String cityName;
    private int countryId;

    public City(long id, String cityName, int countryId) {
        this.id = id;
        this.cityName = cityName;
        this.countryId = countryId;
    }

    public City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
