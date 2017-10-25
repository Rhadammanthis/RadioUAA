package com.triolabs.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Esta clase Font cambia el estilo de fuente
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class Font {
	
	/** Se crea una instancia de Font 
 	 */
	public Font(){
	}
	
	/** Metodo changeFontIntro cambia la funete aun estlio de INTRO
	 * @param context contexto de la actividad
	 * @param textView textView que se cambiara la fuente
 	 */
	public void changeFontIntro(Context context,TextView textView){
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/Intro.otf"); 
		textView.setTypeface(type);
	}
	
	/** Metodo changeFontHelvetica cambia la funete aun estlio de Helvetica
	 * @param context contexto de la actividad 
	 * @param textView textView que se cambiara la fuente
 	 */
	public void changeFontHelvetica(Context context,TextView textView){
		Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/Lato-Reg.ttf"); 
		textView.setTypeface(type);
	}
	
}
