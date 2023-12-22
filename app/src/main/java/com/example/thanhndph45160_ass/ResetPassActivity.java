package com.example.thanhndph45160_ass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanhndph45160_ass.DAO.UserDAO;

public class ResetPassActivity extends AppCompatActivity {

    EditText edt_NewPass;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        edt_NewPass = findViewById(R.id.edt_NewPass);
        Button btn_Submit = findViewById(R.id.btn_Submit);

        userDAO = new UserDAO(this);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass = edt_NewPass.getText().toString();
                String username = getIntent().getStringExtra("username_key");
                String email = getIntent().getStringExtra("email_key");

                boolean passReset = userDAO.ResetPassword(username, email, newpass);
                if (passReset) {
                    Toast.makeText(ResetPassActivity.this, "Mật khẩu đã được đặt lại", Toast.LENGTH_SHORT).show();
                    // Thực hiện chuyển hướng đến màn hình đăng nhập hoặc màn hình khác sau khi đặt lại mật khẩu thành công
                    Intent loginIntent = new Intent(ResetPassActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
//
//                     Kết thúc ResetPassActivity
                    finish();
                } else {
                    Toast.makeText(ResetPassActivity.this, "Không thể đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}