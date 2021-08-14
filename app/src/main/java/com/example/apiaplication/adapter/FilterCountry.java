package com.example.apiaplication.adapter;

import android.widget.Filter;

import com.example.apiaplication.module.Countries;

import java.util.ArrayList;
import java.util.List;

public class FilterCountry extends Filter {

    private AdapterCountriesRec adapterCountry;
    List<Countries> data;

    public FilterCountry(AdapterCountriesRec adapterCountry, List<Countries> data) {
        this.adapterCountry = adapterCountry;
        this.data = data;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();

        if (charSequence != null && charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            List<Countries> filter_countries = new ArrayList<>();

            for (int i=0; i<data.size(); i++){
                if (data.get(i).getcountry().toString().toUpperCase().contains(charSequence)){
                    filter_countries.add(data.get(i));
                }
            }

            filterResults.count = filter_countries.size();
            filterResults.values = filter_countries;
        }
        else {
            filterResults.count = data.size();
            filterResults.values = data;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterCountry.countries = (List<Countries>)filterResults.values;
        adapterCountry.notifyDataSetChanged();
    }
}
