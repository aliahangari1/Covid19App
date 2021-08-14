package com.example.apiaplication.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiaplication.R;
import com.example.apiaplication.VirusInfoActivity;
import com.example.apiaplication.module.Countries;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View vv;
    private LinearLayout parent;

    private static final String api_url = "https://corona.lmao.ninja/v2/all";
    private static final String api_url_countries = "https://corona.lmao.ninja/v2/countries";
    private RequestQueue requestQueue, requestQueueIran;

    private CardView button_read_more;
    private TextView NewConfirmed, TotalConfirmed, TotalDeaths, NewIranConfirmed, TotalIranConfirmed, TotalIranDeaths;
    private TextView Info;
    private SpinKitView progress_bar;
    private List<Countries> countries;

    String new_confirmed = "", total_confirmed = "", total_deaths = "",
            newIran_confirmed = "", totalIran_confirmed = "", totalIran_deaths = "";
    private DecimalFormat decimalFormat;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vv = inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueueIran = Volley.newRequestQueue(getContext());
        decimalFormat = new DecimalFormat("###,###");

        countries = new ArrayList<>();

        parent = vv.findViewById(R.id.frm_home);
        progress_bar = vv.findViewById(R.id.api_progress);
        button_read_more = vv.findViewById(R.id.bt_read_more);
        NewConfirmed = vv.findViewById(R.id.tv_confirm_today);
        TotalConfirmed = vv.findViewById(R.id.tv_confirm_total);
        TotalDeaths = vv.findViewById(R.id.tv_death_total);
        NewIranConfirmed = vv.findViewById(R.id.tv_iran_today);
        TotalIranConfirmed = vv.findViewById(R.id.tv_iran_total);
        TotalIranDeaths = vv.findViewById(R.id.tv_iran_death);
        Info = vv.findViewById(R.id.tv_info);
        Info.setMovementMethod(LinkMovementMethod.getInstance());

        getResponse();
        getIranResponse();

        button_read_more.setOnClickListener(this::goNext);
        return vv;
    }

    private void goNext(View view) {
        Intent intent = new Intent(vv.getContext(), VirusInfoActivity.class);
        startActivity(intent);
    }

    private void getResponse() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            new_confirmed = response.getString("todayCases");
                            total_confirmed = response.getString("cases");
                            total_deaths = response.getString("deaths");

                            NewConfirmed.setText(decimalFormat.format(Integer.valueOf(new_confirmed)));
                            TotalConfirmed.setText(decimalFormat.format(Integer.valueOf(total_confirmed)));
                            TotalDeaths.setText(decimalFormat.format(Integer.valueOf(total_deaths)));

                            progress_bar.setVisibility(View.GONE);
                            parent.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.setVisibility(View.GONE);
                        showDialog();
                    }
                });
        requestQueue.add(request);
    }

    private void showDialog() {
        progress_bar.setVisibility(View.GONE);
        Dialog dialog = new Dialog(vv.getContext());
        dialog.setContentView(R.layout.dialog_update_warning);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button finish = dialog.findViewById(R.id.bt_finish);
        finish.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

    private void getIranResponse() {
        JsonArrayRequest requestArray = new JsonArrayRequest(Request.Method.GET, api_url_countries, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i<response.length(); i++) {
                        JSONObject iran = response.getJSONObject(i);
                        if (iran.getString("country").equals("Iran")){
                            newIran_confirmed= iran.getString("todayCases");
                            totalIran_confirmed = iran.getString("cases");
                            totalIran_deaths = iran.getString("deaths");
                        }
                    }
                    NewIranConfirmed.setText(decimalFormat.format(Integer.valueOf(newIran_confirmed)));
                    TotalIranConfirmed.setText(decimalFormat.format(Integer.valueOf(totalIran_confirmed)));
                    TotalIranDeaths.setText(decimalFormat.format(Integer.valueOf(totalIran_deaths)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NewIranConfirmed.setText("بروز رسانی داده");
                TotalIranConfirmed.setText("بروز رسانی داده");
                TotalIranDeaths.setText("بروز رسانی داده");
            }
        });
        requestQueueIran.add(requestArray);
    }
}
