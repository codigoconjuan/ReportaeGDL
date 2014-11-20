package com.reporta.reportaegdl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportesDBOpenHelper extends SQLiteOpenHelper {
	
	private static final String LOGTAG = "REPORTAGDL";
	
	private static final String DATABASE_NAME = "reportes.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_REPORTES = "reportes";
	public static final String COLUMNA_ID = "reporteId";
	public static final String COLUMNA_TITULO = "titulo";
	public static final String COLUMNA_COORDENADAS = "coordenadas";
	public static final String COLUMNA_TIPO = "tipo";
	public static final String COLUMNA_MENSAJE = "mensaje";
	public static final String COLUMNA_IMAGEN = "imagen";
	
	public static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_REPORTES + " (" +
			COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMNA_TITULO + " TEXT, " + 
			COLUMNA_COORDENADAS + " TEXT, " + 
			COLUMNA_TIPO + " TEXT, " + 
			COLUMNA_MENSAJE + " TEXT, " + 
			COLUMNA_IMAGEN + " TEXT " + 
			")";
	
	

	public ReportesDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "La tabla fue creada");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTES);
		onCreate(db);
	}

}
