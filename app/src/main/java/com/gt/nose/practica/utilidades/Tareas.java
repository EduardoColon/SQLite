package com.gt.nose.practica.utilidades;

/**
 * Created by eduar on 3/06/2017.
 */

public class Tareas {

    private int id ;
    private String fecha, tarea;

    public Tareas(int id, String fecha, String tarea){
        this.id = id;
        this.fecha = fecha;
        this.tarea = tarea;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTarea() {
        return tarea;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
}
