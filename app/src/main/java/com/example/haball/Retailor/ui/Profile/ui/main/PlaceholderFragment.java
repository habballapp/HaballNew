package com.example.haball.Retailor.ui.Profile.ui.main;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.android.volley.toolbox.Volley;
import com.example.haball.R;
import com.example.haball.Registration.BooleanRequest;
import com.example.haball.Retailer_Login.RetailerLogin;
import com.example.haball.Retailor.ui.Profile.Profile_Tabs;
import com.example.haball.Select_User.Register_Activity;
import com.example.haball.SplashScreen.SplashScreen;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private String ChangePass_URL = " http://175.107.203.97:4014/api/users/ChangePassword";
    private String PROFILE_EDIT_URL = "http://175.107.203.97:4014/api/retailer/Save";
    private String Token;
    private String PROFILE_URL = "http://175.107.203.97:4014/api/retailer/";
    private String RetailerId, ID, username, CompanyName;
    private Button btn_changepwd, btn_save_password, update_password;
    private EditText Rfirstname, Remail, Rcode, Rcnic, Rmobile, R_created_date, R_Address, txt_password, txt_newpassword, txt_cfmpassword;
    private Dialog change_password_dail;
    private Boolean password_check = false, confirm_password_check = false;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView tv_pr1, txt_header1;
    private TextInputLayout layout_password1, layout_password3;
    private FragmentTransaction fragmentTransaction;
    private String currentTab = "";
    private Boolean changed = false;

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {

                root = inflater.inflate(R.layout.fragment_retailor_profile, container, false);
                currentTab = "Profile";
                Rfirstname = root.findViewById(R.id.Rfirstname);
                Rcode = root.findViewById(R.id.Rcode);
                Rcnic = root.findViewById(R.id.Rcnic);
                R_created_date = root.findViewById(R.id.R_created_date);

                Remail = root.findViewById(R.id.email_retailer);
                Rmobile = root.findViewById(R.id.Rmobile);
                R_Address = root.findViewById(R.id.R_Address);
                btn_save_password = root.findViewById(R.id.btn_save_password);
                Remail.setFocusable(false);
                Rmobile.setFocusable(false);
                R_Address.setFocusable(false);

                Remail.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (Remail.getRight() - Remail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Remail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                Remail.requestFocus();
                                Remail.setFocusable(true);
                                Remail.setFocusableInTouchMode(true);
                                Remail.setSelection(Remail.getText().length());
                                btn_save_password.setEnabled(true);
                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                Rmobile.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (Rmobile.getRight() - Rmobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                // your action here
                                Rmobile.setInputType(InputType.TYPE_CLASS_NUMBER);
                                Rmobile.requestFocus();
                                Rmobile.setFocusable(true);
                                Rmobile.setFocusableInTouchMode(true);
                                Rmobile.setSelection(Rmobile.getText().length());
                                btn_save_password.setEnabled(true);
                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                R_Address.setOnTouchListener(new View.OnTouchListener() {
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
                                btn_save_password.setEnabled(true);
                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                btn_save_password.setOnClickListener(new View.OnClickListener() {
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
                break;

            }
            case 2: {
                root = inflater.inflate(R.layout.pasword_change, container, false);
                currentTab = "Password";
                txt_password = root.findViewById(R.id.txt_password);
                txt_newpassword = root.findViewById(R.id.txt_newpassword);
                txt_cfmpassword = root.findViewById(R.id.txt_cfmpassword);
                layout_password1 = root.findViewById(R.id.layout_password1);
                layout_password3 = root.findViewById(R.id.layout_password3);
                update_password = root.findViewById(R.id.update_password);
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
                update_password.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
//                        if (!String.valueOf(txt_password.getText()).equals("")) {
                        try {
                            updatePassword();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        } else {
//                            Toast.makeText(getContext(), "Please fill Old Password", Toast.LENGTH_LONG).show();
//                        }

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
            break;
        }


        return root;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//    Remail.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    Remail.clearFocus();
//                    showDiscardDialog();
//                }
//                return false;
//            }
//        });
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
////                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
//                    String remail = Remail.getText().toString();
//                    if (!remail.equals("")) {
//                        showDiscardDialog();
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//                return false;
//            }
//        });
//
//    }
//
//    private void showDiscardDialog() {
//        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getActivity().getSupportFragmentManager();
//
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view_popup = inflater.inflate(R.layout.discard_changes, null);
//        alertDialog.setView(view_popup);
//        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
//        btn_discard.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.i("CreatePayment", "Button Clicked");
//                alertDialog.dismiss();
//                fm.popBackStack();
//            }
//        });
//
//        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//        img_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//            }
//        });
//
//        alertDialog.show();
//    }
//

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
                    Remail.clearFocus();
                    Rmobile.clearFocus();
                    R_Address.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        };
        Remail.setOnKeyListener(listener);
        Rmobile.setOnKeyListener(listener);
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
            username = sharedPreferences1.getString("username", "");
//            Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

//            change_password_dail.dismiss();

            JSONObject map = new JSONObject();
            map.put("Password", txt_password.getText().toString());
            map.put("NewPassword", txt_newpassword.getText().toString());
            map.put("ConfirmPassword", txt_cfmpassword.getText().toString());
//            map.put("ID", ID);
            map.put("Username", username);
            Log.i("MapChangePass", map.toString());
            BooleanRequest sr = new BooleanRequest(Request.Method.POST, ChangePass_URL, String.valueOf(map), new Response.Listener<Boolean>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Boolean result) {
                    Log.i("response", String.valueOf(result));
                    if (result) {
//                            Toast.makeText(getActivity(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        } else {
                        final Dialog fbDialogue = new Dialog(getActivity());
                        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                        fbDialogue.setContentView(R.layout.password_updatepopup);
                        tv_pr1 = fbDialogue.findViewById(R.id.tv_pr1);
//                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                        tv_pr1.setText("Your password has been updated. You would be logged out of your account");
                        fbDialogue.setCancelable(true);
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
                                SharedPreferences login_token = getContext().getSharedPreferences("LoginToken",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = login_token.edit();
                                editor.putString("Login_Token", "");
                                editor.putString("User_Type", "");
                                editor.putString("Retailer_Id", "");
                                editor.putString("username", "");
                                editor.putString("CompanyName", "");
                                editor.putString("UserId", "");

                                editor.commit();

                                Intent intent = new Intent(getContext(), RetailerLogin.class);
                                startActivity(intent);
                                ((FragmentActivity) getContext()).finish();
                            }
                        });
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    printErrorMessage(error);
                    error.printStackTrace();
//                    Toast.makeText(getActivity(), String.valueOf(error),Toast.LENGTH_LONG).show();
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

            final Dialog fbDialogue = new Dialog(getActivity(), R.style.Theme_Dialog);
            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));

            fbDialogue.setContentView(R.layout.password_updatepopup);
            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
            txt_header1.setText("Error");
            txt_header1.setTextColor(getResources().getColor(R.color.error_stroke_color));
            txt_header1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_set_error));
            tv_pr1.setText("Password do not Match");
            fbDialogue.setCancelable(true);
// Setting dialogview
            Window window = fbDialogue.getWindow();
            window.setGravity(Gravity.FILL);

            fbDialogue.show();
            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fbDialogue.dismiss();
                }
            });
        }

    }

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_newpassword.getText().toString().matches(reg_ex)) {
            password_check = true;
            layout_password1.setPasswordVisibilityToggleEnabled(true);
        } else {
            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
            layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setPasswordVisibilityToggleEnabled(false);
        }
        txt_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.color_text));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
                layout_password1.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkConfirmPassword() {
        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
            confirm_password_check = true;
            layout_password3.setPasswordVisibilityToggleEnabled(true);
        } else {
            confirm_password_check = false;
            txt_cfmpassword.setError("Password does not match");
            layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_password3.setPasswordVisibilityToggleEnabled(false);
        }
        txt_cfmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.color_text));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));
                layout_password3.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void saveProfileData() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("RetailerId ", RetailerId);
