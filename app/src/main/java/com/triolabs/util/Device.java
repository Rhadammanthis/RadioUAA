package com.triolabs.util;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.DisplayMetrics;

/**
 * Esta clase Device realiza todas las cosas refente al dispositivo
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class Device {
	
	private static int Width;
	private static int Heigth;
	static DisplayMetrics display;

	/** Se crea una instancia de Device 
 	 */
	public Device(){	
	}
	
	/** Metodo inicializa el display con las medidas del dispositivo
	 * @param context contexto de la actividad
	 */
	public void init(Context context){
		display = context.getResources().getDisplayMetrics();
		setWidth(display.widthPixels);
		setHeigth(display.heightPixels);
	}

	/**
	 * @return the width
	 */
	public static int getWidth() {
		return Width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		Width = width;
	}
	/**
	 * @return the heigth
	 */
	public static int getHeigth() {
		return Heigth;
	}

	/**
	 * @param heigth the heigth to set
	 */
	public static void setHeigth(int heigth) {
		Heigth = heigth;
	}
	
	/** Metodo isExistApp checa si existe una aplicacion instalada en el dispositivo
	 * @param context contexto de la actividad
	 * @param intent intento que se va verificar
	 * @param nameApp nombre del paquete de la app
	 * @return TRUE si existe la app o FALSE si no existe la app
	 */
	public boolean isExistApp(Context context,Intent intent,String nameApp){
		PackageManager packManager = context.getPackageManager();
		List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent,  PackageManager.MATCH_DEFAULT_ONLY);

		boolean resolved = false;
		for(ResolveInfo resolveInfo: resolvedInfoList){
		   if(resolveInfo.activityInfo.packageName.startsWith(nameApp)){
			   intent.setClassName(
		           resolveInfo.activityInfo.packageName, 
		           resolveInfo.activityInfo.name );
		       resolved = true;
		       break;
		   }
		}
		return resolved;
	}
}
