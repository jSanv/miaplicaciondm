package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import adaptadores.adaptadrecyclerview;


//sqllite
import sqllite.conexionsqllite;

//variables estaticas
//variables de la clave de configuracion

import static  com.example.miaplicaciondm.preferencias.clave_logeado;
import static  com.example.miaplicaciondm.preferencias.clave_datos;

import objetos.productos;

public class listadeproductos extends AppCompatActivity {

   private static List<productos> datasource;
    RecyclerView listadeproducto;
    // llamada de cargar datos
    private SharedPreferences confiduracion;

    LinearLayoutManager manager;
    adaptadrecyclerview adaptadorobjeto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadeproductos);

        //accediendo a los datos del app
        this.confiduracion= this.getSharedPreferences(clave_datos,MODE_PRIVATE);

        datasource = new ArrayList<>();
        datasource =leer();

        listadeproducto = findViewById(R.id.vistadeproductos);
        datasource = new ArrayList<>();
        datasource = leer();


        this.manager = new LinearLayoutManager(this);


       this.adaptadorobjeto = new adaptadrecyclerview(this.datasource);


        //Establecemos las propiedades
        this.listadeproducto.setHasFixedSize(true);
        this.listadeproducto.setLayoutManager(this.manager);

        // adaptamos los datos
        this.listadeproducto.setAdapter(this.adaptadorobjeto);


    }




    // asignando los valores que tengo registrado en SQL para un LIST
    public List<productos> leer()
    {
        // consulta sql
        String sql;


           //validamos la opcion desde preferencias para el orden
        if(confiduracion.getInt(clave_logeado,0)==1)
        {
            //ordenar por codigo
            sql = "SELECT * FROM productos order by codigo asc";
        }
        else
        {
            //ordenar por nombre
            sql = "SELECT * FROM productos order by nombre asc";
        }

       // java.util.Date fecha = new Date();
        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor cursor = bd.rawQuery(sql,null);


        if(cursor.moveToFirst())
        {

            do{


                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                Double precio =  cursor.getDouble(cursor.getColumnIndex("precio"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fechacaducidad"));

                datasource.add(new productos(nombre,fecha,cantidad,precio,codigo));

            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();
        return datasource;

    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
