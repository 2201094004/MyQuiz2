package com.syaviraindahmaryam.myquiz2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private Button btnDate;
    private EditText etAmount, etDescription;
    private RadioGroup rgCategory;
    private String selectedDate;
    private Transaction transaction;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnDate = findViewById(R.id.btnDate);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        rgCategory = findViewById(R.id.rgCategory);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        btnDate.setOnClickListener(v -> showDatePickerDialog());

        // Ambil data dari intent
        Intent intent = getIntent();
        if (intent.hasExtra("transaction")) {
            transaction = (Transaction) intent.getSerializableExtra("transaction");
            position = intent.getIntExtra("position", -1);
            populateFields(transaction);
        }

        // Event listener untuk tombol Simpan
        btnSave.setOnClickListener(v -> saveTransaction());

        // Event listener untuk tombol Hapus
        btnDelete.setOnClickListener(v -> deleteTransaction());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, day1) -> {
            month1 += 1;
            selectedDate = String.format("%02d/%02d/%04d", day1, month1, year1);
            btnDate.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void populateFields(Transaction transaction) {
        selectedDate = transaction.getDate();
        btnDate.setText(selectedDate);
        etAmount.setText(String.valueOf(transaction.getAmount()));
        etDescription.setText(transaction.getDescription());
        rgCategory.check(transaction.isIncome() ? R.id.rbIncome : R.id.rbExpense);
    }

    private void saveTransaction() {
        int amount = Integer.parseInt(etAmount.getText().toString());
        String description = etDescription.getText().toString();
        boolean isIncome = rgCategory.getCheckedRadioButtonId() == R.id.rbIncome;
        Transaction updatedTransaction = new Transaction(selectedDate, amount, description, isIncome);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedTransaction", updatedTransaction);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void deleteTransaction() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("deletedTransaction", transaction);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
