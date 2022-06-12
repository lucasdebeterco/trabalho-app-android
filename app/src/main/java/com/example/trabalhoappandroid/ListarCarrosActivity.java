package com.example.trabalhoappandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarCarrosActivity extends AppCompatActivity {
    private ListView listView;
    private CarroDAO dao;
    private List<Carro> carros;
    private List<Carro> carrosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_carros);

        listView = findViewById(R.id.lista_carros);
        dao = new CarroDAO(this);
        carros = dao.obterTodos();
        carrosFiltrados.addAll(carros);
        ArrayAdapter<Carro> adapter = new ArrayAdapter<Carro>(this, android.R.layout.simple_list_item_1, carros);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraCarro(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraCarro(String nome) {
        carrosFiltrados.clear();
        for (Carro a : carros) {
            if (a.getModelo().toLowerCase().contains(nome.toLowerCase())) {
                carrosFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Carro carroExcluir = carrosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o carro?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        carrosFiltrados.remove(carroExcluir);
                        carros.remove(carroExcluir);
                        dao.excluir(carroExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void onResume() {
        super.onResume();
        carros = dao.obterTodos();
        carrosFiltrados.clear();
        carrosFiltrados.addAll(carros);
        listView.invalidateViews();
    }
}
