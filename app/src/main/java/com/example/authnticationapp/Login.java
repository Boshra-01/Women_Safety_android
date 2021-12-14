package com.example.authnticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText Email, Password ;
    Button Loginbtn;
    Button registerHerrbtn ;
    TextView Forget_password;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    Email = findViewById(R.id.email);
    Password= findViewById(R.id.password);
    Forget_password= findViewById(R.id.forgot_password_txt);
    progressBar = findViewById(R.id.progressBar);
    registerHerrbtn = findViewById(R.id.create_textLogin);
    fAuth= FirebaseAuth.getInstance();
    Loginbtn= findViewById(R.id.login_btn);
    Loginbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                Email.setError("Email is Required");
                return;
            }
            if (TextUtils.isEmpty(password)){
                Password.setError("Password is Required");
                return;
            }
            if ( password.length()<6 ){
                Password.setError("Password must be >=6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Authentication

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else {

                        Toast.makeText(Login.this, "Failed to register!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });


           }
       });
        registerHerrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
        }
    });
        Forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialogue = new AlertDialog.Builder(v.getContext());
                passwordResetDialogue.setTitle("Reset Password ?");
                passwordResetDialogue.setMessage("Enter your Email to reset Password");
                passwordResetDialogue.setView(resetMail);
                passwordResetDialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String mail = resetMail.getText().toString();
                       fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(Login.this, "Password reset link has been sent to your Email", Toast.LENGTH_SHORT).show();

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Login.this, "Unable to reset Password"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });

                    }
                });
                passwordResetDialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialogue.create().show();
            }
        });
    }
}
