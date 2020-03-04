package com.andro.itrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String DOMAIN_NAME = "gmail.com";

    private EditText nameTxt, emailTxt, passwordTxt, confirmPasswordTxt;
    private Button registerBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPassTxt);
        registerBtn = (Button) findViewById(R.id.signupButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(nameTxt.getText().toString())
                        && !isEmpty(emailTxt.getText().toString())
                        && !isEmpty(passwordTxt.getText().toString())
                        && !isEmpty(confirmPasswordTxt.getText().toString())){

                    //check if user has a company email address

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!(emailTxt.getText().toString().matches(emailPattern))) {
                        emailTxt.setError("Invalid email address");
                        return;
                    }
                        //check if passwords match
                        if(doStringsMatch(passwordTxt.getText().toString(), confirmPasswordTxt.getText().toString())){

                            //Initiate registration task
                            registerNewEmail(emailTxt.getText().toString(), passwordTxt.getText().toString());

                        }else{
                            Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideSoftKeyboard();

    }

    public void registerNewEmail(final String email, String password){

        showDialog();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            //redirect the user to the login screen

                            String user_id=auth.getCurrentUser().getUid();

                            redirectLoginScreen(user_id);
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();

                        // ...
                    }
                });
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(String s){

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("userId",s);
        startActivity(intent);
        finish();
    }

    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    private boolean isEmpty(String string){
        return string.trim().equals("");
    }


    private void showDialog(){
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    // start asmaa


    //end asmaaa


    // start ramzy


    //end ramzy


    // start aboelnaga


    //end aboelnaga
}
