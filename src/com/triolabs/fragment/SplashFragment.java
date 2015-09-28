package com.triolabs.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.triolabs.kaltura.Constant;
import com.triolabs.kaltura.Kaltura;
import com.triolabs.model.ListProgram;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SplashFragment extends Fragment {
	
	static SplashFragment self;
	
	public SplashFragment() {
		self=this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_splash, container,
				false);
		postDelayed();
		return rootView;
	}
	
	private void postDelayed(){
		Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	Kaltura kaltura = new Kaltura();
	    		kaltura.login();
	        }
	    }, 3000); 
	}
	
	public static void changeViewMain(){
		if(Constant.KS_KALTURA==""||Constant.KS_KALTURA==null){
			AlertDialog.Builder builder = new AlertDialog.Builder(self.getActivity());
			builder.setMessage(self.getResources().getString(R.string.alert_content))
			        .setTitle((self.getResources().getString(R.string.alert_title)))
			        .setCancelable(false)
			        .setNegativeButton((self.getResources().getString(R.string.alert_cancel)),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                        dialog.cancel();
			                        self.getActivity().finish();
			                    }
			                })
			        .setPositiveButton(self.getResources().getString(R.string.alert_acept),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                    	Kaltura kaltura = new Kaltura();
			                		kaltura.login();
			                    }
			                });
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
		Kaltura kaltura = new Kaltura();
		kaltura.getGrill();
	}
	
	/**
     * Metodo getInfoGrill despues de ejecutar el ansyctask de la parrilla de programas
     * regresa a este metodo para mandar ala vista principal
     * @param json contiene la parrilla de los programas
     */
	public static void getInfoGrill(JSONObject json){
		
		if(json==null){
			AlertDialog.Builder builder = new AlertDialog.Builder(self.getActivity());
			builder.setMessage(self.getResources().getString(R.string.alert_content))
			        .setTitle(self.getResources().getString(R.string.alert_title))
			        .setCancelable(false)
			        .setNegativeButton(self.getResources().getString(R.string.alert_cancel),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                    	Kaltura kaltura = new Kaltura();
			                		kaltura.getGrill();
			                        dialog.cancel();
			                    }
			                })
			        .setPositiveButton(self.getResources().getString(R.string.alert_acept),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                		
			                    }
			                });
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
		HashMap<Integer,ArrayList<String>> programmation = new HashMap<Integer,ArrayList<String>>();
		HashMap<Integer,ArrayList<String>> programmationId = new HashMap<Integer,ArrayList<String>>();
		HashMap<Integer,ArrayList<String>> programmationHour = new HashMap<Integer,ArrayList<String>>();
		HashMap<Integer,ArrayList<String>> programmationHourEnd = new HashMap<Integer,ArrayList<String>>();
		JSONArray jarray = null;
		try {
			jarray = json.getJSONArray("weekly_programmation");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			for(int i=0;i<jarray.length();i++){
				JSONArray jarrayDay;
				ArrayList<String> listProgram=new ArrayList<String>();
				ArrayList<String> listHour = new ArrayList<String>();
				ArrayList<String> listProgramId=new ArrayList<String>();
				ArrayList<String> listHourEnd = new ArrayList<String>();
				try {
					jarrayDay = jarray.getJSONArray(i);
					
					for(int j=0;j<jarrayDay.length();j++){
						JSONObject jObj = jarrayDay.getJSONObject(j);
						listProgramId.add(jObj.getString("kaltura_id"));
						listProgram.add(jObj.getString("program_name"));
						listHour.add(jObj.getString("start_time").substring(0,jObj.getString("start_time").length()-3));
						listHourEnd.add(jObj.getString("end_time").substring(0,jObj.getString("end_time").length()-3));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				programmationId.put(i, listProgramId);
				programmation.put(i, listProgram);
				programmationHour.put(i, listHour);
				programmationHourEnd.put(i, listHourEnd);
			}
			ListProgram.setProgrammation(programmation);
			ListProgram.setProgrammationId(programmationId);
			ListProgram.setProgrammationHour(programmationHour);
			ListProgram.setProgrammationHourEnd(programmationHourEnd);
		
		//selectDay(date.getDay()-1);
		Intent intentMain = new Intent(self.getActivity(),MainActivity.class);
		self.startActivity(intentMain);
		self.getActivity().finish();
	}

}