package com.triolabs.asynctask;

import com.triolabs.adapter.ProgramDetailAdapter;
import com.triolabs.kaltura.Constant;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.webservice.RESTClient;
import com.triolabs.webservice.RESTClient.RequestMethod;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
* LikeAsyncTask realiza el request para hacer un like a un capitulo
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class LikeAsyncTask  extends AsyncTask<String, Integer,String>{
	
	private String URL;
	private String entryId;
	TextView textHeart;
	
	/**Se crea una instancia de LikeAsyncTask
	 * @param entryId id del capitulo
	 * @param textHeart textview donde se aumentara el like
     */
	public LikeAsyncTask(String entryId,TextView textHeart){
		this.entryId=entryId;
		this.textHeart=textHeart;
		
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		setURL();
		MainActivity.ansyncTask=true;
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
			return "{null}";
		}
	}


	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.i("resultLike", result.trim());
		ProgramDetailAdapter.setLikeHeart(result.trim(),entryId,textHeart);
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
		URL = Constant.HOST_KALTURA+"/api_v3/?service=media&action=anonymousRank&rank=1&entryId="+entryId+"&ks="+Constant.KS_KALTURA+"&format=1";
	}

}
