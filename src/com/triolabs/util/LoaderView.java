package com.triolabs.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * Esta clase LoaderView se crea un progressDialog para mostrar que esta cargando
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class LoaderView {
	
	ProgressDialog progress;

	/** Se crea una instancia de LoaderView donde se inicalizara para mostrar y ocultar
	 * @param context es el contexto donte se mostara o ocultara el progress
 	 */
	public LoaderView(Context context){
		if(context==null)
			return;
		progress = new ProgressDialog(context);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Cargando...");
		progress.setIndeterminate(true);
		progress.setCancelable(false);

	}
	
	/** Metodo show muestra el progress
 	 */
	public void show(){
		if(progress==null)
			return;
		progress.show();
	}
	
	/** Metodo hide oculta el progress
 	 */
	public void hide(){
		if(progress==null)
			return;
		progress.hide();
	}
	
	public void hideDelayed(){
		if(progress==null)
			return;
		Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	progress.hide();
	        }
	    }, 3000);
	}
	


}
