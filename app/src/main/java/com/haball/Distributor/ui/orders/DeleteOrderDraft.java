package com.haball.Distributor.ui.orders;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.widget.Toast;

        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.haball.Distributor.DistributorDashboard;
        import com.haball.Distributor.ui.home.HomeFragment;
        import com.haball.R;
        import com.haball.Registration.BooleanRequest;
        import com.haball.Retailor.RetailorDashboard;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class DeleteOrderDraft {
    public String URL_DELETE_ORDER_DRAFT = "http://175.107.203.97:4013/api/orders/deletedraft/";
    public String DistributorId, Token;
    public Context mContext;
    private FragmentTransaction fragmentTransaction;

    public DeleteOrderDraft() {
    }

    public void deleteDraft(final Context context, String orderId, final String orderNumber) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        if(!URL_DELETE_ORDER_DRAFT.contains(orderId))
            URL_DELETE_ORDER_DRAFT = URL_DELETE_ORDER_DRAFT + orderId;

//        final Context finalcontext = context;
        BooleanRequest request = new BooleanRequest(Request.Method.GET, URL_DELETE_ORDER_DRAFT, null, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                // TODO handle the response
                if(response)
                    Toast.makeText(context, "Draft for Order # " + orderNumber + " is deleted", Toast.LENGTH_LONG).show();
                SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "1");
                editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) context), DistributorDashboard.class);
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
