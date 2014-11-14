package com.reporta.reportaegdl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    	startActivity(intent);
    }
    
    // Intent para la clase de Luminaria
    public void gotoLuminaria(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar una Luminaria");
    	startActivity(intent);
    }
    
    // Intent para la clase de Fuga
    public void gotoFuga(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar una Fuga de Agua");
    	startActivity(intent);
    }
    
    // Intent para la clase de Arbol
    public void gotoArbol(View v) {
    	Intent intent = new Intent(this, ArbolActivity.class);
    	intent.putExtra("titulo","Reportar un Árbol");
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
}
