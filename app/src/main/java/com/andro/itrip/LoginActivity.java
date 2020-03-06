package com.andro.itrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//Ramzy id
//OdruFKPDG1QxGuZy2cZxE408IYI3

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;

    // widgets
    private EditText emailTxt, passwordTxt;
    private ProgressBar progressBar;
    private Button signInBtn;
    private Button signUpBtn;
    private GoogleSignInClient googleSignInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        progressBar = findViewById(R.id.progressBar);
        signInBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.createAccountButton);

        auth = FirebaseAuth.getInstance();
        /*
        if (auth.getCurrentUser() != null) {
            String user_id=auth.getCurrentUser().getUid();


            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userId",user_id);

            startActivity(intent);
            finish();
        }*/

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //intent.putExtra("userId",);
                startActivity(intent);
                finish();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                final String password = passwordTxt.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
//authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {

                                    Toast.makeText(LoginActivity.this, "Faild", Toast.LENGTH_LONG).show();
                                } else {
                                    String user_id=auth.getCurrentUser().getUid();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userId",user_id);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    // start asmaa


    //end asmaaa


    // start ramzy


    //end ramzy


    // start aboelnaga


    //end aboelnaga
}
