package com.example.haball.Distributor.ui.payments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CreatePaymentRequestFragment extends Fragment {
    private String Token, DistributorId;
    private Button btn_create;

    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";
    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4013/api/prepaidrequests/save";

    private List<String> CompanyNames = new ArrayList<>();
    private HashMap<String, String> companyNameAndId = new HashMap<>();

    private Spinner spinner_company;
    private ArrayAdapter<String> arrayAdapterPayments;
    private String company_names;
    private EditText txt_amount;
    private String prepaid_number, prepaid_id;
    private FragmentTransaction fragmentTransaction;
    private TextInputLayout layout_txt_amount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_payment__screen1, container, false);

        btn_create = root.findViewById(R.id.btn_create);
        btn_create.setEnabled(false);
        btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        spinner_company = root.findViewById(R.id.spinner_company);
        txt_amount = root.findViewById(R.id.txt_amount);

        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        layout_txt_amount.setBoxStrokeColor(Color.parseColor("#e5e5e5"));
        CompanyNames.add("Company *");
        company_names = "";

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, CompanyNames);

        spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    company_names = CompanyNames.get(i);
                    Log.i("company name and id ", companyNameAndId.get(company_names));
                }
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchCompanyData();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!company_names.equals("") && !String.valueOf(txt_amount.getText()).equals("")) {
                    try {
                        makeSaveRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_LONG).show();
                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();

            }
        };
        txt_amount.addTextChangedListener(textWatcher);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        txt_amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_amount.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    showDiscardDialog();
                    return true;
                }
                return false;
            }
        });

    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                fm.popBackStack();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    private void checkFieldsForEmptyValues() {
        String txt_amounts = txt_amount.getText().toString();
        String company = (String) spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()).toString();
        if (txt_amounts.equals("")
                || company.equals("Company *")

        ) {
            btn_create.setEnabled(false);
            btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_create.setEnabled(true);
            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void makeSaveRequest() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("Status", 0);
        map.put("ID", 0);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyNameAndId.get(company_names));
        map.put("PaidAmount", txt_amount.getText().toString());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    prepaid_number = result.getString("PrePaidNumber");
                    prepaid_id = result.getString("ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences PrePaidNumber = getContext().getSharedPreferences("PrePaidNumber",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = PrePaidNumber.edit();
                editor.putString("PrePaidNumber", prepaid_number);
                editor.putString("PrePaidId", prepaid_id);
                editor.putString("CompanyId", companyNameAndId.get(company_names));
                editor.putString("CompanyName", company_names);
                editor.putString("Amount", txt_amount.getText().toString());
                editor.apply();

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), new PaymentScreen3Fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
                Log.e("RESPONSE prepaid_number", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchCompanyData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        URL_PAYMENT_REQUESTS_SELECT_COMPANY = URL_PAYMENT_REQUESTS_SELECT_COMPANY + DistributorId;
        Log.i("URL_PROOF_OF_PAYMENTS ", URL_PAYMENT_REQUESTS_SELECT_COMPANY);

        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                Log.i("aaaaaabb", String.valueOf(result));
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        CompanyNames.add(jsonObject.getString("Name"));
                        companyNameAndId.put(jsonObject.getString("Name"), jsonObject.getString("ID"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COMPANY ID", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_company.setAdapter(arrayAdapterPayments);
    }


    private void printErrorMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
            }

            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = "";
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    Log.i("data", String.valueOf(data));
                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        message = message + data.get(key) + "\n";
                    }
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}