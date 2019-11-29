package sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionsqllite2 extends SQLiteOpenHelper {
    // valores a recibir de la base de datos
    public conexionsqllite2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bdsqLite) {
        //CREACION DE LA TABLA QUE USAREMOS
         bdsqLite.execSQL("create table salidas(correlativo int  primary key autoincrement, codigo text,nombre text,fechacaducidad text, cantidad int, precio Double, fechasalida text)" );


    }

    //por defecto se deja vacio
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
