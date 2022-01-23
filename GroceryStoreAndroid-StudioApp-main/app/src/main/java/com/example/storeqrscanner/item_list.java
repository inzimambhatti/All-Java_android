package com.example.storeqrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class item_list extends AppCompatActivity {
    private RecyclerView itemsList;
    private ItemListManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemsList = (RecyclerView) findViewById(R.id.itemsList);
        itemsList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Items"), Item.class)
                .build();

        adapter = new ItemListManagerAdapter(options);
        itemsList.setAdapter(adapter);
        //Log.i("SLOG adapter ItemList", adapter.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {

        super.onStop();
        adapter.stopListening();
    }
}