package com.nftime.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nftime.app.R;
import com.nftime.app.RecyclerItem.FanTalkItem;
import com.nftime.app.RecyclerItem.TransferContractClauseItem;

import java.util.ArrayList;

public class TransferContractClauseRecyclerAdapter extends RecyclerView.Adapter<TransferContractClauseRecyclerAdapter.ViewHolder> {
    private ArrayList<TransferContractClauseItem> transferContractClauseItems;
    private Intent intent;

    @NonNull
    @Override
    public TransferContractClauseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_transfercontract_clause, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferContractClauseRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(transferContractClauseItems.get(position));
    }

    public void setTransferContractClauseItems(ArrayList<TransferContractClauseItem> transferContractClauseItems) {
        this.transferContractClauseItems = transferContractClauseItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transferContractClauseItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView clauseTitle;
        TextView clauseDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clauseTitle = itemView.findViewById(R.id.tv_contract_clause_title);
            clauseDetail = itemView.findViewById(R.id.tv_contract_clause_detail);
        }

        void onBind(TransferContractClauseItem item) {
            clauseTitle.setText(item.getClauseTitle());
            clauseDetail.setText(item.getClauseDetail());
        }
    }
}
