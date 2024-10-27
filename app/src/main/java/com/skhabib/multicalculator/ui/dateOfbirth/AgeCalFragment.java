package com.skhabib.multicalculator.ui.dateOfbirth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skhabib.multicalculator.databinding.FragmentDateOfBirthBinding;

import java.util.Calendar;


public class AgeCalFragment extends Fragment {


    private FragmentDateOfBirthBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the binding
        binding = FragmentDateOfBirthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonCalculateAge.setOnClickListener(v -> calculateAge());

        return root;
    }
    private void calculateAge() {
        int birthDay = binding.datePickerDob.getDayOfMonth();
        int birthMonth = binding.datePickerDob.getMonth();
        int birthYear = binding.datePickerDob.getYear();

        // Setting the birth date
        Calendar dob = Calendar.getInstance();
        dob.set(birthYear, birthMonth, birthDay);

        // Current date
        Calendar today = Calendar.getInstance();

        int ageYears = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int ageMonths = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        int ageDays = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);

        // Adjust the month and year if necessary
        if (ageDays < 0) {
            ageDays += today.getActualMaximum(Calendar.DAY_OF_MONTH);
            ageMonths -= 1;
        }
        if (ageMonths < 0) {
            ageMonths += 12;
            ageYears -= 1;
        }

        // Display the result
        binding.textAgeResult.setText("Your Age: " + ageYears + " Years, " + ageMonths + " Months, " + ageDays + " Days");
    }
}