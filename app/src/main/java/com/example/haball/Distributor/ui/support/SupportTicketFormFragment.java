package com.example.haball.Distributor.ui.support;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.style.UnderlineSpan;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
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
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.haball.Distribution_Login.Distribution_Login;
        import com.example.haball.Distributor.DistributorDashboard;
        import com.example.haball.R;
        import com.example.haball.Registration.Registration_Actvity2;
        import com.google.gson.JsonArray;
        import com.google.gson.JsonObject;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class SupportTicketFormFragment extends Fragment {

    private EditText BName, Email, MobileNo, Comment;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4008/api/lookup/ISSUE_TYPE_PRIVATE";
    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4008/api/lookup/CRITICALITY_PRIVATE";
    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4008/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4008/api/contact/save";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();

    private String issueType,Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter<String> arrayAdapterIssueType, arrayAdapterCriticality, arrayAdapterPreferredContact;

    private Button ticket_btn;

    private String DistributorId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_support__ticket__form, container, false);

        BName = root.findViewById(R.id.BName);
        Email = root.findViewById(R.id.Email);
        MobileNo = root.findViewById(R.id.MobileNo);
        Comment = root.findViewById(R.id.Comment);
        IssueType = root.findViewById(R.id.IssueType);
        critcicality = root.findViewById(R.id.critcicality);
        Preffered_Contact = root.findViewById(R.id.Preffered_Contact);
        ticket_btn = root.findViewById(R.id.ticket_btn);

        issue_type.add("Issue Type *");
        criticality.add("Criticality *");
        preffered_contact.add("Preferred Method of Contacting *");

        arrayAdapterIssueType = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterCriticality = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterPreferredContact = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, preffered_contact);

        fetchIssueType();
        fetchCriticality();
        fetchPrefferedContact();

        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                issueType = issue_type.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                Criticality = criticality.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                PrefferedContacts = preffered_contact.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeTicketAddRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void makeTicketAddRequest() throws JSONException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences.getString("Distributor_Id","");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("Name", BName.getText().toString());
        map.put("EmailAddress", Email.getText().toString());
        map.put("Mobile", MobileNo.getText().toString());
        map.put("DistributorId", DistributorId);
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("ContactingMethod", PrefferedContacts);
        map.put("Message", Comment.getText().toString());
        map.put("ID", 0);

        Log.i("TICKET OBJECT", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), new SupportFragment());
                fragmentTransaction.commit();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("rightid", "-1");
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchIssueType() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                    }
                    Log.i("issue type values => ",issue_type.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("rightid", "-1");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterIssueType.notifyDataSetChanged();
        IssueType.setAdapter(arrayAdapterIssueType);
    }

    private void fetchCriticality() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);


        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));

                    }
                    Log.i("criticality values => ",criticality.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF criticality", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("rightid", "-1");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCriticality.notifyDataSetChanged();
        critcicality.setAdapter(arrayAdapterCriticality);
    }

    private void fetchPrefferedContact() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token","");
        Log.i("Token", Token);


        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));
                    }

                    Log.i("preffered_contact => ",preffered_contact.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("rightid", "-1");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }

}

