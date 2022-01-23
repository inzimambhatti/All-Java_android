package com.example.storeqrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddItem extends AppCompatActivity {
    private EditText itemName, itemQuantity, itemPrice, itemDiscount, discountStart, discountEnd;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Log.i("SLOG", "add item class");

        itemName = findViewById(R.id.itemName);
        itemQuantity = findViewById(R.id.itemQuantity);
        itemPrice = findViewById(R.id.itemPrice);
        itemDiscount = findViewById(R.id.itemDiscount);
        discountStart = findViewById(R.id.discountStart);
        discountEnd = findViewById(R.id.discountEnd);
        save = findViewById(R.id.save);

        discountStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(discountStart);
            }
        });
        discountEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(discountEnd);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
    }

    private void insertItem() {
        String name = itemName.getText().toString();
        String quantity = itemQuantity.getText().toString().trim();
        String price = itemPrice.getText().toString().trim();
        String discount = itemDiscount.getText().toString().trim();
        String startDiscount = discountStart.getText().toString();
        String endDiscount = discountEnd.getText().toString();

        if (name.isEmpty())
        {
            itemName.setError("Name is required");
            itemName.requestFocus();
            return;
        }
        if (quantity.isEmpty())
        {
            itemQuantity.setError("Quantity is required");
            itemQuantity.requestFocus();
            return;
        }
        if (price.isEmpty())
        {
            itemPrice.setError("Item Price is required");
            itemPrice.requestFocus();
            return;
        }

        int min = 1000000;
        int max = 9999999;
        int random = new Random().nextInt((max - min) + 1) + min;

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", name);
        map.put("name", name);
        map.put("barcode", String.valueOf(random));
        map.put("quantity", quantity);
        map.put("price", price);
        map.put("discount", discount);
        map.put("startDiscount", startDiscount);
        map.put("endDiscount", endDiscount);

        FirebaseDatabase.getInstance().getReference().child("Items").push()
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(AddItem.this, "Item Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddItem.this, Home2.class));
                }
                else {
                    String s = task.getException().getMessage();
                    Toast.makeText(AddItem.this, s, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void showDateTimeDialog(final EditText discountStart) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                        discountStart.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(AddItem.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(AddItem.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}