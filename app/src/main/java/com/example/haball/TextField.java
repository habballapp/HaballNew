package com.example.haball;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextField {
    public TextField() {

    }

    public void changeColor(final Context context, final TextInputLayout textInputLayout, final TextInputEditText textInputEditText) {
        if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color)))
            textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
        textInputLayout.setHintAnimationEnabled(true);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.i("textchanged", "textchanged");
                Log.i("textchanged", String.valueOf(charSequence));
                Log.i("textchanged", String.valueOf(textInputEditText.getText()));
                //  textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//                textInputEditText.setTextColor(context.getResources().getColor(R.color.textcolor));
//                textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.textcolorhint)));
                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (!textInputEditText.getText().toString().trim().equals("")) {
//                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("textchanged", "aftertextchanged");
                Log.i("textchanged", String.valueOf(editable));
                Log.i("textchanged", String.valueOf(textInputEditText.getText()));
                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (textInputEditText.getText().toString().trim().equals("")) {
//                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                    }
                }

            }
        });

        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (!hasFocus && textInputEditText.getText().toString().trim().equals("")) {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//
//                        textInputEditText.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                if (textInputEditText.getText().toString().trim().equals("") && !hasFocus) {
//                                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
//                                } else {
//                                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//                                }
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable editable) {
//
//                            }
//                        });

                    }
                }
            }
        });


    }
}
