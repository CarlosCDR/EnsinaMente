package com.example.ensinamente.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensinamente.R;
import com.example.ensinamente.model.Meta;
import com.example.ensinamente.model.Tarefa;

import java.util.List;

public class MetasAdpter extends RecyclerView.Adapter {

    List<Meta> metas;

    public MetasAdpter(List<Meta> metas) {
        this.metas = metas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metas, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Meta meta = metas.get(position);
        vhClass.tvRecebeNomeTafMeta.setText(meta.getNomeTarefa());
        vhClass.tvRecebeMotivacaoMeta.setText(meta.getMotivacao());
        vhClass.tvRecebeDataMeta.setText(meta.getData());
    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView tvRecebeNomeTafMeta;
        TextView tvRecebeMotivacaoMeta;
        TextView tvRecebeDataMeta;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvRecebeNomeTafMeta = itemView.findViewById(R.id.tvNomeTarefaMeta);
            tvRecebeMotivacaoMeta = itemView.findViewById(R.id.tvMotivacao);
            tvRecebeDataMeta = itemView.findViewById(R.id.tvData);
        }
    }
}
