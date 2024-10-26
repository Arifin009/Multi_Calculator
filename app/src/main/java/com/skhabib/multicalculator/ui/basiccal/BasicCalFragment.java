package com.skhabib.multicalculator.ui.basiccal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast; // Import Toast for user notifications

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.skhabib.multicalculator.databinding.FragmentHomeBinding;


public class BasicCalFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView tvsec, tvmain;
    private String pi = "3.14159265";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BasicCalViewModel basicCalViewModel = new ViewModelProvider(this).get(BasicCalViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize TextViews with null checks
        if (binding.tvmain != null && binding.tvsec != null) {
            tvmain = binding.tvmain;
            tvsec = binding.tvsec;
        } else {
            // Handle the case where the TextViews are not found
            Toast.makeText(getContext(), "Error: TextViews not found.", Toast.LENGTH_SHORT).show();
            return root; // Exit early to avoid further null pointer exceptions
        }

        // Set click listeners for buttons
        setButtonClickListeners();

        return root;
    }

    private void setButtonClickListeners() {
        // Button click listeners with null checks
        if (tvmain == null) return; // Exit if tvmain is null
        binding.b1.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "1"));
        binding.b2.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "2"));
        binding.b3.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "3"));
        binding.b4.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "4"));
        binding.b5.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "5"));
        binding.b6.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "6"));
        binding.b7.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "7"));
        binding.b8.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "8"));
        binding.b9.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "9"));
        binding.b0.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "0"));
        binding.bdot.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "."));
        binding.bdiv.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "/"));
        binding.bmul.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "*"));
        binding.bminus.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "-"));
        binding.bplus.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "+"));

        binding.bac.setOnClickListener(v -> {
            if (tvmain != null && tvsec != null) {
                tvmain.setText("");
                tvsec.setText("");
            }
        });

        binding.bc.setOnClickListener(v -> {
            String val = tvmain.getText().toString();
            if (val != null && val.length() > 0) {
                val = val.substring(0, val.length() - 1);
                tvmain.setText(val);
            } else {
                tvmain.setText("");
                tvsec.setText("");
            }
        });

        // Other button click listeners...
        setupFunctionButtons();
    }

    private void setupFunctionButtons() {
        // Function buttons with null checks
        if (tvmain == null) return; // Exit if tvmain is null
        binding.bb1.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "("));
        binding.bb2.setOnClickListener(v -> tvmain.setText(tvmain.getText() + ")"));

        binding.bsin.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "sin("));
        binding.bcos.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "cos("));
        binding.btan.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "tan("));
        binding.blog.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "log("));
        binding.bln.setOnClickListener(v -> tvmain.setText(tvmain.getText() + "ln("));

        binding.bfact.setOnClickListener(v -> {
            try {
                int val = Integer.parseInt(tvmain.getText().toString());
                int fact = factorial(val);
                tvmain.setText(String.valueOf(fact));
                tvsec.setText(val + "!");
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid input for factorial.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.bsquare.setOnClickListener(v -> {
            try {
                double d = Double.parseDouble(tvmain.getText().toString());
                double square = d * d;
                tvmain.setText(String.valueOf(square));
                tvsec.setText(d + "²");
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid input for square.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.bsqrt.setOnClickListener(v -> {
            try {
                double d = Double.parseDouble(tvmain.getText().toString());
                double sqrt = Math.sqrt(d);
                tvmain.setText(String.valueOf(sqrt));
                tvsec.setText("√" + d);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid input for square root.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.binv.setOnClickListener(v -> {
            try {
                double d = Double.parseDouble(tvmain.getText().toString());
                if (d != 0) {
                    double inv = 1 / d;
                    tvmain.setText(String.valueOf(inv));
                    tvsec.setText("1/" + d);
                } else {
                    Toast.makeText(getContext(), "Cannot divide by zero.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid input for inverse.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.bpi.setOnClickListener(v -> {
            if (tvmain != null) {
                tvmain.setText(tvmain.getText() + pi);
            }
        });

        binding.bequal.setOnClickListener(v -> {
            String val = tvmain.getText().toString();
            String replacedstr = val.replace('÷', '/').replace('×', '*');
            try {
                double result = eval(replacedstr);
                tvmain.setText(String.valueOf(result));
                tvsec.setText(val);
            } catch (RuntimeException e) {
               // Toast.makeText(getContext(), "Error in expression.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding reference
    }

    int factorial(int n) {
        if (n < 0) {
            Toast.makeText(getContext(), "Factorial is not defined for negative numbers.", Toast.LENGTH_SHORT).show();
            return 0; // Return 0 or handle appropriately
        }
        return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // Addition
                    else if (eat('-')) x -= parseTerm(); // Subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseTerm(); // Multiplication
                    else if (eat('/')) x /= parseTerm(); // Division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // Unary Plus
                if (eat('-')) return -parseFactor(); // Unary Minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // Parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if ((ch >= 'a' && ch <= 'z')) { // Functions
                    while ((ch >= 'a' && ch <= 'z')) nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // Exponentiation

                return x;
            }
        }.parse();
    }
}
