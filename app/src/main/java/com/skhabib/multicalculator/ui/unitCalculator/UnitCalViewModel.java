package com.skhabib.multicalculator.ui.unitCalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnitCalViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UnitCalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}