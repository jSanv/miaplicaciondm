package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class preferencias extends AppCompatActivity {

    RadioButton btnabrircamara, btnabririnventario, rdpornombre, rdporcodigo;
    // para guardar datos de configuracion
    //controles para la seccion de mostrar

    RadioButton rdbpornombremostrar, rdbporcodigomostrar;
    private SharedPreferences configurtacion;
    public static final String clave_logeado = "logueado";
    public static final String CLAVE_ORDEN = "COLOR";
    public static final String CLAVE_ORDENMOSTRAR = "MOSTRAR";
    public static String clave_datos = "CLAVEDATOS";


    int seleccionordenargestion = 1;
    int seleccionloguear = 1;
    int seleccionordenarmostrar=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        //controles de la camara
        btnabrircamara = findViewById(R.id.rdarbircamara);
        btnabririnventario = findViewById(R.id.rdabririnserccion);

        //otros controles
        rdpornombre = findViewById(R.id.rdcoloroscuro);
        rdporcodigo = findViewById(R.id.rdcolordefecto);

        //controles para la seccion mostrar
        rdbporcodigomostrar = findViewById(R.id.rdporcodigomostrar);
        rdbpornombremostrar = findViewById(R.id.rdpronombremostrar);


        this.configurtacion = getSharedPreferences(clave_datos, MODE_PRIVATE);

        //cargando los datos establecidos
        cargardatos();

        //validando lo chequeado
        this.rdporcodigo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionordenargestion = 1;
                }
            }
        });
        this.rdpornombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionordenargestion = 2;
                }
            }
        });

        // el tamanio de letra
        this.btnabrircamara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionloguear = 1;
                }
            }
        });
        this.btnabririnventario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionloguear = 2;
                }
            }
        });

        // este es para el orden del menu mostrar
        this.rdbporcodigomostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionordenarmostrar = 1;
                }
            }
        });
        this.rdbpornombremostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Validamos si se encuentra marcado
                if (isChecked) {
                    seleccionordenarmostrar = 2;
                }
            }
        });




    } //fin del create


    //almacenando los datos en el evento click del boton
    public void aceptar(View v) {
        //cargando la informacion a los datos del telefono
        if (configurtacion != null) {
            SharedPreferences.Editor editorconfiguracion = this.configurtacion.edit();
            if (seleccionloguear == 0 || seleccionordenargestion == 0) {
                Toast.makeText(this, "Debe registrar configuracion", Toast.LENGTH_SHORT).show();
            } else {
                editorconfiguracion.putInt(CLAVE_ORDEN, this.seleccionordenargestion);
                editorconfiguracion.putInt(clave_logeado, this.seleccionloguear);
                editorconfiguracion.putInt(CLAVE_ORDENMOSTRAR,this.seleccionordenarmostrar);
                editorconfiguracion.commit();
                Toast.makeText(this, "Configuracion aceptada", Toast.LENGTH_SHORT).show();
                Intent as = new Intent(this, MainActivity.class);
                startActivity(as);
                finish();
            }
        }

    }

    public  void contra(View v)
    {
        Intent as = new Intent(this, nuevacontrasenia.class);
        startActivity(as);
    }

    //cargando los datos
    private void cargardatos() {
        int seleccionlogue = configurtacion.getInt(clave_logeado, 0);
        int seleccionordengestion = configurtacion.getInt(CLAVE_ORDEN, 0);
        int seleccionmostra = configurtacion.getInt(CLAVE_ORDENMOSTRAR,0);


        if (seleccionlogue != 0) {
            if (seleccionlogue == 1) {
                this.btnabrircamara.setChecked(true);
            }
            if (seleccionlogue == 2) {
                this.btnabririnventario.setChecked(true);

            }
            // si la app esta reiniciada

            }
            else {
        this.btnabrircamara.setChecked(true);
        seleccionlogue = 1;
            seleccionordenargestion =1;}


            //ahora el de boton flotante
            if (seleccionordengestion != 0) {
                if (seleccionordengestion == 1) {
                    this.rdporcodigo.setChecked(true);
                }
                if (seleccionordengestion == 2) {
                    this.rdpornombre.setChecked(true);
                }
                //si la app esta reiniciada

            }
            else {
                this.rdporcodigo.setChecked(true);
                seleccionordengestion=1;
                seleccionloguear =1;
            }

        //ahora el de mostrar por de ver
        if (seleccionmostra != 0) {
            if (seleccionmostra == 1) {
                this.rdbporcodigomostrar.setChecked(true);
            }
            if (seleccionmostra == 2) {
                this.rdbpornombremostrar.setChecked(true);
            }
            //si la app esta reiniciada

        }
        else {
            this.rdbporcodigomostrar.setChecked(true);
            seleccionordengestion=1;
            seleccionordenargestion=1;
        }




    }
}
