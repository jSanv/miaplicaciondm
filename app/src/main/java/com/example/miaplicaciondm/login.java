package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import objetos.productos;
import objetos.usuarios;
import sqllite.conexionsqllite;

import static com.example.miaplicaciondm.preferencias.clave_datos;
import static com.example.miaplicaciondm.preferencias.clave_logeado;


public class login extends AppCompatActivity {


    private EditText usuario,credencial;
    Button procesar;
    List<usuarios> datasource;

    //para saber si loguear
    SharedPreferences configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txtusuariologin);
        credencial = findViewById(R.id.txtcredenciallogin);
        procesar = findViewById(R.id.btnprocesarlogin);

        //ver si loguear
        //accediendo a los datos del app
        this.configuracion= this.getSharedPreferences(clave_datos,MODE_PRIVATE);
        //aqui vemos si el usuario quiere que logueemos en esta app
        decidirsiloguear();



        //leemos la cantidad de usuarios
        datasource = leerusuarios();
        if(datasource.size()==0) {
            Intent as = new Intent(this, nuevousuario.class);
            startActivity(as);
            finish();
            Toast.makeText(this, "Debe crear un usuario", Toast.LENGTH_SHORT).show();
        }
        else
        {

        }


    }

    public void clicl(View v )
    {
            if(!usuario.getText().toString().isEmpty() && !credencial.getText().toString().isEmpty() )
            {

                if(datasource.get(0).getCredencial().equals(credencial.getText().toString()) && datasource.get(0).getUsuario().equals(usuario.getText().toString()))
                {
                    Intent as = new Intent(this, MainActivity.class);
                    startActivity(as);
                   finish();
                }

            }
            else
            {
                Toast.makeText(this, "llene todos los campos", Toast.LENGTH_SHORT).show();
            }
    }


    List<usuarios> leerusuarios()
    {

            datasource = new ArrayList<>();
            //consulta sql
            String sql = "SELECT * FROM usuarios";
            //abrir base de datos en dbobjetos
            conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
            //abrir conexion
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor cursor = bd.rawQuery(sql,null);

            if(cursor.moveToFirst())
            {
                do {


                    String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
                    String credencial =  cursor.getString(cursor.getColumnIndex("credencial"));


                    datasource.add(new usuarios(usuario,credencial));

                } while (cursor.moveToNext());
            }

            cursor.close();
            bd.close();
            return datasource;


    }


    @Override
    protected void onResume() {
        super.onResume();
        datasource=leerusuarios();
        if(datasource.size()==0) {
            Intent as = new Intent(this, nuevousuario.class);
            startActivity(as);
            Toast.makeText(this, "Debe crear un usuario", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "numero de usuarios "+datasource.size(), Toast.LENGTH_SHORT).show();
        }
        else
        {

        }
    }

    void decidirsiloguear()
    {

        if(configuracion.getInt(clave_logeado,0)==2)
                {
                    Intent as = new Intent(this,MainActivity.class);
                    startActivity(as);
                    finish();
                }


    }
}
