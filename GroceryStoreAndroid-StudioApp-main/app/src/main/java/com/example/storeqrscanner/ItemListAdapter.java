package com.example.storeqrscanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class ItemListAdapter extends FirebaseRecyclerAdapter<Item, ItemListAdapter.MyViewHolder> {
    public ItemListAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
        //Log.i("SLOG", "ItemListAdapter O:" + options);
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
        //Log.i("SLOG", "ItemListAdapter onBindViewHolder ");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        Log.i("SLOG", "ItemListAdapter onCreateViewHolder ");
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName, itemDiscount, itemPrice, startItemDiscount, endItemDiscount, itemQuantity;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDiscount = (TextView) itemView.findViewById(R.id.itemDiscount);
            startItemDiscount = (TextView) itemView.findViewById(R.id.startItemDiscount);
            endItemDiscount = (TextView) itemView.findViewById(R.id.endItemDiscount);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            //Log.i("SLOG", "ItemListAdapter MyViewHolder ");

        }
    }
}
