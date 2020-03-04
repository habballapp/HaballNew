package com.example.haball.Distributor.ui.support;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.haball.R;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.UnsupportedEncodingException;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;

public class DeleteSupport {
//    public String URL_SUPPORT_STATUS_CHANGE = "http://175.107.203.97:4008/api/contact/StatusChange";
    public String URL_SUPPORT_STATUS_CHANGE = "http://175.107.203.97:4008/api/contact/StatusDelete";
    public String DistributorId, Token;
    public Context mContext;
    private String response = "";

    public DeleteSupport(){}

    public String DeleteSupportTicket(final Context context, String supportId) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("Id", supportId);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SUPPORT_STATUS_CHANGE, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("support delete",String.valueOf(result));
                    Log.i("support delete id",String.valueOf(result.get("Id")));
                    final AlertDialog delete_successAlert = new AlertDialog.Builder(context).create();
                    LayoutInflater delete_inflater = LayoutInflater.from(context);
                    View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
                    delete_successAlert.setView(delete_success_alert);

                    ImageButton img_delete = (ImageButton) delete_success_alert.findViewById(R.id.btn_close_success);
                    img_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete_successAlert.dismiss();
                        }
                    });
                    delete_successAlert.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(sr);
        return response;
    }


    private void printErrorMessage(VolleyError error) {
        if(error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    //                if (data.get(key) instanceof JSONObject) {
                    message = message + data.get(key) + "\n";
                    //                }
                }
                //                    if(data.has("message"))
                //                        message = data.getString("message");
                //                    else if(data. has("Error"))
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
