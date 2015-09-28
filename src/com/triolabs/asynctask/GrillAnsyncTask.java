package com.triolabs.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.triolabs.fragment.SplashFragment;
import com.triolabs.kaltura.Constant;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.webservice.RESTClient;
import com.triolabs.webservice.RESTClient.RequestMethod;

import android.R;
import android.os.AsyncTask;
import android.util.Log;

/**
* GrillAnsyncTask realiza el request para obtener las imagenes y mostrarlas en el imageview
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class GrillAnsyncTask extends AsyncTask<String, Integer,String>{
	
	private String URL;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		setURL();
		MainActivity.ansyncTask=true;
	}
	
	/**Se recupera la URL creada
	 * @return URL ala cual se va hacer el request
     */
	public String getURL() {
		return URL;
	}

	/**Se crea la URL la cual se va apuntar el request
     */
	public void setURL() {
		URL = Constant.URL_GRILL;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		 RESTClient request=new RESTClient(getURL());
		 request.AddParam("action", "get_programmation");
		 request.AddParam("db", "radio");
	        try {
				request.Execute(RequestMethod.POST);
				return request.getResponse();
			} 
	        catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null){
			Log.i("grill", result);
			JSONObject json=null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SplashFragment.getInfoGrill(json);
			return;
		}	
		SplashFragment.getInfoGrill(null);
	}

}
