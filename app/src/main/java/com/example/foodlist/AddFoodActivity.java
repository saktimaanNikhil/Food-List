package com.example.foodlist;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodlist.databinding.ActivityAddFoodBinding;

import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {
    MyDatabaseHelper myDB;
    FoodModel model;
    private ActivityAddFoodBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new MyDatabaseHelper(this);
        model = new FoodModel();
        getSupportActionBar().hide();

        Intent intent = getIntent();
        if (intent != null) {
            String heading = intent.getStringExtra("update");
            if (heading != null && heading.equals("Update")) {
                binding.updateButton.setVisibility(View.VISIBLE);
                binding.saveButton.setVisibility(View.GONE); // Hide the Save button
                String id = intent.getStringExtra("id");
                String price = intent.getStringExtra("price");
                String name = intent.getStringExtra("name");


                binding.titleEditText.setText(name);
                binding.price.setText(price);
                binding.addFoodHeading.setText(heading);
            }
        } else {
            binding.saveButton.setVisibility(View.VISIBLE);
            binding.updateButton.setVisibility(View.GONE);
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String foodName = binding.titleEditText.getText().toString().trim();
                String foodPrice = binding.price.getText().toString().trim();
                myDB.addNote(foodName, foodPrice);

                Intent intent = new Intent(AddFoodActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = intent.getStringExtra("id");
                String title = binding.titleEditText.getText().toString().trim();
                String content = binding.price.getText().toString().trim();


                myDB.updateContact(id, title, content);

                Intent intent = new Intent(AddFoodActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });


    }
}