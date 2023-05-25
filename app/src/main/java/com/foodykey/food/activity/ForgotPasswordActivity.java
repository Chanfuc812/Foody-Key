package com.foodykey.food.activity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.foodykey.food.R;
import com.foodykey.food.utils.StringUtil;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText edtEmail;
    private Button btnResetPassword;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        imgBack = findViewById(R.id.img_back);
    }

    private void initListener() {
        imgBack.setOnClickListener(v -> onBackPressed());
        btnResetPassword.setOnClickListener(v -> onClickValidateResetPassword());
    }

    private void onClickValidateResetPassword() {
        String strEmail = edtEmail.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            resetPassword(strEmail);
        }
    }

    private void resetPassword(String email) {
        showProgressDialog(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this,
                                getString(R.string.msg_reset_password_successfully),
                                Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                    }
                });
    }
}