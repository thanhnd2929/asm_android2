package com.example.thanhndph45160_ass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhndph45160_ass.DAO.UserDAO;
import com.example.thanhndph45160_ass.DbHelper.MyDbHelper;
import com.example.thanhndph45160_ass.Notification.NotifyConfig;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txt_forgot, txt_signup;

    UserDAO userDAO;
    boolean isRegisterDialogShow = false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txt_forgot = findViewById(R.id.txt_forgot);
        txt_signup = findViewById(R.id.txt_Signup);
        userDAO = new UserDAO(this);




        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Đặt biến cờ để kiểm tra xem thông báo đã được hiển thị hay chưa


       if (!isRegisterDialogShow) {
           AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
           builder.setTitle("Thông báo");
           builder.setMessage("Vui lòng đăng kí tài khoản");
           builder.setCancelable(false);
           builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   isRegisterDialogShow = true;
                   Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                   startActivity(intent);

               }
           });
           builder.setNegativeButton("Không đồng ý ", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {

               }
           });
           AlertDialog dialog = builder.create();
           dialog.show();

       }





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();

                boolean check = userDAO.checkLogin(user, pass);
                if (check==true) {
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, TasksActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    NotificationUtils.GuiThongBao(LoginActivity.this, "Thông Báo", "Bạn đã đăng nhập thành công");
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

       txt_forgot.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                showDialogForgot();
           }
       });


    }



    private void showDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_forgot, null);
//        View v = getLayoutInflater().inflate(R.layout.dialog_forgot, null);

        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edt_FgEmail = v.findViewById(R.id.edt_FgEmail);
        EditText edt_FgUsername = v.findViewById(R.id.edt_FgUsername);
        Button btnSend = v.findViewById(R.id.btn_FgSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_FgEmail.getText().toString();
                String username = edt_FgUsername.getText().toString();
                String matkhau = userDAO.ForgotPassword(username, email);
                if (matkhau.equals("")) {
                    Toast.makeText(LoginActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, ResetPassActivity.class);
                    intent.putExtra("username_key", username);
                    intent.putExtra("email_key", email);
                    startActivity(intent);
                }
            }
        });
    }
}