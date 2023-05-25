package com.foodykey.food.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.foodykey.food.local.DataStoreManager;
import com.foodykey.food.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.foodykey.food.R;
import com.foodykey.food.utils.StringUtil;
import com.foodykey.food.utils.GlobalFuntion;

public class SignInActivity extends BaseActivity {


    private EditText edtEmail,edtPassword;
    private Button btnSignIn;
    private LinearLayout btnSignUp, ll_forget_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUi();
        initListener();
    }
    private void initUi(){
        btnSignUp = findViewById(R.id.layout_sign_up);
        btnSignIn = findViewById(R.id.btn_sign_in);
        edtEmail = findViewById(R.id.si_email);
        edtPassword = findViewById(R.id.si_password);
        ll_forget_password = findViewById(R.id.ll_forget_password);
    }

    private void initListener() {
        btnSignUp.setOnClickListener(
                v -> GlobalFuntion.startActivity(SignInActivity.this, SignUpActivity.class));
        btnSignIn.setOnClickListener(v -> onClickValidateSignIn());
        ll_forget_password.setOnClickListener(v-> GlobalFuntion.startActivity(SignInActivity.this, ForgotPasswordActivity.class));
    }
    private void onClickValidateSignIn() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            signInUser(strEmail, strPassword);
        }
    }
    private void signInUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);
                            DataStoreManager.setUser(userObject);
                            GlobalFuntion.startActivity(SignInActivity.this, MainActivity.class);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, getString(R.string.msg_sign_in_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}