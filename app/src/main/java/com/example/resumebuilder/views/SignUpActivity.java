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

public class SignUpActivity extends AppCompatActivity {
    TextView signInBtn;
    Button signUpBtn;
    EditText email, pass1, pass2;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        pass1 = findViewById(R.id.password);
        pass2 = findViewById(R.id.password2);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);


        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);
        viewModel.userSignUpState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean signedUp) {
                if (signedUp) {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(email.getText().toString(), pass1.getText().toString(), pass2.getText().toString())) {
                    viewModel.signUp(email.getText().toString(), pass1.getText().toString());
                }
            }
        });


    }

    private boolean validate(String email, String p1, String p2) {
        if (email.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show();
        } else if (!p2.equals(p1)) {
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
        } else if (p2.length() < 8 || p1.length() < 8) {
            Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }
}