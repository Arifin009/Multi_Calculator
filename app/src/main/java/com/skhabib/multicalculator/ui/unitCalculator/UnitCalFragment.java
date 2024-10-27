package com.skhabib.multicalculator.ui.unitCalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.skhabib.multicalculator.R;
import com.skhabib.multicalculator.databinding.FragmentGalleryBinding;

public class UnitCalFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UnitCalViewModel galleryViewModel =
                new ViewModelProvider(this).get(UnitCalViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fromCovertInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.fromCovertInput.setHint("");
            } else {
                binding.fromCovertInput.setHint("- -");
            }
        });
        binding.toCovertInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.toCovertInput.setHint("");
            } else {
                binding.toCovertInput.setHint("- -");
            }
        });
        String[] categories = {"Length", "Mass", "Area", "Volume", "Time", "Data"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_selected_item,  // Custom layout for selected item
                categories
        );
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);  // Custom layout for dropdown items
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
                subcategories = new String[]{"meters", "kilometers", "miles","inch","feet","centimeters","millimeters"};
                break;
            case "Mass":
                subcategories = new String[]{"grams", "kilograms", "pounds","ounces","milligrams","tonnes"};
                break;
            case "Area":
                subcategories = new String[]{"square meters", "hectares", "acres",
                         "square feet", "square inches", "square centimeters"};
                break;
            case "Volume":
                subcategories = new String[]{"liters", "cubic meters", "gallons", "cubic feet", "cubic inches"};
                break;
            case "Time":
                subcategories = new String[]{"seconds", "minutes", "hours","milliseconds",
                        "microseconds","nanoseconds"};
                break;
            case "Data":
                subcategories = new String[]{"bytes", "kilobytes", "megabytes", "gigabytes","bits","megabits","gigabits"};
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
            //length
            case "meters":
                if (toUnit.equals("kilometers")) return value / 1000;
                if (toUnit.equals("miles")) return value * 0.000621371;
                if (toUnit.equals("inch")) return value * 39.3701;
                if (toUnit.equals("feet")) return value * 3.28084;
                if (toUnit.equals("centimeters")) return value * 100;
                if (toUnit.equals("millimeters")) return value * 1000;
                break;

            case "kilometers":
                if (toUnit.equals("meters")) return value * 1000;
                if (toUnit.equals("miles")) return value * 0.621371;
                if (toUnit.equals("inch")) return value * 39_370.1;
                if (toUnit.equals("feet")) return value * 3280.84;
                if (toUnit.equals("centimeters")) return value * 100_000;
                if (toUnit.equals("millimeters")) return value * 1_000_000;
                break;

            case "miles":
                if (toUnit.equals("meters")) return value / 0.000621371;
                if (toUnit.equals("kilometers")) return value / 0.621371;
                if (toUnit.equals("inch")) return value * 63_360;
                if (toUnit.equals("feet")) return value * 5280;
                if (toUnit.equals("centimeters")) return value * 160_934;
                if (toUnit.equals("millimeters")) return value * 1_609_344;
                break;

            case "inch":
                if (toUnit.equals("meters")) return value / 39.3701;
                if (toUnit.equals("kilometers")) return value / 39_370.1;
                if (toUnit.equals("miles")) return value / 63_360;
                if (toUnit.equals("feet")) return value / 12;
                if (toUnit.equals("centimeters")) return value * 2.54;
                if (toUnit.equals("millimeters")) return value * 25.4;
                break;

            case "feet":
                if (toUnit.equals("meters")) return value / 3.28084;
                if (toUnit.equals("kilometers")) return value / 3280.84;
                if (toUnit.equals("miles")) return value / 5280;
                if (toUnit.equals("inch")) return value * 12;
                if (toUnit.equals("centimeters")) return value * 30.48;
                if (toUnit.equals("millimeters")) return value * 304.8;
                break;

            case "centimeters":
                if (toUnit.equals("meters")) return value / 100;
                if (toUnit.equals("kilometers")) return value / 100_000;
                if (toUnit.equals("miles")) return value / 160_934;
                if (toUnit.equals("inch")) return value / 2.54;
                if (toUnit.equals("feet")) return value / 30.48;
                if (toUnit.equals("millimeters")) return value * 10;
                break;

            case "millimeters":
                if (toUnit.equals("meters")) return value / 1000;
                if (toUnit.equals("kilometers")) return value / 1_000_000;
                if (toUnit.equals("miles")) return value / 1_609_344;
                if (toUnit.equals("inch")) return value / 25.4;
                if (toUnit.equals("feet")) return value / 304.8;
                if (toUnit.equals("centimeters")) return value / 10;
                break;
                //mass
            case "grams":
                if (toUnit.equals("kilograms")) return value / 1000;
                if (toUnit.equals("pounds")) return value * 0.00220462;
                if (toUnit.equals("ounces")) return value * 0.035274;
                if (toUnit.equals("milligrams")) return value * 1000;
                if (toUnit.equals("tonnes")) return value / 1_000_000;
                break;

            case "kilograms":
                if (toUnit.equals("grams")) return value * 1000;
                if (toUnit.equals("pounds")) return value * 2.20462;
                if (toUnit.equals("ounces")) return value * 35.274;
                if (toUnit.equals("milligrams")) return value * 1_000_000;
                if (toUnit.equals("tonnes")) return value / 1000;
                break;

            case "pounds":
                if (toUnit.equals("grams")) return value / 0.00220462;
                if (toUnit.equals("kilograms")) return value / 2.20462;
                if (toUnit.equals("ounces")) return value * 16;
                if (toUnit.equals("milligrams")) return value / 0.00000220462;
                if (toUnit.equals("tonnes")) return value / 2204.62;
                break;

            case "ounces":
                if (toUnit.equals("grams")) return value / 0.035274;
                if (toUnit.equals("kilograms")) return value / 35.274;
                if (toUnit.equals("pounds")) return value / 16;
                if (toUnit.equals("milligrams")) return value / 0.000035274;
                if (toUnit.equals("tonnes")) return value / 35_274;
                break;

            case "milligrams":
                if (toUnit.equals("grams")) return value / 1000;
                if (toUnit.equals("kilograms")) return value / 1_000_000;
                if (toUnit.equals("pounds")) return value * 0.00000220462;
                if (toUnit.equals("ounces")) return value * 0.000035274;
                if (toUnit.equals("tonnes")) return value / 1_000_000_000;
                break;

            case "tonnes":
                if (toUnit.equals("grams")) return value * 1_000_000;
                if (toUnit.equals("kilograms")) return value * 1000;
                if (toUnit.equals("pounds")) return value * 2204.62;
                if (toUnit.equals("ounces")) return value * 35_274;
                if (toUnit.equals("milligrams")) return value * 1_000_000_000;
                break;
                //area
            case "square meters":
                if (toUnit.equals("hectares")) return value / 10_000;
                if (toUnit.equals("acres")) return value * 0.000247105;
                if (toUnit.equals("square feet")) return value * 10.7639;
                if (toUnit.equals("square inches")) return value * 1550;
                if (toUnit.equals("square centimeters")) return value * 10_000;
                break;

            case "hectares":
                if (toUnit.equals("square meters")) return value * 10_000;
                if (toUnit.equals("acres")) return value * 2.47105;
                if (toUnit.equals("square feet")) return value * 107_639;
                if (toUnit.equals("square inches")) return value * 1_550_000;
                if (toUnit.equals("square centimeters")) return value * 100_000_000;
                break;

            case "acres":
                if (toUnit.equals("square meters")) return value / 0.000247105;
                if (toUnit.equals("hectares")) return value / 2.47105;
                if (toUnit.equals("square feet")) return value * 43_560;
                if (toUnit.equals("square inches")) return value * 6_272_640;
                if (toUnit.equals("square centimeters")) return value * 4046.86 * 10000;
                break;

            case "square feet":
                if (toUnit.equals("square meters")) return value / 10.7639;
                if (toUnit.equals("hectares")) return value / 107_639;
                if (toUnit.equals("acres")) return value / 43_560;
                if (toUnit.equals("square inches")) return value * 144;
                if (toUnit.equals("square centimeters")) return value * 929.03;
                break;

            case "square inches":
                if (toUnit.equals("square meters")) return value / 1550;
                if (toUnit.equals("hectares")) return value / 1_550_000;
                if (toUnit.equals("acres")) return value / 6_272_640;
                if (toUnit.equals("square feet")) return value / 144;
                if (toUnit.equals("square centimeters")) return value * 6.4516;
                break;

            case "square centimeters":
                if (toUnit.equals("square meters")) return value / 10_000;
                if (toUnit.equals("hectares")) return value / 100_000_000;
                if (toUnit.equals("acres")) return value / 4_046_860;
                if (toUnit.equals("square feet")) return value / 929.03;
                if (toUnit.equals("square inches")) return value / 6.4516;
                break;
                //volume
            case "liters":
                if (toUnit.equals("cubic meters")) return value / 1000;
                if (toUnit.equals("gallons")) return value * 0.264172;
                if (toUnit.equals("cubic feet")) return value * 0.0353147;
                if (toUnit.equals("cubic inches")) return value * 61.0237;
                break;

            case "cubic meters":
                if (toUnit.equals("liters")) return value * 1000;
                if (toUnit.equals("gallons")) return value * 264.172;
                if (toUnit.equals("cubic feet")) return value * 35.3147;
                if (toUnit.equals("cubic inches")) return value * 61_023.7;
                break;

            case "gallons":
                if (toUnit.equals("liters")) return value / 0.264172;
                if (toUnit.equals("cubic meters")) return value / 264.172;
                if (toUnit.equals("cubic feet")) return value * 0.133681;
                if (toUnit.equals("cubic inches")) return value * 231;
                break;

            case "cubic feet":
                if (toUnit.equals("liters")) return value / 0.0353147;
                if (toUnit.equals("cubic meters")) return value / 35.3147;
                if (toUnit.equals("gallons")) return value / 0.133681;
                if (toUnit.equals("cubic inches")) return value * 1728;
                break;

            case "cubic inches":
                if (toUnit.equals("liters")) return value / 61.0237;
                if (toUnit.equals("cubic meters")) return value / 61_023.7;
                if (toUnit.equals("gallons")) return value / 231;
                if (toUnit.equals("cubic feet")) return value / 1728;
                break;
