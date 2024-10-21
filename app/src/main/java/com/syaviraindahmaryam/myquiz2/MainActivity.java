package com.syaviraindahmaryam.myquiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view dari layout
        tvSaldo = findViewById(R.id.tvSaldo);
        lvTransactions = findViewById(R.id.lvTransactions);
        FloatingActionButton fabAddTransaction = findViewById(R.id.fabAddTransaction);
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                startActivity(intent);
            }
        });


        // Inisialisasi list transaksi dan adapter
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactionList);
        lvTransactions.setAdapter(adapter);

        // Tampilkan saldo awal
        tvSaldo.setText("Saldo: Rp " + totalSaldo);

        // Handle klik FloatingActionButton untuk menambah transaksi
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mulai TambahActivity untuk menambah transaksi baru
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    // Menangani hasil dari TambahActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Dapatkan transaksi baru dari TambahActivity
            Transaction newTransaction = (Transaction) data.getSerializableExtra("newTransaction");
            // Tambahkan transaksi baru ke dalam list
            transactionList.add(newTransaction);
            adapter.notifyDataSetChanged();
            // Update saldo sesuai dengan transaksi baru
            updateSaldo(newTransaction);
        }
    }

    // Metode untuk mengupdate saldo setelah transaksi baru ditambahkan
    private void updateSaldo(Transaction transaction) {
        if (transaction.isIncome()) {
            totalSaldo += transaction.getAmount();  // Tambah saldo jika pemasukan
        } else {
            totalSaldo -= transaction.getAmount();  // Kurangi saldo jika pengeluaran
        }
        // Update tampilan saldo
        tvSaldo.setText("Saldo: Rp " + totalSaldo);
    }
}