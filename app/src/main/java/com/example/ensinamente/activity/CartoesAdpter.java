package com.example.ensinamente.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensinamente.R;
import com.example.ensinamente.model.FlashCards;

import java.util.List;

public class CartoesAdpter extends RecyclerView.Adapter {

    List<FlashCards> cartoes;
    private View viewOnCreate;
    TextView frente;
    TextView verso;
    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener (OnRecyclerViewClickListener listener){
          this.listener = listener;
    }


    public CartoesAdpter(List<FlashCards> cartoes) {
        this.cartoes = cartoes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartoes, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view, listener);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        FlashCards cartao = cartoes.get(position);
        vhClass.tvFrente.setText(cartao.getFrente());

    }

    @Override
    public int getItemCount() {
        return cartoes.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
           TextView tvFrente;
        public ViewHolderClass(@NonNull View itemView, OnRecyclerViewClickListener listener) {
            super(itemView);
            tvFrente = itemView.findViewById(R.id.tvFrente);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getAbsoluteAdapterPosition());
                    }
                }
            });
        }

    }


}
