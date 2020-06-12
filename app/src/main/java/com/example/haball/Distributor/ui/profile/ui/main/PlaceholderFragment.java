package com.example.haball.Distributor.ui.profile.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.example.haball.Distributor.ui.profile.Profile_Model;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.example.haball.TextField;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private Button change_pwd, update_password, distri_btn_save;
    private TextInputEditText   edt_firstname, edt_lastname, edt_email, edt_dist_mobile, R_Address;
    private TextInputLayout layout_edt_dist_code,layout_edt_firstname,layout_edt_lastname,layout_edt_email,
                            layout_tv_cnic,layout_edt_dist_mobile,layout_tv_NTN,layout_tv_companyname,layout_tv_created_date,
                            layout_R_Address;
    private TextInputEditText txt_password, txt_newpassword, txt_cfmpassword;
    public TextInputEditText edt_dist_code, tv_cnic, tv_NTN, tv_companyname, tv_created_date, tv_pr1;
    private String PROFILE_URL = "http://175.107.203.97:4013/api/distributor/";
    private String PROFILE_ADDRESS_URL = "http://175.107.203.97:4013/api/distributor/ReadAdditionalAddress/";
    private String ChangePass_URL = "http://175.107.203.97:4013/api/Users/ChangePassword";
    private String PROFILE_EDIT_URL = "http://175.107.203.97:4013/api/distributor/save";
    private String Token;
    private String DistributorId, ID, Username, Phone;
    private Dialog change_password_dail;
    private Boolean password_check = false, confirm_password_check = false;
    private int keyDel;
    private TextInputLayout layout_password1, layout_password3,layout_password;
    private String currentTab = "";
    private Boolean changed = false;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {

                root = inflater.inflate(R.layout.fragment_distributor_profile, container, false);

                layout_edt_dist_code = root.findViewById(R.id.layout_edt_dist_code);
                layout_edt_firstname = root.findViewById(R.id.layout_edt_firstname);
                layout_edt_lastname = root.findViewById(R.id.layout_edt_lastname);
                layout_edt_email = root.findViewById(R.id.layout_edt_email);
                layout_tv_cnic = root.findViewById(R.id.layout_tv_cnic);
                layout_edt_dist_mobile = root.findViewById(R.id.layout_edt_dist_mobile);
                layout_tv_NTN = root.findViewById(R.id.layout_tv_NTN);
                layout_tv_companyname = root.findViewById(R.id.layout_tv_companyname);
                layout_tv_created_date = root.findViewById(R.id.layout_tv_created_date);

                currentTab = "Profile";

                edt_dist_code = root.findViewById(R.id.edt_dist_code);
                edt_firstname = root.findViewById(R.id.edt_firstname);
                edt_lastname = root.findViewById(R.id.edt_lastname);
                edt_email = root.findViewById(R.id.edt_email);
                edt_dist_mobile = root.findViewById(R.id.edt_dist_mobile);
                tv_cnic = root.findViewById(R.id.tv_cnic);
                tv_NTN = root.findViewById(R.id.tv_NTN);
                tv_companyname = root.findViewById(R.id.tv_companyname);
                tv_created_date = root.findViewById(R.id.tv_created_date);
                R_Address = root.findViewById(R.id.R_Address);
                distri_btn_save = root.findViewById(R.id.distri_btn_save);
                edt_firstname.setFocusable(false);
                R_Address.setFocusable(false);
                edt_lastname.setFocusable(false);
                edt_email.setFocusable(false);
                edt_dist_mobile.setFocusable(false);

                new TextField().changeColor(this.getContext(),layout_edt_dist_code,edt_dist_code);
                new TextField().changeColor(this.getContext(),layout_edt_firstname,edt_firstname);
                new TextField().changeColor(this.getContext(),layout_edt_lastname,edt_lastname);
                new TextField().changeColor(this.getContext(),layout_edt_email,edt_email);
                new TextField().changeColor(this.getContext(),layout_tv_cnic,tv_cnic);
                new TextField().changeColor(this.getContext(),layout_edt_dist_mobile,edt_dist_mobile);
                new TextField().changeColor(this.getContext(), layout_tv_NTN, tv_NTN);
                new TextField().changeColor(this.getContext(), layout_tv_companyname, tv_companyname);
                new TextField().changeColor(this.getContext(), layout_tv_created_date,tv_created_date);

                edt_firstname.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (edt_firstname.getRight() - edt_firstname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                edt_firstname.setInputType(InputType.TYPE_CLASS_TEXT);
                                edt_firstname.requestFocus();
                                edt_firstname.setFocusable(true);
                                edt_firstname.setFocusableInTouchMode(true);
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                edt_firstname.setSelection(edt_firstname.getText().length());
                                distri_btn_save.setEnabled(true);
                                distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });

                R_Address.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (R_Address.getRight() - R_Address.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                R_Address.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                                R_Address.requestFocus();
                                R_Address.setFocusable(true);
                                R_Address.setFocusableInTouchMode(true);
                                R_Address.setSelection(R_Address.getText().length());
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                distri_btn_save.setEnabled(true);
                                distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                edt_lastname.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (edt_lastname.getRight() - edt_lastname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                edt_lastname.setInputType(InputType.TYPE_CLASS_TEXT);
                                edt_lastname.requestFocus();
                                edt_lastname.setFocusable(true);
                                edt_lastname.setFocusableInTouchMode(true);
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                edt_lastname.setSelection(edt_lastname.getText().length());
                                distri_btn_save.setEnabled(true);
                                distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                edt_email.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (edt_email.getRight() - edt_email.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                edt_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                edt_email.requestFocus();
                                edt_email.setFocusable(true);
                                edt_email.setFocusableInTouchMode(true);
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                edt_email.setSelection(edt_email.getText().length());
                                distri_btn_save.setEnabled(true);
                                distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                edt_dist_mobile.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (edt_dist_mobile.getRight() - edt_dist_mobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                edt_dist_mobile.setInputType(InputType.TYPE_CLASS_NUMBER);
                                edt_dist_mobile.requestFocus();
                                edt_dist_mobile.setFocusable(true);
                                edt_dist_mobile.setFocusableInTouchMode(true);
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                edt_dist_mobile.setSelection(edt_dist_mobile.getText().length());
                                distri_btn_save.setEnabled(true);
                                distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });

                edt_dist_mobile.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        edt_dist_mobile.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {

                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });

                        if (keyDel == 0) {
                            int len = edt_dist_mobile.getText().length();
                            if (len == 4) {
                                edt_dist_mobile.setText(edt_dist_mobile.getText() + "-");
                                edt_dist_mobile.setSelection(edt_dist_mobile.getText().length());
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


                distri_btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            saveProfileData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                profileData();

            }
            break;

            case 2:
                root = inflater.inflate(R.layout.pasword_change, container, false);
                currentTab = "Password";
                layout_password3 = root.findViewById(R.id.layout_password3);
                txt_password = root.findViewById(R.id.txt_password);
                txt_newpassword = root.findViewById(R.id.txt_newpassword);
                txt_cfmpassword = root.findViewById(R.id.txt_cfmpassword);
                layout_password1 = root.findViewById(R.id.layout_password1);
                layout_password =  root.findViewById(R.id.layout_password );
                update_password = root.findViewById(R.id.update_password);

                new TextField().changeColor(this.getContext(),layout_password,txt_password);
                new TextField().changeColor(this.getContext(),layout_password1,txt_newpassword);
                new TextField().changeColor(this.getContext(),layout_password3,txt_cfmpassword);

                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
                update_password.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (!String.valueOf(txt_password.getText()).equals("")) {
                            try {
                                updatePassword();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "Please fill Old Password", Toast.LENGTH_LONG).show();
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

                txt_password.addTextChangedListener(textWatcher);
                txt_newpassword.addTextChangedListener(textWatcher);
                txt_cfmpassword.addTextChangedListener(textWatcher);
        }


        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(currentTab.equals("Profile"))
            onResumeProfile();
        else if(currentTab.equals("Password"))
            onResumePassword();
    }

    private void onResumeProfile(){
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    edt_firstname.clearFocus();
                    edt_lastname.clearFocus();
                    edt_email.clearFocus();
                    edt_dist_mobile.clearFocus();
                    R_Address.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        };
        edt_firstname.setOnKeyListener(listener);
        edt_lastname.setOnKeyListener(listener);
        edt_email.setOnKeyListener(listener);
        edt_dist_mobile.setOnKeyListener(listener);
        R_Address.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    if (changed) {
                        showDiscardDialog();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

    }

    private void onResumePassword(){
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_password.clearFocus();
                    txt_newpassword.clearFocus();
                    txt_cfmpassword.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        };
        txt_password.setOnKeyListener(listener);
        txt_newpassword.setOnKeyListener(listener);
        txt_cfmpassword.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    String txtpassword = txt_password.getText().toString();
                    String txtnewpassword = txt_newpassword.getText().toString();
                    String txtcfmpassword = txt_cfmpassword.getText().toString();
                    if (!txtpassword.equals("") || !txtnewpassword.equals("") || !txtcfmpassword.equals("")) {
                        showDiscardDialog();
                        return true;
                    } else {
                        return false;
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
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                fm.popBackStack();
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
        String password = txt_password.getText().toString();
        String newPass = txt_newpassword.getText().toString();
        String confrm_pass = txt_cfmpassword.getText().toString();
        if (password.equals("")
                || newPass.equals("")
                || confrm_pass.equals("")


        ) {
            update_password.setEnabled(false);
            update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            update_password.setEnabled(true);
            update_password.setBackground(getResources().getDrawable(R.drawable.button_background));
        }

    }

    private boolean checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            edt_email.setError("This field is required");
            return false;
        } else if (!edt_email.getText().toString().matches(reg_ex)) {
            edt_email.setError("Email (format: johnsmith@Example.com)\n");
            return false;
        } else {
            edt_email.setError(null);
            return true;
        }
    }

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_newpassword.getText().toString().matches(reg_ex)) {
            password_check = true;
//            txt_password.setError(null);
//            layout_password1.setBoxStrokeColor(getResources().getColor(R.color.textboxstrokecolor));
//            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
//            layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolor)));
//            txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
            layout_password1.setPasswordVisibilityToggleEnabled(true);
        } else {
            layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
            Toast.makeText(getContext(), "Please enter password with minimum 6 characters & 1 Numeric or special character", Toast.LENGTH_LONG).show();
//            txt_password.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
//            password_check = false;
            layout_password1.setPasswordVisibilityToggleEnabled(false);
        }
        txt_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void profileData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId1 ", DistributorId);
        PROFILE_URL = PROFILE_URL + DistributorId;
        PROFILE_ADDRESS_URL = PROFILE_ADDRESS_URL + DistributorId;
        Log.i("Token1", Token);
        StringRequest sr = new StringRequest(Request.Method.GET, PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Log.i("aaaaa", result);
                try {
                    if (result != null && !result.equals("")) {
                        Gson gson = new Gson();
                        Profile_Model profile_model = gson.fromJson(result, Profile_Model.class);
                        Phone = profile_model.getPhone();
                        edt_dist_code.setText(profile_model.getDealerCode());
                        edt_firstname.setText(profile_model.getFirstName());
                        edt_lastname.setText(profile_model.getLastName());
                        edt_email.setText(profile_model.getEmail());
                        edt_dist_mobile.setText(profile_model.getMobile());
                        tv_cnic.setText(profile_model.getCNIC());
                        tv_NTN.setText(profile_model.getCompanyNTN());
                        tv_companyname.setText(profile_model.getCompanyName());
//                        R_Address.setText(profile_model.getAddress());
                        String string = profile_model.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        tv_created_date.setText(Date);

                        fetchAddresses();

                    }
//                    SharedPreferences companyId = getActivity().getSharedPreferences("SendData",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = companyId.edit();
//                    editor.putString("first_name" , edt_firstname.getText().toString());
//                    editor.putString("email" , edt_email.getText().toString());
//                    editor.putString("phone_number" , edt_dist_mobile.getText().toString());
//                    editor.apply();
//                    Log.i("editor_chcek" , String.valueOf(companyId));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);

    }

    private void fetchAddresses() {

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, PROFILE_ADDRESS_URL, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        if (result.getJSONObject(i).get("StatusValue").equals("Active")) {
                            String address = String.valueOf(result.getJSONObject(i).get("Address1"));
                            address.concat(", ");
                            address.concat(String.valueOf(result.getJSONObject(i).get("CityName")));
                            address.concat(", ");
                            address.concat(String.valueOf(result.getJSONObject(i).get("ProvinceName")));
                            address.concat(", ");
                            address.concat(String.valueOf(result.getJSONObject(i).get("CountryName")));
                            ;
                            R_Address.setText(address);
                            break;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printErrorMessage(error);

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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void updatePassword() throws JSONException {

        checkPasswords();
        checkConfirmPassword();
        if (password_check && confirm_password_check) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");
            Log.i("Login_Token", Token);
            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            ID = sharedPreferences1.getString("ID", "");
            Username = sharedPreferences1.getString("username", "");
            // Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

            // change_password_dail.dismiss();

            JSONObject map = new JSONObject();
            map.put("Password", txt_password.getText().toString());
            map.put("NewPassword", txt_newpassword.getText().toString());
            map.put("NewPassword1", txt_cfmpassword.getText().toString());
            //        map.put("Password", "Force@123");
            //        map.put("NewPassword", "Force@123");
            //        map.put("NewPassword1", "Force@123");
            map.put("ID", ID);
            map.put("Username", Username);
            Log.i("Map", map.toString());
            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, ChangePass_URL, map, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject result) {
                    Log.i("response545", String.valueOf(result));
                    try {
                        if (result.has("message")) {
                            Toast.makeText(getActivity(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            final Dialog fbDialogue = new Dialog(getActivity());
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);
                            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                            fbDialogue.setCancelable(true);
                            fbDialogue.show();
                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                            close_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbDialogue.dismiss();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

                    }
                    //                Log.e("RESPONSE", result.toString());
                    //                Toast.makeText(Distribution_Login.this,result.toString(),Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    printErrorMessage(error);

                    error.printStackTrace();
                    // Toast.makeText(getActivity(), String.valueOf(error),Toast.LENGTH_LONG).show();
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
            sr.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 1000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            Volley.newRequestQueue(getActivity()).add(sr);
        } else {
            Toast.makeText(getActivity(), "Password do not Match", Toast.LENGTH_LONG).show();
        }


    }
//
//    private void checkPasswords() {
//        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
//        if (txt_newpassword.getText().toString().matches(reg_ex)) {
//            password_check = true;
//            txt_newpassword.setError(null);
//        } else {
//            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
//            password_check = false;
//        }
//    }

    private void checkConfirmPassword() {
        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
            confirm_password_check = true;
            layout_password3.setPasswordVisibilityToggleEnabled(true);
        } else {
            confirm_password_check = false;
            txt_cfmpassword.setError("Password does not match");
            txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));

            layout_password3.setPasswordVisibilityToggleEnabled(false);
        }
        txt_cfmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void saveProfileData() throws JSONException {
        if (checkEmail()) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");

            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            DistributorId = sharedPreferences1.getString("Distributor_Id", "");
            Log.i("Distributor_Id ", DistributorId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", DistributorId);
            jsonObject.put("FirstName", edt_firstname.getText().toString());
            jsonObject.put("LastName", edt_lastname.getText().toString());
            jsonObject.put("CompanyName", tv_companyname.getText().toString());
            jsonObject.put("CompanyNTN", tv_NTN.getText().toString());
            jsonObject.put("CNIC", tv_cnic.getText().toString());
            jsonObject.put("Phone", Phone);
            jsonObject.put("Mobile", edt_dist_mobile.getText().toString());
            jsonObject.put("Email", edt_email.getText().toString());
            jsonObject.put("DealerCode", edt_dist_code.getText().toString());
            jsonObject.put("Address", R_Address.getText().toString());
            jsonObject.put("UserType", 0);

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, PROFILE_EDIT_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    try {
                        Toast.makeText(getContext(), "Profile Information Successfully updated for " + result.getString("DealerCode"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    printErrorMessage(error);

                    error.printStackTrace();
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
    }
}
