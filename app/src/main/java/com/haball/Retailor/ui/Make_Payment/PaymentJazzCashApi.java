package com.haball.Retailor.ui.Make_Payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.CustomToast;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.TextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PaymentJazzCashApi extends Fragment {
    private String Token, ID;
    private Button btn_create;

    //    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "https://retailer.haball.pk/api/kyc/KYCDistributorList";
//    private String URL_PAYMENT_REQUESTS_GET_DATA = "https://retailer.haball.pk/api/payaxis/PrePaidPay/";
    private String URL_PAYMENT_REQUESTS_GET_DATA = "http://175.107.203.97:4014/api/payaxis/PrePaidPay/359934761903956";
    private String URL_Jazz_Cash_Transaction = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/DoTransactionMWallet";
    private String URL_RegenerateTxnReference = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/RegenerateGenerateTxnReference";
    private String URL_Calculate_Secure_Hash = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/CalculateSecureHash";

    private String URL_PAYMENT_REQUESTS_SAVE = "https://retailer.haball.pk/api/prepaidrequests/save";

    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private FragmentTransaction fragmentTransaction;
    private String prepaid_id;
    private TextInputEditText txt_company, txt_payment_id, txt_beneficiary, txt_amount, txt_transaction_charges, txt_total_amount, txt_account_no, txt_cnic, txt_otp;
    private TextInputLayout layout_company, layout_payment_id, layout_beneficiary, layout_txt_amount, layout_transaction_charges, layout_txt_total_amount, layout_txt_account_no, layout_txt_cnic, layout_txt_otp;
    private String prepaid_number;
    private Typeface myFont;
    private Loader loader;
    private int keyDel;
    private String pp_Language = "";
    private String pp_MerchantID = "";
    private String pp_SubMerchantID = "";
    private String pp_Password = "";
    private String pp_BankID = "";
    private String pp_ProductID = "";
    private String pp_TxnRefNo = "";
    private String pp_Amount = "";
    private String pp_TxnCurrency = "";
    private String pp_TxnDateTime = "";
    private String pp_BillReference = "";
    private String pp_Description = "";
    private String pp_TxnExpiryDateTime = "";
    private String pp_SecureHash = "";
    private String ppmpf_1 = "";
    private String ppmpf_2 = "";
    private String ppmpf_3 = "";
    private String ppmpf_4 = "";
    private String ppmpf_5 = "";
    private String pp_MobileNumber = "";
    private String pp_CNIC = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pay_by_jazz_cash, container, false);

        btn_create = root.findViewById(R.id.btn_create);
        btn_create.setEnabled(false);
        btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        txt_company = root.findViewById(R.id.txt_company);
        layout_company = root.findViewById(R.id.layout_company);
        txt_payment_id = root.findViewById(R.id.txt_payment_id);
        layout_payment_id = root.findViewById(R.id.layout_payment_id);
        txt_beneficiary = root.findViewById(R.id.txt_beneficiary);
        layout_beneficiary = root.findViewById(R.id.layout_beneficiary);
        txt_amount = root.findViewById(R.id.txt_amount);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        layout_transaction_charges = root.findViewById(R.id.layout_transaction_charges);
        txt_total_amount = root.findViewById(R.id.txt_total_amount);
        layout_txt_total_amount = root.findViewById(R.id.layout_txt_total_amount);
        txt_account_no = root.findViewById(R.id.txt_account_no);
        layout_txt_account_no = root.findViewById(R.id.layout_txt_account_no);
        txt_cnic = root.findViewById(R.id.txt_cnic);
        layout_txt_cnic = root.findViewById(R.id.layout_txt_cnic);
        txt_otp = root.findViewById(R.id.txt_otp);
        layout_txt_otp = root.findViewById(R.id.layout_txt_otp);
        loader = new Loader(getContext());

        new TextField().changeColor(getContext(), layout_company, txt_company);
        new TextField().changeColor(getContext(), layout_payment_id, txt_payment_id);
        new TextField().changeColor(getContext(), layout_beneficiary, txt_beneficiary);
        new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
        new TextField().changeColor(getContext(), layout_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(getContext(), layout_txt_total_amount, txt_total_amount);
        new TextField().changeColor(getContext(), layout_txt_account_no, txt_account_no);
        new TextField().changeColor(getContext(), layout_txt_cnic, txt_cnic);
        new TextField().changeColor(getContext(), layout_txt_otp, txt_otp);

        getJazzCashData();

        txt_cnic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_cnic.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = txt_cnic.getText().length();
                    if (len == 5 || len == 13) {
                        txt_cnic.setText(txt_cnic.getText() + "-");
                        txt_cnic.setSelection(txt_cnic.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(txt_account_no.getText()).equals("") && !String.valueOf(txt_cnic.getText()).equals("")) {
                    btn_create.setEnabled(false);
                    btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                    try {
                        makeSaveRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_LONG).show();
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
        txt_account_no.addTextChangedListener(textWatcher);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String txt_account = String.valueOf(txt_account_no.getText());
        final String txt_cnic_no = String.valueOf(txt_cnic.getText());
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        txt_amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_amount.clearFocus();
                    if (!txt_account.equals("") || !txt_cnic_no.equals("")) {
                        showDiscardDialog();
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
                    }
                }
                return false;
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    if (!txt_account.equals("") || !txt_cnic_no.equals("")) {
                        showDiscardDialog();
                        return true;
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
                    }
                }
                return false;
            }
        });

    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();

