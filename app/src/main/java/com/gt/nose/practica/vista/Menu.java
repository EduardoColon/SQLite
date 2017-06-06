package com.gt.nose.practica.vista;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.gt.nose.practica.R;
import com.gt.nose.practica.utilidades.DataBase;

public class Menu extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private EditText tarea, tareaModificar, idModificar, idEliminar;
    private Button guardar, modificar, eliminar, consultar;
    private DatePicker datePicker;
    private int year, month, day;
    private String sFecha, sTarea, sIdModificar, sTareaModificar, sIdEliminar;
    private Boolean exito = true;
    private DataBase dataBase;
    private ExpandableLayout elGuardar, elModificar, elEliminar, elConsultar;
    private TextView tvGuardar, tvModificar, tvEliminar, tvConsultar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        dataBase = new DataBase(getApplicationContext());

        year = month = day = 0;
        initVars();

    }



    private void initVars() {

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tarea = (EditText) findViewById(R.id.etTarea);
        tareaModificar = (EditText) findViewById(R.id.etTareaModificar);
        idModificar = (EditText) findViewById(R.id.etIdModificar);
        idEliminar = (EditText) findViewById(R.id.etIdEliminar);
        guardar = (Button) findViewById(R.id.bGuardar);
        modificar = (Button) findViewById(R.id.bModificar);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        eliminar = (Button) findViewById(R.id.bEliminar);
        consultar = (Button) findViewById(R.id.bConsultar);
        elGuardar = (ExpandableLayout) findViewById(R.id.elGuardar);
        elModificar = (ExpandableLayout) findViewById(R.id.elModificar);
        elEliminar = (ExpandableLayout) findViewById(R.id.elEliminar);
        elConsultar = (ExpandableLayout) findViewById(R.id.elConsultar);
        tvGuardar = (TextView) findViewById(R.id.tvGuardar);
        tvModificar = (TextView) findViewById(R.id.tvModificar);
        tvEliminar = (TextView) findViewById(R.id.tvEliminar);
        tvConsultar = (TextView) findViewById(R.id.tvConsultar);

        guardar.setOnClickListener(this);
        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        consultar.setOnClickListener(this);

        tvGuardar.setOnTouchListener(this);
        tvConsultar.setOnTouchListener(this);
        tvEliminar.setOnTouchListener(this);
        tvModificar.setOnTouchListener(this);

        scrollView.scrollTo(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.instrucciones:

                new AlertDialog.Builder(this).setTitle("Instrucciones").setMessage("La aplicación tiene un funcionamento sencillo, a la hora de guardar, o modificar un registro " +
                        " se tomara automaticamente la fecha que este colocada en ese momento en el calendario, lo demas es pan comido :3").setPositiveButton("OKIS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;

            case R.id.dedicatoria:
                new AlertDialog.Builder(this).setTitle("Dedicatoria").setMessage("Aplicacion dedicada a mi monita linda de mi corazon que me dio la idea de hacer esta" +
                        " aplicación para repasar y que me va a apoyar en todo momento por si empiezo a trabajar, te amo mucho miamor").setPositiveButton("OKIS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();
        sFecha = String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
        sTarea = tarea.getText().toString().trim();
        sTareaModificar = tareaModificar.getText().toString().trim();
        sIdModificar = idModificar.getText().toString().trim();
        sIdEliminar = idEliminar.getText().toString().trim();

        switch(v.getId()){

            case R.id.bGuardar:

                if(sTarea.matches("")){

                    new AlertDialog.Builder(Menu.this).setTitle("Error").setMessage("Debe ingresar una tarea").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}}).show();

                }else{


                    try{
                        dataBase.insertarTarea(sFecha, sTarea);
                    }catch(SQLiteException e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        exito = false;
                    }finally {
                        if(exito)
                            Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.bEliminar:

                if(sIdEliminar.matches("")){

                    new AlertDialog.Builder(Menu.this).setTitle("Error").setMessage("Debe llenar todos los campos").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}}).show();

                }else{

                    int id = Integer.parseInt(sIdEliminar);
                    try{
                        dataBase.borrarTarea(id);
                    }catch(SQLiteException e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        exito = false;
                    }finally {
                        if(exito)
                            Toast.makeText(this, "Eliminado con exito", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.bConsultar:


                new AlertDialog.Builder(Menu.this).setTitle("Listado de tareas")
                        .setMessage(String.valueOf(dataBase.getData())).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;

            case R.id.bModificar:

                if(sTareaModificar.matches("") || sIdModificar.matches("")){

                    new AlertDialog.Builder(Menu.this).setTitle("Error").setMessage("Debe llenar todos los campos").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}}).show();

                }else{

                    int id = Integer.parseInt(sIdModificar);
                    try{
                        dataBase.modificarTarea(sFecha, sTareaModificar, id);
                    }catch(SQLiteException e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        exito = false;
                    }finally {
                        if(exito)
                            Toast.makeText(this, "Modificado con exito", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
        }
    }

    @Override
    protected void onPause() {

        finish();
    }

    @Override
    protected void onStop() {
        finish();
    }

    @Override
    protected void onDestroy() {
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.tvGuardar:

                elGuardar.toggle();

                if(elConsultar.isExpanded()){
                    elConsultar.toggle();
                }

                if(elModificar.isExpanded()){
                    elModificar.toggle();
                }

                if(elEliminar.isExpanded()){
                    elEliminar.toggle();
                }

                if(elGuardar.isExpanded()){
                    elGuardar.toggle();
                }

                break;

            case R.id.tvConsultar:

                elConsultar.toggle();

                if(elGuardar.isExpanded()){
                    elGuardar.toggle();
                }

                if(elModificar.isExpanded()){
                    elModificar.toggle();
                }

                if(elEliminar.isExpanded()){
                    elEliminar.toggle();
                }
                break;

            case R.id.tvEliminar:

                elEliminar.toggle();

                if(elGuardar.isExpanded()){
                    elGuardar.toggle();
                }

                if(elConsultar.isExpanded()){
                    elConsultar.toggle();
                }
                if(elModificar.isExpanded()){
                    elModificar.toggle();
                }

                break;

            case R.id.tvModificar:

                elModificar.toggle();

                if(elConsultar.isExpanded()){
                    elConsultar.toggle();
                }

                if(elEliminar.isExpanded()){
                    elEliminar.toggle();
                }

                break;

        }


        return false;
    }

}
