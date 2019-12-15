package com.example.myitime.ui.helpandfeedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myitime.R;

public class HelpandfeedbackFragment extends Fragment {

    private HelpandfeedbackViewModel helpandfeedbackViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpandfeedbackViewModel =
                ViewModelProviders.of(this).get(HelpandfeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_helpandfeedback, container, false);
        final TextView textView = root.findViewById(R.id.text_helpandfeedback);
        helpandfeedbackViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}