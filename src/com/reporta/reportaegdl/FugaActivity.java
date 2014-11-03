package com.reporta.reportaegdl;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FugaActivity extends Activity {
	
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	GoogleMap mMap;  
	
	ImageView iv;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fuga_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		
		if (servicesOK()) {
			Toast.makeText(this, "Listo para los mapas", Toast.LENGTH_SHORT).show();
		}
		
		Button b = (Button) findViewById(R.id.fotoBoton);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else {
			Toast.makeText(this, "Error al conectar a Google Play", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Bitmap bm = (Bitmap) data.getExtras().get("data");
		
		iv.setImageBitmap(bm);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
