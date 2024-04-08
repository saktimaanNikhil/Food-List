package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FinalFoodListActivity extends AppCompatActivity {
    TextView jsonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_food_list);
        jsonText = findViewById(R.id.jsonText);
        getSupportActionBar().setTitle("Final Food List");

        String json = getIntent().getStringExtra("foodListJson");
        Log.d("JSON", json); // Add this line to log the JSON string

        Gson gson = new Gson();
        ArrayList<FoodModel> foodItemList = gson.fromJson(json, new TypeToken<ArrayList<FoodModel>>(){}.getType());
        jsonText.setText(gson.toJson(foodItemList));

    }
}