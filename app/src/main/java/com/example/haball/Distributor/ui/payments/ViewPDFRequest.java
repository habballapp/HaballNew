package com.example.haball.Distributor.ui.payments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ViewPDFRequest {
    public String URL_PDF_VIEW = "http://175.107.203.97:4008/api/Invoices/DetailReport/";
    public String DistributorId, Token;
    public Context mContext;
    public ViewPDFRequest(){}

    public void viewPDF(Context context, String paymentId) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        URL_PDF_VIEW = URL_PDF_VIEW+paymentId;

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);

        StringRequest sr = new StringRequest(Request.Method.POST, URL_PDF_VIEW, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String result) {
                Log.i("PDF VIEW..", result);
                try {
                    showPdf(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(sr);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPdf(String result) throws IOException {

        InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));

        Log.i("stream", String.valueOf(stream));

        try {
            //input is your input stream object
            File file = new File(Environment.getExternalStorageDirectory(), "filename.pdf");
            OutputStream output = new FileOutputStream(file);
            Log.i("output", String.valueOf(output));

            try {
                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;
                    Log.i("byte ", "bytes");

                    while ((read = stream.read(buffer)) != -1) {
                        Log.i("read ", String.valueOf(read));

                        output.write(buffer, 0, read);
                        Log.i("output.write ", "output.write");

                    }
                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // handle exception, define IOException and others
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

}
