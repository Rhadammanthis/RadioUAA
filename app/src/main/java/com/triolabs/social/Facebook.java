package com.triolabs.social;


/**
* Esta clase fecebook realiza todo lo referente de facebook
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Jose Maria Armendariz, Alex Campos, Ivan
* @version 1.0
*/
import com.triolabs.util.Device;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Facebook {
	
	Context context;//contexto de la app
	

	/** construcor Facebook
	 * @param context de la actividad donde se instancia
 	 */
	public Facebook(Context context){
		this.context=context;
	}
	
	/** Metodo shareFacebookWithIntent publica una url en facebook
	 * @param: url que se va a compartir
 	 */
	public void shareFacebookWithIntent(String url,String nameProgram){
		//se crea el intent
		Intent facebookIntent = new Intent(Intent.ACTION_SEND);
		facebookIntent.putExtra(Intent.EXTRA_TEXT, url);
		facebookIntent.setType("text/plain");
		Device device = new Device();
		if(device.isExistApp(context, facebookIntent,"com.facebook.katana")){
			// si el dispositivo ya tiene la app de facebook instalada
			context.startActivity(facebookIntent);
		}else{
			// si el dispositivo no tiene la app de facebook instalada se manda 
			//al navegador con un metodo de php para compartir
			String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + url;
			facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
			context.startActivity(facebookIntent);
		}
	}

}
