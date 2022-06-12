package com.example.trabalhoappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText marca;
    private EditText modelo;
    private EditText ano;
    private CarroDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        marca = findViewById(R.id.editMarca);
        modelo = findViewById(R.id.editModelo);
        ano = findViewById(R.id.editAno);
        dao = new CarroDAO(this);
    }
}