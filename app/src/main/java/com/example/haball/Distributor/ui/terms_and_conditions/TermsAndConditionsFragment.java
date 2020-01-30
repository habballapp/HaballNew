package com.example.haball.Distributor.ui.terms_and_conditions;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haball.R;

public class TermsAndConditionsFragment extends Fragment {

    private TermsAndConditionsViewModel mViewModel;

    public static TermsAndConditionsFragment newInstance() {
        return new TermsAndConditionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.terms_and_conditions_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TermsAndConditionsViewModel.class);
        // TODO: Use the ViewModel
    }

}
