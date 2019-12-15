package com.example.myitime.ui.helpandfeedback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpandfeedbackViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpandfeedbackViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}