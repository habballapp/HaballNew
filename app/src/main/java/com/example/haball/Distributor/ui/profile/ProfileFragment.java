package com.example.haball.Distributor.ui.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haball.R;

public class ProfileFragment extends Fragment {

    private Button change_pwd, update_password;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_distributor_profile, container, false);
        change_pwd = root.findViewById(R.id.btn_changepwd);

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog change_password_dail = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                change_password_dail.setContentView(R.layout.pasword_change);
                change_password_dail.setCancelable(true);
                change_password_dail.show();
                ImageButton close_button = change_password_dail.findViewById(R.id.image_button);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();
                    }
                });
                update_password = change_password_dail.findViewById(R.id.update_password);
                update_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();

                        final Dialog fbDialogue = new Dialog(getActivity());
                        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                        fbDialogue.setContentView(R.layout.password_updatepopup);
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
                });
            }

        });

        return root;
    }

}