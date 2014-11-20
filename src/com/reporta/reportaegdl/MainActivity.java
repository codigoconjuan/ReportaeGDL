package com.reporta.reportaegdl;

import com.reporta.reportaegdl.db.ReportesDBOpenHelper;
import com.reporta.reportaegdl.db.ReportesDataSource;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
	
	
	// crear la conexion a la BD
	ReportesDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
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
}
