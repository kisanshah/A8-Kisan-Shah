package com.example.resumebuilder.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resumebuilder.R;

import java.util.ArrayList;

public class ExportActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        spinner = findViewById(R.id.spinner);
        ArrayList<String> docType = new ArrayList<>();
        docType.add("pdf");
        docType.add("jpg");
        docType.add("docx");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, docType);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }
}