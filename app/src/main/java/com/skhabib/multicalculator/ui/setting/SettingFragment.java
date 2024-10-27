package com.skhabib.multicalculator.ui.setting;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentLoanBinding;
import com.skhabib.multicalculator.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

private FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the binding
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Privacy Policy")
                        .setMessage("Multi Calculator is a versatile mobile application created to assist users with a variety of calculations " +
                                        "in one convenient tool. It is Developed by Md. Arifin Zaman, the app offers functionalities like basic calculations, " +
                                        "unit conversions, and other specialized calculators, all within an easy-to-use interface. " +
                                        "\n\nInformation Collection and Use:\n" +
                                        "\nPersonal Data\n" +
                                        "\nMulti Calculator does not collect any personal data from users. The app operates independently on your device, " +
                                        "and no user data is collected, stored, or shared."+
                                        "\n\nUsage Data\n\n" +
                                        "We do not collect, track, or monitor usage data within the app. All calculations and processes are managed directly" +
                                        " on your device without external data logging." +
                                        "\n\nData Security\n\n" +
                                        "As we do not collect or store any personal data, your privacy and data security are preserved. All data processing " +
                                        "happens on your device, ensuring that your information stays secure and private."
                                )

                        .create()
                        .show();
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

       binding.darkmodeSwitch.setChecked(isDarkMode); // Initialize switch based on saved preference

        binding.darkmodeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Save preference
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();
        });
        return root;
    }
}