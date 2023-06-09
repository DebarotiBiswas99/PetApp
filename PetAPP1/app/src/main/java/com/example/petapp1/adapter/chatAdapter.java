package com.example.petapp1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petapp1.R;
import com.example.petapp1.models.chat_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private FirebaseAuth mAuth;

    private List<chat_model> chatData;
    public chatAdapter(List<chat_model> chatData) {
        this.chatData =chatData;
    }
    FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public chatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == MSG_TYPE_RIGHT)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatui, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatui_left, parent, false);

        return new chatAdapter.ViewHolder(view);


    }
    @Override
    public int getItemViewType(int position) {
        try {
            if (chatData.get(position).getSender_id().equals(user.getUid())) {
                return MSG_TYPE_RIGHT;
            } else {
                return MSG_TYPE_LEFT;
            }
        }catch (Exception e){
            return 0;
        }

    }



    @Override
    public void onBindViewHolder(chatAdapter.ViewHolder holder, int position) {
        chat_model data=chatData.get(position);

        try {
            if (data.sender_id.equals(user.getUid())) {
                holder.txtmsg.setText(data.getMessage());
                holder.txtdate.setText(data.getTime_stamp());
            } else {
                holder.txtname.setText(data.getName());
                holder.txtmsg.setText(data.getMessage());
                holder.txtdate.setText(data.getTime_stamp());
            }
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname,txtmsg, txtdate;
        private CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname=(TextView)itemView.findViewById(R.id.chat_name);
            txtmsg=(TextView)itemView.findViewById(R.id.chat_msg);
            txtdate = (TextView)itemView.findViewById(R.id.chat_time);
            cv = (CardView)itemView.findViewById(R.id.chat_card);



        }
    }
}
