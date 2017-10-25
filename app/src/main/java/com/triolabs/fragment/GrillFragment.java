package com.triolabs.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import com.triolabs.adapter.GrillAdapter;
import com.triolabs.model.ListProgram;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.DateCoverter;
import com.triolabs.util.Font;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* GrillFragment es el fragmento muestra la informacion de la parrilla descargada en el splash
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class GrillFragment extends Fragment implements OnClickListener{

    static HashMap<Integer,ArrayList<String>> programmation;
    static HashMap<Integer,ArrayList<String>> programmationId;
    static HashMap<Integer,ArrayList<String>> programmationHour;
    static DateCoverter date;
    static RelativeLayout layoutMonday;
    static RelativeLayout layoutThursday;
    static RelativeLayout layoutWednesday;
    static RelativeLayout layoutTuesday;
    static RelativeLayout layoutFriday;
    static RelativeLayout layoutSaturday;
    static RelativeLayout layoutSunday;
	static TextView textMonday;
	static TextView textThursday;
	static TextView textWednesday;
	static TextView textTuesday;
	static TextView textFriday;
	static TextView textSaturday;
	static TextView textSunday;
	static GrillFragment self;
	static ListView listGrill;
	static GrillAdapter grillAdapter;
	private static boolean grill;
	static HorizontalScrollView scrollDays;
	
	/** Se crea una instancia de GrillFragment se incializa self 
 	 */
	public GrillFragment(){
		self=this;
	}
	
	/**
	 *  Metodo onCreateView obtiene los datos de la parrilla y inicializa los componentes de la vista
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_grill, container,
				false);
		setGrill(true);
		TextView textDate =(TextView)rootView.findViewById(R.id.grill_title_rigth);
		TextView textLeft =(TextView)rootView.findViewById(R.id.grill_title_left);
		textMonday =(TextView)rootView.findViewById(R.id.grill_day_monday_text);
		textThursday =(TextView)rootView.findViewById(R.id.grill_day_thursday_text);
		textWednesday =(TextView)rootView.findViewById(R.id.grill_day_wednesday_text);
		textTuesday=(TextView)rootView.findViewById(R.id.grill_day_tuesday_text);
		textFriday=(TextView)rootView.findViewById(R.id.grill_day_friday_text);
		textSaturday=(TextView)rootView.findViewById(R.id.grill_day_saturday_text);
		textSunday=(TextView)rootView.findViewById(R.id.grill_day_sunday_text);
		layoutMonday =(RelativeLayout)rootView.findViewById(R.id.grill_day_monday);
		layoutThursday =(RelativeLayout)rootView.findViewById(R.id.grill_day_thursday);
		layoutWednesday =(RelativeLayout)rootView.findViewById(R.id.grill_day_wednesday);
		layoutTuesday =(RelativeLayout)rootView.findViewById(R.id.grill_day_tuesday);
		layoutFriday =(RelativeLayout)rootView.findViewById(R.id.grill_day_friday);
		layoutSaturday =(RelativeLayout)rootView.findViewById(R.id.grill_day_saturday);
		layoutSunday =(RelativeLayout)rootView.findViewById(R.id.grill_day_sunday);
		listGrill=(ListView)rootView.findViewById(R.id.grill_list);
		layoutMonday.setOnClickListener(self);
		layoutThursday.setOnClickListener(self);
		layoutWednesday.setOnClickListener(self);
		layoutTuesday.setOnClickListener(self);
		layoutFriday.setOnClickListener(self);
		layoutSaturday.setOnClickListener(self);
		layoutSunday.setOnClickListener(self);
		date = new DateCoverter();
		String dateString =date.getNameMonth()+" "+date.getYear();
		textDate.setText(dateString);
		Font font = new Font();
		font.changeFontHelvetica(getActivity(), textDate);
		font.changeFontIntro(getActivity(), textLeft);
		font.changeFontHelvetica(getActivity(), textMonday);
		font.changeFontHelvetica(getActivity(), textThursday);
		font.changeFontHelvetica(getActivity(), textWednesday);
		font.changeFontHelvetica(getActivity(), textTuesday);
		font.changeFontHelvetica(getActivity(), textFriday);
		font.changeFontHelvetica(getActivity(), textSaturday);
		font.changeFontHelvetica(getActivity(), textSunday);
		LinearLayout layoutTvOnline =(LinearLayout)rootView.findViewById(R.id.grill_label);
		layoutTvOnline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.sendRadioOnline();
			}});
		scrollDays =(HorizontalScrollView)rootView.findViewById(R.id.grill_days);
		programmation=ListProgram.getProgrammation();
		programmationId=ListProgram.getProgrammationId();
		programmationHour=ListProgram.getProgrammationHour();
		selectDay(date.getDay()-1);
		return rootView;
	}
	

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
			case R.id.grill_day_monday:
				selectDay(1);
				break;
			case R.id.grill_day_thursday:
				selectDay(4);
				break;
			case R.id.grill_day_wednesday:
				selectDay(3);
				break;
			case R.id.grill_day_tuesday:
				selectDay(2);
				break;
			case R.id.grill_day_friday:
				selectDay(5);
				break;
			case R.id.grill_day_saturday:
				selectDay(6);
				break;
			case R.id.grill_day_sunday:
				selectDay(0);
				break;
		}
		
	}
	
	/**
	 *  Metodo setScroll desplaza el scroll al lado derecho
     */
	private static void setScroll(){
		scrollDays.post(new Runnable() { 
	        public void run() { 
	        	scrollDays.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
	        } 
		});
	}

	/**
	 *  Metodo isGrill recurpera el booleano para saber si se esta en la vista
	 *  @return boolean TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static boolean isGrill() {
		return grill;
	}

	/**
	 *  Metodo setGrill asigna el booleano para saber si se esta en la vista
	 *  @param programming TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static void setGrill(boolean grill) {
		GrillFragment.grill = grill;
	}
	
	/**
	 *  Metodo selectDay carga la lista segun el dia y cambia de color el tab
	 *  @param position es numero de dia 0 a 7
     */
	private static void selectDay(int position){
		try{
			if(programmation.get(position)!=null){
				grillAdapter = new GrillAdapter(self.getActivity(),programmation.get(position),programmationHour.get(position));
				listGrill.setAdapter(grillAdapter);
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		switch(position){
		case 1:
			textMonday.setTextColor(Color.WHITE);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.BLACK);
			break;
		case 4:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.WHITE);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.BLACK);
			break;
		case 3:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.WHITE);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.BLACK);
			break;
		case 2:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.WHITE);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.BLACK);
			break;
		case 5:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.WHITE);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.BLACK);
			setScroll();
			break;
		case 6:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.WHITE);
			textSunday.setTextColor(Color.BLACK);
			setScroll();
			break;
		case 0:
			textMonday.setTextColor(Color.BLACK);;
			textThursday.setTextColor(Color.BLACK);
			textWednesday.setTextColor(Color.BLACK);
			textTuesday.setTextColor(Color.BLACK);
			textFriday.setTextColor(Color.BLACK);
			textSaturday.setTextColor(Color.BLACK);
			textSunday.setTextColor(Color.WHITE);
			setScroll();
			break;
		}
	}
	
	/*public static void getInfoGrill(JSONObject json){
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
		
		try {
			programmation = new HashMap<Integer,ArrayList<String>>();
			programmationId = new HashMap<Integer,ArrayList<String>>();
			programmationHour = new HashMap<Integer,ArrayList<String>>();
			JSONArray jarray = json.getJSONArray("weekly_programmation");
			for(int i=0;i<jarray.length();i++){
				JSONArray jarrayDay = jarray.getJSONArray(i);
				ArrayList<String> listProgram=new ArrayList<String>();
				ArrayList<String> listHour = new ArrayList<String>();
				ArrayList<String> listProgramId=new ArrayList<String>();
				for(int j=0;j<jarrayDay.length();j++){
					JSONObject jObj = jarrayDay.getJSONObject(j);
					listProgramId.add(jObj.getString("kaltura_id"));
					listProgram.add(jObj.getString("program_name"));
					listHour.add(jObj.getString("horario"));
				}
				programmation.put(i, listProgramId);
				programmation.put(i, listProgram);
				programmationHour.put(i, listHour);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//selectDay(date.getDay()-1);
	}*/

}
