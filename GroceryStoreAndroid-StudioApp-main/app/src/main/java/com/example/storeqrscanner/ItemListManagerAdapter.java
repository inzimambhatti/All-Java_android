package com.example.storeqrscanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class ItemListManagerAdapter extends FirebaseRecyclerAdapter<Item, ItemListManagerAdapter.MyViewHolder> {
    public ItemListManagerAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getName());
        holder.itemQuantity.setText("Available Quantity : " + model.getQuantity());
        if (!model.getDiscount().isEmpty()){
            holder.itemDiscount.setText(model.getDiscount() + "%  Discount!");
            holder.startItemDiscount.setText("Start on : " + model.getStartDiscount());
            holder.endItemDiscount.setText("End on : " + model.getEndDiscount());
        }
        else {
            holder.itemDiscount.setText("");
            holder.startItemDiscount.setText("");
            holder.endItemDiscount.setText("");
        }


        holder.itemPrice.setText(model.getPrice() + "  S.R.");

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.itemName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update_item))
                        .setExpanded(true,1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText itemName = myview.findViewById(R.id.itemNameU);
                EditText itemDiscount = myview.findViewById(R.id.itemDiscountU);
                EditText startItemDiscount = myview.findViewById(R.id.discountStartU);
                EditText endItemDiscount = myview.findViewById(R.id.discountEndU);
                EditText itemPrice = myview.findViewById(R.id.itemPriceU);
                EditText itemQuantity = myview.findViewById(R.id.itemQuantityU);
                Button submit = myview.findViewById(R.id.saveU);

                itemName.setText(model.getName());
                itemDiscount.setText(model.getDiscount());
                startItemDiscount.setText(model.getStartDiscount());
                endItemDiscount.setText(model.getEndDiscount());
                itemPrice.setText(model.getPrice());
                itemQuantity.setText(model.getQuantity());



                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",itemName.getText().toString());
                        map.put("quantity",itemQuantity.getText().toString());
                        map.put("price",itemPrice.getText().toString());
                        map.put("discount",itemDiscount.getText().toString());
                        map.put("startDiscount",startItemDiscount.getText().toString());
                        map.put("endDiscount",endItemDiscount.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Items")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemName.getContext());
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to delete this item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Items")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_manager, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName, itemDiscount, itemPrice, startItemDiscount, endItemDiscount, itemQuantity;
        Button editItem, deleteItem;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDiscount = (TextView) itemView.findViewById(R.id.itemDiscount);
            startItemDiscount = (TextView) itemView.findViewById(R.id.startItemDiscount);
            endItemDiscount = (TextView) itemView.findViewById(R.id.endItemDiscount);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            editItem = itemView.findViewById(R.id.editItem);
            deleteItem = itemView.findViewById(R.id.deleteItem);

        }
    }
}
