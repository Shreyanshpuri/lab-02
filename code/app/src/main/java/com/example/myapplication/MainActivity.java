package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    private Button deleteCity;
    private int selectedIndex = -1;

    private void clearSelection() {
        selectedIndex = -1;
        cityList.clearChoices();
        cityAdapter.notifyDataSetChanged();
        deleteCity.setEnabled(false);

    }

    private void deleteSelectedCity() {
        if (selectedIndex != -1) {
            // THIS LINE WAS MISSING: Actually remove the item from the list
            dataList.remove(selectedIndex);
            selectedIndex = -1;
            cityList.clearChoices();
            cityAdapter.notifyDataSetChanged();
            deleteCity.setEnabled(false);
        }
    }

    private void showAddCity() {
        final android.widget.EditText input = new android.widget.EditText(this);
        input.setMaxLines(1);

        final AlertDialog dialog =
                new AlertDialog.Builder(this)
                        .setTitle("Add City")
                        .setView(input)
                        .setPositiveButton("Confirm", (d, w) -> {
                            String city = input.getText().toString();
                            dataList.add(city);
                            cityAdapter.notifyDataSetChanged();
                        })
                        .create();
        dialog.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);

        cityList.setAdapter(cityAdapter);

        Button addCity = findViewById(R.id.add_city_button);
        deleteCity = findViewById(R.id.delete_city_button);
        deleteCity.setOnClickListener(v -> deleteSelectedCity());
        addCity.setOnClickListener(v -> showAddCity());

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            if (position == selectedIndex) {
                clearSelection();
            } else {
                selectedIndex = position;
                cityList.setItemChecked(position, true);
                deleteCity.setEnabled(true);
            }
        });
    }

}