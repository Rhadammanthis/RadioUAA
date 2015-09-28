package com.triolabs.adapter;

import java.util.ArrayList;

import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
* GrillAdapter devuelve las celdas de la lista de la parrilla
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class GrillAdapter extends BaseAdapter {
	
	ArrayList<String> programName;
	ArrayList<String> programHour;
	Context context;
	
	/**Se crea una instancia de LastChapterAdapter
	 * @param context contexto de la actividad
	 * @param programName lista de los nombres de programa
	 * @param programHour de los horarios de los programas
     */
	public GrillAdapter(Context context,ArrayList<String> programName,ArrayList<String> programHour){
		this.programHour=programHour;
		this.programName=programName;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return programName.size();
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
			convertView = view.inflate(R.layout.grill_item_list, null);
		}
		
		Font font = new Font();
		
		TextView textName =(TextView) convertView.findViewById(R.id.grill_item_name);
		TextView textHour =(TextView) convertView.findViewById(R.id.grill_item_hour);
		font.changeFontHelvetica(context, textName);
		font.changeFontHelvetica(context, textHour);
		textName.setText(programName.get(position));
		textHour.setText(programHour.get(position));
		return convertView;
	}

}
