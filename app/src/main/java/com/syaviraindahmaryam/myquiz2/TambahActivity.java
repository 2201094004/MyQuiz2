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

public class TambahActivity extends AppCompatActivity {

    private Button btnDate; // Mengganti TextView dengan Button untuk memilih tanggal
    private EditText etAmount, etDescription;
    private RadioGroup rgCategory;
    private String selectedDate; // Menyimpan tanggal yang dipilih

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        btnDate = findViewById(R.id.btnDate);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        rgCategory = findViewById(R.id.rgCategory);
        Button btnSave = findViewById(R.id.btnSave);

        // Menangani klik pada Button untuk memilih tanggal
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Menangani klik tombol Simpan
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(etAmount.getText().toString());
                String description = etDescription.getText().toString();
                boolean isIncome = rgCategory.getCheckedRadioButtonId() == R.id.rbIncome;

                Transaction transaction = new Transaction(selectedDate, amount, description, isIncome);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("newTransaction", transaction);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Mengatur format bulan dan hari agar terlihat benar
            selectedMonth += 1; // Bulan dimulai dari 0
            selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth, selectedYear);
            btnDate.setText(selectedDate); // Tampilkan tanggal yang dipilih di Button
        }, year, month, day);

        datePickerDialog.show();
    }
}