package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

import java.util.List;

import objetos.productossalida;
import objetos.productos;


public class adaptadrecyclerview extends RecyclerView.Adapter<viewholderproductosestadisticas> {

    private List<productos> listContactos;

    public adaptadrecyclerview(List<productos> datasources){
        //Inicializamos el datasources
        this.listContactos = datasources;
    }

    @NonNull
    @Override
    public viewholderproductosestadisticas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creamos la vista usando el Layout de plantilla
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantillaproductos,parent,false);
        viewholderproductosestadisticas vhContactos = new viewholderproductosestadisticas(v);
        return vhContactos;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderproductosestadisticas holder, int position) {
        //Establecemos la información del datasources al ViewHolder
        //  holder.getLbldificultad().setText(this.listContactos.get(position).getDificultad());
        holder.getLblnombre().setText( this.listContactos.get(position).getNombre());


        if(this.listContactos.get(position).getCantidad()==0)
        {
            holder.getLblcantidad().setText("Agotado");

        }
        else
        {
            // convertimos a String el Integer, osino da error
            holder.getLblcantidad().setText("cantidad: "+ Integer.toString( this.listContactos.get(position).getCantidad()));

        }

        holder.getLblcodigo().setText("codigo: "+  this.listContactos.get(position).getCodigo());

        //validamos si existe fecha de vencimiento en el producto
        if(!listContactos.get(position).getFechacaducidad().equals(""))
        {
            holder.getLblfechavencimiento().setText("Vencimiento: "+  this.listContactos.get(position).getFechacaducidad());
        }
        else { holder.getLblfechavencimiento().setText("Sin fecha de vencimiento"); }

        holder.getLblprecio().setText("Precio: "+ Double.toString( this.listContactos.get(position).getPrecio()));


    }


    @Override
    public int getItemCount() {
        {
            return this.listContactos.size();
    }
}
}
