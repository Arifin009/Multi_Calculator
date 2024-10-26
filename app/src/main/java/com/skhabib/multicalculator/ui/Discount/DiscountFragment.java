package com.skhabib.multicalculator.ui.Discount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentDiscountBinding; // Ensure this matches your layout file

public class DiscountFragment extends Fragment {
    private FragmentDiscountBinding binding; // Use the correct binding class

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the binding
        binding = FragmentDiscountBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); // Get the root view

        // You can now access your views via binding
        // For example, to set text on a TextView in the layout
        // binding.textView.setText("Welcome to Discount Fragment!");

        return root; // Return the root view
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // You can set up your UI logic here
    }
}
