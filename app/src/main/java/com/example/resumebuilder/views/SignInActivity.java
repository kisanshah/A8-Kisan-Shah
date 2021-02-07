package com.example.resumebuilder.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.resumebuilder.R;
import com.example.resumebuilder.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    TextView resetBtn, signUpBtn;
    Button signInBtn, skipBtn;
    MainViewModel viewModel;
    EditText password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        skipBtn = findViewById(R.id.skipBtn);
        resetBtn = findViewById(R.id.resetBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);
        viewModel.getLiveUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                    goToMainActivity();
                }
            }
        });


        signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate(email.getText().toString(), password.getText().toString())) {
                            viewModel.signIn(email.getText().toString(), password.getText().toString());
                        }
                    }
                });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });


    }

    private boolean validate(String email, String pass) {
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));

    }
}