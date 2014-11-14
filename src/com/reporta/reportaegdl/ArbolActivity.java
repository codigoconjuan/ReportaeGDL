package com.reporta.reportaegdl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.reporta.reportaegdl.WorkaroundMapFragment;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;

import android.widget.ScrollView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat") public class ArbolActivity extends FragmentActivity implements 
 GooglePlayServicesClient.ConnectionCallbacks,
 GooglePlayServicesClient.OnConnectionFailedListener
{
	//variables tomar foto
	ImageView ivImage;
	
	

	
	
	// variables mapas
	
	
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	private ScrollView mScrollView;
	private GoogleMap mMap;  
	LocationClient mLocationClient; 
	Marker marker; 

	private static final double 
		GUADALAJARA_LAT = 20.6734406,
		GUADALAJARA_LON = -103.3442215;
	
	private static final float DEFAULTZOOM = 13;
	private static final float DEFAULTZOOMTOUCH = 16;
	private static final int REQUEST_CAMERA = 0;
	private static final int SELECT_FILE = 0;
	private String urlFile = "";
	// 
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    
    private Bitmap bm;
     
 
    
    
	

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			if (servicesOK()) {
				
				setContentView(R.layout.arbol_activity);
				
				
				/** DINAMICAMENTE AGARRAR EL TITULO **/
				
				String value = "";
				Bundle extras = getIntent().getExtras();
				if(extras !=null) {
				    value = extras.getString("titulo");
				}
				final TextView mTextView = (TextView) findViewById(R.id.textView1);
				mTextView.setText(value);
				
				
				
				
				//imagenes
				uploadButton = (Button)findViewById(R.id.enviarArbol);
			    messageText  = (TextView)findViewById(R.id.messageText);
			    messageText.setText("Uploading file path :- '"+urlFile+"'");
			    
			     /************* Php script path ****************/
		        upLoadServerUri = "http://sistemasmexicanos.com/android/guardar.php";
		         
		        uploadButton.setOnClickListener(new OnClickListener() {            
		            @Override
		            public void onClick(View v) {
		                dialog = ProgressDialog.show(ArbolActivity.this, "", "Uploading file...", true);
		                new Thread(new Runnable() {
		                        public void run() {
		                             runOnUiThread(new Runnable() {
		                                    public void run() {
		                                        messageText.setText("uploading started.....");
		                                    }
		                                });                      
		                           
		                             //uploadFile(uploadFilePath + "" + uploadFileName);
		                             uploadFile(urlFile);
		                                                      
		                        }
		                      }).start();        
		                }
		            });

			        
			        // fin imagenes
	
		        mMap = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		        mScrollView = (ScrollView) findViewById(R.id.scrollview);
		        
			      ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
			          @Override
			          public void onTouch() {
			              mScrollView.requestDisallowInterceptTouchEvent(true);
			          }
			     });
		        
				if (initMap()) {
					Toast.makeText(this, "Listo para los mapas", Toast.LENGTH_SHORT).show();
					gotoLocation(GUADALAJARA_LAT, GUADALAJARA_LON, DEFAULTZOOM);
					mLocationClient = new LocationClient(this, this, this);
					mLocationClient.connect();
					
					mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
						
						@Override
						public void onMapLongClick(LatLng ll) {
							Geocoder gc = new Geocoder(ArbolActivity.this);
							List<Address> list = null;
							
							try {
								list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
							} catch (IOException e) {
								
								e.printStackTrace();
								return;
							}
							
							Address add = list.get(0);
							ArbolActivity.this.setMarker(add.getLocality(), add.getCountryName(), ll.latitude, ll.longitude );
						}
					});
					
					mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
						
						public boolean onMarkerClick(Marker marker) {
							String msg = marker.getTitle() + " (" +marker.getPosition().latitude + "," + marker.getPosition().longitude + ")";
							Toast.makeText(ArbolActivity.this, msg, Toast.LENGTH_LONG).show();
 							return false;
						}
					});
					
					mMap.setOnMarkerDragListener(new OnMarkerDragListener() {
						
						@Override
						public void onMarkerDragStart(Marker arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onMarkerDragEnd(Marker marker) {
							Geocoder gc = new Geocoder(ArbolActivity.this);
							List<Address> list = null;
							LatLng ll = marker.getPosition();
							
							try {
								list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
							} catch (IOException e) {
								
								e.printStackTrace();
								return;
							}
							
							Address add = list.get(0);
							marker.setTitle(add.getLocality());
							marker.setSnippet(add.getCountryName());
							marker.showInfoWindow();
							
						}
							
						
						
						@Override
						public void onMarkerDrag(Marker arg0) {
						
							
						}
					});
					
					

					
					
				} 
				else {
					Toast.makeText(this, "No se pudo mostrar el mapa", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				setContentView(R.layout.arbol_activity);
			}
			
		
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
			
			// boton geolocalizacion
			Button geo = (Button) findViewById(R.id.ubicacion);
			geo.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					gotoCurrentLocation();
				}
			});
			
			// boton Imagenes
			Button camera = (Button) findViewById(R.id.fotoBoton);
			camera.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					selectImage();
				}
			});

	}
	
	private void setMarker(String locality, String country, double lat, double lng) {
		
		if (marker != null) {
			marker.remove();
		}

		MarkerOptions options = new MarkerOptions()	
			.title(locality)
			.position(new LatLng(lat, lng))
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
			.draggable(true);
		if (country.length() > 0) {
			options.snippet(country);
		}
		
		marker = mMap.addMarker(options);
		
	}

	private void gotoLocation(double lat, double lng, float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.moveCamera(update);
		
	
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
	
	private boolean initMap() {
		if(mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
		}
		return (mMap != null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
		
		
		if (resultCode == RESULT_OK) {

			if (requestCode == REQUEST_CAMERA) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					//Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					bm = BitmapFactory.decodeFile(f.getAbsolutePath(), btmapOptions);
					
										
				    bm = Bitmap.createScaledBitmap(bm, 700, 700, true);
					ivImage.setImageBitmap(bm);

				    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				    String imageFileName = "JPEG_" + timeStamp + "_";
				    File storageDir = Environment.getExternalStoragePublicDirectory(
				            Environment.DIRECTORY_PICTURES);
				    File image = File.createTempFile(
				        imageFileName,  /* prefix */
				        ".jpg",         /* suffix */
				        storageDir      /* directory */
				    );
					
		
					f.delete();
					
					OutputStream fOut = null;
					
					File file = new File(image.getAbsolutePath());
					
					
					//** URL DEL ARCHIVO ***/
					urlFile = image.getPath();
					Log.v("La url es", urlFile);
					
					
					
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 1, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == SELECT_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, ArbolActivity.this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				ivImage.setImageBitmap(bm);
			}
			

		}
		
		
		
	}


	
	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.ubicacion:
			gotoCurrentLocation();
			break;
		case R.id.action_settings:
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	

	
	// Geolocalización function
	
	protected void gotoCurrentLocation() {
		Location currentLocation = mLocationClient.getLastLocation();
		if (currentLocation == null) {
			Toast.makeText(this, "Current location isn't available", Toast.LENGTH_SHORT).show();
		}
		else {
			LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOMTOUCH);
			mMap.animateCamera(update);
			
			if(marker!=null) {
				marker.remove();
			}
			
			//marker
			MarkerOptions options = new MarkerOptions()
			.position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));

			
			marker = mMap.addMarker(options);
		}
	}
	

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Conectado a Geolocalizacion", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	

	
	private void selectImage() {
		final CharSequence[] items = { "Tomar una Fotografia", "Elegir de la Librería",
				"Cancelar" };

		AlertDialog.Builder builder = new AlertDialog.Builder(ArbolActivity.this);
		builder.setTitle("Agregar una Fotografia");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Tomar una Fotografia")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, REQUEST_CAMERA);
				} else if (items[item].equals("Elegir de la Librería")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Seleccionar Archivo"),
							SELECT_FILE);
				} else if (items[item].equals("Cancelar")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}


    public int uploadFile(String sourceFileUri) {
        
        
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
         
        if (!sourceFile.isFile()) {
             
             dialog.dismiss(); 
              
             Log.e("uploadFile", "Source File not exist :"
                                 +urlFile);
              
             runOnUiThread(new Runnable() {
            	 
                 public void run() {
                     messageText.setText("Source File not exist :"
                             +urlFile);
                             
                 }
                 
             }); 
              
             return 0;
          
        }
        else
        {
        	try { 
                
                // open a URL connection to the Servlet
              //FileInputStream fileInputStream = new FileInputStream(sourceFile);
         //BitmapDrawable map = (BitmapDrawable)ivImage.getDrawable();
       //  Bitmap bitmap = map.getBitmap();
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
			   bm.compress(Bitmap.CompressFormat.JPEG, 100, stream); // convert Bitmap to ByteArrayOutputStream
			   InputStream fileInputStream = new ByteArrayInputStream(stream.toByteArray());
   
   
                 URL url = new URL(upLoadServerUri);
                 
                 
                 
                 
                 // Open a HTTP  connection to  the URL
                 conn = (HttpURLConnection) url.openConnection(); 
                 conn.setDoInput(true); // Allow Inputs
                 conn.setDoOutput(true); // Allow Outputs
                 conn.setUseCaches(false); // Don't use a Cached Copy
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                 conn.setRequestProperty("uploaded_file", fileName); 
                  
                 dos = new DataOutputStream(conn.getOutputStream());
        
                 dos.writeBytes(twoHyphens + boundary + lineEnd); 
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=" + fileName + " " + lineEnd);
                  
                 dos.writeBytes(lineEnd);
        
                 // create a buffer of  maximum size
                 bytesAvailable = fileInputStream.available(); 
        
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
        
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                    
                 while (bytesRead > 0) {
                      
                   dos.write(buffer, 0, bufferSize);
                   bytesAvailable = fileInputStream.available();
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                    
                  }
        
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        
                 // Responses from the server (code and message)
                 serverResponseCode = conn.getResponseCode();
                 String serverResponseMessage = conn.getResponseMessage();
                   
                 Log.i("uploadFile", "HTTP Response is : "
                         + serverResponseMessage + ": " + serverResponseCode);
                  
                 if(serverResponseCode == 200){
                      
                     runOnUiThread(new Runnable() {
                          public void run() {
                               
                              String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                            +" http://www.androidexample.com/media/uploads/"
                                            +urlFile;
                               
                              messageText.setText(msg);
                              Toast.makeText(ArbolActivity.this, "File Upload Complete.", 
                                           Toast.LENGTH_SHORT).show();
                          }
                      });                
                 }    
                  
                 //close the streams //
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                   
            } catch (MalformedURLException ex) {
                 
                dialog.dismiss();  
                ex.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(ArbolActivity.this, "MalformedURLException", 
                                                            Toast.LENGTH_SHORT).show();
                    }
                });
                 
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {
                 
                dialog.dismiss();  
                e.printStackTrace();
                 
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(ArbolActivity.this, "Got Exception : see logcat ", 
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                                                 + e.getMessage(), e);  
            }
            dialog.dismiss();       
            return serverResponseCode; 
             
         } // End else block 
       } 

	
}
