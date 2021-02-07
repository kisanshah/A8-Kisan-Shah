package com.example.resumebuilder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.resumebuilder.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends AndroidViewModel {

    AuthRepository authRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public MutableLiveData<FirebaseUser> getLiveUser() {
        return authRepository.getUserLiveData();
    }

    public MutableLiveData<Boolean> userSignOutState() {
        return authRepository.getUserSignOutState();
    }

    public MutableLiveData<Boolean> userSignUpState() {
        return authRepository.getUserSignUpState();
    }

    public MutableLiveData<Boolean> userResetState() {
        return authRepository.getUserResetState();
    }

    public void signIn(String email, String pass) {
        authRepository.signIn(email, pass);
    }

    public void signUp(String email, String pass) {
        authRepository.signUp(email, pass);
    }

    public void signOut() {
        authRepository.signOut();
    }

    public void reset(String email) {
        authRepository.reset(email);
    }
}
