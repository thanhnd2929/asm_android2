package com.example.thanhndph45160_ass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thanhndph45160_ass.DAO.TasksDAO;
import com.example.thanhndph45160_ass.DTO.TasksDTO;
import com.example.thanhndph45160_ass.Fragment.AboutFragment;
import com.example.thanhndph45160_ass.Fragment.HistoryFragment;
import com.example.thanhndph45160_ass.Fragment.TasksFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    FragmentManager fm;
    TasksFragment tasksFrag;
    AboutFragment aboutFrag;

    TasksDAO tasksDAO;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        tasksDAO = new TasksDAO(getApplicationContext());


        // ánh xạ
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);







        // set up toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("Quản lý công việc");


        // nhấn item navigation

        tasksFrag = new TasksFragment();
        aboutFrag = new AboutFragment();


        fm = getSupportFragmentManager();

        // set fragment mặc định khi chạy lên
        fm.beginTransaction()
                .replace(R.id.frag_container, tasksFrag)
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.mThoat) {
                    Intent intent = new Intent(TasksActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Kết thúc TasksActivity sau khi chuyển sang LoginActivity
                } else {
                    Fragment fragment = null;
                    if (item.getItemId() == R.id.mHome) {
                        fragment = tasksFrag;
                    } else if (item.getItemId() == R.id.mGioiThieu) {
                        fragment = aboutFrag;
                    } else {
                        fragment = tasksFrag;
                    }

                    fm.beginTransaction()
                            .replace(R.id.frag_container, fragment)
                            .commit();

                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


}