package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import objetos.*;
import sqllite.conexionsqllite;

//importaciones estaticas
//claves de acceso de informacion
import static  com.example.miaplicaciondm.preferencias.clave_logeado;
import static  com.example.miaplicaciondm.preferencias.clave_datos;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rellay1, rellay2;



    //esta variable controla lo que hara la activity gestion de productos
    public static int gestionaccion;

    // llamada de cargar datos
    private SharedPreferences confiduracion;


    FloatingActionButton botonagregar;
    LinearLayout configurar, agregar, estadisticas, gestionar;
    LinearLayout lista;
    TextView lblvalores;

    //verificamos si hay usuarios en la lista
    List<usuarios> datasourceusuarios;

    //para controlar el Toast de bienvenida
    private static Boolean controlbienvenido = false;

    List<productos> listadeproductos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //primero vemos si el usuarios ha logueado
        leerusuarios();

        gestionaccion = 1;

        lblvalores = findViewById(R.id.lblvalores);

        agregar = findViewById(R.id.btnagregar);
        lista = findViewById(R.id.btnverlista);
        configurar = findViewById(R.id.btnajustes);
        estadisticas = findViewById(R.id.btnestadisticas);
        gestionar = findViewById(R.id.btngestionar);


        //controlando el bienvenido que se muestre 1 vez
        if (!controlbienvenido) {

            //usando el super toast de bienvenido
            SuperActivityToast.create(this, new Style(), Style.TYPE_BUTTON)
                    // .setButtonText("UNDO")
                    .setButtonIconResource(R.drawable.iconobienvenido)
                    .setOnButtonClickListener("good_tag_name", null, null)
                    .setProgressBarColor(Color.WHITE)
                    .setText("Bienvenido ")
                    .setDuration(Style.DURATION_SHORT)
                    .setFrame(Style.FRAME_KITKAT)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                    .setAnimations(Style.ANIMATIONS_SCALE).show();
            controlbienvenido = true;
        }

        //accediendo a los datos del app
        this.confiduracion = this.getSharedPreferences(clave_datos, MODE_PRIVATE);


        // si la lista de productos esta nula o vacia y adaptamos el label del mensaje
        if (listadeproductos == null) {
            listadeproductos = new ArrayList<>();
            listadeproductos = leer();
            lblvalores.setText("Numero de items en la lista: " + listadeproductos.size());
        } else {
            lblvalores.setText("Numero de items en la lista: " + listadeproductos.size());
        }


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btnagregar: {
                Intent as = new Intent(this, agregarproducto.class);
                startActivity(as);
                break;
            }

            case R.id.btnajustes: {
                Intent as = new Intent(this, preferencias.class);
                startActivity(as);
                break;
            }
            case R.id.btnverlista: {
                Intent as = new Intent(this, listadeproductos.class);
                startActivity(as);
                break;
            }
            case R.id.btnestadisticas: {
                Intent as = new Intent(this, estadisticas.class);
                startActivity(as);
                break;
            }
            case R.id.btngestionar: {
                Intent as = new Intent(this, gestiondeproductos.class);
                startActivity(as);
                break;
            }
            case R.id.btnsalida: {
                Intent as = new Intent(this, salidadeproductos.class);
                startActivity(as);
                break;
            }


        }
    }


    //metodos para resumen
    @Override
    public void onResume() {
        super.onResume();

        gestionaccion = 1;
        // put your code here...


        // si la lista de productos esta nula o vacia y adaptamos el label del mensaje
        if (listadeproductos == null) {
            listadeproductos = new ArrayList<>();
            listadeproductos = leer();
            lblvalores.setText("Numero de items en la lista: " + listadeproductos.size());
        } else {
            lblvalores.setText("Numero de items en la lista: " + listadeproductos.size());
        }


    }


    // asignando los valores que tengo registrado en SQL para un LIST
    public List<productos> leer() {
        listadeproductos = new ArrayList<>();
        //consulta sql
        String sql = "SELECT * FROM productos";
        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor cursor = bd.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {


                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                Double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fechacaducidad"));

                listadeproductos.add(new productos(nombre, fecha, cantidad, precio, codigo));

            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();
        return listadeproductos;

    }


    void verificarlogeado() {

        if (confiduracion.getInt(clave_logeado, 0) == 1) {

        } else {
            Intent as = new Intent(this, login.class);
            startActivity(as);
        }


    }

    // sin uso
    void leerusuarios() {

        datasourceusuarios = new ArrayList<>();
        //consulta sql
        String sql = "SELECT * FROM usuarios";
        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor cursor = bd.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {


                String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
                String credencial = cursor.getString(cursor.getColumnIndex("credencial"));


                datasourceusuarios.add(new usuarios(usuario, credencial));

            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();

        /*  if(datasourceusuarios.size()==0)
        {
            Intent as = new Intent(this,login.class);
            startActivity(as);
        }*/


    }


}
