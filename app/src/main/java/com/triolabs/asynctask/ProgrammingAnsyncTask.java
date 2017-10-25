package com.triolabs.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.triolabs.fragment.CategoriesFragment;
import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.kaltura.Constant;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.webservice.RESTClient;
import com.triolabs.webservice.RESTClient.RequestMethod;

import android.os.AsyncTask;
import android.util.Log;

/**
* ProgrammingAnsyncTask realiza el request para traer la lista de los programas
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ProgrammingAnsyncTask extends AsyncTask<String, Integer,String> {

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
		URL = Constant.HOST_KALTURA+"/api_v3/?service="+Constant.SERVICE_KALTURA_PLAYLIST+"&action="
					+Constant.ACTION_KALTURA_LIST+"&pager:pageSize&ks="+Constant.KS_KALTURA+"&format="+Constant.FORMAT_KALTURA;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		 RESTClient request=new RESTClient(getURL());
	        try {
				request.Execute(RequestMethod.GET);
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
			Log.i("playlist", result);
			JSONObject json=null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(ProgrammingFragment.programType!= null)
				ProgrammingFragment.getEntryId(json);
			CategoriesFragment.getEntryId(json);
			//ProgrammingFragment.getEntryId(json);
			return;
		}
		CategoriesFragment.getEntryId(null);
		//ProgrammingFragment.getEntryId(null);
			
	}

}
