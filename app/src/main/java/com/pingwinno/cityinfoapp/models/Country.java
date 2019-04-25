package com.pingwinno.cityinfoapp.models;

import java.util.List;
import java.util.Objects;

public class Country {
    private String country;
    private List<String> cities;

    public Country() {
    }

    public Country(String country, List<String> cities) {
        this.country = country;
        this.cities = cities;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country1 = (Country) o;

        if (!Objects.equals(country, country1.country))
            return false;
        return Objects.equals(cities, country1.cities);
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (cities != null ? cities.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", cities=" + cities +
                '}';
    }
}
