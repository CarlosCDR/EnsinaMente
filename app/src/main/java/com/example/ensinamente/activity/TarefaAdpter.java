package com.example.ensinamente.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.FlashCards;
import com.example.ensinamente.model.Tarefa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdpter extends RecyclerView.Adapter {


    List<Tarefa> tarefas;
    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener (OnRecyclerViewClickListener listener){
        this.listener = listener;
    }


    public TarefaAdpter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view, listener);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Tarefa tarefa = tarefas.get(position);
        vhClass.tvRecebeTarefa.setText(tarefa.getNomeTarefa());
        vhClass.tvRecebeDisciplina.setText(tarefa.getDisciplina());
        vhClass.tvRecebeMetodo.setText(tarefa.getMetodo());
        vhClass.tvRecebeCriticidade.setText(tarefa.getCriticidadeTarefa());

    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView tvRecebeTarefa;
        TextView tvRecebeDisciplina;
        TextView tvRecebeMetodo;
        TextView tvRecebeCriticidade;
        public ViewHolderClass(@NonNull View itemView, OnRecyclerViewClickListener listener) {
            super(itemView);
            tvRecebeTarefa = itemView.findViewById(R.id.tvTarefa);
            tvRecebeDisciplina = itemView.findViewById(R.id.tvDisciplina);
            tvRecebeMetodo = itemView.findViewById(R.id.tvMetodo);
            tvRecebeCriticidade = itemView.findViewById(R.id.tvCriticidade);
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
