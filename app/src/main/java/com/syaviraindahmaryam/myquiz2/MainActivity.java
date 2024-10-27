package com.syaviraindahmaryam.myquiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvSaldo;
    private ListView lvTransactions;
    private TransactionAdapter adapter;
    private ArrayList<Transaction> transactionList;
    private int totalSaldo = 170000;  // Inisialisasi saldo awal
    private static final int REQUEST_ADD_TRANSACTION = 1;
    private static final int REQUEST_EDIT_TRANSACTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSaldo = findViewById(R.id.tvSaldo);
        lvTransactions = findViewById(R.id.lvTransactions);
        FloatingActionButton fabAddTransaction = findViewById(R.id.fabAddTransaction);

        // Inisialisasi list transaksi dan adapter
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactionList);
        lvTransactions.setAdapter(adapter);

        tvSaldo.setText("Saldo: Rp " + totalSaldo);

        // Klik untuk menambah transaksi
        fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            startActivityForResult(intent, REQUEST_ADD_TRANSACTION);
        });

        // Klik item untuk edit atau delete
        lvTransactions.setOnItemClickListener((parent, view, position, id) -> {
            Transaction transaction = transactionList.get(position);
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            intent.putExtra("transaction", transaction);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_EDIT_TRANSACTION);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD_TRANSACTION) {
                Transaction newTransaction = (Transaction) data.getSerializableExtra("newTransaction");
                transactionList.add(newTransaction);
                updateSaldo(newTransaction);
            } else if (requestCode == REQUEST_EDIT_TRANSACTION) {
                Transaction updatedTransaction = (Transaction) data.getSerializableExtra("updatedTransaction");
                int position = data.getIntExtra("position", -1);
                Transaction oldTransaction = transactionList.set(position, updatedTransaction);
                adjustSaldoAfterEdit(oldTransaction, updatedTransaction);
            } else if (requestCode == REQUEST_EDIT_TRANSACTION && data.hasExtra("deletedTransaction")) {
                Transaction deletedTransaction = (Transaction) data.getSerializableExtra("deletedTransaction");
                int position = data.getIntExtra("position", -1);
                transactionList.remove(position);
                adjustSaldoAfterDelete(deletedTransaction);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void updateSaldo(Transaction transaction) {
        totalSaldo += transaction.isIncome() ? transaction.getAmount() : -transaction.getAmount();
        tvSaldo.setText("Saldo: Rp " + totalSaldo);
    }

    private void adjustSaldoAfterEdit(Transaction oldTransaction, Transaction newTransaction) {
        totalSaldo += newTransaction.isIncome() ? newTransaction.getAmount() : -newTransaction.getAmount();
        totalSaldo -= oldTransaction.isIncome() ? oldTransaction.getAmount() : -oldTransaction.getAmount();
        tvSaldo.setText("Saldo: Rp " + totalSaldo);
    }

    private void adjustSaldoAfterDelete(Transaction transaction) {
        totalSaldo -= transaction.isIncome() ? transaction.getAmount() : -transaction.getAmount();
        tvSaldo.setText("Saldo: Rp " + totalSaldo);
    }
}
