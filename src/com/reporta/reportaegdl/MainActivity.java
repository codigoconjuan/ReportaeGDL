package com.reporta.reportaegdl;

import java.util.List;

import com.reporta.reportaegdl.db.ReportesDBOpenHelper;
import com.reporta.reportaegdl.db.ReportesDataSource;
import com.reporta.reportaegdl.model.Reporte;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


public class MainActivity extends Activity {
	private static final String LOGTAG = "REPORTAGDL";
	
	// crear la conexion a la BD
	ReportesDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.activity_main);
        
        /*
        //instanciar BD
        datasource = new ReportesDataSource(this);
        datasource.open(); 
        
        List<Reporte> reportes = datasource.findAll();
        
        if (reportes.size()  == 0) {
        	
        	//llama a la funciónd e abajo.
        	createData();
        	reportes = datasource.findAll();
        }
        
        // la ponemos un arrayList y creamos la vista
        ArrayAdapter<Reporte> adapter = new ArrayAdapter<Reporte>(this,android.R.layout.simple_list_item_1, reportes);
		setListAdapter(adapter);
        
        */
         
        

        //instanciar BD
         datasource = new ReportesDataSource(this);
        
    }





	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    // Intent para la clase de Bache
    public void gotoBache(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar un Bache");
    	intent.putExtra("id", "2");
    	startActivity(intent);
    }
    
    // Intent para la clase de Luminaria
    public void gotoLuminaria(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar una Luminaria");
    	intent.putExtra("id", "3");
    	startActivity(intent);
    }
    
    // Intent para la clase de Fuga
    public void gotoFuga(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar una Fuga de Agua");
    	intent.putExtra("id", "4");
    	startActivity(intent);
    }
    
    // Intent para la clase de Arbol
    public void gotoArbol(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar un Árbol");
    	intent.putExtra("id", "1");
    	startActivity(intent);
    }

    // Intent para los reportes
    public void gotoReportes(View v) {
    	Intent intent = new Intent(this, GuardadosActivity.class);
    	startActivity(intent);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	datasource.open();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	datasource.close();
    }
    
    
    // data prueba
    private void createData() {
    	Reporte reporte = new Reporte();
    	reporte.setTitle("Reporte 1");
    	reporte.setCoordenadas(103.2001);
    	reporte.setTipo("Árbol");
    	reporte.setDescription("Arbol a punto de caer");
    	reporte.setImage("una_imagen");
    	reporte = datasource.create(reporte);
    	Log.i(LOGTAG, "Inserción correcta con el id " + reporte.getId());
    	
    	reporte = new Reporte();
    	reporte.setTitle("Reporte 2");
    	reporte.setCoordenadas(103.2001);
    	reporte.setTipo("Bache");
    	reporte.setDescription("Bache abierto");
    	reporte.setImage("dos_imagen");
    	reporte = datasource.create(reporte);
    	Log.i(LOGTAG, "Inserción correcta con el id " + reporte.getId());
    	
    	reporte = new Reporte();
    	reporte.setTitle("Reporte 2");
    	reporte.setCoordenadas(103.2001);
    	reporte.setTipo("Luminaria");
    	reporte.setDescription("lUMINARIA APAGADA");
    	reporte.setImage("tres_imagen");
    	reporte = datasource.create(reporte);
    	Log.i(LOGTAG, "Inserción correcta con el id " + reporte.getId());
    }
    
    
}
