package com.example.foodlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodlist.databinding.ActivityAddFoodBinding;
import com.example.foodlist.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper myDB;
    ArrayList<FoodModel> data;
    CustomAdapter customAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new MyDatabaseHelper(this);
        data= new ArrayList<>();

        binding.findFoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String json = gson.toJson(data); // Convert data to JSON
                Intent intent = new Intent(MainActivity.this, FinalFoodListActivity.class);
                intent.putExtra("foodListJson", json);
                startActivity(intent);

            }
        });



        storeDataInArrays();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(this,data);
        binding.recyclerView.setAdapter(customAdapter);

        binding.addFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddFoodActivity.class);
                startActivity(intent);

            }
        });

    }
    void storeDataInArrays(){
        Cursor cursor = myDB.readAlData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                FoodModel model = new FoodModel();
                model.id = cursor.getInt(0);
                model.name = cursor.getString(1);
                model.price = cursor.getString(2);
                data.add(model);

            }
        }
    }
}