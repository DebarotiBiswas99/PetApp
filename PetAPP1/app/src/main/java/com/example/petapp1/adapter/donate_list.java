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
import com.example.petapp1.R;
import com.example.petapp1.models.donor_data;

import java.util.List;

public class donate_list extends RecyclerView.Adapter<donate_list.ViewHolder> {
    private List<donor_data> donorData;

    public donate_list(List<donor_data> donorData) {
        this.donorData = donorData;
    }


    @NonNull

    @Override
    public donate_list.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_list,parent,false);
        return new donate_list.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull donate_list.ViewHolder holder, int position) {
        donor_data data=donorData.get(position);
        holder.txtbreed.setText(data.getDonor_breed());
        holder.txtcity.setText(data.getDonor_city());
        holder.txtstate.setText(data.getDonor_state());
        holder.txtblood.setText(data.getDonor_bloodgroup());




        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dID= donorData.get(position).getDonor_id().toString();
               // Toast.makeText(view.getContext(), "donor ID"+dID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DonationDetail.class);
                intent.putExtra("donor_id", dID);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return donorData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtbreed,txtcity,txtblood,txtstate;
        private ConstraintLayout cl;
        public ViewHolder(View itemView) {
            super(itemView);

            txtbreed=(TextView)itemView.findViewById(R.id.donate_card_breed);
            txtcity=(TextView)itemView.findViewById(R.id.donate_card_city);
            txtstate=(TextView)itemView.findViewById(R.id.donate_card_state);
            txtblood=(TextView)itemView.findViewById(R.id.donate_card_blood);
            cl = itemView.findViewById(R.id.donate_card_constraint);
        }
    }
}
