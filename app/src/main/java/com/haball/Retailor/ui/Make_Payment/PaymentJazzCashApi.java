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

public class PaymentJazzCashApi extends Fragment {
    private String Token, ID;
    private Button btn_create;

    //    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "https://retailer.haball.pk/api/kyc/KYCDistributorList";
    private String URL_PAYMENT_REQUESTS_GET_DATA = "https://retailer.haball.pk/api/payaxis/PrePaidPay/359934761903956";
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
        txt_amount.addTextChangedListener(textWatcher);

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
//        String txt_amounts = txt_amount.getText().toString();
//        if (Double.parseDouble(txt_amounts) >= 500) {
//            loader.showLoader();
//            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                    Context.MODE_PRIVATE);
//            Token = sharedPreferences.getString("Login_Token", "");
//
//            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                    Context.MODE_PRIVATE);
//            ID = sharedPreferences1.getString("ID", "");
//            Log.i("ID  ", ID);
//            Log.i("Token", Token);
//
//            JSONObject map = new JSONObject();
//            map.put("ID", 0);
//            map.put("DealerCode", companyNameAndId.get(company_names));
////        map.put("DealerCode", "201911672");
//            map.put("PaidAmount", txt_amount.getText().toString());
//
//            Log.i("JSON ", String.valueOf(map));
//
//            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject result) {
//                    loader.hideLoader();
//                    try {
//                        Log.i("Response PR", result.toString());
//                        prepaid_number = result.getString("PrePaidNumber");
//                        prepaid_id = result.getString("ID");
//                    } catch (JSONException e) {
//                        Log.i("Response PR", e.toString());
//                        e.printStackTrace();
//                    }
//
//                    btn_create.setEnabled(true);
//                    btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//
//                    SharedPreferences PrePaidNumber = getContext().getSharedPreferences("PrePaidNumber",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = PrePaidNumber.edit();
//                    editor.putString("PrePaidNumber", prepaid_number);
//                    editor.putString("PrePaidId", prepaid_id);
//                    editor.putString("CompanyId", companyNameAndId.get(company_names));
//                    editor.putString("CompanyName", company_names);
//                    editor.putString("Amount", txt_amount.getText().toString());
//                    editor.apply();
//
//                    showSuccessDialog(prepaid_number);
//
////                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
////                Log.e("RESPONSE prepaid_number", result.toString());
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    loader.hideLoader();
//                    new ProcessingError().showError(getContext());
//                    new HaballError().printErrorMessage(getContext(), error);
//                    error.printStackTrace();
//
//                    btn_create.setEnabled(true);
//                    btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Authorization", "bearer " + Token);
//                    return params;
//                }
//            };
//            sr.setRetryPolicy(new DefaultRetryPolicy(
//                    15000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            Volley.newRequestQueue(getContext()).add(sr);
//        } else {
//            new CustomToast().showToast(getActivity(), "Amount cannot be less than PKR 500.");
//        }
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

