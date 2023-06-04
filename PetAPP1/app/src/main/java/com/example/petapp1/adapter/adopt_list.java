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

import com.example.petapp1.AdoptionDetail;
import com.example.petapp1.DonationDetail;
import com.example.petapp1.R;
import com.example.petapp1.models.givepet_data;

import java.util.List;

public class adopt_list extends RecyclerView.Adapter<adopt_list.ViewHolder> {
    private List<givepet_data> givepetData;

    public adopt_list(List<givepet_data> givepetData) { this.givepetData = givepetData; }


    @NonNull

    @Override
    public adopt_list.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adopt_list,parent,false);
        return new adopt_list.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adopt_list.ViewHolder holder, int position) {
        givepet_data data=givepetData.get(position);
        holder.txtbreed.setText(data.getGivepet_breed());
        holder.txtcity.setText(data.getGivepet_city());
        holder.txtstate.setText(data.getGivepet_state());




        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gID= givepetData.get(position).getGivepet_id().toString();
                // Toast.makeText(view.getContext(), "givepet ID"+gID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), AdoptionDetail.class);
                intent.putExtra("givepet_id", gID);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return givepetData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtbreed,txtcity,txtstate;
        private ConstraintLayout cl;
        public ViewHolder(View itemView) {
            super(itemView);

            txtbreed=(TextView)itemView.findViewById(R.id.adopt_card_breed);
            txtcity=(TextView)itemView.findViewById(R.id.adopt_card_city);
            txtstate=(TextView)itemView.findViewById(R.id.adopt_card_state);
            cl = itemView.findViewById(R.id.adopt_card_constraint);
        }
    }
}