//                fm.popBackStack();
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

    private void getJazzCashData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        JSONObject map = new JSONObject();
//        map.put("ID", 0);
//        map.put("DealerCode", companyNameAndId.get(company_names));
////        map.put("DealerCode", "201911672");
//        map.put("PaidAmount", txt_amount.getText().toString());

//        Log.i("JSON ", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_GET_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    Log.i("Response PR", result.toString());


//                    prepaid_number = result.getString("PrePaidNumber");
//                    prepaid_id = result.getString("ID");


                    SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("PaymentId",
                            Context.MODE_PRIVATE);

                    SharedPreferences sharedPreferences_retailer = getContext().getSharedPreferences("SendData",
                            Context.MODE_PRIVATE);

                    txt_payment_id.setText(String.valueOf(result.getString("pp_BillReference")));
                    if (!String.valueOf(txt_payment_id.getText()).equals(""))
                        txt_payment_id.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_company.setText(sharedPreferences1.getString("CompanyName", ""));
                    if (!String.valueOf(txt_company.getText()).equals(""))
                        txt_company.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_amount.setText(String.valueOf(result.getString("pp_Amount")));
                    if (!String.valueOf(txt_amount.getText()).equals(""))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_beneficiary.setText(String.valueOf(result.getString("pp_Description")));
                    if (!String.valueOf(txt_beneficiary.getText()).equals(""))
                        txt_beneficiary.setTextColor(getResources().getColor(R.color.textcolor));


                    pp_Language = String.valueOf(result.getString("pp_Language"));
                    pp_MerchantID = String.valueOf(result.getString("pp_MerchantID"));
                    pp_SubMerchantID = String.valueOf(result.getString("pp_SubMerchantID"));
                    pp_Password = String.valueOf(result.getString("pp_Password"));
//                    pp_BankID = String.valueOf(result.getString("pp_BankID"));
//                    pp_ProductID = String.valueOf(result.getString("pp_ProductID"));
                    pp_TxnRefNo = String.valueOf(result.getString("pp_TxnRefNo"));
                    pp_Amount = String.valueOf(result.getString("pp_Amount"));
                    pp_TxnCurrency = String.valueOf(result.getString("pp_TxnCurrency"));
                    pp_TxnDateTime = String.valueOf(result.getString("pp_TxnDateTime"));
                    pp_BillReference = String.valueOf(result.getString("pp_BillReference"));
                    pp_Description = String.valueOf(result.getString("pp_Description"));
                    pp_TxnExpiryDateTime = String.valueOf(result.getString("pp_TxnExpiryDateTime"));
                    pp_SecureHash = String.valueOf(result.getString("pp_SecureHash"));
