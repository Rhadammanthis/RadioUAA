package com.triolabs.asynctask;

import com.triolabs.fragment.SplashFragment;
import com.triolabs.kaltura.Constant;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.webservice.RESTClient;
import com.triolabs.webservice.RESTClient.RequestMethod;

import android.os.AsyncTask;
import android.util.Log;

/**
* LoginAsyncTask realiza el request para hacer login en kaltura y traer el ks
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class LoginAsyncTask  extends AsyncTask<String, Integer,String> {
	
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
		URL = Constant.HOST_KALTURA+"/api_v3/?service="+Constant.SERVICE_KALTURA_LOGIN+"&action="
						+Constant.ACTION_KALTURA_LOGIN+"&loginId="+Constant.USER_KALTURA+"&password="+Constant.PASSWORD_KALTURA+"&format="+Constant.FORMAT_KALTURA;
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
		 	try {
		 		if(result!=null){
					result=result.replace("\"", "");
					result=result.replace("=", "").trim();
				}
				Constant.setKSKaltura(result);
				SplashFragment.changeViewMain();
			} 
	        catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	        
	}

}
