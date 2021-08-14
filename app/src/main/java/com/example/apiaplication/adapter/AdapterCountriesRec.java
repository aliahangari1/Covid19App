package com.example.apiaplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiaplication.R;
import com.example.apiaplication.module.Countries;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCountriesRec extends RecyclerView.Adapter<AdapterCountriesRec.MyHolder> implements Filterable {

    private Context cxt;
    public List<Countries> countries;
    public List<Countries> data_filter;
    String newC, newD, totalC, totalD, totalR;

    private FilterCountry filterCountry;

    public AdapterCountriesRec(Context cxt, List<Countries> countries) {
        this.cxt = cxt;
        this.countries = countries;
        this.data_filter = countries;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cxt).inflate(R.layout.item_countries_rec, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        newC = countries.get(position).gettodayCases();
        newD = countries.get(position).gettodayDeaths();
        totalC = countries.get(position).getcases();
        totalD = countries.get(position).getdeaths();
        totalR = countries.get(position).getrecovered();

        holder.NewConfirmed.setText(decimalFormat.format(Integer.valueOf(newC)));
        holder.NewDeaths.setText(decimalFormat.format(Integer.valueOf(newD)));
        holder.TotalConfirmed.setText(decimalFormat.format(Integer.valueOf(totalC)));
        holder.TotalDeaths.setText(decimalFormat.format(Integer.valueOf(totalD)));
        holder.TotalRecovered.setText(decimalFormat.format(Integer.valueOf(totalR)));
        holder.country_name.setText(countries.get(position).getcountry());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    @Override
    public Filter getFilter() {
        if(filterCountry == null){
            filterCountry = new FilterCountry(this, data_filter);
        }
        return filterCountry;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView TotalConfirmed, TotalRecovered,TotalDeaths,
                NewConfirmed, NewDeaths;
        private TextView country_name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            NewConfirmed = itemView.findViewById(R.id.NewConfirmed);
            TotalConfirmed = itemView.findViewById(R.id.TotalConfirmed);
            NewDeaths = itemView.findViewById(R.id.NewDeaths);
            TotalDeaths = itemView.findViewById(R.id.TotalDeaths);
            TotalRecovered = itemView.findViewById(R.id.TotalRecovered);

            country_name = itemView.findViewById(R.id.tv_country);
        }
    }
}
