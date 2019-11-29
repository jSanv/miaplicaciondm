package adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

import java.util.List;

import objetos.productos;

public class adaptadorrecyclergestiondeproductos extends RecyclerView.Adapter<viewholderproductosgestion> implements View.OnClickListener {

    private List<productos> listContactos;

    //metodo de escucha click
    private View.OnClickListener listener;

    public adaptadorrecyclergestiondeproductos(List<productos> datasources){
        //Inicializamos el datasources
        this.listContactos = datasources;
    }

    @NonNull
    @Override
    public viewholderproductosgestion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creamos la vista usando el Layout de plantilla
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantillagestiondeproducto,parent,false);

        //metodo de escucha
        v.setOnClickListener(this);

        viewholderproductosgestion vhContactos = new viewholderproductosgestion(v);
        return vhContactos;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderproductosgestion holder, int position) {

        //correlativo
        holder.getLblcorrelativo().setText(Integer.toString(position+1) );


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
        return this.listContactos.size();
    }

    //evento antes del onclick pero para escuhcar el click del objeto donde es

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }

    }
}
