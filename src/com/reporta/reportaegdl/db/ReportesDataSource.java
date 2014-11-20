package com.reporta.reportaegdl.db;

import com.reporta.reportaegdl.model.Reporte;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportesDataSource {
	
	private static final String LOGTAG = "REPORTAGDL";
	
	SQLiteOpenHelper dbhelper; 
	SQLiteDatabase database;
	
	public ReportesDataSource(Context context) {
		dbhelper = new ReportesDBOpenHelper(context);
	}
	
	public void open() {
		Log.i(LOGTAG, "Conexion a la BD abierta");
		database = dbhelper.getWritableDatabase();
	}
	
	public void close() {
		Log.i(LOGTAG, "Conexion a la BD cerrada");
		dbhelper.close();
	}
	
	public Reporte create(Reporte reporte) {
		ContentValues values = new ContentValues();
		values.put(ReportesDBOpenHelper.COLUMNA_TITULO, reporte.getTitle());
		values.put(ReportesDBOpenHelper.COLUMNA_COORDENADAS, reporte.getCoordenadas() );
		values.put(ReportesDBOpenHelper.COLUMNA_TIPO, reporte.getTipo());
		values.put(ReportesDBOpenHelper.COLUMNA_MENSAJE, reporte.getDescription());
		values.put(ReportesDBOpenHelper.COLUMNA_IMAGEN, reporte.getImage());
	}
}
