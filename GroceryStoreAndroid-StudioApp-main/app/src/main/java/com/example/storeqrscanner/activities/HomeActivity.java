package com.example.storeqrscanner.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.storeqrscanner.Home2;
import com.example.storeqrscanner.Item;
import com.example.storeqrscanner.ItemAdapter;
import com.example.storeqrscanner.ItemListAdapter;
import com.example.storeqrscanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    List<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.btnScan);
        itemList = new ArrayList<>();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(HomeActivity.this);
                intentIntegrator.setPrompt("For Flash use Volume Up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
        Button bt = findViewById(R.id.btnCheckout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AddCardActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();

            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();

        }
        else if (item.getItemId()==R.id.cardInfo){

            startActivity(new Intent(HomeActivity.this,AddCardActivity.class));

        }
        else if (item.getItemId() == R.id.reset){
            startActivity(new Intent(HomeActivity.this,ResetPasswordActivity.class));
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );if (intentResult.getContents()!= null)
        {
            Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_SHORT).show();
            getItem(intentResult.getContents());

        }
        else {
            Toast.makeText(this, "oops! something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private  void getItem(String s){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");
        reference.orderByChild("barcode").equalTo(s)
    .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            for (DataSnapshot ds: snapshot.getChildren())
            {
                Item modelShop = ds.getValue(Item.class);
                String shopCity = ""+ds.child("city").getValue();
                itemList.add(modelShop);
            }
            adapter = new ItemAdapter(itemList);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    });
    }

}