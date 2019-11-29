package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miaplicaciondm.R;

import objetos.menu;

import java.util.List;

public class adaptadmenu extends BaseAdapter {


    //Declaracion de variables
    private List<menu> datasource;
    private Context cntx;
    private int IdLayoutPlantilla;

    public List<menu> GetData(){
        return this.datasource;
    }
    public adaptadmenu(Context context, int IdPlantilla, List<menu> sources){
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
    public menu getItem(int position) {
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
            TextView labelNombre = convertView.findViewById(R.id.lblprinnombre);
            TextView labelDescripcion = convertView.findViewById(R.id.lblprindescripcion);

            //Obtenemos el dato a mostrar
            labelNombre.setText(this.datasource.get(position).getTitulo());
            labelDescripcion.setText(this.datasource.get(position).getDescripcion());
        }
        return convertView;
    }
}
