package com.reporta.reportaegdl.db;

import java.util.ArrayList;
import java.util.List;

import com.reporta.reportaegdl.model.Reporte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportesDataSource {
	
	private static final String LOGTAG = "REPORTAGDL";
	
	SQLiteOpenHelper dbhelper; 
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		ReportesDBOpenHelper.COLUMNA_ID, 
		ReportesDBOpenHelper.COLUMNA_TITULO, 
		ReportesDBOpenHelper.COLUMNA_TIPO, 
		ReportesDBOpenHelper.COLUMNA_MENSAJE, 
		ReportesDBOpenHelper.COLUMNA_COORDENADAS, 
		ReportesDBOpenHelper.COLUMNA_IMAGEN
	};
	
	
	
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
		long insertid = database.insert(ReportesDBOpenHelper.TABLE_REPORTES, null, values);
		reporte.setId(insertid);
		return reporte;
	}
	
	public List<Reporte> findAll() {
		List<Reporte> reportes = new ArrayList<Reporte>();
		
		Cursor cursor = database.query(ReportesDBOpenHelper.TABLE_REPORTES, allColumns,
				null, null, null, null, null);
		
		Log.i(LOGTAG, "Query" + cursor.getCount() + " entradas");
		
		if(cursor.getCount() > 0) {
			while (cursor.moveToNext() ) {
				Reporte reporte = new Reporte();
				
				reporte.setId(cursor.getLong(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_ID)));
				reporte.setTitle(cursor.getString(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_TITULO)));
				reporte.setTitle(cursor.getString(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_TITULO)));
				reporte.setDescription(cursor.getString(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_MENSAJE)));
				reporte.setCoordenadas(cursor.getDouble(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_COORDENADAS)));
				reporte.setImage(cursor.getString(cursor.getColumnIndex(ReportesDBOpenHelper.COLUMNA_IMAGEN)));
				reportes.add(reporte);
			}
			
		}
		
		return reportes;
		
		
	}
}
