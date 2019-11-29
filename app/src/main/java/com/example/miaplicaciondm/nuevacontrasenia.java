package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import objetos.usuarios;
import sqllite.conexionsqllite;

public class nuevacontrasenia extends AppCompatActivity {

    EditText actual, nueva,repeticion;
    Button cambiar;

    List<usuarios> datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevacontrasenia);
        actual = findViewById(R.id.txtactual);
        nueva = findViewById(R.id.txtnueva);
        repeticion = findViewById(R.id.txtrepe);
        cambiar = findViewById(R.id.btncambiar);

        datasource = leerusuarios();

    }

    public void click(View v )
    {


        //validamos
        if(!actual.getText().toString().isEmpty() && !nueva.getText().toString().isEmpty() && !repeticion.getText().toString().isEmpty())
        {
            if(nueva.getText().toString().equals(repeticion.getText().toString()))
            {
                if(datasource.get(0).getCredencial().equals(actual.getText().toString()))
                {
                    //actializamos
                    actualizar();
                    //finalizamos
                    finish();
                }
                else
                {
                    Toast.makeText(this, "Credencial actual invalida", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Credenciales incompatibles", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "llene todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    // actualizamos si todo esta correcto
    private void actualizar()
    {
        //asignamos la conexion
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("credencial",  nueva.getText().toString() );


        // int cantidad = bd.update("productos", registro, "codigo= 1"+ codigo, null);
        //comando de actualizar por defecto de SQL
        int cantidad = bd.update("usuarios", registro, "usuario="+ datasource.get(0).getUsuario(), null);
        //cerramos la base de datos
        bd.close();

        //si el comando da 1 es TRUE y elimino
        if(cantidad==1){
            Toast.makeText(this,"Modificacion exitosa", Toast.LENGTH_SHORT).show();
        }else {
            // no existe en la base de datos por quedarse en 0 que es FALSE
            Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
        }


    }

    // leemos los usuarios
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

}
