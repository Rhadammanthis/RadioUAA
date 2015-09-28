package com.triolabs.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.radio_uaa_android.RadioOnlineActivity;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* LastChapterAdapter devuelve las celdas de la lista de los los ultimos capitulos
* @Developer Raul Quintero Esparza
* @Designer Ivan Padillla
* @version 1.0
*/
public class LastChapterAdapter extends BaseAdapter{
	
	static ArrayList<Chapter> listChapter;
	static Context context;
	public static boolean playVideo=false;
	
	/**Se crea una instancia de LastChapterAdapter
	 * @param context contexto de la actividad
	 * @param options lista de los ultimos capitulos
     */
	public LastChapterAdapter(Context context,ArrayList<Chapter> listChapter){
		LastChapterAdapter.context=context;
		LastChapterAdapter.listChapter=listChapter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listChapter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(context==null)
			return null;
		
		if(convertView==null){
			LayoutInflater view = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = view.inflate(R.layout.last_chapter_item, parent, false);
		}
		
		TextView chapterName =(TextView)convertView.findViewById(R.id.last_chapter_name_program);
		TextView programName =(TextView)convertView.findViewById(R.id.last_chapter_name);
		TextView chapterDate=(TextView)convertView.findViewById(R.id.last_chapter_date);
		final RelativeLayout layoutTriangle=(RelativeLayout)convertView.findViewById(R.id.last_chapter_item_triangle);
		final RelativeLayout layoutInfo=(RelativeLayout)convertView.findViewById(R.id.last_chapter_item_right);
		final Chapter chapter = listChapter.get(position);
		ImageView imageChapter =(ImageView)convertView.findViewById(R.id.last_chapter_item_image);
		ProgressBar progressBar =(ProgressBar)convertView.findViewById(R.id.progress_image);
		Device device = new Device();
		Image image = new Image();
		Bitmap bmChapter = image.getBitmapFromMemCache(chapter.getPhotoChapter(),device.getWidth()/2,device.getHeigth()/6);
		if(bmChapter==null){
			ImageAsyncTask task = new ImageAsyncTask(context,imageChapter,progressBar,chapter.getPhotoChapter(),device.getWidth()/2,device.getHeigth()/7,false);
			task.execute();
		}else{
			imageChapter.setImageBitmap(bmChapter);
		}
		Font font = new Font();
		font.changeFontHelvetica(context, chapterName);
		font.changeFontHelvetica(context, chapterDate);
		font.changeFontHelvetica(context, programName);
		if(chapter.getNameChapter().length()<20)
			chapterName.setText(chapter.getNameChapter());
		else
			chapterName.setText(chapter.getNameChapter().substring(0, 16)+"...");
		String dateYear = ""+chapter.getDateChapter().get(Calendar.YEAR);
		chapterDate.setText(chapter.getDateNumberChapter()+" de "+chapter.getDateMonthChapter()+" de "+dateYear);
		if(chapter.getNameProgram().length()<25)
			programName.setText(chapter.getNameProgram());
		else
			programName.setText(chapter.getNameProgram().substring(0, 22)+"...");
		convertView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if(event.getAction() == MotionEvent.ACTION_DOWN){
	                //Button Pressed
					layoutInfo.setBackgroundColor(context.getResources().getColor(R.color.green_medium));
					layoutTriangle.setBackgroundResource(R.drawable.triangle_green);
	            }
				if(event.getAction() == MotionEvent.ACTION_CANCEL){
					layoutInfo.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
				}
	            if(event.getAction() == MotionEvent.ACTION_UP){
	                 //finger was lifted
	            	layoutInfo.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
					selectChapter(chapter);
	            }
	            return true;
			}
			
		});
		return convertView;
	}
	
	/**Metodo selectChapter asigna el capitulo actual y manda ala actividad de TV Online
	 * @param chapterParam capitulo que se asignara
     */
	private void selectChapter(Chapter chapterParam){
		if(!playVideo){
			playVideo=true;
			ListProgram listProgram= new ListProgram();
			listProgram.setChapterExists(true);
			listProgram.setCurrentChapter(chapterParam);
			Intent intentTvOnline = new Intent(context,RadioOnlineActivity.class);
			context.startActivity(intentTvOnline);
		}
	}

}
