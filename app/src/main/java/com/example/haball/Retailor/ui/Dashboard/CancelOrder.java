package com.example.haball.Retailor.ui.Dashboard;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.haball.Retailor.RetailorDashboard;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;

public class CancelOrder {
    public String URL_CANCEL_ORDER = "http://175.107.203.97:4014/api/orders/cancelorder";
    public String Token;
    public Context mContext;
    private FragmentTransaction fragmentTransaction;

    public CancelOrder() {
    }

    public void cancelOrder(final Context context, String orderId, final String orderNumber) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("ID", orderId);

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CANCEL_ORDER, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                Toast.makeText(context, "Order # " + orderNumber + " is cancelled", Toast.LENGTH_LONG).show();
                SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "1");
                editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) context), RetailorDashboard.class);
                ((FragmentActivity) context).startActivity(login_intent);
                ((FragmentActivity) context).finish();
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);

    }

}

