package com.example.haball.Distributor.ui.payments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Document;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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
//                byte[] bytes = result.getBytes(Charset.forName("UTF-8"));
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                    try {
////                        byte[] decodedString = Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8"));
////                        String string = new String(decodedString);
////                        Log.i("PDF BYTE DECODED..", string);
////
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////
////                }
////                String string = new String(bytes);
////                Log.i("PDF BYTE ARRAY..", string);
//                saveToFile(bytes, "file.pdf");
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

    public void saveToFile(byte[] byteArray, String pFileName){
        File f = new File(Environment.getExternalStorageDirectory() + "/myappname");
        if (!f.isDirectory()) {
            f.mkdir();
        }

        String fileName = Environment.getExternalStorageDirectory() + "/myappname/" + pFileName;

        try {

            FileOutputStream fPdf = new FileOutputStream(fileName);

            fPdf.write(byteArray);
            fPdf.flush();
            fPdf.close();
            Toast.makeText(mContext, "File successfully saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, "File create error", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "File write error", Toast.LENGTH_LONG).show();
        }

    }
}
