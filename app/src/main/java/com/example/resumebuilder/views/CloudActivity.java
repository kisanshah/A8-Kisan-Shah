package com.example.resumebuilder.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resumebuilder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CloudActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FloatingActionButton fab;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
        auth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.selectFile);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        ref = storage.getReference();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    Toast.makeText(CloudActivity.this, "You are not registered to upload", Toast.LENGTH_SHORT).show();
                } else {
                    chooseFile();
                }
            }
        });

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String random = UUID.randomUUID().toString();
        StorageReference storeRef = ref.child(userUid).child(random);

        storeRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CloudActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CloudActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}