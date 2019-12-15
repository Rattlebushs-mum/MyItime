package com.example.myitime.ui.senior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myitime.R;

public class SeniorFragment extends Fragment {

//    private ImageView btnback,btnsure;
//    private View viewbar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_senior, container, false);

//        viewbar=root.findViewById(R.id.senior_bar);
//        btnback=viewbar.findViewById(R.id.iv_back);
//        btnsure=viewbar.findViewById(R.id.iv_sure);
//        btnback.setImageResource(R.drawable.cha);
//        btnsure.setImageResource(R.drawable.question);
        return root;
    }
}