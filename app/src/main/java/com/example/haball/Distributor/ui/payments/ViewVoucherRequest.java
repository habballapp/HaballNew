package com.example.haball.Distributor.ui.payments;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.pdf.PdfDocument;
        import android.net.Uri;
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
        import androidx.core.content.FileProvider;

        import com.android.volley.AuthFailureError;
        import com.android.volley.BuildConfig;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
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
        import java.util.ArrayList;
        import java.util.Base64;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;

        import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class ViewVoucherRequest {
    public String URL_VOUCHER_VIEW = "http://175.107.203.97:4008/api/prepaidrequests/printrecipt";
    public String DistributorId, Token;
    public Context mContext;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public ViewVoucherRequest(){}

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
//        URL_VOUCHER_VIEW = URL_VOUCHER_VIEW+paymentId;

        JSONObject map = new JSONObject();
        map.put("ID", Integer.parseInt(paymentId));
        Log.i("map", String.valueOf(map));

        final Context finalcontext = context;
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, URL_VOUCHER_VIEW, map, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                // TODO handle the response
                try {
                    if (response!=null) {
                        String dir = Environment.getExternalStorageDirectory() + "/Download/";
                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
                        String name = dir + "Voucher - " + timeStamp + ".pdf";
                        FileOutputStream fPdf = new FileOutputStream(name);

                        fPdf.write(response);
                        fPdf.flush();
                        fPdf.close();
                        Log.i("Download Complete", "Download complete.");
                        Toast.makeText(mContext, "File saved in Downloads", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                    e.printStackTrace();
                }
            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }, null)  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
    }
}