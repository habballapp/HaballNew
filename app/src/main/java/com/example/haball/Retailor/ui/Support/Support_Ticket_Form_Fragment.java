package com.example.haball.Retailor.ui.Support;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Support.Support_Ditributor.Support_Ticket_Form;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support_Ticket_Form_Fragment extends Fragment {

    private EditText BName, Email, MobileNo, Comment;
    private String DistributorId;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4030/api/lookup/public/ISSUE_TYPE_PUBLIC";
    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4030/api/lookup/public/CRITICALITY_PUBLIC";
    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4030/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4030/api/contact/save";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();

    private String issueType,Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter arrayAdapterIssueType;
    private ArrayAdapter arrayAdapterCriticality;
    private ArrayAdapter arrayAdapterPreferredContact;
    private Button ticket_btn;



    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_support__ticket__form__retailerform, container, false);

      

      

      


        BName = root.findViewById(R.id.BName);
        Email = root.findViewById(R.id.Email);
        MobileNo = root.findViewById(R.id.MobileNo);
        Comment = root.findViewById(R.id.Comment);
        IssueType = root.findViewById(R.id.IssueType);
        critcicality = root.findViewById(R.id.critcicality);
        Preffered_Contact = root.findViewById(R.id.Preffered_Contact);
        ticket_btn = root.findViewById(R.id.ticket_btn);
        ticket_btn.setEnabled(false);
        ticket_btn.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );
        

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
                checkFieldsForEmptyValues();
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
                checkFieldsForEmptyValues();
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
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(BName.getText().toString()) ||
                        TextUtils.isEmpty(Email.getText().toString()) ||
                        TextUtils.isEmpty(Comment.getText().toString()) ||
                        TextUtils.isEmpty(MobileNo.getText().toString())) {

                    Snackbar.make(v, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    try {
                        makeTicketAddRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        } );



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

        BName.addTextChangedListener( textWatcher );
        Email.addTextChangedListener( textWatcher );
        MobileNo.addTextChangedListener( textWatcher );


        return root;
    }

    private void checkFieldsForEmptyValues() {

        String bname = BName.getText().toString();
        String email = Email.getText().toString();
        String mobile = MobileNo.getText().toString();
        String contact = (String) Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String issue_type = IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String critical = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();

        if (bname.equals( "" )
                || email.equals("")
                || mobile.equals( "" )
                || contact.equals("Preferred Method of Contacting *")
                || issue_type.equals( "Issue Type *" )
                || critical.equals( "Criticality *" )
        ) {
            ticket_btn.setEnabled( false );
            ticket_btn.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );

        } else {
            ticket_btn.setEnabled( true );
            ticket_btn.setBackground( getResources().getDrawable( R.drawable.button_background ) );
        }
    }
    private void fetchIssueType() {
        JsonArrayRequest sr = new JsonArrayRequest( Request.Method.GET, URL_SPINNER_ISSUETYPE,null,new Response.Listener<JSONArray>() {
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
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this.getContext()).add(sr);
        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterIssueType.notifyDataSetChanged();
        IssueType.setAdapter(arrayAdapterIssueType);
    }

    private void fetchCriticality() {
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
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this.getContext()).add(sr);
        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCriticality.notifyDataSetChanged();
        critcicality.setAdapter(arrayAdapterCriticality);
    }

    private void fetchPrefferedContact() {
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
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this.getContext()).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }

    private void makeTicketAddRequest() throws JSONException {
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
//                sr.finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }

        });
        Volley.newRequestQueue(this.getContext()).add(sr);
    }


}
