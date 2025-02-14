package com.example.tuningdetails.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuningdetails.R;
import com.example.tuningdetails.Data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegActivity extends AppCompatActivity {

    public EditText input_email, input_password, input_name;
    public Button signUpBtn;
    public TextView signInBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        input_email = findViewById(R.id.input_email);
        input_name = findViewById(R.id.input_name);
        input_password = findViewById(R.id.input_pass);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://tuningdetails-eb90a-default-rtdb.europe-west1.firebasedatabase.app");
        users = db.getReference("Users");

        signUpBtn.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(input_email.getText().toString()) && !TextUtils.isEmpty(input_password.getText().toString()) && !TextUtils.isEmpty(input_name.getText().toString())){
                mAuth.createUserWithEmailAndPassword(input_email.getText().toString(), input_password.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        User user = new User();
                        user.setEmail(input_email.getText().toString());
                        user.setName(input_name.getText().toString());
                        user.setPassword(input_password.getText().toString());
                        users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user);
                        Toast.makeText(RegActivity.this, "Регистрация пройдена", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(RegActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });

        signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RegActivity.this, LogActivity.class);
            startActivity(intent);
        });
    }
}