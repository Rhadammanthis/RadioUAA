
package com.triolabs.social;

import com.triolabs.util.Device;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

/**
* Esta clase twitter realiza todo lo referente de facebook
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Jose Maria Armendariz, Alex Campos, Ivan
* @version 1.0
*/
public class Twitter {
	
	Context context;//contexto de la app
	
	
	/** construcor Twitter
	 * @param context de la actividad donde se instancia
 	 */
	public Twitter(Context context){
		this.context=context;
	}
	
	/** metodo shareTwitterWithIntent publica una status en twitter
	 * @param status que se va a compartir
 	 */
	public void shareTwitterWithIntent(String status){
		//se crea el intent
		Intent twitterIntent = new Intent(Intent.ACTION_SEND);
		twitterIntent.putExtra(Intent.EXTRA_TEXT, status);
		twitterIntent.setType("text/plain");
		Device device = new Device();
		if(device.isExistApp(context, twitterIntent, "com.twitter.android")){
			// si el dispositivo ya tiene la app de twitter instalada
		   context.startActivity(twitterIntent);
		}else{
			// si el dispositivo no tiene la app de twitter instalada se manda 
			//al navegador con un metodo de twitter para compartir
			String tweetUrl = "https://twitter.com/intent/tweet?text="+status;
			Uri uri = Uri.parse(tweetUrl);
			twitterIntent=new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(twitterIntent);
		}
	}

}
