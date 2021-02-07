package com.example.resumebuilder.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.resumebuilder.R;
import com.example.resumebuilder.viewmodel.MainViewModel;

public class ResetActivity extends AppCompatActivity {
    EditText email;
    Button resetBtn;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        email = findViewById(R.id.email);
        resetBtn = findViewById(R.id.reset);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);

        viewModel.userResetState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean reset) {
                if (reset) {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(ResetActivity.this, "Field Cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.reset(email.getText().toString());
                }
            }
        });
    }
}