package com.example.haball.Distributor.ui.payments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Payment.ProofOfPaymentAdapter;
import com.example.haball.Payment.ProofOfPaymentModel;
import com.example.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProofOfPaymentForm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String Token;
    private String URL_PROOF_OF_PAYMENTS = "http://175.107.203.97:4008/api/prepaidrequests/PrepaidPOP/";
    private String URL_MODE_OF_PAYMENTS = "http://175.107.203.97:4008/api/lookup/PROOF_OF_PAYMENT";

    private Spinner spinner_payment_id, spinner_mode_of_payments;
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterPaymentModes;
    private List<ProofOfPaymentModel> proofOfPaymentsList = new ArrayList<>();
    private List<String> payment_ids = new ArrayList<>();
    private List<String> payment_modes = new ArrayList<>();
    private String DistributorId, selected_paymentid, selected_paymentmode;
    private Button btn_upload;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            String imageName = getRealPathFromURI(imageUri);
            Toast.makeText(getContext(), imageName, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_proof__of__payment__form, container, false);

        payment_ids.add("Payment ID *");
        payment_modes.add("Payment Mode *");

        spinner_payment_id = root.findViewById(R.id.spinner_id);
        spinner_mode_of_payments = root.findViewById(R.id.payment_mode);
        btn_upload = root.findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View view_popup = inflater.inflate(R.layout.upload_file_layout, null);
//                alertDialog.setView(view_popup);
//
//                alertDialog.show();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, payment_ids);
        arrayAdapterPaymentModes = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, payment_modes);
        spinner_payment_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    selected_paymentid = payment_ids.get(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_mode_of_payments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else{
                    selected_paymentmode = payment_modes.get(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        recyclerView = root.findViewById(R.id.rv_proof_of_payments);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        fetchPaymentsId();
        fetchPaymentModes();
        return root;
    }

    private void fetchPaymentModes() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        URL_PROOF_OF_PAYMENTS = URL_PROOF_OF_PAYMENTS + DistributorId;
        Log.i("URL_PROOF_OF_PAYMENTS ", URL_PROOF_OF_PAYMENTS);

        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_MODE_OF_PAYMENTS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        payment_modes.add(jsonObject.getString("value"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE PAYMENT MODES", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("rightid", "-1");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPaymentModes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPaymentModes.notifyDataSetChanged();
        spinner_mode_of_payments.setAdapter(arrayAdapterPaymentModes);
    }


    private void fetchPaymentsId() {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");

            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            DistributorId = sharedPreferences1.getString("Distributor_Id", "");
            Log.i("DistributorId ", DistributorId);

            URL_PROOF_OF_PAYMENTS = URL_PROOF_OF_PAYMENTS + DistributorId;
            Log.i("URL_PROOF_OF_PAYMENTS ", URL_PROOF_OF_PAYMENTS);

            Log.i("Token", Token);

            JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PROOF_OF_PAYMENTS, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray result) {
                    try {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < result.length(); i++) {
                            jsonObject = result.getJSONObject(i);
                            payment_ids.add(jsonObject.getString("PaymentNumber"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("RESPONSE OF PAYMENT ID", result.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
            Volley.newRequestQueue(getContext()).add(sr);
            arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterPayments.notifyDataSetChanged();
            spinner_payment_id.setAdapter(arrayAdapterPayments);
        }
    private String getRealPathFromURI(Uri contentURI) {

        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }
}
