package com.andro.itrip.registerActivity;

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

import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.R;
import com.andro.itrip.mainActivity.MainActivity;
import com.andro.itrip.loginActivity.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.ViewInterface {

    private static final String DOMAIN_NAME = "gmail.com";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+((['_. -][a-zA-Z ])?[a-zA-Z]*)*$");

    private RegisterContract.PresenterInterface registerPresenter;
    private EditText nameTxt, emailTxt, passwordTxt, confirmPasswordTxt;
    private Button registerBtn, haveAccountButton;
    private ProgressBar progressBar;
    private TextInputLayout nameLayout, emailLayout, passwordLayout, confirmLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmPasswordTxt = findViewById(R.id.confirmPassTxt);
        registerBtn = findViewById(R.id.signupButton);
        progressBar = findViewById(R.id.progressBar);
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmLayout = findViewById(R.id.confirmLayout);
        haveAccountButton = findViewById(R.id.haveAccountButton);

        registerPresenter = new RegisterPresenter(this);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initiate registration task
                if (HelpingMethods.isNetworkConnected()) {
                    if (validateName() & validateEmail() & validatePassword() & validateConfirmPassword()) {
                        registerPresenter.registerNewAccount(emailTxt.getText().toString(), passwordTxt.getText().toString());
                        showProgressBar();

                    }
                } else {
                    Toast.makeText(GlobalApplication.getAppContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();

                }
            }
        });


        haveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        hideSoftKeyboard();

    }


    /**
     * Redirects the user to the main screen
     */
    @Override
    public void redirectMainScreen() {
        hideProgressBar();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void displayError(int message) {
        hideProgressBar();
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
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

    public boolean validateConfirmPassword() {
        String confirmPassInput = confirmPasswordTxt.getText().toString().trim();
        boolean passwordMatches;
        if (confirmPassInput.isEmpty()) {
            confirmLayout.setError(getString(R.string.empty_password));
            passwordMatches = false;
        } else if (doStringsMatch(passwordTxt.getText().toString().trim(), confirmPassInput)) {
            passwordMatches = true;
            confirmLayout.setError(null);
        } else {
            passwordMatches = false;
            confirmLayout.setError(getString(R.string.password_error));


        }
        return passwordMatches;

    }

    public boolean validateName() {
        String nameInput = nameTxt.getText().toString().trim();
        boolean isValidName;

        if (nameInput.isEmpty()) {
            nameLayout.setError(getString(R.string.empty_name));
            isValidName = false;
        } else if (!NAME_PATTERN.matcher(nameInput).matches()) {
            nameLayout.setError(getString(R.string.name_error));
            isValidName = false;
        } else {
            nameLayout.setError(null);
            isValidName = true;
        }
        return isValidName;
    }

}
