package com.example.thanhndph45160_ass.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhndph45160_ass.Adapter.SpinnerAdapter;
import com.example.thanhndph45160_ass.Adapter.TaskAdapter;
import com.example.thanhndph45160_ass.DAO.TasksDAO;
import com.example.thanhndph45160_ass.DTO.StatusDTO;
import com.example.thanhndph45160_ass.DTO.TasksDTO;
import com.example.thanhndph45160_ass.NotificationUtils;
import com.example.thanhndph45160_ass.R;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    RecyclerView rv_task;
    ImageView img_ButtonAdd;

    TaskAdapter taskAdapter;
    TasksDAO tasksDAO;

    ArrayList<TasksDTO> listTask;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_task = view.findViewById(R.id.rv_tasks);
        img_ButtonAdd = view.findViewById(R.id.img_ButtonAdd);
        tasksDAO = new TasksDAO(getContext());
        loadData();


        img_ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddTask();
            }
        });

    }

    public void loadData() {
        ArrayList<TasksDTO> listTask = tasksDAO.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_task.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(getContext(), listTask);
        taskAdapter.notifyDataSetChanged();
        rv_task.setAdapter(taskAdapter);
    }

    public void showDialogAddTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        EditText edt_DialogAdd_Name = view.findViewById(R.id.edt_DialogAdd_Name);
        EditText edt_DialogAdd_Content = view.findViewById(R.id.edt_DialogAdd_Content);
        EditText edt_DialogAdd_Start = view.findViewById(R.id.edt_DialogAdd_Start);
        EditText edt_DialogAdd_End = view.findViewById(R.id.edt_DialogAdd_End);
        EditText edt_DialogAdd_UserID = view.findViewById(R.id.edt_DialogAdd_UserID);
        Spinner SP_DialogAdd_Status = view.findViewById(R.id.SP_DialogAdd_Status);
        Button btn_DialogAdd_Add = view.findViewById(R.id.btn_DialogAdd_Add);
        Button btn_DialogAdd_Cancel = view.findViewById(R.id.btn_DialogAdd_Cancel);

        tasksDAO = new TasksDAO(getContext());
        listTask = new ArrayList<>();

        // hiển thị dữ liệu lên spinner
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter();
        SP_DialogAdd_Status.setAdapter(spinnerAdapter);


        btn_DialogAdd_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_DialogAdd_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_DialogAdd_Name.getText().toString();
                String content = edt_DialogAdd_Content.getText().toString();
                String start = edt_DialogAdd_Start.getText().toString();
                String end = edt_DialogAdd_End.getText().toString();
                String UserId = edt_DialogAdd_UserID.getText().toString();

                StatusDTO statusDTO = (StatusDTO) SP_DialogAdd_Status.getSelectedItem();
                int status = statusDTO.getStatus();


                if (name.isEmpty() || content.isEmpty()|| start.isEmpty()|| end.isEmpty()|| UserId.isEmpty()) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int id_moi = tasksDAO.addRow(new TasksDTO(name, content, status, start, end, Integer.parseInt(UserId)));
                    if (id_moi > 0) {
                        loadData();
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        NotificationUtils.GuiThongBao(getContext(), "Thông Báo", "Thêm Thành Công");
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }
}
