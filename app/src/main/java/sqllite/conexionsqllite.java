package sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionsqllite extends SQLiteOpenHelper {

    // valores a recibir de la base de datos
    public conexionsqllite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bdsqLite) {
        //CREACION DE LA TABLA QUE USAREMOS
        bdsqLite.execSQL("create table productos(codigo text primary key ,nombre text,fechacaducidad text, cantidad integer, precio real)");
        bdsqLite.execSQL(" create table usuarios(usuario text primary key, credencial text)" );
        bdsqLite.execSQL("create table salidas(correlativo integer  primary key autoincrement, codigo text,nombre text,fechacaducidad text, cantidad integer, precio real, fechasalida text)" );


    }

    //por defecto se deja vacio
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
  sqLiteDatabase.execSQL("drop table if exists productos");
        sqLiteDatabase.execSQL("drop table if exists salidas");
       sqLiteDatabase.execSQL("drop table if exists usuarios");
        onCreate(sqLiteDatabase);


    }
}
