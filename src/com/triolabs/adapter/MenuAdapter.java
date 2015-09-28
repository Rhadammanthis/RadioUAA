package com.triolabs.adapter;

import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
* MenuAdapter devuelve las celdas del menu lateral
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class MenuAdapter extends BaseAdapter {
	
	Context context;
	String[] options;
	int[] imageOptions={R.drawable.radio,R.drawable.radionline,R.drawable.alfa,R.drawable.calendario,R.drawable.menu_parrilla,R.drawable.contacto,R.drawable.salir};
	boolean contact;
	
	/**Se crea una instancia de MenuAdapter
	 * @param context contexto de la actividad
	 * @param options lista de opciones
	 * @param contact si esta en la vista de contacto
     */
	public MenuAdapter(Context context,String [] options,boolean contact){
		this.options=options;
		this.context=context;
		this.contact=contact;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return options.length;
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
		LayoutInflater view = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Font font = new Font();
		if(position==0){
			convertView = view.inflate(R.layout.menu_item_fisrt, null);
			TextView textOption =(TextView) convertView.findViewById(R.id.text_option);
			textOption.setText(options[position]);
			font.changeFontIntro(context, textOption);
		}else{
			convertView = view.inflate(R.layout.menu_item_option, null);
	        ImageView imageOption =(ImageView)convertView.findViewById(R.id.icon_option);
	        imageOption.setImageResource(imageOptions[position-1]);
	        TextView textOption =(TextView) convertView.findViewById(R.id.text_option);
	        textOption.setText(options[position]);
	        font.changeFontHelvetica(context, textOption);
		}
		
		if(contact)
			if(position==3||position==4)
				convertView= view.inflate(R.layout.menu_item_null, null);
        
		return convertView;
	}

}
