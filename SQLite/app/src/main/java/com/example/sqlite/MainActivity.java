package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_save;
    Button btn_update;
    Button btn_del;
    Button btn_query;
    TextView et_id, et_name, et_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save = findViewById(R.id.btn_save);
        et_name = findViewById(R.id.et_name);
        et_psw = findViewById(R.id.et_psw);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDAO personDAO = new PersonDAO(MainActivity.this);
                long id = personDAO.add(et_name.getText().toString().trim(), et_psw.getText().toString().trim());
                Toast.makeText(MainActivity.this, "save"+id , Toast.LENGTH_SHORT).show();
            }
        });

        btn_update = findViewById(R.id.btn_update);
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_psw = findViewById(R.id.et_psw);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDAO personDAO = new PersonDAO(MainActivity.this);
                long count = personDAO.update(Long.parseLong(et_id.getText().toString().trim()), et_name.getText().toString().trim(), et_psw.getText().toString().trim());
                Toast.makeText(MainActivity.this, "update"+count , Toast.LENGTH_SHORT).show();
            }
        });

        btn_del = findViewById(R.id.btn_del);
        et_id = findViewById(R.id.et_id);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDAO personDAO = new PersonDAO(MainActivity.this);
                long count = personDAO.delete(Long.parseLong(et_id.getText().toString().trim()));
                Toast.makeText(MainActivity.this, "delete"+count , Toast.LENGTH_SHORT).show();
            }
        });

        btn_query = findViewById(R.id.btn_query);
        et_id = findViewById(R.id.et_id);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDAO personDAO = new PersonDAO(MainActivity.this);
                Boolean result = personDAO.query(Long.parseLong(et_id.getText().toString().trim()));
                Toast.makeText(MainActivity.this, "query"+result , Toast.LENGTH_SHORT).show();
                if(result)
                    Toast.makeText(MainActivity.this, "query succeed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}