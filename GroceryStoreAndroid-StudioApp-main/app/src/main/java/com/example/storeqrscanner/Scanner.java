package com.example.storeqrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    private FirebaseUser user;
    private DatabaseReference refernce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        String data = rawResult.getText().toString();
        Log.i("SLOG", "data: " + data );

        if (!data.isEmpty()){
            refernce = FirebaseDatabase.getInstance().getReference("Items");

            Query query = refernce.orderByChild("barcode").equalTo(data);
            String Id = refernce.push().getKey();

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("SLOG", "snapshot : " + snapshot.getValue());
                    if (snapshot.exists()){
                        for (DataSnapshot child: snapshot.getChildren()) {
                            Item item = snapshot.child(child.getKey()).getValue(Item.class);
                            if (item != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Scanner.this);
                                View view = getLayoutInflater().inflate(R.layout.item_row, null);
                                TextView itemName = view.findViewById(R.id.itemName);
                                TextView itemPrice = view.findViewById(R.id.itemPrice);
                                TextView itemQuantity = view.findViewById(R.id.itemQuantity);
                                TextView itemDiscount = view.findViewById(R.id.itemDiscount);
                                TextView startItemDiscount = view.findViewById(R.id.startItemDiscount);
                                TextView endItemDiscount = view.findViewById(R.id.endItemDiscount);

                                itemName.setText(item.name);
                                itemPrice.setText(item.price + "  S.R.");
                                itemQuantity.setText("Available Quantity : " + item.quantity);

                                if(!item.discount.isEmpty()){
                                    itemDiscount.setText(item.discount + "%  Discount!");
                                }
                                else {
                                    itemDiscount.setText("");
                                }
                                if(!item.startDiscount.isEmpty()){
                                    startItemDiscount.setText("Start on : " + item.startDiscount);

                                }
                                else {
                                    startItemDiscount.setText("");
                                }
                                if(!item.endDiscount.isEmpty()){
                                    endItemDiscount.setText("End on : " + item.endDiscount);

                                }
                                else {
                                    endItemDiscount.setText("");
                                }


                                //builder.setMessage("Price : " + item.price);
                                //builder.setTitle(item.name);
                                builder.setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(getApplicationContext(), Home2.class));
                                    }
                                }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.setView(view);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            else {
                                Toast.makeText(Scanner.this, "Item Not found", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            Toast.makeText(this, "Item Not found", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }
    @Override
    protected void onResume(){
        super.onResume();
        scannerView.setResultHandler(this);
    }
}