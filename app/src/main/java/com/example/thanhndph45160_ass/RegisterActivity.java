package com.example.thanhndph45160_ass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanhndph45160_ass.DAO.UserDAO;

public class RegisterActivity extends AppCompatActivity {

    EditText edt_RgUsername, edt_RgPassword, edt_RgEmail, edt_RgFullname, edt_RgRePassword;
    Button btnRegister;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_RgUsername = findViewById(R.id.edt_RgUsername);
        edt_RgPassword = findViewById(R.id.edt_RgPassword);
        edt_RgEmail = findViewById(R.id.edt_RgEmail);
        edt_RgFullname = findViewById(R.id.edt_RgFullname);
        edt_RgRePassword = findViewById(R.id.edt_RgRePassword);
        btnRegister = findViewById(R.id.btnRegister);

        userDAO = new UserDAO(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_RgUsername.getText().toString();
                String pass = edt_RgPassword.getText().toString();
                String repass = edt_RgRePassword.getText().toString();
                String email = edt_RgEmail.getText().toString();
                String fullname = edt_RgFullname.getText().toString();
                if(!pass.equals(repass)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = userDAO.Register(user, pass, email, fullname);
                    if (check) {
                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}