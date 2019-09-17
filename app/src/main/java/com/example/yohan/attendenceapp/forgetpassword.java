package com.example.yohan.attendenceapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
    private TextInputLayout resetpassword;
    private Button resetButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        mAuth = FirebaseAuth.getInstance();

        resetpassword = findViewById(R.id.reset_Email);
        resetButton = findViewById(R.id.btnresetPassword1);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = resetpassword.getEditText().getText().toString();
                resetPassword(email);

            }
        });


    }

    public void resetPassword(String email){
        final ProgressDialog progressDialog = new ProgressDialog(forgetpassword.this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                            Toast.LENGTH_SHORT).show();
                    resetpassword.getEditText().setText("");
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Email don't exist", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
