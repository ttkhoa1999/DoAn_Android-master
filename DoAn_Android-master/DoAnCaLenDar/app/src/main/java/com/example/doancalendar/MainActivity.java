package com.example.doancalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    @SuppressLint("WrongConstant")
    public void initC(){
        editText = findViewById(R.id.edtMSSV);
        //if (this.KiemTraTonTaiFileLuuTru()) {
      //      this.ChuyenIntent(0);
      //  } else {
        boolean ret = ConnectionReceiver.isConnected(this.getBaseContext());
        btnDangNhap=findViewById(R.id.btnDangNhap);
        if (ret) {
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
        else {
            this.btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View v) {
                    boolean ret = ConnectionReceiver.isConnected(MainActivity.this.getBaseContext());
                    if (!ret) {
                        Toast.makeText(MainActivity.this.getBaseContext(), "Không có kết nối dữ liệu.", 0).show();
                    } else {
                       // initC();
                    }

                }
            });
            Toast.makeText(this.getBaseContext(), "Không có kết nối dữ liệu.", 0).show();
        }

        }
  //  }
    /*private boolean KiemTraTonTaiFileLuuTru() {
        String path = Environment.getDataDirectory() + "/data/com.xemthoikhoabieu/files/ThoiKhoaBieu.xlxs";
        File file = new File(path);
        return file.exists();
    }
    private void ChuyenIntent(int n) {
        try {
            Intent it;
            if (n == 0) {
                it = new Intent(this, DocDuLieuTKB.class);
                this.startActivity(it);
                this.finish();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }*/
}