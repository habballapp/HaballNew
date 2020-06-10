package com.example.haball.Retailor.ui.Support;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.example.haball.Support.Support_Ditributor.Support_Ticket_Form;
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

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support_Ticket_Form_Fragment extends Fragment {

    private EditText BName, Email, MobileNo, Comment;
    private String DistributorId;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_DATA = " http://175.107.203.97:4014/api/lookup/null";
    //    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4014/api/lookup/public/ISSUE_TYPE_PRIVATE";
//    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4014/api/lookup/public/CRITICALITY_PRIVATE";
//    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4014/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4014/api/support/PrivateSave";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();
    private Map<String, String> issue_type_map = new HashMap<>();
    private Map<String, String> criticality_map = new HashMap<>();
    private Map<String, String> preffered_contact_map = new HashMap<>();

    private String issueType, Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter arrayAdapterIssueType;
    private ArrayAdapter arrayAdapterCriticality;
    private ArrayAdapter arrayAdapterPreferredContact;
    private Button ticket_btn;
    private Typeface myFont;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_support__ticket__form__retailerform, container, false);


        SharedPreferences data = getContext().getSharedPreferences("SendData",
                Context.MODE_PRIVATE);
        final String first_name = data.getString("first_name", "");
        final String email = data.getString("email", "");
        final String phone_number = data.getString("phone_number", "");

        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        Log.i("name", first_name);
        Log.i("email", email);
        Log.i("phone_number", phone_number);

        BName = root.findViewById(R.id.BName);
        Email = root.findViewById(R.id.Email);
        MobileNo = root.findViewById(R.id.MobileNo);
        Comment = root.findViewById(R.id.Comment);
        IssueType = root.findViewById(R.id.IssueType);
        critcicality = root.findViewById(R.id.critcicality);
        Preffered_Contact = root.findViewById(R.id.Preffered_Contact);
        ticket_btn = root.findViewById(R.id.ticket_btn);
        ticket_btn.setEnabled(false);
        ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        Email.setText(email);
        MobileNo.setText(phone_number);
        BName.setText(first_name);


        issue_type.add("Issue Type *");
        criticality.add("Criticality *");
        preffered_contact.add("Preferred Method of Contacting *");


//        arrayAdapterIssueType = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterIssueType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, issue_type) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };
//        arrayAdapterCriticality = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterCriticality = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, criticality) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };
//        arrayAdapterPreferredContact = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, preffered_contact);
        arrayAdapterPreferredContact = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, preffered_contact) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };

        fetchSpinnerData();
//        fetchCriticality();
//        fetchPrefferedContact();


        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                            ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
//                    issueType = issue_type.get(i);
                    issueType = issue_type_map.get(issue_type.get(i));
                    checkFieldsForEmptyValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                            ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
//                    Criticality = criticality.get(i);
                    Criticality = criticality_map.get(criticality.get(i));
                    checkFieldsForEmptyValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(50,0 ,50 ,0);
                            ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    PrefferedContacts = preffered_contact_map.get(preffered_contact.get(i));
                    checkFieldsForEmptyValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(BName.getText().toString()) ||
                        TextUtils.isEmpty(Email.getText().toString()) ||
//                        TextUtils.isEmpty(Comment.getText().toString()) ||
                        TextUtils.isEmpty(MobileNo.getText().toString())) {

                    Snackbar.make(v, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
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
        MobileNo.addTextChangedListener(textWatcher);


        return root;
    }

    private void checkFieldsForEmptyValues() {

        String bname = BName.getText().toString();
        String email = Email.getText().toString();
        String mobile = MobileNo.getText().toString();
        String comment = Comment.getText().toString();
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
                || email.equals("")
                || mobile.equals("")
//                || comment.equals("")
                || contact.equals("Preferred Method of Contacting *")
                || issue_type.equals("Issue Type *")
                || critical.equals("Criticality *")
        ) {
            ticket_btn.setEnabled(false);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            ticket_btn.setEnabled(true);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
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
//                printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
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
//                printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
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
//                printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
//        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPreferredContact.notifyDataSetChanged();
//        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
//    }


    private void fetchSpinnerData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);


        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_DATA, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        if (jsonObject.get("type").equals("ISSUE_TYPE_PRIVATE")) {
                            issue_type.add(jsonObject.getString("value"));
                            issue_type_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        }
                        else if (jsonObject.get("type").equals("CONTACTING_METHOD")){
                            preffered_contact.add(jsonObject.getString("value"));
                            preffered_contact_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        }
                        else if (jsonObject.get("type").equals("CRITICALITY_PRIVATE")) {
                            criticality.add(jsonObject.getString("value"));
                            criticality_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        }

                    }
                    arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterCriticality.notifyDataSetChanged();
                    critcicality.setAdapter(arrayAdapterCriticality);

                    arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterPreferredContact.notifyDataSetChanged();
                    Preffered_Contact.setAdapter(arrayAdapterPreferredContact);

                    arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterIssueType.notifyDataSetChanged();
                    IssueType.setAdapter(arrayAdapterIssueType);
                    Log.i("preffered_contact => ", preffered_contact.toString());
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
        Volley.newRequestQueue(this.getContext()).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }

    private void makeTicketAddRequest() throws JSONException {
        JSONObject map = new JSONObject();
        map.put("ContactName", BName.getText().toString());
        map.put("Email", Email.getText().toString());
        map.put("MobileNumber", MobileNo.getText().toString());
//        map.put("DistributorId", DistributorId);
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("PreferredContactMethod", PrefferedContacts);
        map.put("Description", Comment.getText().toString());
        map.put("ID", 0);

        Log.i("TICKET_OBJECT", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
                Toast.makeText(getContext(), "Ticket generated successfully.", Toast.LENGTH_LONG).show();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
                fragmentTransaction.commit();
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
        Volley.newRequestQueue(this.getContext()).add(sr);
    }

    private void printErrorMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
