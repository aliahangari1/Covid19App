package com.example.apiaplication.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiaplication.R;
import com.example.apiaplication.adapter.AdapterCountriesRec;
import com.example.apiaplication.module.Countries;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment {

    View vv;
    private LinearLayout parent;

    private static final String api_url = "https://corona.lmao.ninja/v2/countries";
    private RequestQueue requestQueue;
    private TextView progress_bar, Info;
    private EditText search_box;

    private RecyclerView recyclerView;
    private AdapterCountriesRec adapter;
    private LinearLayoutManager manager;
    private List<Countries> countries;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vv = inflater.inflate(R.layout.fragment_country, container, false);

        countries = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());

        parent = vv.findViewById(R.id.lyt_countries);
        progress_bar = vv.findViewById(R.id.api_progress_c);
        search_box = vv.findViewById(R.id.et_search_box);

        search_box.addTextChangedListener(onTextInput());
        Info = vv.findViewById(R.id.tv_info);
        Info.setMovementMethod(LinkMovementMethod.getInstance());

        getResponse();
        setRecyclerView();
        return vv;
    }

    private TextWatcher onTextInput() {
        TextWatcher TW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return TW;
    }

    private void setRecyclerView(){
        recyclerView = vv.findViewById(R.id.rec_view_country);
        recyclerView.setNestedScrollingEnabled(false);
        manager= new LinearLayoutManager(vv.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void getResponse(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, api_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                    try {

                        for(int i=0; i<response.length(); i++){
                            JSONObject country = response.getJSONObject(i);
                            countries.add(new Countries(country.getString("country"), country.getString("todayCases"), country.getString("cases"),
                                    country.getString("todayDeaths"), country.getString("deaths"), country.getString("recovered")));
                        }

                        adapter = new AdapterCountriesRec(vv.getContext(), countries);
                        recyclerView.setAdapter(adapter);
                        //adapter.notifyItemChanged(countries.size());
                        parent.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }, new Response.ErrorListener() {
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
}

