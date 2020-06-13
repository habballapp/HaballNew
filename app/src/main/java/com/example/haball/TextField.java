package com.example.haball;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextField {
    public TextField() {

    }

    public void changeColor(final Context context, final TextInputLayout textInputLayout, final TextInputEditText textInputEditText) {
        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
        textInputLayout.setHintAnimationEnabled(true);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (textInputEditText.getText().toString().trim().equals("")) {
                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                } else {
                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                }

//                textInputLayout.setBoxStrokeColor(context.getResources().getColor(R.color.color_text));
                //  textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//                textInputEditText.setTextColor(context.getResources().getColor(R.color.textcolor));
//                textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.textcolorhint)));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (!hasFocus && textInputEditText.getText().toString().trim().equals("")) {
                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                } else {
                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));

                    textInputEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            if (textInputEditText.getText().toString().trim().equals("") && !hasFocus) {
                                textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                            } else {
                                textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }
        });


    }
}
