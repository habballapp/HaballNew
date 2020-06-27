package com.haball.Support.Support_Ditributor;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
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
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.HaballError;
import com.haball.R;
import com.haball.TextField;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import androidx.fragment.app.FragmentManager;

public class Support_Ticket_Form extends AppCompatActivity {

    private TextInputEditText  BName, Email, MobileNo, Comment;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4013/api/lookup/public/ISSUE_TYPE_PUBLIC";
    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4013/api/lookup/public/CRITICALITY_PUBLIC";
    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4013/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4013/api/contact/save";

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
    private int keyDel;

    private String DistributorId;
    private TextInputLayout layout_BName,layout_Email,layout_MobileNo,layout_Comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need__support);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

//        actionBar.setCustomView(customView);
//        actionBar.setDisplayShowCustomEnabled(true);


        BName = findViewById(R.id.BName);
        Email = findViewById(R.id.Email);
        MobileNo = findViewById(R.id.MobileNo);
        Comment = findViewById(R.id.Comment);
        IssueType = findViewById(R.id.IssueType);
        critcicality = findViewById(R.id.critcicality);
        Preffered_Contact = findViewById(R.id.Preffered_Contact);
        login_submit = findViewById(R.id.login_submit);

        layout_BName = findViewById(R.id.layout_BName);
        layout_Email = findViewById(R.id.layout_Email);
        layout_MobileNo = findViewById(R.id.layout_MobileNo);
        layout_Comment = findViewById(R.id.layout_Comment);

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

        new TextField().changeColor(this, layout_BName, BName);
        new TextField().changeColor(this, layout_Email, Email);
        new TextField().changeColor(this,  layout_MobileNo , MobileNo);
        new TextField().changeColor(this, layout_Comment, Comment);

        fetchIssueType();
        fetchCriticality();
        fetchPrefferedContact();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(14);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
    //                issueType = issue_type.get(i);
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
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(14);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                        try {
                               ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
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
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(14);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                        try {
                               ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
    //                PrefferedContacts = preffered_contact.get(i);
                    PrefferedContacts = preffered_contact_map.get(preffered_contact.get(i));
                    checkFieldsForEmptyValues();
                }
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
                    if (len == 4) {
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

        String issue_type = (String) IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String critical = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();
        String contact = Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
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


    @Override
    public void onBackPressed() {
        String txt_BName = BName.getText().toString();
        String txt_Email = Email.getText().toString();
        String txt_MobileNo = MobileNo.getText().toString();
        String txt_IssueType = (String) IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String txt_critcicality = (String) critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();
        String txt_Preffered_Contact = (String) Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String txt_Comment = Comment.getText().toString();

        if(!txt_BName.equals("") || !txt_Email.equals("") || !txt_MobileNo.equals("") || !txt_IssueType.equals("Issue Type *") || !txt_critcicality.equals("Criticality *") || !txt_Preffered_Contact.equals("Preferred Method of Contacting *") || !txt_Comment.equals("")) {
            showDiscardDialog();
        } else {
            finish();
        }
    }


    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                finish();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
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
                Toast.makeText(getApplicationContext(), "Ticket Created Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Support_Ticket_Form.this, Distribution_Login.class);
                startActivity(intent);
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
            }

        });
        Volley.newRequestQueue(this).add(sr);
    }

    private void fetchIssueType() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                        issue_type_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }
                    Log.i("issue type values => ", issue_type.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
            }
        }) {
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
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterIssueType.notifyDataSetChanged();
        IssueType.setAdapter(arrayAdapterIssueType);
    }

    private void fetchCriticality() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));
                        criticality_map.put(jsonObject.getString("value"), jsonObject.getString("key"));

                    }
                    Log.i("criticality values => ", criticality.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF criticality", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
            }
        }) {
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
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCriticality.notifyDataSetChanged();
        critcicality.setAdapter(arrayAdapterCriticality);
    }

    private void fetchPrefferedContact() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));
                        preffered_contact_map.put(jsonObject.getString("value"), jsonObject.getString("key"));

                    }

                    Log.i("preffered_contact => ", preffered_contact.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
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


    // private void printErrorMessage(VolleyError error) {
    //     if (error instanceof NetworkError) {
    //         Toast.makeText(this, "Network Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ServerError) {
    //         Toast.makeText(this, "Server Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof AuthFailureError) {
    //         Toast.makeText(this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ParseError) {
    //         Toast.makeText(this, "Parse Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof NoConnectionError) {
    //         Toast.makeText(this, "No Connection Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof TimeoutError) {
    //         Toast.makeText(this, "Timeout Error !", Toast.LENGTH_LONG).show();
    //     }

    //     if (error.networkResponse != null && error.networkResponse.data != null) {
    //         try {
    //             String message = "";
    //             String responseBody = new String(error.networkResponse.data, "utf-8");
    //             Log.i("responseBody", responseBody);
    //             JSONObject data = new JSONObject(responseBody);
    //             Log.i("data", String.valueOf(data));
    //             Iterator<String> keys = data.keys();
    //             while (keys.hasNext()) {
    //                 String key = keys.next();
    //                 message = message + data.get(key) + "\n";
    //             }
    //             Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    //         } catch (UnsupportedEncodingException e) {
    //             e.printStackTrace();
    //         } catch (JSONException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
}
