package com.example.miaplicaciondm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import objetos.productossalida;
import sqllite.conexionsqllite;

//el adaptador
import adaptadores.adaptadoressalidas.adaptadorrecyclersalidas;

public class bandejadesalida extends AppCompatActivity {

    private static List<productossalida> datasource;
    RecyclerView listadeproducto;
    // llamada de cargar datos
    private TextView totalsalida;
    private Button borrarregistros;

    LinearLayoutManager manager;
    adaptadorrecyclersalidas adaptadorobjeto;
    Double total=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandejadesalida);

        totalsalida = findViewById(R.id.lbltotal);
        borrarregistros = findViewById(R.id.btnborrarregistro);


        //leemos los productos
        listadeproducto = findViewById(R.id.recyclersalida);
        datasource = new ArrayList<>();
        datasource = leer();

        //contamos el total global de la bandeja de salida
        totalsalida();


        this.manager = new LinearLayoutManager(this);


        this.adaptadorobjeto = new adaptadorrecyclersalidas(this.datasource);


        //Establecemos las propiedades
        this.listadeproducto.setHasFixedSize(true);
        this.listadeproducto.setLayoutManager(this.manager);

        // adaptamos los datos
        this.listadeproducto.setAdapter(this.adaptadorobjeto);



    }

    //borrando registros click
    public void borrarregistros(View v )
    {

        //preguntamos si quiere eliminar la bandeja de salida pero los correlativos aun seguiran vigentes

        new AlertDialog.Builder(this).setTitle("¡Aviso!").setMessage("¿Desea Eliminar la bandeja de salida ?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // si la respuesta es positiva
                        //asignamos la conexion
                        conexionsqllite admin = new conexionsqllite(bandejadesalida.this, "dbobjetos", null, 1);
                        //abrimos la base de datos
                        SQLiteDatabase bd = admin.getWritableDatabase();

                        //comando de SQLLITE para eliminar donde el CODIGO = codigo(de la caja de texto)
                        bd.execSQL("delete from salidas");
                        //cerramos la base de datos
                        bd.close();

                        // si la eliminacion es 1 es por que es TRUE y se ha eliminado


                        // no esta ingresando codigo en la caja de texto
                        datasource.clear();
                        datasource= leer();
                        adaptadorobjeto.notifyDataSetChanged();

                    }
                }).show(); //fin del proceso

    }


    // asignando los valores que tengo registrado en SQL para un LIST
    public List<productossalida> leer()
    {
        // consulta sql
        String sql;

        //ordenar por nombre
        sql = "SELECT * FROM salidas order by correlativo desc";


        // java.util.Date fecha = new Date();
        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bda = admin.getWritableDatabase();
        Cursor cursor = bda.rawQuery(sql,null);


        if(cursor.moveToFirst())
        {

            do{


                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                Double precio =  cursor.getDouble(cursor.getColumnIndex("precio"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fechacaducidad"));
                String fechsalida = cursor.getString(cursor.getColumnIndex("fechasalida"));
                int correlativo = cursor.getInt(cursor.getColumnIndex("correlativo"));


                datasource.add(new productossalida(nombre,fecha,cantidad,precio,codigo,fechsalida,correlativo));

            } while (cursor.moveToNext());
        }

        cursor.close();
        bda.close();
        return datasource;

    }

    private void totalsalida()
    {
        total=0.0;
        if(datasource.size()>0)
        {
            for(int i = 0; i<(datasource.size()); i++)
            {

                total = total+ datasource.get(i).getPrecio();
            }
            //asignamos el total global al label
            totalsalida.setText("El total de la salida es: $"+total);
        }


    }

    //metodos para el resumen
    @Override
    protected void onResume() {
        super.onResume();
        totalsalida();
    }
}