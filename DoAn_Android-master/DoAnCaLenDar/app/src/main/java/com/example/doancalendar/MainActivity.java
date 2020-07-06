package com.example.doancalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String MSSV;
    EditText editText;
    Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initC();
    }
    public void initC(){
        editText = findViewById(R.id.edtMSSV);
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MSSV=editText.getText().toString();
                if(MSSV.equals("") || MSSV.length()!=7)
                {
                    Toast.makeText(getBaseContext(), "Vui lòng nhập mã số sinh viên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    {
                    Intent it = new Intent(MainActivity.this,DocDuLieuTKB.class);
                    startActivity(it);
                    finish();
                    }
            }
        });
    }
}