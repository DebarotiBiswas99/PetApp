package com.example.petapp1.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petapp1.DonationDetail;
import com.example.petapp1.FoodDetail;
import com.example.petapp1.R;
import com.example.petapp1.models.addpetfood_data;

import java.util.List;

public class addpetfood_list extends RecyclerView.Adapter<addpetfood_list.ViewHolder> {
    private List<addpetfood_data> addpetfoodData;

    public addpetfood_list(List<addpetfood_data> addpetfoodData) {
        this.addpetfoodData = addpetfoodData;
    }


    @NonNull

    @Override
    public addpetfood_list.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addpetfood_list,parent,false);
        return new addpetfood_list.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addpetfood_list.ViewHolder holder, int position) {
        addpetfood_data data=addpetfoodData.get(position);
        holder.txtpettype.setText(data.getAdd_pettype());
        holder.txtfoodname.setText(data.getAdd_foodname());

        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fID= addpetfoodData.get(position).getAdd_id().toString();
                // Toast.makeText(view.getContext(), "addpetfood ID"+dID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), FoodDetail.class);
                intent.putExtra("addpetfood_id", fID);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return addpetfoodData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtpettype, txtfoodname;
        private ConstraintLayout cl;
        public ViewHolder(View itemView) {
            super(itemView);

            txtpettype=(TextView)itemView.findViewById(R.id.addpetfood_card_pettype);
            txtfoodname=(TextView)itemView.findViewById(R.id.addpetfood_card_foodname);
            cl = itemView.findViewById(R.id.addpetfood_card_constraint);
        }
    }
}
