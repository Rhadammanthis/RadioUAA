package com.triolabs.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Esta clase Image es para la creacion de bitmap
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class Image {
	
	private static Map<String, Bitmap> mapImage = new HashMap<String, Bitmap>();
	
	/** Se crea una instancia de Image 
 	 */
	public Image(){
	
	}
	
	/** Metodo getBitmapFromURL crea un bitmap desde una URL
	 * @param urlString  string con la url de la imagen
	 * @return Bitmap que se creo de la url o NULL si no se pudo crear bitmap
 	 */
	public Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap bitmap = BitmapFactory.decodeStream(input);
	        return bitmap;
	    } catch (IOException e) {
	        // Log exception
	        return null;
	    }
	}
	
	/** Metodo setBitmapScale crea un bitmap con la escala especifica
	 * @param bitmap bitmap de que se creara la escala 
	 * @param width ancho de la escala 
	 * @param heigth alto de la escala
	 * @return Bitmap que se escalo
 	 */
	public Bitmap setBitmapScale(Bitmap bitmap, int width, int heigth){
		return Bitmap.createScaledBitmap(bitmap,width, heigth, false);
	}

	/** Metodo addBitmapToMemoryCache se agrega un bitmap ala memoria cache
	 * @param image que se guardara
	 * @param key identificador del bitmap
 	 */
	public void addBitmapToMemoryCache(Bitmap image,String key){
		mapImage.put(key, image);
	}
	
	/** Metodo getBitmapFromMemCache regresa un bitmap de guardado
	 * @param key identificador del bitmap 
	 * @param width ancho de la escala
	 * @param heigth alto de la escala
	 * @return Bitmap que se recupero o NULL si no se pudo recuperar
 	 */
	public Bitmap getBitmapFromMemCache(String key,int width, int heigth){
		if(mapImage.get(key)!=null)
			return Bitmap.createScaledBitmap(mapImage.get(key),width, heigth, false);
		else
			return null;
		
	}
}
