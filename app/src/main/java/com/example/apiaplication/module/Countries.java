package com.example.apiaplication.module;

public class Countries {
    private String country, todayCases, cases,
            todayDeaths, deaths, todayRecovered, recovered;

    public Countries(String country, String todayCases, String cases, String todayDeaths, String deaths, String recovered) {
        this.country = country;
        this.todayCases = todayCases;
        this.cases = cases;
        this.todayDeaths = todayDeaths;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public String getcountry() {
        return country;
    }

    public String gettodayCases() {
        return todayCases;
    }

    public String getcases() {
        return cases;
    }

    public String gettodayDeaths() {
        return todayDeaths;
    }

    public String getdeaths() {
        return deaths;
    }

    public String gettodayRecovered() {
        return todayRecovered;
    }

    public String getrecovered() {
        return recovered;
    }

    public void setcountry(String country) {
        country = country;
    }

    public void setcountryCode(String countryCode) {
        countryCode = countryCode;
    }


    public void settodayCases(String todayCases) {
        todayCases = todayCases;
    }

    public void setcases(String cases) {
        cases = cases;
    }

    public void settodayDeaths(String todayDeaths) {
        todayDeaths = todayDeaths;
    }

    public void setdeaths(String deaths) {
        deaths = deaths;
    }

    public void settodayRecovered(String todayRecovered) {
        todayRecovered = todayRecovered;
    }

    public void setrecovered(String recovered) {
        recovered = recovered;
    }
}
