package com.skhabib.multicalculator.ui.unitCalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.skhabib.multicalculator.databinding.FragmentGalleryBinding;

public class UnitCalFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UnitCalViewModel galleryViewModel =
                new ViewModelProvider(this).get(UnitCalViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String[] categories = {"Length", "Mass", "Area", "Volume", "Time", "Data"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.catagorySpinner.setAdapter(categoryAdapter);

        // Set listener for category spinner to update subcategories
        binding.catagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                updateSubcategorySpinners(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no item is selected if needed
            }
        });

        binding.fromCovertInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateConversion();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        // Set listener for the from subcategory spinner
        binding.convertFromCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateConversion();  // Calculate conversion when from unit changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // Set listener for the to subcategory spinner
        binding.convertToCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateConversion();  // Calculate conversion when to unit changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return root;
    }
    private void updateSubcategorySpinners(String category) {
        String[] subcategories;
        switch (category) {
            case "Length":
                subcategories = new String[]{"Meters", "Kilometers", "Miles","inch","feet","centimeters","millimeters",
                        "yards","millimeter","Decimeter"};
                break;
            case "Mass":
                subcategories = new String[]{"Grams", "Kilograms", "Pounds","Ounces","Milligrams","Tonnes"};
                break;
            case "Area":
                subcategories = new String[]{"Square Meters", "Hectares", "Acres",
                        "Square Kilometers", "Square Miles", "Square Feet", "Square Inches", "Square Centimeters"};
                break;
            case "Volume":
                subcategories = new String[]{"Liters", "Cubic Meters", "Gallons", "Cubic Feet", "Cubic Inches",
                        "Cubic Centimeters", "Cubic Miles", "Cubic Yards", "Cubic Foot", "Cubic Inch"};
                break;
            case "Time":
                subcategories = new String[]{"Seconds", "Minutes", "Hours","Milliseconds",
                        "Microseconds","Nanoseconds", "Days", "Weeks", "Months", "Years", "Decades", "Centuries"};
                break;
            case "Data":
                subcategories = new String[]{"Bytes", "Kilobytes", "Megabytes", "Gigabytes",
                        "Terabytes", "Petabytes","bit","Megabit","Gigabit","Terabit"};
                break;
            default:
                subcategories = new String[]{};
                break;
        }

        ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, subcategories);
        subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.convertFromCatagory.setAdapter(subcategoryAdapter);
        binding.convertToCatagory.setAdapter(subcategoryAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private double convertUnits(String fromUnit, String toUnit, double value) {
        if (fromUnit.equals(toUnit)) return value;

        switch (fromUnit) {
            case "Meters":
                if (toUnit.equals("Kilometers")) return value / 1000;
                if (toUnit.equals("Miles")) return value * 0.000621371;
                if (toUnit.equals("inch")) return value * 39.3701;
                if (toUnit.equals("feet")) return value * 3.28084;
                if (toUnit.equals("centimeters")) return value * 100;
                if (toUnit.equals("millimeters")) return value * 1000;
                if (toUnit.equals("yards")) return value * 1.09361;
                if (toUnit.equals("millimeter")) return value * 1000000;
                if (toUnit.equals("Decimeter")) return value * 10;
                break;
            case "Kilometers":
                if (toUnit.equals("Meters")) return value * 1000;
                if (toUnit.equals("Miles")) return value * 0.621371;

                break;
            case "Hectares":
                if (toUnit.equals("Acres")) return value * 2.47105;
                if (toUnit.equals("Square Meters")) return value * 10000;
                if (toUnit.equals("Square Kilometers")) return value * 0.01;
                if (toUnit.equals("Square Miles")) return value * 0.00015625;
                if (toUnit.equals("Square Feet")) return value * 10763910.4167;
                if (toUnit.equals("Square Inches")) return value * 15500031.25;
                if (toUnit.equals("Square Centimeters")) return value * 10000000000.0;
                break;
            case "Acres":
                if (toUnit.equals("Hectares")) return value / 2.47105;
                if (toUnit.equals("Square Meters")) return value * 4046.86;
                if (toUnit.equals("Square Kilometers")) return value * 0.000247105;
                if (toUnit.equals("Square Miles")) return value * 0.000015625;
                if (toUnit.equals("Square Feet")) return value * 43560;
                if (toUnit.equals("Square Inches")) return value * 6272640;
                if (toUnit.equals("Square Centimeters")) return value * 4046856422.4;
                break;
            case "Milligrams":
                if (toUnit.equals("Grams")) return value / 1000;
                if (toUnit.equals("Kilograms")) return value / 1000000;
                if (toUnit.equals("Pounds")) return value / 453.592;
                if (toUnit.equals("Ounces")) return value / 28.3495;
                if (toUnit.equals("Tonnes")) return value / 1000000000;
                break;
            case "Tonnes":
                if (toUnit.equals("Grams")) return value * 1000000;
                if (toUnit.equals("Kilograms")) return value * 1000;
                if (toUnit.equals("Pounds")) return value * 2204.62;
                if (toUnit.equals("Ounces")) return value * 35274;
                if (toUnit.equals("Milligrams")) return value * 1000000000;
                break;
            case "Ounces":
                if (toUnit.equals("Grams")) return value * 28.3495;
                if (toUnit.equals("Kilograms")) return value / 35.274;
                if (toUnit.equals("Pounds")) return value / 16;
                if (toUnit.equals("Milligrams")) return value * 29.5735;

                break;

            case "Decimeter":
                if (toUnit.equals("Meters")) return value / 10;
                if (toUnit.equals("millimeters")) return value * 10;
                if (toUnit.equals("yards")) return value / 10;
                if (toUnit.equals("feet")) return value / 10;
                if (toUnit.equals("inch")) return value / 10;
                if (toUnit.equals("centimeters")) return value * 10;
                if (toUnit.equals("millimeter")) return value * 1000000;

                break;
            case "millimeters":
                if (toUnit.equals("Meters")) return value / 1000;
                if (toUnit.equals("centimeters")) return value / 10;
                if (toUnit.equals("yards")) return value / 1093.61;
                if (toUnit.equals("millimeter")) return value * 1000000;
                if (toUnit.equals("Decimeter")) return value / 10;
                if (toUnit.equals("feet")) return value / 304.8;
                if (toUnit.equals("inch")) return value / 25.4;
                break;
            case "Square Meters":
                if (toUnit.equals("Square Kilometers")) return value / 1000000;
                if (toUnit.equals("Square Miles")) return value * 0.000000386102;
                if (toUnit.equals("Square Feet")) return value * 10.7639;
                if (toUnit.equals("Square Inches")) return value * 1550.0031;
                if (toUnit.equals("Square Centimeters")) return value * 10000000;
                break;
            case "Square Kilometers":
                if (toUnit.equals("Square Meters")) return value * 1000000;
                if (toUnit.equals("Square Miles")) return value * 0.386102;
                if (toUnit.equals("Square Feet")) return value * 10763910.4167;
                if (toUnit.equals("Square Inches")) return value * 15500031.25;
                if (toUnit.equals("Square Centimeters")) return value * 10000000000.0;
                break;
            case "Square Miles":
                if (toUnit.equals("Square Meters")) return value * 2589988.110336;
                if (toUnit.equals("Square Kilometers")) return value * 1.60934;
                if (toUnit.equals("Square Feet")) return value * 27878400.0;
                if (toUnit.equals("Square Inches")) return value * 401467200.0;
                if (toUnit.equals("Square Centimeters")) return value * 2589988110336.0;
                break;
            case "Square Feet":
                if (toUnit.equals("Square Meters")) return value / 10.7639;
                if (toUnit.equals("Square Inches")) return value * 144;
                if (toUnit.equals("Square Centimeters")) return value * 92903.04;
                if (toUnit.equals("Square Miles")) return value / 27878400.0;
                if (toUnit.equals("Square Kilometers")) return value / 10763910.4167;
                break;
            case "Square Inches":
                if (toUnit.equals("Square Meters")) return value / 1550.0031;
                if (toUnit.equals("Square Feet")) return value / 144;
                if (toUnit.equals("Square Centimeters")) return value * 645.16;
                if (toUnit.equals("Square Miles")) return value / 401467200.0;
                if (toUnit.equals("Square Kilometers")) return value / 15500031.25;

                break;
            case "Square Centimeters":
                if (toUnit.equals("Square Meters")) return value / 10000000;
                if (toUnit.equals("Square Feet")) return value / 156250;
                if (toUnit.equals("Square Inches")) return value / 645.16;
                if (toUnit.equals("Square Miles")) return value / 2589988110336.0;
                if (toUnit.equals("Square Kilometers")) return value / 10000000000.0;

                break;
            case "Liters":
                if (toUnit.equals("Cubic Meters")) return value / 1000;
                if (toUnit.equals("Gallons")) return value * 0.264172;
                if (toUnit.equals("Cubic Feet")) return value * 28.31685;
                if (toUnit.equals("Cubic Inches")) return value * 61023.744;
                if (toUnit.equals("Cubic Centimeters")) return value * 1000000;
                if (toUnit.equals("Cubic Miles")) return value / 219969242.0;
                if (toUnit.equals("Cubic Yards")) return value * 1.30799;
                break;
            case "Cubic Meters":
                if (toUnit.equals("Liters")) return value * 1000;
                if (toUnit.equals("Gallons")) return value * 264.172;
                if (toUnit.equals("Cubic Feet")) return value * 35.3147;
                if (toUnit.equals("Cubic Inches")) return value * 61023.744;
                if (toUnit.equals("Cubic Centimeters")) return value * 1000000000;
                if (toUnit.equals("Cubic Miles")) return value / 219969242.0;
                if (toUnit.equals("Cubic Yards")) return value * 1.30799;

                break;
            case "Gallons":
                if (toUnit.equals("Liters")) return value / 0.264172;
                if (toUnit.equals("Cubic Meters")) return value / 264.172;
                if (toUnit.equals("Cubic Feet")) return value * 7.48052;
                if (toUnit.equals("Cubic Inches")) return value * 1728;
                if (toUnit.equals("Cubic Centimeters")) return value * 26417.2;
                break;
            case "Cubic Feet":
                if (toUnit.equals("Cubic Meters")) return value / 28316.85;
                if (toUnit.equals("Cubic Inches")) return value * 1728;
                if (toUnit.equals("Cubic Centimeters")) return value * 2831685;
                if (toUnit.equals("Liters")) return value / 7.48052;
                if (toUnit.equals("Gallons")) return value / 7.48052;
                if (toUnit.equals("Cubic Miles")) return value / 2787840000.0;
                if (toUnit.equals("Cubic Yards")) return value * 0.568182;

                break;
            case "Cubic Inches":
                if (toUnit.equals("Cubic Meters")) return value / 1728;
                if (toUnit.equals("Cubic Feet")) return value / 1728;
                if (toUnit.equals("Cubic Centimeters")) return value * 16387.064;
                if (toUnit.equals("Liters")) return value / 7.48052;
                if (toUnit.equals("Gallons")) return value / 7.48052;
                if (toUnit.equals("Cubic Miles")) return value / 2787840000.0;
                if (toUnit.equals("Cubic Yards")) return value / 1728;
                break;
            case "Cubic Centimeters":
                if (toUnit.equals("Cubic Meters")) return value / 1000000000;
                if (toUnit.equals("Cubic Feet")) return value / 2831685;
                if (toUnit.equals("Cubic Inches")) return value / 16387.064;
                if (toUnit.equals("Liters")) return value / 7.48052;
                if (toUnit.equals("Gallons")) return value / 7.48052;
                if (toUnit.equals("Cubic Miles")) return value / 2787840000.0;
                if (toUnit.equals("Cubic Yards")) return value / 16387.064;

                break;
            case "Cubic Miles":
                if (toUnit.equals("Cubic Meters")) return value /2787840000.0;
                if (toUnit.equals("Cubic Yards")) return value * 7645548.737;
                if (toUnit.equals("Cubic Feet")) return value * 2787840000.0;
                if (toUnit.equals("Cubic Inches")) return value * 2787840000000.0;
                if (toUnit.equals("Cubic Centimeters")) return value * 2787840000000000.0;
                if (toUnit.equals("Liters")) return value / 219969242.0;
                if (toUnit.equals("Gallons")) return value / 219969242.0;
                break;
            case "Cubic Yards":
                if (toUnit.equals("Cubic Meters")) return value * 0.836127;
                if (toUnit.equals("Cubic Miles")) return value / 7645548.737;
                if (toUnit.equals("Cubic Feet")) return value * 3;
                if (toUnit.equals("Cubic Inches")) return value * 72913.2;
                if (toUnit.equals("Cubic Centimeters")) return value * 836127;
                if (toUnit.equals("Liters")) return value * 61.0237;
                if (toUnit.equals("Gallons")) return value * 61.0237;

                break;
            case "Seconds":
                if (toUnit.equals("Minutes")) return value / 60;
                if (toUnit.equals("Hours")) return value / 3600;
                if (toUnit.equals("Milliseconds")) return value * 1000;
                if (toUnit.equals("Microseconds")) return value * 1000000;
                if (toUnit.equals("Nanoseconds")) return value * 1000000000;
                if (toUnit.equals("Days")) return value / 86400;
                if (toUnit.equals("Weeks")) return value / 604800;
                if (toUnit.equals("Months")) return value / 2592000;
                if (toUnit.equals("Years")) return value / 31536000;
                if (toUnit.equals("Decades")) return value / 315360000;
                if (toUnit.equals("Centuries")) return value / 3153600000.0;
                break;
            case "Minutes":
                if (toUnit.equals("Seconds")) return value * 60;
                if (toUnit.equals("Hours")) return value / 60;
                if (toUnit.equals("Milliseconds")) return value * 60000;
                if (toUnit.equals("Microseconds")) return value * 60000000;
                if (toUnit.equals("Nanoseconds")) return value * 60000000000.0;
                if (toUnit.equals("Days")) return value / 1440;
                if (toUnit.equals("Weeks")) return value / 10080;
                if (toUnit.equals("Months")) return value / 43800;
                if (toUnit.equals("Years")) return value / 525600;
                if (toUnit.equals("Decades")) return value / 5256000.0;
                if (toUnit.equals("Centuries")) return value / 52560000.0;

                break;
            case "Hours":
                if (toUnit.equals("Seconds")) return value * 3600;
                if (toUnit.equals("Minutes")) return value * 60;
                if (toUnit.equals("Milliseconds")) return value * 3600000;
                if (toUnit.equals("Microseconds")) return value * 3600000000.0;
                if (toUnit.equals("Nanoseconds")) return value * 3600000000000.0;
                if (toUnit.equals("Days")) return value / 24;
                if (toUnit.equals("Weeks")) return value / 168;
                if (toUnit.equals("Months")) return value / 730;
                if (toUnit.equals("Years")) return value / 8760;
                if (toUnit.equals("Decades")) return value / 87600.0;
                if (toUnit.equals("Centuries")) return value / 876000.0;

                break;
            case "Milliseconds":
                if (toUnit.equals("Seconds")) return value / 1000;
                if (toUnit.equals("Microseconds")) return value * 1000;
                if (toUnit.equals("Nanoseconds")) return value * 1000000;
                if (toUnit.equals("Hours")) return value / 3600000;
                if (toUnit.equals("Minutes")) return value / 60000;
                if (toUnit.equals("Days")) return value / 86400000;
                if (toUnit.equals("Weeks")) return value / 604800000;
                if (toUnit.equals("Months")) return value / 2592000000.0;
                if (toUnit.equals("Years")) return value / 31536000000.0;
                if (toUnit.equals("Decades")) return value / 315360000000.0;
                if (toUnit.equals("Centuries")) return value / 3153600000000.0;

                break;
            case "Microseconds":
                if (toUnit.equals("Seconds")) return value / 1000000;
                if (toUnit.equals("Milliseconds")) return value / 1000;
                if (toUnit.equals("Nanoseconds")) return value * 1000;
                if (toUnit.equals("Hours")) return value / 3600000000.0;
                if (toUnit.equals("Minutes")) return value / 60000000;
                if (toUnit.equals("Days")) return value / 86400000000.0;
                if (toUnit.equals("Weeks")) return value / 604800000000.0;
                if (toUnit.equals("Months")) return value / 2592000000000.0;
                if (toUnit.equals("Years")) return value / 31536000000000.0;
                if (toUnit.equals("Decades")) return value / 315360000000000.0;
                if (toUnit.equals("Centuries")) return value / 3153600000000000.0;

                break;
            case "Nanoseconds":
                if (toUnit.equals("Seconds")) return value / 1000000000;
                if (toUnit.equals("Milliseconds")) return value / 1000000;
                if (toUnit.equals("Microseconds")) return value / 1000;
                if (toUnit.equals("Hours")) return value / 3600000000000.0;
                if (toUnit.equals("Minutes")) return value / 60000000000.0;
                if (toUnit.equals("Days")) return value / 86400000000000.0;
                if (toUnit.equals("Weeks")) return value / 604800000000000.0;
                if (toUnit.equals("Months")) return value / 2592000000000000.0;
                if (toUnit.equals("Years")) return value / 31536000000000000.0;
                if (toUnit.equals("Decades")) return value / 315360000000000000.0;
                if (toUnit.equals("Centuries")) return value / 3153600000000000000.0;
                break;
            case "Days":
                if (toUnit.equals("Seconds")) return value * 86400;
                if (toUnit.equals("Weeks")) return value / 7;
                if (toUnit.equals("Months")) return value / 30;
                if (toUnit.equals("Years")) return value / 365;
                if (toUnit.equals("Decades")) return value / 3650;
                if (toUnit.equals("Centuries")) return value / 36500.0;

                break;
            case "Weeks":
                if (toUnit.equals("Seconds")) return value * 604800;
                if (toUnit.equals("Days")) return value * 7;
                if (toUnit.equals("Months")) return value / 4.34524;
                if (toUnit.equals("Years")) return value / 52;
                if (toUnit.equals("Decades")) return value / 520.0;
                if (toUnit.equals("Centuries")) return value / 5200.0;

                break;
            case "Months":
                if (toUnit.equals("Seconds")) return value * 2592000;
                if (toUnit.equals("Years")) return value / 12;
                if (toUnit.equals("Days")) return value * 30;
                if (toUnit.equals("Weeks")) return value * 4.34524;
                if (toUnit.equals("Decades")) return value / 120.0;
                if (toUnit.equals("Centuries")) return value / 1200.0;


                break;
            case "Years":
                if (toUnit.equals("Seconds")) return value * 31536000;
                if (toUnit.equals("Months")) return value * 12;
                if (toUnit.equals("Decades")) return value / 10;
                if (toUnit.equals("Centuries")) return value / 100;
                if (toUnit.equals("Days")) return value * 365;
                if (toUnit.equals("Weeks")) return value * 52;

                break;
            case "Decades":
                if (toUnit.equals("Years")) return value * 10;
                if (toUnit.equals("Centuries")) return value / 10;
                if (toUnit.equals("Months")) return value * 120;
                if (toUnit.equals("Weeks")) return value * 520;
                if (toUnit.equals("Days")) return value * 3650;

                break;
            case "Centuries":
                if (toUnit.equals("Years")) return value * 100;
                if (toUnit.equals("Decades")) return value * 10;
                if (toUnit.equals("Months")) return value * 1200;
                if (toUnit.equals("Weeks")) return value * 5200;
                if (toUnit.equals("Days")) return value * 36500;
                break;
            case "Bytes":
                if (toUnit.equals("Kilobytes")) return value / 1024;
                if (toUnit.equals("Megabytes")) return value / 1048576;
                if (toUnit.equals("Gigabytes")) return value / 1073741824;
                if (toUnit.equals("Terabytes")) return value / 1099511627776.0;
                if (toUnit.equals("Petabytes")) return value / 1125899906842624.0;
                break;
            case "Kilobytes":
                if (toUnit.equals("Bytes")) return value * 1024;
                if (toUnit.equals("Megabytes")) return value / 1024;
                if (toUnit.equals("Gigabytes")) return value / 1048576;
                if (toUnit.equals("Terabytes")) return value / 1073741824;
                if (toUnit.equals("Petabytes")) return value / 1099511627776.0;

                break;
            case "Megabytes":
                if (toUnit.equals("Bytes")) return value * 1048576;
                if (toUnit.equals("Kilobytes")) return value * 1024;
                if (toUnit.equals("Gigabytes")) return value / 1024;
                if (toUnit.equals("Terabytes")) return value / 1048576;
                if (toUnit.equals("Petabytes")) return value / 1073741824;
                break;
            case "Gigabytes":
                if (toUnit.equals("Bytes")) return value * 1073741824;
                if (toUnit.equals("Kilobytes")) return value * 1048576;
                if (toUnit.equals("Megabytes")) return value * 1024;
                if (toUnit.equals("Terabytes")) return value / 1024;
                if (toUnit.equals("Petabytes")) return value / 1048576;

                break;
            case "yards":
                if (toUnit.equals("Meters")) return value / 1.09361;
                if (toUnit.equals("millimeters")) return value * 914.4;
                if (toUnit.equals("Decimeter")) return value * 10;

                break;
            case "millimeter":
                if (toUnit.equals("Meters")) return value / 1000000;
                if (toUnit.equals("millimeters")) return value / 1000;
                if (toUnit.equals("yards")) return value / 914.4;
                if (toUnit.equals("Decimeter")) return value / 10;

                break;
            case "feet":
                if (toUnit.equals("Meters")) return value / 3.28084;
                if (toUnit.equals("inch")) return value * 12;
                if (toUnit.equals("Decimeter")) return value * 30.48;

                break;
            case "inch":
                if (toUnit.equals("Meters")) return value / 39.3701;
                if (toUnit.equals("feet")) return value / 12;
                if (toUnit.equals("Decimeter")) return value * 2.54;

                break;
            case "centimeters":
                if (toUnit.equals("Meters")) return value / 100;
                if (toUnit.equals("millimeters")) return value * 10;
                if (toUnit.equals("yards")) return value / 10;
                if (toUnit.equals("feet")) return value / 30.48;
                if (toUnit.equals("inch")) return value / 2.54;

                break;
            case "Miles":
                if (toUnit.equals("Meters")) return value / 0.000621371;
                if (toUnit.equals("Kilometers")) return value / 0.621371;
                if (toUnit.equals("Feet")) return value * 5280;
                if (toUnit.equals("Yards")) return value * 1760;
                if (toUnit.equals("Inches")) return value * 63360;

                break;
            case "Grams":
                if (toUnit.equals("Kilograms")) return value / 1000;
                if (toUnit.equals("Pounds")) return value * 0.00220462;

                break;
            case "Kilograms":
                if (toUnit.equals("Grams")) return value * 1000;
                if (toUnit.equals("Pounds")) return value * 2.20462;

                break;
            case "Pounds":
                if (toUnit.equals("Grams")) return value / 0.00220462;
                if (toUnit.equals("Kilograms")) return value / 2.20462;

                break;

            // Add additional conversions for other units here
        }

        return 0;
    }
    private void calculateConversion() {
        String fromUnit = binding.convertFromCatagory.getSelectedItem().toString();
        String toUnit = binding.convertToCatagory.getSelectedItem().toString();
        double inputValue;

        try {
            inputValue = Double.parseDouble(binding.fromCovertInput.getText().toString());
        } catch (NumberFormatException e) {
            binding.toCovertInput.setText("");
            return;
        }

        double conversionResult = convertUnits(fromUnit, toUnit, inputValue);
        binding.toCovertInput.setText(String.valueOf(conversionResult));
    }
}