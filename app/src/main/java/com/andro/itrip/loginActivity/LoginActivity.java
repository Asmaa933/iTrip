package com.andro.itrip.loginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andro.itrip.SavedPreferences;
import com.andro.itrip.mainActivity.MainActivity;
import com.andro.itrip.R;
import com.andro.itrip.registerActivity.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;



public class LoginActivity extends AppCompatActivity implements LoginContract.ViewInterface {

    LoginContract.PresenterInterface loginPresenter;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,}$");
    private GoogleSignInClient googleSignInBtn;


    // widgets
    private EditText emailTxt, passwordTxt;
    private ProgressBar progressBar;
    private Button signInBtn;
    private Button signUpBtn;
    private TextInputLayout emailLayout, passwordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SavedPreferences.getInstance().readUserID() != "")
        {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        progressBar = findViewById(R.id.progressBar);
        signInBtn = findViewById(R.id.haveAccountButton);
        signUpBtn = findViewById(R.id.createAccountButton);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        loginPresenter = new LoginPresenter(this);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                if (validateEmail() & validatePassword()) {
                    loginPresenter.verifyCredentials(email, password, LoginActivity.this);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        hideSoftKeyboard();

    }


    @Override
    public void displayError(int message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccessful() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public boolean validateEmail() {
        String emailInput = emailTxt.getText().toString().trim();
        boolean isValidEmail;
        if (emailInput.isEmpty()) {
            emailLayout.setError(getString(R.string.empty_email));
            isValidEmail = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailLayout.setError(getString(R.string.invalid_email));
            isValidEmail = false;
        } else {
            emailLayout.setError(null);
            isValidEmail = true;
        }
        return isValidEmail;

    }

    public boolean validatePassword() {
        String passwordInput = passwordTxt.getText().toString().trim();
        boolean isValidPassword;

        if (passwordInput.isEmpty()) {
            passwordLayout.setError(getString(R.string.empty_password));
            isValidPassword = false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordLayout.setError(getString(R.string.weak_password));
            isValidPassword = false;
        } else {
            passwordLayout.setError(null);
            isValidPassword = true;
        }
        return isValidPassword;
    }
    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
