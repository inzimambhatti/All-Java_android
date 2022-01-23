package com.example.storeqrscanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeqrscanner.Home2;
import com.example.storeqrscanner.R;
import com.example.storeqrscanner.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView btnLogin,btnForget,btnSIgnup;
    private EditText etEmail, etPassword;
    private DatabaseReference refernce;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnForget = findViewById(R.id.btnForget);
        btnSIgnup = findViewById(R.id.btnSignUp);



//        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//        Boolean currentLoginState = preferences.getBoolean("saveLogin",false);
//        Log.i("SLOG", "currentLoginState : " + currentLoginState);
//        if (currentLoginState){
//            startActivity(new Intent(LoginActivity.this, Home2.class));
//            finish();
//        }

        btnLogin.setOnClickListener(v -> userLogin());
        btnForget.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class)));
        btnSIgnup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,Register.class)));

    }



    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()){
            etEmail.setError(("Email is required"));
            etEmail.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Provide valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        assert user != null;
                        if(user.isEmailVerified()) {
                            refernce = FirebaseDatabase.getInstance().getReference("Users");
                            userId = user.getUid();
                            refernce.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userProfile = snapshot.getValue(User.class);
                                    if (userProfile != null) {
                                        if (userProfile.userRoles.isManager) {

                                            SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putBoolean("isManager", true);
                                            editor.apply();
                                            startActivity(new Intent(LoginActivity.this, Home2.class));
                                            finish();
                                        } else {
                                            startHome();
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else{
                            user.sendEmailVerification();
                            Toast.makeText(LoginActivity.this, "Check your email to verify your email.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
               .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startHome(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}