//                    ppmpf_1 = String.valueOf(result.getString("ppmpf_1"));
//                    ppmpf_2 = String.valueOf(result.getString("ppmpf_2"));
//                    ppmpf_3 = String.valueOf(result.getString("ppmpf_3"));
//                    ppmpf_4 = String.valueOf(result.getString("ppmpf_4"));
//                    ppmpf_5 = String.valueOf(result.getString("ppmpf_5"));
//                    pp_MobileNumber = String.valueOf(result.getString("pp_MobileNumber"));
//                    pp_CNIC = String.valueOf(result.getString("pp_CNIC"));

                    pp_MobileNumber = sharedPreferences_retailer.getString("phone_number", "");
                    pp_CNIC = sharedPreferences_retailer.getString("cnic", "");
                    pp_CNIC = pp_CNIC.substring(pp_CNIC.length() - 6);
//                    txt_cnic.setText(pp_CNIC);
                    txt_cnic.setText("345678");
//
//                    layout_transaction_charges = root.findViewById(R.id.layout_transaction_charges);
//                    txt_total_amount = root.findViewById(R.id.txt_total_amount);
//                    layout_txt_total_amount = root.findViewById(R.id.layout_txt_total_amount);
//                    txt_account_no = root.findViewById(R.id.txt_account_no);
//                    layout_txt_account_no = root.findViewById(R.id.layout_txt_account_no);
//                    txt_cnic = root.findViewById(R.id.txt_cnic);
//                    layout_txt_cnic = root.findViewById(R.id.layout_txt_cnic);
//                    txt_otp = root.findViewById(R.id.txt_otp);
//                    layout_txt_otp = root.findViewById(R.id.layout_txt_otp);
                } catch (JSONException e) {
                    Log.i("Response PR", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();

                btn_create.setEnabled(true);
                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void checkFieldsForEmptyValues() {
        String txt_account = String.valueOf(txt_account_no.getText());
        String txt_cnic_no = String.valueOf(txt_cnic.getText());
        if (txt_cnic_no.equals("")
                || txt_account.equals("")

        ) {
            btn_create.setEnabled(false);
            btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_create.setEnabled(true);
            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void makeSaveRequest() throws JSONException {
        loader.showLoader();

//        StringRequest sr = new StringRequest(Request.Method.POST, URL_RegenerateTxnReference, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loader.hideLoader();
//                JSONObject resultRegen = null;
//                try {
//                    resultRegen = new JSONObject(response);
//
//                    final JSONObject finalResultRegen = resultRegen;
//                    StringRequest sr = new StringRequest(Request.Method.POST, URL_Calculate_Secure_Hash, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            loader.hideLoader();
//                            Log.i("JSON_RESPONSE", response);
//                            JSONObject result = null;
//                            try {
//                                result = new JSONObject(response);


        StringRequest sr = new StringRequest(Request.Method.POST, URL_Jazz_Cash_Transaction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader.hideLoader();
                Log.i("JSON_RESPONSE", response);
                JSONObject result = null;
                try {
                    result = new JSONObject(response);

//                showSuccessDialog(prepaid_number);

                    try {
                        new CustomToast().showToast(getActivity(), result.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
//                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
//                Log.e("RESPONSE prepaid_number", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();

                btn_create.setEnabled(true);
                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Length", "742");
                params.put("Host", "sandbox.jazzcash.com.pk");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                params.put("Cookie", "7D16C5AA7D431DCB8410E08F674D4DAC=5j5dbijewsiuchvkcxb5hixg; __RequestVerificationToken_L0N1c3RvbWVyUG9ydGFs0=hnGmBOxY-jZXXkJM6jzpdTYaIZrB9TmFKuN0408HWo2SSTtoBIuW66kBlmyEBezYBpSuOsXfQzhB0zGG_ET0xXseAuDTY9raQzA3XrRreaJcJxvETCpWzsF7MT3jgLMGpSnRYQONQmlDAxArRK9BnA2; DAD4D476F80E0148BCD134D7AA5C61D7=l1ozp5bummp0i3hszm35pcz4");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
//        map.put("pp_Language", pp_Language);
                map.put("Request[pp_Language]", "EN");
//        map.put("pp_MerchantID", pp_MerchantID);
                map.put("Request[pp_MerchantID]", "MC37757");
//        map.put("pp_SubMerchantID", pp_SubMerchantID);
                map.put("Request[pp_SubMerchantID]", "");
//        map.put("pp_Password", pp_Password);
                map.put("Request[pp_Password]", "s0xcy12dv2");
//        map.put("pp_BankID", pp_BankID);
                map.put("Request[pp_BankID]", "");
//        map.put("pp_ProductID", pp_ProductID);
                map.put("Request[pp_ProductID]", "");
//        map.put("pp_TxnRefNo", pp_TxnRefNo);
//                try {
                    map.put("Request[pp_TxnRefNo]", "T20200629140125");
//                } catch (JSONException e) {
//                    map.put("Request[pp_TxnRefNo]", "");
//                    e.printStackTrace();
//                }
//        map.put("pp_Amount", pp_Amount);
                map.put("Request[pp_Amount]", "32653200");
//        map.put("pp_TxnCurrency", pp_TxnCurrency);
                map.put("Request[pp_TxnCurrency]", "PKR");
//        map.put("pp_TxnDateTime", pp_TxnDateTime);
                map.put("Request[pp_TxnDateTime]", "20200629140134");
//        map.put("pp_BillReference", pp_BillReference);
                map.put("Request[pp_BillReference]", "billRef");
//        map.put("pp_Description", pp_Description);
                map.put("Request[pp_Description]", "Description of transaction");
//        map.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
                map.put("Request[pp_TxnExpiryDateTime]", "20200630140134");
//        map.put("pp_SecureHash", pp_SecureHash);
                map.put("Request[pp_SecureHash]", "068AE9D269816DFF4729230750706019B598E56AE6F861B64DE5D2B70E1F1C78");
//        map.put("ppmpf_1", ppmpf_1);
                map.put("Request[ppmpf_1]", "");
//        map.put("ppmpf_2", ppmpf_2);
                map.put("Request[ppmpf_2]", "");
//        map.put("ppmpf_3", ppmpf_3);
                map.put("Request[ppmpf_3]", "");
//        map.put("ppmpf_4", ppmpf_4);
                map.put("Request[ppmpf_4]", "");
//        map.put("ppmpf_5", ppmpf_5);
                map.put("Request[ppmpf_5]", "");
//        map.put("pp_MobileNumber", pp_MobileNumber);
                map.put("Request[pp_MobileNumber]", "03123456789");
//        map.put("pp_CNIC", pp_CNIC);
                map.put("Request[pp_CNIC]", "345678");

                Log.i("JSON ", String.valueOf(map));
                return map;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
////                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
////                Log.e("RESPONSE prepaid_number", result.toString());
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            loader.hideLoader();
//                            new ProcessingError().showError(getContext());
//                            new HaballError().printErrorMessage(getContext(), error);
//                            error.printStackTrace();
//
//                            btn_create.setEnabled(true);
//                            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//                        }
//                    }) {
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                            return params;
//                        }
//
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//
//                            Map<String, String> map = new HashMap<>();
////        map.put("pp_Language", pp_Language);
//                            map.put("Request[pp_Language]", "EN");
////        map.put("pp_MerchantID", pp_MerchantID);
//                            map.put("Request[pp_MerchantID]", "MC37757");
////        map.put("pp_SubMerchantID", pp_SubMerchantID);
//                            map.put("Request[pp_SubMerchantID]", "");
////        map.put("pp_Password", pp_Password);
//                            map.put("Request[pp_Password]", "s0xcy12dv2");
////        map.put("pp_BankID", pp_BankID);
//                            map.put("Request[pp_BankID]", "");
////        map.put("pp_ProductID", pp_ProductID);
//                            map.put("Request[pp_ProductID]", "");
////        map.put("pp_TxnRefNo", pp_TxnRefNo);
//                            try {
//                                map.put("Request[pp_TxnRefNo]", finalResultRegen.getString("txnRefNumber"));
//                            } catch (JSONException e) {
//                                map.put("Request[pp_TxnRefNo]", "");
//                                e.printStackTrace();
//                            }
////        map.put("pp_Amount", pp_Amount);
//                            map.put("Request[pp_Amount]", "32653200");
////        map.put("pp_TxnCurrency", pp_TxnCurrency);
//                            map.put("Request[pp_TxnCurrency]", "PKR");
////        map.put("pp_TxnDateTime", pp_TxnDateTime);
//                            map.put("Request[pp_TxnDateTime]", "20200629130547");
////        map.put("pp_BillReference", pp_BillReference);
//                            map.put("Request[pp_BillReference]", "billRef");
////        map.put("pp_Description", pp_Description);
//                            map.put("Request[pp_Description]", "Description of transaction");
////        map.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
//                            map.put("Request[pp_TxnExpiryDateTime]", "20200630130536");
////        map.put("pp_SecureHash", pp_SecureHash);
//                            map.put("Request[pp_SecureHash]", "F71FCBEB8E92B71A5509936A4EBB8DC246EE135E2B3083C949E5CE112370A631");
////        map.put("ppmpf_1", ppmpf_1);
//                            map.put("Request[ppmpf_1]", "");
////        map.put("ppmpf_2", ppmpf_2);
//                            map.put("Request[ppmpf_2]", "");
////        map.put("ppmpf_3", ppmpf_3);
//                            map.put("Request[ppmpf_3]", "");
////        map.put("ppmpf_4", ppmpf_4);
//                            map.put("Request[ppmpf_4]", "");
////        map.put("ppmpf_5", ppmpf_5);
//                            map.put("Request[ppmpf_5]", "");
////        map.put("pp_MobileNumber", pp_MobileNumber);
//                            map.put("Request[pp_MobileNumber]", "03123456789");
////        map.put("pp_CNIC", pp_CNIC);
//                            map.put("Request[pp_CNIC]", "345678");
//
//                            Log.i("JSON ", String.valueOf(map));
//                            return map;
//                        }
//                    };
//                    sr.setRetryPolicy(new DefaultRetryPolicy(
//                            15000,
//                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                    Volley.newRequestQueue(getContext()).add(sr);
//                } catch (JSONException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.hideLoader();
//                new ProcessingError().showError(getContext());
//                new HaballError().printErrorMessage(getContext(), error);
//                error.printStackTrace();
//
//                btn_create.setEnabled(true);
//                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("apiOperation", "MWALLET");
//
//                Log.i("JSON ", String.valueOf(map));
//                return map;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void showSuccessDialog(String paymentID) {

        final Dialog fbDialogue = new Dialog(getActivity());
        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.password_updatepopup);
        TextView tv_pr1, txt_header1;
        txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
        tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
        tv_pr1.setText("");
        txt_header1.setText("Payment Created");
        String steps1 = "Payment ID ";
        String steps2 = " has been created successfully.";
        String title = paymentID;
        SpannableString ss1 = new SpannableString(title);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);

        tv_pr1.append(steps1);
        tv_pr1.append(ss1);
        tv_pr1.append(steps2);
        fbDialogue.setCancelable(true);
        fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        fbDialogue.getWindow().setAttributes(layoutParams);
        fbDialogue.show();

        ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbDialogue.dismiss();
            }
        });

        fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();
            }
        });
    }
}

