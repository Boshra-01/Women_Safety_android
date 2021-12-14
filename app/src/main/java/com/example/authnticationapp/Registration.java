package com.example.authnticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText Fullname, Email, Password, Phone;
     Button RegistrationBtn;
     TextView Loginbtn;
     FirebaseAuth fAuth;
     ProgressBar progressBar;
     String UserID;
     FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Fullname= findViewById(R.id.nametxt);
        Email= findViewById(R.id.email);
        Password= findViewById(R.id.password);
        Phone= findViewById(R.id.phone_no);
        RegistrationBtn= findViewById(R.id.register_btn);
        Loginbtn = findViewById(R.id.create_textLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        if (fAuth.getCurrentUser()!= null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
           finish();
        }

    RegistrationBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String email = Email.getText().toString().trim();
           final String fullname = Fullname.getText().toString();
           final String phone = Phone.getText().toString();
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
            // registration through firebase
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Registration.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                        UserID= fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("Users").document("UserID");
                        Map<String,Object> User = new HashMap<>();
                        User.put("full_Name", fullname);
                        User.put("email", email);
                        User.put("phoneno", phone);
                        documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: User profile has been created for "+ UserID );
                            }
                        });

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                        {
                        Toast.makeText(Registration.this, "Failed to register!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
            }

        }
    );
    }


});
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });
 }
}
