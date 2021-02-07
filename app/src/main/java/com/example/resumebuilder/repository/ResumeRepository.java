package com.example.resumebuilder.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResumeRepository {
    private final FirebaseAuth auth;
    private final FirebaseStorage storage;
    MutableLiveData<Boolean> uploadState;

    public ResumeRepository(Application application) {
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        uploadState = new MutableLiveData<>();
    }

    void uploadFile(String path) throws FileNotFoundException {
        StorageReference reference = storage.getReference();
        InputStream stream = new FileInputStream(new File(path));
        UploadTask uploadTask = reference.putStream(stream);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                uploadState.setValue(task.isSuccessful());
            }
        });
    }
}
