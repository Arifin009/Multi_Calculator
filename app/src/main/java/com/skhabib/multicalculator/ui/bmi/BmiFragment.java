package com.skhabib.multicalculator.ui.bmi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentBmiBinding;
import com.skhabib.multicalculator.databinding.FragmentLoanBinding;


public class BmiFragment extends Fragment {

private FragmentBmiBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBmiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
        return root;
    }
    private void calculateBMI() {
        // Get weight, feet, inches, and age values
        String weightStr = binding.weightInput.getText().toString();
        String feetStr = binding.heightFeetInput.getText().toString();
        String inchesStr = binding.heightInchesInput.getText().toString();
        String ageStr = binding.ageInput.getText().toString();

        // Check if inputs are valid
        if (weightStr.isEmpty() || feetStr.isEmpty() || inchesStr.isEmpty() || ageStr.isEmpty()) {
            binding.resultText.setText("Please enter weight, height, and age");
            return;
        }

        // Get gender selection
        int selectedGenderId = binding.genderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            binding.resultText.setText("Please select a gender");
            return;
        }
        RadioButton selectedGender =binding.getRoot().findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();

        // Parse inputs
        float weight = Float.parseFloat(weightStr);
        int feet = Integer.parseInt(feetStr);
        int inches = Integer.parseInt(inchesStr);
        int age = Integer.parseInt(ageStr);

        // Convert height from feet and inches to meters
        float heightInMeters = convertToMeters(feet, inches);

        // Calculate BMI
        float bmi = weight / (heightInMeters * heightInMeters);

        // Determine BMI category based on age and gender
        String bmiCategory = getBMICategory(bmi, age, gender);

        // Display result
        String result = String.format("Your BMI: %.2f\nCategory: %s", bmi, bmiCategory);
        binding.resultText.setText(result);
    }

    private float convertToMeters(int feet, int inches) {
        // 1 foot = 0.3048 meters, 1 inch = 0.0254 meters
        return (feet * 0.3048f) + (inches * 0.0254f);
    }

    private String getBMICategory(float bmi, int age, String gender) {
        // Define BMI ranges based on gender and age groups (example ranges)
        if (age < 18) {
            // For children and teens, BMI ranges are typically different
            return (bmi < 18.5) ? "Underweight" : (bmi < 24) ? "Normal" : (bmi < 29) ? "Overweight" : "Obese";
        } else {
            // For adults, ranges vary slightly by gender
            if (gender.equals("Male")) {
                return (bmi < 18.5) ? "Underweight" : (bmi < 24.9) ? "Normal weight" : (bmi < 29.9) ? "Overweight" : "Obese";
            } else {
                return (bmi < 18.5) ? "Underweight" : (bmi < 24.0) ? "Normal weight" : (bmi < 29.0) ? "Overweight" : "Obese";
            }
        }
    }
}