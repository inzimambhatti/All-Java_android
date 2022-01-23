package com.example.storeqrscanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDiscount, itemPrice, startItemDiscount, endItemDiscount, itemQuantity;
        ImageButton imgBtn;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemDiscount = (TextView) itemView.findViewById(R.id.itemDiscount);
            startItemDiscount = (TextView) itemView.findViewById(R.id.startItemDiscount);
            endItemDiscount = (TextView) itemView.findViewById(R.id.endItemDiscount);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            imgBtn =  itemView.findViewById(R.id.btnDelete);
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ItemAdapter(List<Item> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Item item = localDataSet.get(position);
        viewHolder.itemName.setText(item.name);
        viewHolder.itemDiscount.setText("discount:"+item.discount);
        viewHolder.startItemDiscount.setText(item.startDiscount);
        viewHolder.endItemDiscount.setText(item.endDiscount);
        viewHolder.itemPrice.setText("Rs:"+item.price);
        viewHolder.itemQuantity.setText("Quantity Available"+item.quantity);
        viewHolder.imgBtn.setVisibility(View.VISIBLE);
        viewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDataSet.remove(position);
                notifyItemRemoved(position);
            }
        });
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}