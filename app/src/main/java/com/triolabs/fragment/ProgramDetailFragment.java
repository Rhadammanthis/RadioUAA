package com.triolabs.fragment;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.triolabs.adapter.ProgramDetailAdapter;
import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.kaltura.Constant;
import com.triolabs.kaltura.Kaltura;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.social.Facebook;
import com.triolabs.social.Twitter;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.DateCoverter;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;
import com.triolabs.util.LoaderView;
import com.triolabs.util.OrderList;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* ProgramDetailFragment es el fragmento muestra la informacion de un programa y su lista de capitulos
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ProgramDetailFragment extends Fragment implements OnClickListener {
	
	static ProgramDetailFragment self;
	static Program program;
	static ListView listViewChapter;
	static RelativeLayout headerLayout,seeMore;
	static LinearLayout socialLayout;
	public static ArrayList<Chapter> listChapter;
	static int limit;
	static int current;
	static LoaderView loader;
	static ListProgram listProgram;
	static AlertDialog.Builder builder=null;
	int height;
	static RelativeLayout descriptionLayout;
	private static boolean programDetail;
	
	/** Se crea una instancia de ProgramDetailFragment se incializa self 
 	 */
	public ProgramDetailFragment(){
		self=this;
	}
	
	/**
	 *  Metodo onCreateView se obtiene el programa actual y se piden sus capitulos
	 *  y se inicializan sus componentes
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_program_detail, container,
				false);
		setProgramDetail(true);
		ImageView imageBanner =(ImageView)rootView.findViewById(R.id.program_detail_banner);
		headerLayout =(RelativeLayout)rootView.findViewById(R.id.program_detail_header);
		seeMore =(RelativeLayout)rootView.findViewById(R.id.program_see_more);
		socialLayout =(LinearLayout)rootView.findViewById(R.id.program_detail_social);
		ProgressBar progressBar =(ProgressBar)rootView.findViewById(R.id.progress_image);
		listChapter= new ArrayList<Chapter>();
		listProgram = new ListProgram();
		Device device = new Device();
		program = listProgram.getCurrentProgram();
		Image image = new Image();
		Bitmap bmChapter = image.getBitmapFromMemCache(program.getPhotoProgram(),device.getWidth()/2,device.getHeigth()/6);
		if(bmChapter==null){
			ImageAsyncTask task = new ImageAsyncTask(getActivity(),imageBanner,progressBar,program.getPhotoProgram(),device.getWidth(),device.getHeigth(),false);
			task.execute();
		}else{
			imageBanner.setImageBitmap(bmChapter);
		}
		Font font = new Font();
		TextView textLeft = (TextView)rootView.findViewById(R.id.program_detail_title_left);
		TextView textRigth = (TextView)rootView.findViewById(R.id.program_detail_name);
		TextView textLabel = (TextView)rootView.findViewById(R.id.program_detail_label_text);
		TextView textInfo= (TextView)rootView.findViewById(R.id.program_detail_text_info);
		TextView textOne= (TextView)rootView.findViewById(R.id.program_detail_text_one);
		TextView textTwo = (TextView)rootView.findViewById(R.id.program_detail_text_two);
		TextView textThree = (TextView)rootView.findViewById(R.id.program_detail_text_tree);
		TextView textScheduleLabel = (TextView)rootView.findViewById(R.id.program_detail_schedule_label);
		TextView textSchedule = (TextView)rootView.findViewById(R.id.program_detail_schedule);

		TextView textPeriodicity= (TextView)rootView.findViewById(R.id.program_detail_info_periodicity);
		TextView textType = (TextView)rootView.findViewById(R.id.program_detail_info_type);
		TextView textProducer = (TextView)rootView.findViewById(R.id.program_detail_info_producer);
		TextView textSynopsisLabel = (TextView)rootView.findViewById(R.id.program_detail_synopsis_label);
		TextView textSynopsis = (TextView)rootView.findViewById(R.id.program_detail_synopsis_content);
		font.changeFontIntro(getActivity(), textLeft);
		font.changeFontIntro(getActivity(), textRigth);
		font.changeFontIntro(getActivity(), textLabel);
		font.changeFontIntro(getActivity(), textInfo);
		font.changeFontHelvetica(getActivity(), textOne);
		font.changeFontHelvetica(getActivity(), textTwo);
		font.changeFontHelvetica(getActivity(), textThree);
		font.changeFontHelvetica(getActivity(), textScheduleLabel);
		font.changeFontHelvetica(getActivity(), textSchedule);

		font.changeFontHelvetica(getActivity(), textPeriodicity);
		font.changeFontHelvetica(getActivity(), textType);
		font.changeFontHelvetica(getActivity(), textProducer);
		font.changeFontHelvetica(getActivity(), textSynopsisLabel);
		font.changeFontHelvetica(getActivity(), textSynopsis);
		textSchedule.setText(program.getScheduleProgram());
		textPeriodicity.setText(program.getPeriodicityProgram());
		textType.setText(program.getTypeProgram());
		textProducer.setText(program.getProducerProgram());
	
		/*if(program.getNameProgram().length()<15)
			textRigth.setText(program.getNameProgram());
		else
			textRigth.setText(program.getNameProgram().substring(0, 14)+"...");*/
		
		textRigth.setText(program.getNameProgram());
		textSynopsis.setText(program.getSynopsisProgram());
		listViewChapter = (ListView)rootView.findViewById(R.id.program_detail_list);
		initSocial(rootView);
		getChapter();
		return rootView;
	}	
	
	/**
	 *  Metodo updateListView actualiza la lista de los capitulos
	 *  @param listChapter lista de capitulos a actualizar
	 *  @param programName nombre del programa que pertenecen los capitulos
     */
	public static void updateListView(ArrayList<Chapter> listChapter,String programName){
		ProgramDetailAdapter adapter = new ProgramDetailAdapter(self.getActivity(),listChapter,programName);
		listViewChapter.setAdapter(adapter);
	}
	
	/**
	 *  Metodo initSocial inicializa los botones de redes sociales y se agrega su evento
	 *  @param rootView es la vista de donde se encuentran los componentes a inicializar
     */
	private void initSocial(View rootView){
		ImageView shareTwitter =(ImageView)rootView.findViewById(R.id.program_detail_twitter);
		shareTwitter.setOnClickListener(self);
		ImageView shareFacebook =(ImageView)rootView.findViewById(R.id.program_detail_facebook);
		shareFacebook.setOnClickListener(self);
		descriptionLayout =(RelativeLayout)rootView.findViewById(R.id.program_detail_desc);
		LinearLayout showDetail =(LinearLayout)rootView.findViewById(R.id.program_detail_text);
		showDetail.setOnClickListener(self);
	}
	
	/**
	 *  Metodo getChapter manda a pedir los capitulos del programa
     */
	private static void getChapter(){
		Kaltura kaltura = new Kaltura();
		limit=program.getIdChapter().length;
		Log.i("limit",""+ limit);
		current=0;
		loader=new LoaderView(self.getActivity());
		loader.show();
		for(int i=0;i<program.getIdChapter().length;i++){
			kaltura.getChapter(program.getIdChapter()[i]);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		String urlFace="http://radio.uaa.mx/social/share.html?idProgram="+program.getIdProgram();
		switch(view.getId()){
		
			case R.id.program_detail_twitter:
				loader= new LoaderView(self.getActivity());
				loader.show();
				loader.hideDelayed();
				Twitter twitter = new Twitter(self.getActivity());
				twitter.shareTwitterWithIntent(urlFace+" @radiouaa");
				break;
			case R.id.program_detail_facebook:
				loader= new LoaderView(self.getActivity());
				loader.show();
				loader.hideDelayed();
				Facebook facebook = new Facebook(self.getActivity());
				facebook.shareFacebookWithIntent(urlFace,program.getNameProgram());
				break;
			case R.id.program_detail_text:
				 if(descriptionLayout.getVisibility() == View.VISIBLE){
					 descriptionLayout.setVisibility(View.GONE);
					 seeMore.setBackgroundResource(R.drawable.icon_see_more);
				 }else{
					 descriptionLayout.setVisibility(View.VISIBLE);
					 seeMore.setBackgroundResource(R.drawable.icon_less);
				 }
				break;
				
			
		}
		
	}
	
	/**
	 *  Metodo hideDetail esconde la descripcion del programa
     */
	public static  void hideDetail(){
		headerLayout.setVisibility(View.GONE);
		socialLayout.setVisibility(View.GONE);
		descriptionLayout.setVisibility(View.GONE);
		
	}
	
	/**
	 *  Metodo hideDetail showDetail la descripcion del programa
     */
	public static void showDetail(){
		headerLayout.setVisibility(View.VISIBLE);
		socialLayout.setVisibility(View.VISIBLE);
		descriptionLayout.setVisibility(View.VISIBLE);
	}
	
	/**
	 *  Metodo getInfoMedia metodo de regreso cuando se pide cada capitulos se va agregando ala lista de capitulo
	 *  @param json contiene los datos del capitulo
	 *  @param entryId es el id del capitulo que se descargo los metadata
     */
	public static void getInfoMedia(JSONObject json,String entryId){
		
		if(builder!=null)
			return;
		
		if(json==null&&builder==null){
			loader.hide();
			builder = new AlertDialog.Builder(self.getActivity());
			builder.setMessage(self.getResources().getString(R.string.alert_content))
			        .setTitle(self.getResources().getString(R.string.alert_title))
			        .setCancelable(false)
			        .setNegativeButton(self.getResources().getString(R.string.alert_cancel),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                        dialog.cancel();
			                        builder=null;
			                    }
			                })
			        .setPositiveButton(self.getResources().getString(R.string.alert_acept),
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                    	getChapter();
			                    	builder=null;
			                    }
			                });
			AlertDialog alert = builder.create();
			alert.show();
			MainActivity.ansyncTask=false;
			return;
		}
		try {
			if(json.getInt("mediaType")==Constant.KALTURA_MEDIATYPE){
				Chapter chapter = new Chapter();
					chapter.setId(entryId);
					String id=json.getString("rootEntryId");
					String urlPhoto =Constant.HOST_KALTURA+"/p/101/sp/10100/thumbnail/entry_id/"+id+"/def_height/480/def_width/640/version/100000/type/1";
					chapter.setNameChapter(json.getString("name"));
					chapter.setUrlChapter(json.getString("dataUrl"));
					chapter.setPhotoChapter(urlPhoto);
					chapter.setLikes(json.getString("votes"));
					chapter.setDownloadChapter(json.getString("downloadUrl"));
					//Long longDate = json.getLong("createdAt");
					try{
						String stringDate=json.getString("tags").split(",")[2];
						Log.i("tag fecha", stringDate);
						DateCoverter date = new DateCoverter(stringDate);
						chapter.setDateChapter(date.getDate());
						chapter.setDateNumberChapter(""+date.getDayMouth());
						chapter.setDateMonthChapter(date.getNameMonth());
						listChapter.add(chapter);
					}catch(ArrayIndexOutOfBoundsException e){
						
					}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(current>=limit-1){
			OrderList order = new OrderList();
			order.byDateChapter(listChapter);
			ListProgram.setListChapter(listChapter);
			updateListView(listChapter,program.getIdProgram());
			loader.hide();
		}
		
		current++;
		MainActivity.ansyncTask=false;
		
	}

	/**
	 *  Metodo isProgramDetail recurpera el booleano para saber si se esta en la vista
	 *  @return boolean TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static boolean isProgramDetail() {
		return programDetail;
	}

	/**
	 *  Metodo setProgramDetail asigna el booleano para saber si se esta en la vista
	 *  @param programming TRUE si esta en esta vista o FALSE si no esta en esta vista
     */
	public static void setProgramDetail(boolean programDetail) {
		ProgramDetailFragment.programDetail = programDetail;
	}	
	
	
	
}
