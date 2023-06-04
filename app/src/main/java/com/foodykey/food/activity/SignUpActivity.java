package com.foodykey.food.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.foodykey.food.local.DataStoreManager;
import com.foodykey.food.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.foodykey.food.R;
import com.foodykey.food.utils.GlobalFuntion;
import com.foodykey.food.utils.StringUtil;

public class SignUpActivity extends BaseActivity {

    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private ImageView imageBack;
    private LinearLayout layoutSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();
        initListener();
    }

    private void initUi(){
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_sign_in);
        imageBack = findViewById(R.id.img_back);
        layoutSignIn = findViewById(R.id.layout_sign_in);
        edtConfirmPassword =findViewById(R.id.Edt_confirm_password);
    }
    private void initListener(){
        layoutSignIn.setOnClickListener(v -> finish());
        imageBack.setOnClickListener(v -> onBackPressed());
        btnSignUp.setOnClickListener(v -> onClickValidateSignUp());
    }
    private void onClickValidateSignUp() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strConfirmPassword = edtConfirmPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strConfirmPassword)){
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_confirm_password_require), Toast.LENGTH_SHORT).show();
        }else if (!strPassword.equals(strConfirmPassword)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_confirm_password), Toast.LENGTH_SHORT).show();
        }
        else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            signUpUser(strEmail, strPassword);
        }
    }

    private void signUpUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);
                            DataStoreManager.setUser(userObject);
                            GlobalFuntion.startActivity(SignUpActivity.this, SignInActivity.class);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, getString(R.string.msg_sign_up_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}