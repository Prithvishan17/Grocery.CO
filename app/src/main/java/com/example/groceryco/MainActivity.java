package com.example.groceryco;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.groceryco.adapter.GroceryListAdapter;
import com.example.groceryco.model.GroceryModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GroceryListAdapter.GroceryListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Category");

        List<GroceryModel> groceryModelList = getDrinksData();

        initRecyclerView(groceryModelList);

    }

    private void initRecyclerView(List<GroceryModel> groceryModelList){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GroceryListAdapter adapter = new GroceryListAdapter(groceryModelList, this);
        recyclerView.setAdapter(adapter);

    }

    private List<GroceryModel> getDrinksData(){
        InputStream inputStream = getResources().openRawResource(R.raw.grocerylist);
        Writer writer = new StringWriter();
        char[] buffer = new char [1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }
            catch(Exception e){

            }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        GroceryModel[] groceryModels = gson.fromJson(jsonStr, GroceryModel[].class);
        List<GroceryModel> restList = Arrays.asList(groceryModels);

        return restList;

    }

    @Override
    public void onItemClick(GroceryModel groceryModel) {
        Intent intent = new Intent(MainActivity.this, GroceryListActivity.class);
        intent.putExtra("GroceryModel", groceryModel);
        startActivity(intent);

    }
}