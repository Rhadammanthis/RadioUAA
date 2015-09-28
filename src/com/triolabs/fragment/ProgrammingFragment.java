package com.triolabs.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.triolabs.adapter.ProgrammingAdapter;
import com.triolabs.kaltura.Constant;
import com.triolabs.kaltura.Kaltura;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.DateCoverter;
import com.triolabs.util.Font;
import com.triolabs.util.LoaderView;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
* ProgrammingFragment es el fragmento muestra la lista de programas
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ProgrammingFragment extends Fragment {
	
	private static boolean programming;
	static ListView listProgramView;
	public static ProgrammingFragment self;
	static Program program;
	static Chapter chapter;
	static ArrayList<Chapter> listC;
	static ListProgram listProgram;
	static ListProgram listChapter;
	static ArrayList<Program> listP;
	static LoaderView loader;
	static ArrayList<String[]> idChapters;
	
	public static String programType;
	
	/** Se crea una instancia de ProgrammingFragment se incializa self 
 	 */
	public ProgrammingFragment(){
		self=this;
	}
	
	/**
	 *  Metodo onCreateView ejecuta kaltura.getProgramming() para traer la lista de programas,
	 *  y inicializa los componentes
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_programming, container,
				false);
		setProgramming(true);
		getActivity().getActionBar().show();
		MainActivity.changeWhiteActionBar();
		Kaltura kaltura = new Kaltura();
		kaltura.getProgramming();
		listProgramView =(ListView)rootView.findViewById(R.id.programming_list);
		LinearLayout layoutTvOnline =(LinearLayout)rootView.findViewById(R.id.programming_label);
		layoutTvOnline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.sendRadioOnline();
			}});
		loader = new LoaderView(self.getActivity());
		
		Font font = new Font();
		TextView textLeft = (TextView)rootView.findViewById(R.id.programming_title_left);
		TextView textRigth = (TextView)rootView.findViewById(R.id.programming_title_rigth);
		TextView textSlogan = (TextView)rootView.findViewById(R.id.last_chapter_slogan);
		font.changeFontIntro(getActivity(), textSlogan);
		font.changeFontIntro(getActivity(), textLeft);
		font.changeFontIntro(getActivity(), textRigth);
		return rootView;
	}
	
	/**
	 *  Metodo updateListView actualiza la lista de los programas
	 *  @param listProgram es la lista de los programas a actualizar
     */
	public static void updateListView(ArrayList<Program> listProgram){
		ProgrammingAdapter adapter = new ProgrammingAdapter(self.getActivity(),listProgram);
		adapter.notifyDataSetChanged();
		listProgramView.setAdapter(adapter);
	}
	
	/**
	 *  Metodo getEntryId es la llamada de rergreso de el callback resgresa el json recuperado
	 *  @param json con la lista de programas
     */
	public static void getEntryId(JSONObject json){
		
		if(json==null){
			AlertDialog.Builder builder = new AlertDialog.Builder(self.getActivity());
			builder.setMessage(self.getResources().getString(R.string.alert_content))
			        .setTitle(self.getResources().getString(R.string.alert_title))
			        .setCancelable(false)
			        .setNegativeButton(self.getResources().getString(R.string.alert_cancel),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                        dialog.cancel();
			                    }
			                })
			        .setPositiveButton(self.getResources().getString(R.string.alert_acept),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                		Kaltura kaltura = new Kaltura();
			                		kaltura.getProgramming();
			                    }
			                });
			AlertDialog alert = builder.create();
			alert.show();
			MainActivity.ansyncTask=false;
			return;
		}
		
		program = new Program();
		listProgram = new ListProgram();
		listP = new ArrayList<Program>();
		listC = new ArrayList<Chapter>();
		idChapters = new ArrayList<String[]>();
		try {
			JSONArray jArray= json.getJSONArray("objects");
			String[] idArray = null;
			for(int i=0;i<jArray.length();i++){
				JSONObject jObj = jArray.getJSONObject(i);
				String idVideos=jObj.getString("playlistContent");
				idArray=idVideos.split(",");
				idChapters.add(idArray);
				program.setIdChapter(idArray);
				program.setIdProgram(jObj.getString("id"));
				program.setNameProgram(jObj.getString("name"));
				program.setSynopsisProgram(jObj.getString("description"));
				String stringTags =jObj.getString("tags");
				stringTags=stringTags.replace("$", "\"");
				Log.i("stringTags", stringTags);
				try {
					JSONObject jsonTags=new JSONObject(stringTags);
					String urlPhotoProgram =Constant.HOST_KALTURA+"/p/101/sp/10100/thumbnail/entry_id/"+jsonTags.getString("id_thumbnail")+"/def_height/480/def_width/640/version/100000/type/1";
					program.setPhotoProgram(urlPhotoProgram);
					program.setStringScheduleRepeatProgram(capitalizeString(jsonTags.getString("repeticion")));
					program.setPeriodicityProgram(capitalizeString(jsonTags.getString("periodicidad")));
					program.setProducerProgram(capitalizeString(jsonTags.getString("productor")));
					program.setTypeProgram(capitalizeString(jsonTags.getString("tipo_produccion")));
					program.setScheduleProgram(capitalizeString(jsonTags.getString("horario")));
					DateCoverter date = new DateCoverter(jObj.getLong("createdAt"));
					program.setDateProgram(date.getDate());
					listP.add(program);
					program = new Program();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Program> prog = new ArrayList<Program>();

		int i=0;
		if(programType != null)
		{
			if(!programType.equals("1"))
			{
				for(Program temp: listP)
				{
					if(temp.getTypeProgram().equals(programType))
					{
						prog.add(temp);
					}

					i++;
				}
			}

		}		

		//Log.d("sweden", "Length after: " + String.valueOf(prog.size()));
		if(programType != null)
		{
			if(programType.equals("1"))
				listProgram.setListProgram(listP);
			else
				listProgram.setListProgram(prog);
		}
		
		updateListView(listProgram.getListProgram());
		loader.hide();
		MainActivity.ansyncTask=false;
	}
	
	public static String capitalizeString(String string) {
		 if(string==null)
			 return "";
		  char[] chars = string.toLowerCase().toCharArray();
		  boolean found = false;
		  for (int i = 0; i < chars.length; i++) {
		    if (!found && Character.isLetter(chars[i])) {
		      chars[i] = Character.toUpperCase(chars[i]);
		      found = true;
		    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
		      found = false;
		    }
		  }
		  return String.valueOf(chars);
		}
	
	/**
	 *  Metodo isProgramming recurpera el booleano para saber si se esta en la vista
	 *  @return boolean TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static boolean isProgramming() {
		return programming;
	}

	/**
	 *  Metodo isProgramming asigna el booleano para saber si se esta en la vista
	 *  @param programming TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static void setProgramming(boolean programming) {
		ProgrammingFragment.programming = programming;
	}
	
}
