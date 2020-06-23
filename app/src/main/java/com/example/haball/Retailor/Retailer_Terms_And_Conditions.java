package com.example.haball.Retailor;

        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentActivity;
        import androidx.lifecycle.ViewModelProviders;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.ColorStateList;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.Build;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.RelativeLayout;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.haball.R;
        import com.example.haball.Registration.BooleanRequest;
        import com.example.haball.Retailer_Login.RetailerLogin;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.lang.reflect.Type;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Retailer_Terms_And_Conditions extends Fragment {

    private Button back_button;
    private String Token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.retailer_logged_in_terms_and_conditions, container, false);

        back_button = root.findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getContext(), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();
            }
        });

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }

}
