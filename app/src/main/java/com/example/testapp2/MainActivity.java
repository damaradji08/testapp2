package com.example.testapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.view.*;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    com.example.testapp2.DatabaseManager dm;
    private EditText eNm, eTelp, eKode;
    private Button bBaru, bSimpan, bUbah, bHapus;
    TableLayout tabel4data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new com.example.testapp2.DatabaseManager(this);
        tabel4data = findViewById(R.id.table_data);
        eKode = findViewById(R.id.edTextKode);
        eNm = findViewById(R.id.edTextNama);
        eTelp = findViewById(R.id.edTextPhone);
        bSimpan = findViewById(R.id.btnSimpan);
        bUbah = findViewById(R.id.btnUbah);
        bHapus = findViewById(R.id.btnHapus);
        bBaru = findViewById(R.id.btnBaru);

        bSimpan.setOnClickListener(v -> simpanTable());
        bUbah.setOnClickListener(v -> ubahTable());
        bHapus.setOnClickListener(v -> hapusTable());
        bBaru.setOnClickListener(v -> kosongkanField());

        updateTable();
    }

    protected void simpanTable() {
        try {
            dm.addRow(Integer.parseInt(eKode.getText().toString()),
                    eNm.getText().toString(),
                    eTelp.getText().toString());

            Toast.makeText(getBaseContext(), eNm.getText().toString() + " berhasil disimpan",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Gagal simpan: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void ubahTable() {
        try {
            dm.UpdateRecord(Integer.parseInt(eKode.getText().toString()),
                    eNm.getText().toString(),
                    eTelp.getText().toString());

            Toast.makeText(getBaseContext(), eNm.getText().toString() + " berhasil diubah",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Gagal ubah: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void hapusTable() {
        try {
            dm.DeleteRecord(Integer.parseInt(eKode.getText().toString()));

            Toast.makeText(getBaseContext(), "Data berhasil dihapus",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Gagal hapus: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void kosongkanField() {
        eNm.setText("");
        eTelp.setText("");
        eKode.setText("");
    }

    protected void updateTable() {
        tabel4data.removeAllViews();
        ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();

        for (ArrayList<Object> baris : data) {
            TableRow tabelBaris = new TableRow(this);

            for (Object obj : baris) {
                TextView txt = new TextView(this);
                txt.setTextSize(18);
                txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txt.setText(obj.toString());
                tabelBaris.addView(txt);
            }

            tabel4data.addView(tabelBaris);
        }
    }
}
