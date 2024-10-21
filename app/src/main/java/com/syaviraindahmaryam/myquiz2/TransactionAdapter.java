package com.syaviraindahmaryam.myquiz2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> transactions;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        }

        // Ambil transaksi berdasarkan posisi
        Transaction transaction = transactions.get(position);

        // Hubungkan data ke view
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);

        // Set data ke TextView
        tvDate.setText(transaction.getDate());
        tvAmount.setText("Rp. " + transaction.getAmount());
        tvDescription.setText(transaction.getDescription());

        return convertView;
    }
}