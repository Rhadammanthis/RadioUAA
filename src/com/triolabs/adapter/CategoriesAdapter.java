package com.triolabs.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.fragment.GrillFragment;
import com.triolabs.fragment.LastChapterFragment;
import com.triolabs.fragment.ProgramDetailFragment;
import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class CategoriesAdapter extends BaseAdapter {
	
	static List<String> listCategory;
	static Context context;
	private FragmentManager fragManager;
	private Resources res;
	
	/**Se crea una instancia de ProgrammingAdapter
	 * @param context context
	 * @param listProgram lista de los programas
     */
	public CategoriesAdapter(Context context,List<String> listCategory){
		CategoriesAdapter.listCategory =listCategory;
		CategoriesAdapter.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCategory.size();
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
	
	public void setFragmentManager(FragmentManager fM)
	{
		fragManager = fM;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(context==null)
			return null;
		
		if(convertView==null){
			LayoutInflater view = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = view.inflate(R.layout.categories_item, parent, false);
		}
		
		String title = listCategory.get(position);
		
		final TextView textCategoryTitle = (TextView)convertView.findViewById(R.id.categories_name_program);
		final RelativeLayout layoutTriangle=(RelativeLayout)convertView.findViewById(R.id.categories_item_triangle);
		final RelativeLayout layoutInfo=(RelativeLayout)convertView.findViewById(R.id.categories_item_right);
		final ImageView imageV = (ImageView) convertView.findViewById(R.id.categories_item_image);
		
		//Image
		Image image=new Image();		
		Bitmap imageBM = null;
		if (textCategoryTitle.getText().equals("Especializados"))
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.cat_especializados); //nuok
		else if (textCategoryTitle.getText().equals("Musicales"))
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.cat_musicales); //npoksi
		else if (textCategoryTitle.getText().equals("Opinión"))
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.cat_opinion);
		else if (textCategoryTitle.getText().equals("Proyección Universitaria"))
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.cat_proyeccionuniversitaria);
		else if (textCategoryTitle.getText().equals("Radios Asociados"))
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.cat_radiosasociados);
		else
			imageBM =	BitmapFactory.decodeResource(res, R.drawable.banner_app_radio);

		imageBM = image.setBitmapScale(imageBM, Device.getWidth()/2,Device.getHeigth()/7);
		imageV.setImageBitmap(imageBM);
		
		textCategoryTitle.setText(title);
		
		Font font = new Font();
		font.changeFontHelvetica(context, textCategoryTitle);
		
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

	            	//load new fragment
	            	LastChapterFragment.setLastChapter(false);
					GrillFragment.setGrill(false);
					ProgramDetailFragment.setProgramDetail(false);
					ProgrammingFragment.programType = textCategoryTitle.getText().toString();
					displayView(new ProgrammingFragment(),"ProgrammingView");
	            }
	            return true;
			}
			
		});
		
		return convertView;
	}
	

	
	private void displayView(Fragment fragment,String backStack){
		fragManager.beginTransaction()
			.replace(R.id.container, fragment,backStack).addToBackStack(null).commit();
	}
	
	public void setResources(Resources res) 
	{
		// TODO Auto-generated method stub
		this.res = res;
	}

}
