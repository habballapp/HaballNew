package com.example.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distribution_Login.Distribution_Login;
import com.example.haball.Distributor.DistributorDashboard;

import com.example.haball.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Registration_Actvity2 extends AppCompatActivity {

    private Button btn_register;
    private ImageButton btn_back;
    private String username, password, confirmpassword, firstname, lastname, email, cnic, mobile_number, phone_number, ntn, conpany_name, website;
    private EditText Address, postal_shipping, Address02, postal_billing;
    private String URL_SPINNERS_COUNTRY = "http://175.107.203.97:4008/api/country";
    private String URL_SPINNERS_PROVINCE = "http://175.107.203.97:4008/api/state/ReadByCountry/1";
    private String URL_SPINNERS_CITY = "http://175.107.203.97:4008/api/city/ReadByState/1";
    private String URL = "http://175.107.203.97:4008/api/distributor/Register";

    private String country1, country2, city1, city2, province1, province2;
    private Spinner spinner_country, spinner_city, spinner_province, spinner_country2, spinner_city2, spinner_province2;

    private ArrayList<String> countries, cities, provinces;

    ArrayAdapter<String> arrayAdapterCountry, arrayAdapterCity, arrayAdapterProvince;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__actvity2);

        countries = new ArrayList<>();
        cities = new ArrayList<>();
        provinces = new ArrayList<>();

        countries.add("Select Country *");
        cities.add("Select City *");
        provinces.add("Select Province *");

        arrayAdapterCountry = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, countries);
        arrayAdapterCity = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        arrayAdapterProvince = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, provinces);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            password = extras.getString("password");
            confirmpassword = extras.getString("confirmpassword");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
            email = extras.getString("email");
            cnic = extras.getString("cnic");
            mobile_number = extras.getString("mobile_number");
            phone_number = extras.getString("phone_number");
            ntn = extras.getString("ntn");
            conpany_name = extras.getString("conpany_name");
            website = extras.getString("website");
        }

        spinner_country = findViewById(R.id.spinner_country);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_province = findViewById(R.id.spinner_province);
        spinner_country2 = findViewById(R.id.spinner_country2);
        spinner_city2 = findViewById(R.id.spinner_city2);
        spinner_province2 = findViewById(R.id.spinner_province2);

        spinner_country.setPrompt("Choose Widget Mode");
        spinner_city.setPrompt("Choose Widget Mode");
        spinner_province.setPrompt("Choose Widget Mode");
        spinner_country2.setPrompt("Choose Widget Mode");
        spinner_city2.setPrompt("Choose Widget Mode");
        spinner_province2.setPrompt("Choose Widget Mode");

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading,\n please wait.");
//        progressDialog.show();

        fetch_countries();
        fetch_province();
        fetch_cities();

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                country1 = countries.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                province1 = provinces.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                city1 = cities.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_country2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                country2 = countries.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_province2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                province2 = provinces.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                city2 = cities.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        Address = findViewById(R.id.Address);
        postal_shipping = findViewById(R.id.postal_shipping);
        Address02 = findViewById(R.id.Address02);
        postal_billing = findViewById(R.id.postal_billing);

        btn_back = (ImageButton)customView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeRegisterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeRegisterRequest() throws JSONException {

        JSONObject map = new JSONObject();
        map.put("status", 1);
        map.put("UserType", 0);
        map.put("Username", username);
        map.put("Password", password);
        map.put("ConfirmPassword", confirmpassword);
        map.put("FirstName", firstname);
        map.put("LastName", lastname);
        map.put("Email", email);
        map.put("CNIC", cnic);
        map.put("Mobile", mobile_number);
        map.put("Phone", phone_number);
        map.put("CompanyNTN", ntn);
        map.put("CompanyName", conpany_name);
        map.put("URL", website);
        map.put("ShippingCountryId", 1);
        map.put("BillingCountryId", 1);
        map.put("ShippingProvinceId", 1);
        map.put("BillingProvinceId", 1);
        map.put("ShippingCityId", 1);
        map.put("BillingCityId", 1);
        map.put("ShippingAddress1", Address.getText().toString());
        map.put("BillingAddress1", Address02.getText().toString());
        map.put("ShippingPostCode", postal_shipping.getText().toString());
        map.put("BillingPostCode", postal_billing.getText().toString());
        map.put("IsAgree", true);

        Log.i("MAP OBJECT", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
                try {
                    if(!result.get("DealerCode").toString().isEmpty()){
                        Intent i = new Intent(Registration_Actvity2.this, DistributorDashboard.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration_Actvity2.this,e.toString(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Registration_Actvity2.this,error.toString(),Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(this).add(sr);
    }

    private void fetch_cities() {
        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_CITY,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject obj = result.getJSONObject(0);
                    cities.add(obj.getString("Name"));
                    Log.i("cities",cities.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF CITIES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCity.notifyDataSetChanged();
        spinner_city.setAdapter(arrayAdapterCity);
        spinner_city2.setAdapter(arrayAdapterCity);
    }

    private void fetch_province() {
        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_PROVINCE,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    for(int i=0;i<result.length();i++) {
                        JSONObject obj = result.getJSONObject(i);
                        provinces.add(obj.getString("Name"));
                    }
                    Log.i("provinces",provinces.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF PROVINCES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterProvince.notifyDataSetChanged();
        spinner_province.setAdapter(arrayAdapterProvince);
        spinner_province2.setAdapter(arrayAdapterProvince);

    }

    private void fetch_countries() {

        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_COUNTRY,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject obj = result.getJSONObject(0);
                    countries.add(obj.getString("Name"));
                    Log.i("countries",countries.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COUNTRIES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCountry.notifyDataSetChanged();
        spinner_country.setAdapter(arrayAdapterCountry);
        spinner_country2.setAdapter(arrayAdapterCountry);

    }


}