//        PROFILE_URL = PROFILE_URL + RetailerId;
        Log.i("Token Retailer ", Token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", RetailerId);
        jsonObject.put("Name", Rfirstname.getText().toString());
        jsonObject.put("CNIC", Rcnic.getText().toString());
        jsonObject.put("Mobile", Rmobile.getText().toString());
        jsonObject.put("CompanyName", CompanyName);
        jsonObject.put("Address", R_Address.getText().toString());
        jsonObject.put("Email", Remail.getText().toString());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, PROFILE_EDIT_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Toast.makeText(getContext(), "Profile Information Successfully updated for " + result.getString("RetailerCode"), Toast.LENGTH_LONG).show();
                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container_ret, new Profile_Tabs()).addToBackStack("tag");;
                    fragmentTransaction.commit();

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

    private void printErrorMessage(VolleyError error) {

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

    private void profileData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("RetailerId ", RetailerId);
        PROFILE_URL = PROFILE_URL + RetailerId;
        Log.i("Token Retailer ", Token);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PROFILE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("aaaaa", String.valueOf(result));
                    CompanyName = result.getString("CompanyName");
                    Rfirstname.setText(result.getString("Name"));
                    Remail.setText(result.getString("Email"));
                    Rcode.setText(result.getString("RetailerCode"));
                    Rcnic.setText(result.getString("CNIC"));
                    Rmobile.setText(result.getString("Mobile"));
                    R_Address.setText(result.getString("Address"));
                    String string = result.getString("CreatedDate");
                    String[] parts = string.split("T");
                    String Date = parts[0];
                    R_created_date.setText(Date);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //printErrorMessage(error);
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
}