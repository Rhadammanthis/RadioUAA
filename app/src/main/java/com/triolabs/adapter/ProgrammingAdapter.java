package com.triolabs.adapter;

import java.util.ArrayList;
import java.util.Locale;

import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.fragment.ProgramDetailFragment;
import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* ProgrammingAdapter devuelve las celdas de la lista de los programacion
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ProgrammingAdapter extends BaseAdapter {
	
	static ArrayList<Program> listProgram;
	static Context context;
	
	/**Se crea una instancia de ProgrammingAdapter
	 * @param context context
	 * @param listProgram lista de los programas
     */
	public ProgrammingAdapter(Context context,ArrayList<Program> listProgram){
		this.listProgram =listProgram;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listProgram.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(context==null)
			return null;
		
		if(convertView==null){
			LayoutInflater view = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = view.inflate(R.layout.programming_item_list, parent, false);
		}
		
		Program program = listProgram.get(position);
		ImageView imageProgram =(ImageView)convertView.findViewById(R.id.programming_item_image);
		ProgressBar progressBar =(ProgressBar)convertView.findViewById(R.id.progress_image);
		final RelativeLayout layoutText =(RelativeLayout)convertView.findViewById(R.id.programming_item_right);
		final RelativeLayout layoutTriangle=(RelativeLayout)convertView.findViewById(R.id.programming_item_triangle);
		Device device = new Device();
		Image image = new Image();
		Bitmap bmProgram = image.getBitmapFromMemCache(program.getPhotoProgram(),device.getWidth()/2,device.getHeigth()/6);
		if(bmProgram==null){
			ImageAsyncTask task = new ImageAsyncTask(context,imageProgram,progressBar,program.getPhotoProgram(),device.getWidth()/2,device.getHeigth()/6,false);
			task.execute();
		}else{
			imageProgram.setImageBitmap(bmProgram);
		}
		TextView textScheduleLabel = (TextView)convertView.findViewById(R.id.programming_schedule_label);
		TextView textSchedule = (TextView)convertView.findViewById(R.id.programming_schedule);
		TextView textTypeLabel = (TextView)convertView.findViewById(R.id.programming_type_label);
		TextView textType = (TextView)convertView.findViewById(R.id.programming_type);
		TextView textName = (TextView)convertView.findViewById(R.id.programming_name);
		Font font = new Font();
		font.changeFontHelvetica(context, textScheduleLabel);
		font.changeFontHelvetica(context, textSchedule);
		font.changeFontHelvetica(context, textTypeLabel);
		font.changeFontHelvetica(context, textType);
		font.changeFontHelvetica(context, textName);
		textSchedule.setText(program.getScheduleProgram());
		textType.setText(program.getTypeProgram());
		if(program.getNameProgram().length()<20)
			textName.setText(program.getNameProgram());
		else
			textName.setText(program.getNameProgram().substring(0, 16)+"...");
		
		convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectProgram(position);
			}});
		convertView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
	                //Button Pressed
					layoutText.setBackgroundColor(context.getResources().getColor(R.color.green_medium));
					layoutTriangle.setBackgroundResource(R.drawable.triangle_green);
	            }
				if(event.getAction() == MotionEvent.ACTION_CANCEL){
					layoutText.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
				}
	            if(event.getAction() == MotionEvent.ACTION_UP){
	                 //finger was lifted
	            	layoutText.setBackgroundColor(context.getResources().getColor(R.color.white));
					layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
	            	selectProgram(position);
	            }
	            return true;
			}});
		return convertView;
	}
	
	/**Metodo selectProgram asigna el programa actual y manda al fragment ProgramDetailFragment
	 * @param position es la posicion de la lista
     */
	private static void selectProgram(int position){
		if(ProgrammingFragment.isProgramming()){
			MainActivity.addLastElementHitory(context.getResources().getString(R.string.program_detail_title));
			MainActivity.changeBarTitle(context.getResources().getString(R.string.program_detail_title));
			ProgrammingFragment.setProgramming(false);
			ListProgram listProgramCurrent = new ListProgram();
			listProgramCurrent.setCurrentProgram(listProgram.get(position));
			ProgramDetailFragment fragment = new ProgramDetailFragment();
			ProgrammingFragment.self.getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment,"ProgramDetailView").addToBackStack("ProgrammingView").commit(); 
		}
	}

}
