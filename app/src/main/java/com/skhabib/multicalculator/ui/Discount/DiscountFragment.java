package com.skhabib.multicalculator.ui.Discount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentDiscountBinding;

public class DiscountFragment extends Fragment {
    private FragmentDiscountBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the binding
        binding = FragmentDiscountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up the calculate button click listener
        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDiscount();
            }
        });

        return root;
    }

    private void calculateDiscount() {
        // Get input values
        String originalPriceInput = binding.editOriginalPrice.getText().toString();
        String discountInput = binding.editDiscount.getText().toString();
        String taxInput = binding.editTax.getText().toString();

        // Validate inputs
        if (originalPriceInput.isEmpty() || discountInput.isEmpty() || taxInput.isEmpty()) {
            Toast.makeText( getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse inputs
        double originalPrice = Double.parseDouble(originalPriceInput);
        double discountPercentage = Double.parseDouble(discountInput);
        double taxPercentage = Double.parseDouble(taxInput);

        // Calculate discount amount
        double discountAmount = (discountPercentage / 100) * originalPrice;

        // Calculate tax amount
        double taxableAmount = originalPrice - discountAmount;
        double taxAmount = (taxPercentage / 100) * taxableAmount;

        // Calculate final price
        double finalPrice = taxableAmount + taxAmount;

        // Display results
        binding.textDiscountAmount.setText("Discount Amount: " + String.format("%.2f", discountAmount));
        binding.textTaxAmount.setText("Tax Amount: " + String.format("%.2f", taxAmount));
        binding.textFinalPrice.setText("Final Price: " + String.format("%.2f", finalPrice));
    }
}
