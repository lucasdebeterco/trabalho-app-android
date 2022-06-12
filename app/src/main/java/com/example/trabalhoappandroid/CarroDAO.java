package com.example.trabalhoappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CarroDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public CarroDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Carro carro) {
        ContentValues values = new ContentValues();
        values.put("marca", carro.getMarca());
        values.put("modelo", carro.getModelo());
        values.put("ano", carro.getAno());
        return banco.insert("carro", null, values);
    }

    public void excluir(Carro carro) {
        banco.delete("carro", "id = ?", new String[] {carro.getId().toString()});
    }

    public List<Carro> obterTodos() {
        List<Carro> carros = new ArrayList<>();

        Cursor cursor = banco.query("carro", new String[]{"id", "marca", "modelo", "ano"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Carro carro = new Carro();
            carro.setId(cursor.getInt(0));
            carro.setMarca(cursor.getString(1));
            carro.setModelo(cursor.getString(2));
            carro.setAno(cursor.getString(3));
            carros.add(carro);
        }

        return carros;
    }
}
