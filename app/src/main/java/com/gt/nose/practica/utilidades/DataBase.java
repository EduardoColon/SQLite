package com.gt.nose.practica.utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper{

    private static final int VERSION_BASEDATOS = 1;

    private static final String NOMBRE_BASEDATOS = "BaseDeDatos";

    private static final String TABLA_TAREA = "CREATE TABLE tabla" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT NOT NULL, tarea TEXT NOT NULL)";

    public DataBase(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_TAREA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TAREA);
        onCreate(db);
    }

    public void insertarTarea(String fecha, String tarea) {
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("fecha", fecha);
            valores.put("tarea", tarea);
            db.insert("tabla", null, valores);
            db.close();
        }
    }

    public void modificarTarea( String fecha, String tarea, long id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("fecha", fecha);
        valores.put("tarea", tarea);
        db.update("tabla", valores, "_id=" + id, null);
        db.close();
    }

    public void borrarTarea(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tabla", "_id="+id, null);
        db.close();
    }

    public String getData() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columnas = new String[]{"_id", "fecha", "tarea"};
        Cursor c = db.query("tabla", columnas, null, null, null, null, null);
        String resultado = "";

        int iFila = c.getColumnIndex("_id");
        int iFecha = c.getColumnIndex("fecha");
        int iTarea = c.getColumnIndex("tarea");
        int i = 1;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            resultado = resultado + "Tarea" + i  + "\nID: " + c.getString(iFila) + "\nFecha: " + c.getString(iFecha) + "\nTarea:" + c.getString(iTarea) + "\n\n";
            i++;
        }
        return resultado;
    }

}


