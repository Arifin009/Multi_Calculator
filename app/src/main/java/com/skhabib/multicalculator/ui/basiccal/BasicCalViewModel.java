package com.skhabib.multicalculator.ui.basiccal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BasicCalViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BasicCalViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}