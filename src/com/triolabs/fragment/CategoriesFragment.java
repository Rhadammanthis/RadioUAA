package com.triolabs.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.triolabs.adapter.CategoriesAdapter;
import com.triolabs.adapter.LastChapterAdapter;
import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.kaltura.Constant;
import com.triolabs.kaltura.Kaltura;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.DateCoverter;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;
import com.triolabs.util.OrderList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * LastChapterFragment es el fragmento muestra los ultimos 10 capitulos agregados
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class CategoriesFragment extends Fragment {

	private static boolean lastChapter;
	static CategoriesFragment self;
	static ListView listCategoriesView;
	private final static Handler handler = new Handler();
	static int indexBanner = -1;
	static int limitBanner = -1;
	static ArrayList<String> urlBanner;
	static ImageView imageBanner;
	static ProgressBar progressBanner;

	public static String categories;
	public static List<String> categoria;
	private static FragmentManager fragMan;
	private static Resources res;

	/** Se crea una instancia de LastChapterFragment se incializa self 
	 */
	public CategoriesFragment(){
		self=this;
	}


	/**
	 *  Metodo onCreateView se ejecuta el asynctask para traer todos los videos almacenados en kaltura
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_last_chapter, container,
				false);
		//		setLastChapter(true);
		categories = "1";
		categoria = new ArrayList<String>();
		Kaltura kaltura = new Kaltura();
		kaltura.getBanner();
		kaltura.getProgramming();
		listCategoriesView=(ListView)rootView.findViewById(R.id.last_chapter_list);
		LinearLayout layoutTvOnline =(LinearLayout)rootView.findViewById(R.id.last_chapter_label);
		RelativeLayout layoutSwap = (RelativeLayout)rootView.findViewById(R.id.last_chapter_swap);
		TextView titleOne =(TextView)rootView.findViewById(R.id.last_chapter_title_left);
		TextView titleTwo =(TextView)rootView.findViewById(R.id.last_chapter_title_rigth);
		TextView textSlogan = (TextView)rootView.findViewById(R.id.last_chapter_slogan);
		imageBanner= (ImageView)rootView.findViewById(R.id.last_chapter_image_banner);
		progressBanner= (ProgressBar)rootView.findViewById(R.id.last_chapter_image_progress);
		Font font = new Font();
		font.changeFontIntro(getActivity(), textSlogan);
		font.changeFontIntro(getActivity(), titleOne);
		font.changeFontIntro(getActivity(), titleTwo);

		titleOne.setText("Últimos Capítulos");
		fragMan = getFragmentManager();
		res = getResources();

		layoutTvOnline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.sendRadioOnline();
			}});
		layoutSwap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//titleOne.setText("Categoria");
				//MainActivity.showCategories();
				getFragmentManager().beginTransaction()
				.replace(R.id.container, new LastChapterFragment(),"LastChapterView").addToBackStack(null).commit();
			}
		});
		return rootView;
	}

	/**
	 *  Metodo updateListView actualiza la lista de los capitulos
	 *  @param listChapter lista de capitulos a actualizar
	 */
	public static void updateListView(ArrayList<Chapter> listChapter){
		LastChapterAdapter adapter = new LastChapterAdapter(self.getActivity(),listChapter);
		listCategoriesView.setAdapter(adapter);
	}

	/**
	 *  Metodo getInfoChapterLast regresa despues de ejecutar ansynctask y solo agrega los ultimos 10
	 *  @param json contiene la lista de todos los capitulos
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

		Program program = new Program();
		ListProgram listProgram = new ListProgram();
		ArrayList<Program> listP = new ArrayList<Program>();
		ArrayList<Chapter> listC = new ArrayList<Chapter>();
		ArrayList<String[]> idChapters = new ArrayList<String[]>();
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
					Log.d("ree", "Produccion de los programas:" + capitalizeString(jsonTags.getString("tipo_produccion")));
					categoria.add(capitalizeString(jsonTags.getString("tipo_produccion")));
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

		listProgram.setListProgram(listP);
		//updateListView(listProgram.getListProgram());
		updateListViewCategory(categoria);

		MainActivity.ansyncTask=false;
	}

	public static  void updateListViewCategory(List<String> listProgram){

		Set<String> hs = new LinkedHashSet<String>();
		hs.addAll(listProgram);
		listProgram.clear();
		listProgram.addAll(hs);

		CategoriesAdapter adapter = new CategoriesAdapter(self.getActivity(),listProgram);
		adapter.setFragmentManager(fragMan);
		adapter.setResources(res);
		adapter.notifyDataSetChanged();
		listCategoriesView.setAdapter(null);
		listCategoriesView.setAdapter(adapter);
	}

	/**
	 *  Metodo capitalizeString cambia 
	 *  @param programming TRUE si esta en esta vista o FALSE si no esta en esta vista
	 */
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
	 *  Metodo getInfoChapterLast regresa despues de ejecutar ansynctask y solo agrega los ultimos 10
	 *  @param json contiene la lista de todos los capitulos
	 */
	public static void getBanner(JSONObject json){
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
					kaltura.getLastChapter();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			MainActivity.ansyncTask=false;
			return;
		}

		try {
			urlBanner= new ArrayList<String>();
			String[] idBanner =json.getString("playlistContent").split(",");
			for(int i=0; i<idBanner.length;i++){
				urlBanner.add("http://kaltura.uaa.mx/p/102/sp/10200/thumbnail/entry_id/"+idBanner[i]+"/def_height/306/def_width/1027/version/100000/type/1");
			}
			limitBanner=urlBanner.size()-1;
			handler.post(changeBanner);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainActivity.ansyncTask=false;
	}

	private final static Runnable changeBanner = new Runnable(){
		public void run(){
			try {
				if(indexBanner==limitBanner)
					indexBanner=0;
				else
					indexBanner++;
				//prepare and send the data here..
				Image image = new Image();
				Bitmap bmChapter = image.getBitmapFromMemCache(urlBanner.get(indexBanner),Device.getWidth(),Device.getHeigth()/4);
				if(bmChapter==null){
					ImageAsyncTask task = new ImageAsyncTask(self.getActivity(),imageBanner,progressBanner,urlBanner.get(indexBanner),Device.getWidth(),Device.getHeigth()/4,false);
					task.execute();
				}else{
					imageBanner.setImageBitmap(bmChapter);
				}
				handler.postDelayed(this, 5000);    
			}
			catch (Exception e) {
				e.printStackTrace();
			}   
		}
	};


	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(changeBanner);
		super.onDestroyView();
	}

	/**
	 *  Metodo isLastChapter recurpera el booleano para saber si se esta en la vista
	 *  @return boolean TRUE si esta en esta vista o FALSE si no esta en esta vista
	 */
	public static boolean isLastChapter() {
		return lastChapter;
	}

	/**
	 *  Metodo setLastChapter asigna el booleano para saber si se esta en la vista
	 *  @param programming TRUE si esta en esta vista o FALSE si no esta en esta vista
	 */
	public static void setLastChapter(boolean lastChapter) {
		CategoriesFragment.lastChapter = lastChapter;
	}

}
