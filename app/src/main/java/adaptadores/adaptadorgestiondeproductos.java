package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miaplicaciondm.R;

import java.util.List;

import objetos.productos;

public class adaptadorgestiondeproductos extends BaseAdapter {

    //Declaracion de variables
    private List<productos> datasource;
    private Context cntx;
    private int IdLayoutPlantilla;

    public List<productos> GetData(){
        return this.datasource;
    }

    public adaptadorgestiondeproductos(Context context, int IdPlantilla, List<productos> sources){
        //Inicializamos las variables
        this.cntx = context;
        this.IdLayoutPlantilla = IdPlantilla;
        this.datasource = sources;
    }

    @Override
    public int getCount() {
        return this.datasource.size();
    }

    @Override
    public productos getItem(int position) {
        return this.datasource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Validamos que no exista un formato
        if(convertView == null){
            //Creamos el Administrador de Layout
            LayoutInflater inflater = (LayoutInflater) this.cntx.getSystemService(this.cntx.LAYOUT_INFLATER_SERVICE);
            //Le damos vida al archivo xml ahora es un Layout
            convertView = inflater.inflate(this.IdLayoutPlantilla,null);

            //Buscamos los controles de nuestra plantilla
            TextView labelNombre = convertView.findViewById(R.id.lblnombre);
            TextView labelcodigo = convertView.findViewById(R.id.lblcodigo);
            TextView labelprecio = convertView.findViewById(R.id.lblprecio);
            TextView labelcantidad = convertView.findViewById(R.id.lblcantidad);
            TextView labelfecha = convertView.findViewById(R.id.lblfechavencimiento);
            TextView lblcorrelativo = convertView.findViewById(R.id.lblcorrelativo);



            //Obtenemos el dato a mostrar

            labelNombre.setText(this.datasource.get(position).getNombre());
            labelcantidad.setText(Integer.toString(this.datasource.get(position).getCantidad()) );
            labelprecio.setText( Double.toString(this.datasource.get(position).getPrecio()) );
            labelfecha.setText(this.datasource.get(position).getFechacaducidad());
            labelcodigo.setText(this.datasource.get(position).getCodigo());
            lblcorrelativo.setText(Integer.toString(position+1));
        }
        return convertView;
    }
}