//time
            case "seconds":
                if (toUnit.equals("milliseconds")) return value * 1000;
                if (toUnit.equals("microseconds")) return value * 1_000_000;
                if (toUnit.equals("nanoseconds")) return value * 1_000_000_000;
                if (toUnit.equals("minutes")) return value / 60;
                if (toUnit.equals("hours")) return value / 3600;
                break;

            case "minutes":
                if (toUnit.equals("seconds")) return value * 60;
                if (toUnit.equals("milliseconds")) return value * 60 * 1000;
                if (toUnit.equals("microseconds")) return value * 60 * 1_000_000;
                if (toUnit.equals("nanoseconds")) return value * 60 * 1_000_000_000;
                if (toUnit.equals("hours")) return value / 60;
                break;

            case "hours":
                if (toUnit.equals("seconds")) return value * 3600;
                if (toUnit.equals("milliseconds")) return value * 3600 * 1000;
                if (toUnit.equals("microseconds")) return value * 3600 * 1_000_000;
                if (toUnit.equals("nanoseconds")) return value * 3600 * 1_000_000_000;
                if (toUnit.equals("minutes")) return value * 60;
                break;

            case "milliseconds":
                if (toUnit.equals("seconds")) return value / 1000;
                if (toUnit.equals("microseconds")) return value * 1000;
                if (toUnit.equals("nanoseconds")) return value * 1_000_000;
                if (toUnit.equals("minutes")) return value / (60 * 1000);
                if (toUnit.equals("hours")) return value / (3600 * 1000);
                break;

            case "microseconds":
                if (toUnit.equals("seconds")) return value / 1_000_000;
                if (toUnit.equals("milliseconds")) return value / 1000;
                if (toUnit.equals("nanoseconds")) return value * 1000;
                if (toUnit.equals("minutes")) return value / (60 * 1_000_000);
                if (toUnit.equals("hours")) return value / (3600 * 1_000_000);
                break;

            case "nanoseconds":
                if (toUnit.equals("seconds")) return value / 1_000_000_000;
                if (toUnit.equals("milliseconds")) return value / 1_000_000;
                if (toUnit.equals("microseconds")) return value / 1000;
                if (toUnit.equals("minutes")) return value / (60 * 1_000_000_000);
                if (toUnit.equals("hours")) return value / (3600 * 1_000_000_000);
                break;

            case "Centuries":
                if (toUnit.equals("Years")) return value * 100;
                if (toUnit.equals("Decades")) return value * 10;
                if (toUnit.equals("Months")) return value * 1200;
                if (toUnit.equals("Weeks")) return value * 5200;
                if (toUnit.equals("Days")) return value * 36500;
                break;
            case "bytes":
                if (toUnit.equals("kilobytes")) return value / 1024;
                if (toUnit.equals("megabytes")) return value / (1024 * 1024);
                if (toUnit.equals("gigabytes")) return value / (1024 * 1024 * 1024);
                if (toUnit.equals("bits")) return value * 8;
                if (toUnit.equals("megabits")) return value * 8 / 1024;
                if (toUnit.equals("gigabits")) return value * 8 / (1024 * 1024);
                break;

            case "kilobytes":
                if (toUnit.equals("bytes")) return value * 1024;
                if (toUnit.equals("megabytes")) return value / 1024;
                if (toUnit.equals("gigabytes")) return value / (1024 * 1024);
                if (toUnit.equals("bits")) return value * 8 * 1024;
                if (toUnit.equals("megabits")) return value * 8;
                if (toUnit.equals("gigabits")) return value * 8 / 1024;
                break;

            case "megabytes":
                if (toUnit.equals("bytes")) return value * (1024 * 1024);
                if (toUnit.equals("kilobytes")) return value * 1024;
                if (toUnit.equals("gigabytes")) return value / 1024;
                if (toUnit.equals("bits")) return value * 8 * (1024 * 1024);
                if (toUnit.equals("megabits")) return value * 8;
                if (toUnit.equals("gigabits")) return value * 8 / 1024;
                break;

            case "gigabytes":
                if (toUnit.equals("bytes")) return value * (1024 * 1024 * 1024);
                if (toUnit.equals("kilobytes")) return value * (1024 * 1024);
                if (toUnit.equals("megabytes")) return value * 1024;
                if (toUnit.equals("bits")) return value * 8 * (1024 * 1024 * 1024);
                if (toUnit.equals("megabits")) return value * 8 * (1024 * 1024) / 1024;
                if (toUnit.equals("gigabits")) return value * 8;
                break;

            case "bits":
                if (toUnit.equals("bytes")) return value / 8;
                if (toUnit.equals("kilobytes")) return value / (8 * 1024);
                if (toUnit.equals("megabytes")) return value / (8 * 1024 * 1024);
                if (toUnit.equals("gigabytes")) return value / (8 * 1024 * 1024 * 1024);
                if (toUnit.equals("megabits")) return value / 1_000_000;
                if (toUnit.equals("gigabits")) return value / 1_000_000_000;
                break;

            case "megabits":
                if (toUnit.equals("bytes")) return value * (8 * 1024 * 1024);
                if (toUnit.equals("kilobytes")) return value * (8 * 1024);
                if (toUnit.equals("megabytes")) return value / 8;
                if (toUnit.equals("gigabytes")) return value / (8 * 1024);
                if (toUnit.equals("bits")) return value * 1_000_000;
                if (toUnit.equals("gigabits")) return value / 1000;
                break;

            case "gigabits":
                if (toUnit.equals("bytes")) return value * (8 * 1024 * 1024 * 1024);
                if (toUnit.equals("kilobytes")) return value * (8 * 1024 * 1024);
                if (toUnit.equals("megabytes")) return value * (8 * 1024);
                if (toUnit.equals("bits")) return value * 1_000_000_000;
                if (toUnit.equals("megabits")) return value * 1000;
                if (toUnit.equals("gigabytes")) return value / 8;
                break;



            // Add additional conversions for other units here
        }

        return 0;
    }
    private void calculateConversion() {
        // Check if selected items are null
        Object fromUnitObject = binding.convertFromCatagory.getSelectedItem();
        Object toUnitObject = binding.convertToCatagory.getSelectedItem();

        // Check for null before calling toString
        if (fromUnitObject == null || toUnitObject == null) {
            // Optionally show a message to the user
            Log.e("UnitCalFragment", "One of the selected units is null");
            return; // Exit early if either spinner has no selection
        }

        String fromUnit = fromUnitObject.toString();
        String toUnit = toUnitObject.toString();

        double inputValue;

        // Try to parse the input value
        try {
            String inputText = binding.fromCovertInput.getText().toString();
            if (inputText.isEmpty()) {
                binding.toCovertInput.setText(""); // Clear output if input is empty
                return;
            }
            inputValue = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            binding.toCovertInput.setText(""); // Clear output if parsing fails
            Log.e("UnitCalFragment", "Invalid input value", e);
            return;
        }

        // Perform the conversion
        double conversionResult = convertUnits(fromUnit, toUnit, inputValue);
        binding.toCovertInput.setText(String.valueOf(conversionResult));
    }

}