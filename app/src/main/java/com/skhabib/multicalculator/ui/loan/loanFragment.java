package com.skhabib.multicalculator.ui.loan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentDateOfBirthBinding;
import com.skhabib.multicalculator.databinding.FragmentLoanBinding;


public class loanFragment extends Fragment {
    private EditText editPrincipal, editInterestRate, editTerm;
    private Spinner spinnerRepaymentMethod;
    private TextView textTotalPayment, textTotalInterest;
    private Button buttonCalculate;

    private static final String[] repaymentMethods = {
            "Equal Total Payment", "Bullet Payment", "Equal Principal Payment"
    };
private FragmentLoanBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the binding
        binding = FragmentLoanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editPrincipal = binding.editPrincipal;
        editInterestRate = binding.editInterestRate;
        editTerm = binding.editTerm;
        spinnerRepaymentMethod = binding.spinnerRepaymentMethod;
        textTotalPayment = binding.textTotalPayment;
        textTotalInterest = binding.textTotalInterest;
        buttonCalculate = binding.buttonCalculate;

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,  // Custom layout for selected item
                repaymentMethods
        );
        spinnerRepaymentMethod.setAdapter(categoryAdapter);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoan();
            }
        });

        return root;
    }
    private void calculateLoan() {
        if (editInterestRate.getText().toString().isEmpty() || editPrincipal.getText().toString().isEmpty() || editTerm.getText().toString().isEmpty())
        {
            Toast.makeText( getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        double principal = Double.parseDouble(editPrincipal.getText().toString());
        double annualInterestRate = Double.parseDouble(editInterestRate.getText().toString());
        int term = Integer.parseInt(editTerm.getText().toString());
        String selectedMethod = (String) spinnerRepaymentMethod.getSelectedItem();

        double totalPayment = 0;
        double totalInterest = 0;

        // Convert annual interest rate to monthly
        double monthlyInterestRate = annualInterestRate / 100 / 12;

        switch (selectedMethod) {
            case "Equal Total Payment":
                // Calculate the EMI using the formula
                double emi = principal * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, term)) /
                        (Math.pow(1 + monthlyInterestRate, term) - 1);
                totalPayment = emi * term; // Total Payment is EMI times the number of months
                totalInterest = totalPayment - principal; // Total Interest is Total Payment minus Principal
                break;

            case "Bullet Payment":
                // Calculate interest for Bullet payment
                totalInterest = principal * monthlyInterestRate * term; // Total Interest is Principal times monthly interest times number of months
                totalPayment = totalInterest + principal; // Total Payment is Principal plus Interest
                break;

            case "Equal Principal Payment":
                double principalPayment = principal / term; // Fixed principal repayment each month
                for (int i = 1; i <= term; i++) {
                    double interestPayment = (principal - (principalPayment * (i - 1))) * monthlyInterestRate; // Calculate interest for the current month
                    totalInterest += interestPayment; // Sum up the interest payments
                }
                totalPayment = totalInterest + principal; // Total Payment is Total Interest plus Principal
                break;

            default:
                // If no valid method is selected
                return;
        }

        // Display the results
        textTotalPayment.setText(String.format("%.2f", totalPayment));
        textTotalInterest.setText(String.format("%.2f", totalInterest));
    }


}