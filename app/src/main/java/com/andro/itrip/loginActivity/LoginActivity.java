package com.andro.itrip.loginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.SavedPreferences;
import com.andro.itrip.User;
import com.andro.itrip.mainActivity.MainActivity;
import com.andro.itrip.R;
import com.andro.itrip.registerActivity.RegisterActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

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
    private GoogleSignInButton googlebtn;
    private TextInputLayout emailLayout, passwordLayout;
    private GoogleSignInOptions gso;

    private static final int RC_SIGN_IN = 2;
    private GoogleApiClient mGoogleSignInClient;


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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // Toast.makeText(getActivity(), connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        progressBar = findViewById(R.id.progressBar);
        signInBtn = findViewById(R.id.haveAccountButton);
        signUpBtn = findViewById(R.id.createAccountButton);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        googlebtn = findViewById(R.id.googleButton);

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
                if(HelpingMethods.isNetworkConnected()){

                    String email = emailTxt.getText().toString().trim();
                    String password = passwordTxt.getText().toString().trim();
                    if (validateEmail() & validatePassword()) {
                        loginPresenter.verifyCredentials(email, password, LoginActivity.this);
                        progressBar.setVisibility(View.VISIBLE);
                        enableViews(false);
                    }
                    }
                else {
                    enableViews(true);
                    Toast.makeText(GlobalApplication.getAppContext(),getString(R.string.check_internet),Toast.LENGTH_LONG).show();
                }
                }
            });

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        hideSoftKeyboard();

    }


    @Override
    public void displayError(int message) {
        progressBar.setVisibility(View.GONE);
        enableViews(true);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccessful() {
        progressBar.setVisibility(View.GONE);
        enableViews(true);
        //SavedPreferences.getInstance().writeLoginEmail(emailTxt.getText().toString().trim());
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

    public void enableViews(boolean flag){
        emailTxt.setEnabled(flag);
       // emailTxt.setFocusable(flag);

        passwordTxt.setEnabled(flag);
       // passwordTxt.setFocusable(flag);
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){

                GoogleSignInAccount account=result.getSignInAccount();
                progressBar.setVisibility(View.VISIBLE);
                enableViews(false);
                firebaseAuthWithGoogle(account);
            }else {
                enableViews(true);
                // Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            String userId = user.getUid();
                            String username = user.getDisplayName();
                            String email = user.getEmail();
                            User googleUser = new User(userId,username,email);
                            FireBaseHandler.getInstance().addUser(googleUser);


                            progressBar.setVisibility(View.GONE);
                            enableViews(true);

                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            enableViews(true);
                            Toast.makeText(LoginActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();

                        }
                        // ...
                    }
                });
    }
}
