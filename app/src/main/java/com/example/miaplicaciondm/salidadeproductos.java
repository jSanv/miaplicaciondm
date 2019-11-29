package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//persistencia
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.Calendar;

import sqllite.conexionsqllite;
import sqllite.conexionsqllite2;


import static com.example.miaplicaciondm.MainActivity.gestionaccion;

public class salidadeproductos extends AppCompatActivity implements View.OnClickListener {

    Button btnprocesar, btnseleccionar, btnbandejasalida;
    EditText cantidadsalida;
    TextView nombreydispo,seleccion;


    String codigo, cantidad, vencimiento, precio,nombre,posici;
    Bundle receptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salidadeproductos);

        //decimos a gestion de productos que la accion es 2 para que los datos los mande aqui
        gestionaccion=2;

        btnprocesar = findViewById(R.id.btnaceptar);
        btnseleccionar = findViewById(R.id.btnseleccionarproducto);
        btnbandejasalida = findViewById(R.id.btnbandejasalida);


        cantidadsalida = findViewById(R.id.txtcantidadsalida);

        nombreydispo = findViewById(R.id.lblnombreydisponibilidad);
        seleccion = findViewById(R.id.lblseleccion);



        receptor = getIntent().getExtras();
        if(receptor!=null)
        {
            codigo = receptor.getString("codigo");
            cantidad = receptor.getString("cantidad");
            vencimiento = receptor.getString("vencimiento");
            precio = receptor.getString("precio");
            nombre = receptor.getString("nombre");
            posici = receptor.getString("position");
            nombreydispo.setText("Seleccionado: "+nombre+" disponibles: "+cantidad);
            seleccion.setText("Precio por unidad "+precio);
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btnseleccionarproducto:
            {
                Intent as = new Intent(this,gestiondeproductos.class);
                startActivity(as);

                break;
            }
            case R.id.btnbandejasalida:
            {
                Intent as = new Intent(this,bandejadesalida.class);
                startActivity(as);
                break;
            }
            case R.id.btnaceptar:
            {


                if(!cantidadsalida.getText().toString().isEmpty() && receptor!=null)
                {
                    // si la cantidad total es menor o igual a la salida
                    if(Integer.parseInt(cantidadsalida.getText().toString())<= Integer.parseInt(cantidad) && Integer.parseInt(cantidadsalida.getText().toString())>0)
                    {
                        Toast.makeText(this, "Salida de producto exitosa", Toast.LENGTH_SHORT).show();
                        //actualizamos los datos de disponibilidad
                        int   cantidadactual = Integer.parseInt(cantidad) - Integer.parseInt(cantidadsalida.getText().toString());
                        cantidad =  Integer.toString(cantidadactual);
                        nombreydispo.setText("Seleccionado: "+nombre+" disponibles: "+cantidad);
                        //datos a mandar
                        double preciototal = (Double.parseDouble(precio)* Double.parseDouble(cantidadsalida.getText().toString()));
                        //mandamos los datos de totales a sqllite
                        agregardatos(Integer.parseInt(cantidadsalida.getText().toString()) ,preciototal);

                        //ahora actualizamos la base de datos principal
                        actualizardatosprimarios(cantidadactual);

                        //hacemos el total y lo mostramos en el label de arriba (el primero)
                        seleccion.setText("Total del pedido: $"+Double.toString(preciototal));

                        //limpiamos el edit text de la cantidad a sacar o vender
                        cantidadsalida.setText("");


                    }
                    else
                    {
                        Toast.makeText(this, "La cantidad no puede ser mayor a la disponible ni igual a 0", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Ingrese la cantidad a procesar", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestionaccion=2;
    }

    private void agregardatos(int cantidadpor,double preciopor)
    {
        int dia,mes,anio;
        String fechactual;
        //obteniendo fecha actual
        Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = (fecha.get(Calendar.MONTH)+1);
        if(mes == 13)
        {
            mes = 01;
        }
        anio = fecha.get(Calendar.YEAR);
        fechactual = dia+"/"+mes+"/"+anio;

        //asignamos la conexion
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("codigo", codigo);
        registro.put("nombre", nombre);
        registro.put("precio", preciopor);
        registro.put("fechacaducidad", vencimiento);
        registro.put("cantidad", cantidadpor );
        registro.put("fechasalida", fechactual );


        //insertamos en la base de datos
        bd.insert("salidas", null, registro);
        //cerramos la base de datos
        bd.close();

        //uso de supertoast
        //usando el super toast de bienvenido
        SuperActivityToast.create( salidadeproductos.this, new Style(), Style.TYPE_PROGRESS_BAR)
                // .setButtonText("UNDO")
                .setButtonIconResource(R.mipmap.ic_launcher)
                .setOnButtonClickListener("good_tag_name", null, null)
                .setProgressBarColor(Color.WHITE)
                .setText("Insertando datos...")
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_KITKAT)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                .setAnimations(Style.ANIMATIONS_SCALE).show();

    }

    private void actualizardatosprimarios(int cantidad)
    {
//abrimos la base de datos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //evaluamos los registros de la base de datos
        ContentValues registro = new ContentValues();

        //ingresamos los actuales
        registro.put("cantidad", cantidad);

        // int cantidad = bd.update("articulos", registro, "codigo= 1"+ codigo, null);
        //comando de actualizar por defecto de SQL
        int cantidads = bd.update("productos", registro, "codigo="+ codigo, null);
        //cerramos la base de datos
        bd.close();

        //si el comando da 1 es TRUE y elimino
        if(cantidads==1){
            Toast.makeText(this,"Actualizacion exitosa", Toast.LENGTH_SHORT).show();
        }else {
            // no existe en la base de datos por quedarse en 0 que es FALSE
            Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
        }



    }
}