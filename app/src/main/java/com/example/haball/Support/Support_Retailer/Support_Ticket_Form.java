package com.example.haball.Support.Support_Retailer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Select_User.Register_Activity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Support_Ticket_Form extends AppCompatActivity {

    private EditText BName, Email, MobileNo, Comment;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_DATA = "http://175.107.203.97:4014/api/support/PublicUsers";
    //    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4013/api/lookup/public/ISSUE_TYPE_PUBLIC";
//    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4013/api/lookup/public/CRITICALITY_PUBLIC";
//    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4013/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4014/api/support/PublicSave";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();
    private HashMap<String, String> issue_type_map = new HashMap<>();
    private HashMap<String, String> criticality_map = new HashMap<>();
    private HashMap<String, String> preffered_contact_map = new HashMap<>();

    private String issueType, Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter<String> arrayAdapterIssueType, arrayAdapterCriticality, arrayAdapterPreferredContact;

    private Button login_submit, login_btn;

    private String DistributorId;
    private TextView tv_main_heading, tv_sub_heading;
    private int keyDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need__support);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);


        BName = findViewById(R.id.BName);
        Email = findViewById(R.id.Email);
        MobileNo = findViewById(R.id.MobileNo);
        Comment = findViewById(R.id.Comment);
        IssueType = findViewById(R.id.IssueType);
        critcicality = findViewById(R.id.critcicality);
        Preffered_Contact = findViewById(R.id.Preffered_Contact);
        login_submit = findViewById(R.id.login_submit);
        tv_main_heading = findViewById(R.id.tv_main_heading);
        tv_main_heading.setText(String.valueOf(tv_main_heading.getText()).replace("Distributor", "Retailer"));
        tv_sub_heading = findViewById(R.id.tv_sub_heading);
        tv_sub_heading.setText(String.valueOf(tv_sub_heading.getText()).replace("Distributor", "Retailer"));

        login_submit.setEnabled(false);
        login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        login_btn = findViewById(R.id.login_btn);
        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        issue_type.add("Issue Type *");
        criticality.add("Criticality *");
        preffered_contact.add("Preferred Method of Contacting *");

        arrayAdapterIssueType = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterCriticality = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterPreferredContact = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, preffered_contact);

//        fetchIssueType();
//        fetchCriticality();
//        fetchPrefferedContact();
        fetchSpinnerData();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
//                issueType = issue_type.get(i);
                issueType = issue_type_map.get(issue_type.get(i));
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    Criticality = criticality_map.get(criticality.get(i));
                }
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
//                PrefferedContacts = preffered_contact.get(i);
                PrefferedContacts = preffered_contact_map.get(preffered_contact.get(i));
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(BName.getText().toString()) ||
                        TextUtils.isEmpty(Email.getText().toString()) ||
                        TextUtils.isEmpty(Comment.getText().toString()) ||
                        TextUtils.isEmpty(MobileNo.getText().toString())) {

                    Snackbar.make(view, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                } else {

                    try {
                        makeTicketAddRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
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
        BName.addTextChangedListener(textWatcher);
        Email.addTextChangedListener(textWatcher);
        MobileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MobileNo.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = MobileNo.getText().length();
                    if(len == 4) {
                        MobileNo.setText(MobileNo.getText() + "-");
                        MobileNo.setSelection(MobileNo.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                checkFieldsForEmptyValues();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void checkFieldsForEmptyValues() {

        String bname = BName.getText().toString();
        String email = Email.getText().toString();
        String mobile = MobileNo.getText().toString();
        String contact = "Preferred Method of Contacting *";
        if (Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()) != null)
            contact = Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String issue_type = "Issue Type *";
        if (IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()) != null)
            issue_type = IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String critical = "Criticality *";
        if (critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()) != null)
            critical = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();

        if (bname.equals("")
                || mobile.equals("")
                || email.equals("")
                || issue_type.equals("Issue Type *")
                || critical.equals("Criticality *")
                || contact.equals("Preferred Method of Contacting *")
        ) {
            login_submit.setEnabled(false);
            login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            login_submit.setEnabled(true);
            login_submit.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void makeTicketAddRequest() throws JSONException {
        JSONObject map = new JSONObject();
        map.put("ContactName", BName.getText().toString());
        map.put("Email", Email.getText().toString());
        map.put("MobileNumber", MobileNo.getText().toString());
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("PreferredContactMethod", PrefferedContacts);
        map.put("Description", Comment.getText().toString());

        Log.i("TICKET OBJECT", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
                Toast.makeText(getApplicationContext(), "Ticket Created Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
                startActivity(intent);
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(sr);
    }
//
//    private void fetchIssueType() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        issue_type.add(jsonObject.getString("value"));
//                    }
//                    Log.i("issue type values => ", issue_type.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterIssueType.notifyDataSetChanged();
//        IssueType.setAdapter(arrayAdapterIssueType);
//    }
//
//    private void fetchCriticality() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        criticality.add(jsonObject.getString("value"));
//
//                    }
//                    Log.i("criticality values => ", criticality.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE OF criticality", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterCriticality.notifyDataSetChanged();
//        critcicality.setAdapter(arrayAdapterCriticality);
//    }
//
//    private void fetchPrefferedContact() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        preffered_contact.add(jsonObject.getString("value"));
//                    }
//
//                    Log.i("preffered_contact => ", preffered_contact.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE preferedcont", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPreferredContact.notifyDataSetChanged();
//        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
//    }

    private void fetchSpinnerData() {
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_SPINNER_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                JSONObject jsonObject = null;
                try {
                    JSONArray temp_preffered_contact = result.getJSONArray("CONTACTING_METHOD");
                    jsonObject = null;
                    for (int i = 0; i < temp_preffered_contact.length(); i++) {
                        jsonObject = temp_preffered_contact.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));
                        preffered_contact_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }

                    JSONArray temp_criticality = result.getJSONArray("CRITICALITY_PUBLIC");
                    jsonObject = null;
                    for (int i = 0; i < temp_criticality.length(); i++) {
                        jsonObject = temp_criticality.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));
                        criticality_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }

                    JSONArray temp_issue_type = result.getJSONArray("ISSUE_TYPE_PUBLIC");
                    jsonObject = null;
                    for (int i = 0; i < temp_issue_type.length(); i++) {
                        jsonObject = temp_issue_type.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                        issue_type_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }
                    Log.i("preffered_contact => ", preffered_contact.toString());
                    arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterPreferredContact.notifyDataSetChanged();
                    Preffered_Contact.setAdapter(arrayAdapterPreferredContact);

                    arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterCriticality.notifyDataSetChanged();
                    critcicality.setAdapter(arrayAdapterCriticality);

                    arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterIssueType.notifyDataSetChanged();
                    IssueType.setAdapter(arrayAdapterIssueType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);
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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }


    private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(this, "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                Log.i("responseBody", responseBody);
                JSONObject data = new JSONObject(responseBody);
                Log.i("data", String.valueOf(data));
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    message = message + data.get(key) + "\n";
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
