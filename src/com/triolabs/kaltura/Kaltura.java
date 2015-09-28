package com.triolabs.kaltura;

import android.widget.TextView;

import com.triolabs.asynctask.BannerAnsyncTask;
import com.triolabs.asynctask.GrillAnsyncTask;
import com.triolabs.asynctask.LastChapterAsyncTask;
import com.triolabs.asynctask.LikeAsyncTask;
import com.triolabs.asynctask.LoginAsyncTask;
import com.triolabs.asynctask.ProgrammingAnsyncTask;
import com.triolabs.asynctask.ProgrammingInfoAnsyncTask;

/**
 * Kaltura realiza los ansyctask para las peticiones de kaltura
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class Kaltura {
	
	/** Se crea una instancia de Kaltura 
 	 */
	public Kaltura(){
		
	}
	
	/**Metodo ejecuta el ansyntask de login para obtener el KS de kaltura y regeresa al metodo SplashFragment.changeViewMain() y asigna Constant.KS el resultado
 	 */
	public void login(){
		LoginAsyncTask login = new LoginAsyncTask();
		login.execute();
	}
	
	/**Metodo ejecuta el ansyntask de traer los ultimos capitulos y regresa LastChapterFragment.getInfoChapterLast(result);
 	 */
	public void getLastChapter(){
		LastChapterAsyncTask lastChapter = new LastChapterAsyncTask();
		lastChapter.execute();
	}
	
	/**Metodo ejecuta el ansyntask para traer todos los programas y regresa ProgrammingFragment.getEntryId(result);
 	 */
	public void getProgramming(){
		ProgrammingAnsyncTask programming = new ProgrammingAnsyncTask();
		programming.execute();
	}
	
	/**Metodo ejecuta el ansyntask el capitulo y regresa ProgramDetailFragment.getInfoMedia(result,entryId);
	 * @param entryId id del capitulo que se traera la informacion
 	 */
	public void getChapter(String id){
		ProgrammingInfoAnsyncTask getInfoMedia = new ProgrammingInfoAnsyncTask(id);
		getInfoMedia.execute();
	}
	
	/**Metodo ejecuta el ansyntask para dar like a un capitulo y regresa ProgramDetailAdapter.setLikeHeart(result,entryId,textHeart);
	 * @param entryId id del capitulo que se da el like
	 * @param textHeart textview al que se modificara su valor
 	 */
	public void setLikeChapter(String id,TextView textHeart){
		LikeAsyncTask likeAsyncTask = new LikeAsyncTask(id,textHeart);
		likeAsyncTask.execute();
	}
	
	/**Metodo ejecuta el ansyntask para traer todos los datos de la parrilla y regresa GrillFragment.getInfoGrill(result);
 	 */
	public void getGrill(){
		GrillAnsyncTask grillTask = new GrillAnsyncTask();
		grillTask.execute();
	}
	
	/**Metodo ejecuta el ansyntask para traer las playlist de los banners y recuperar sus id
 	 */
	public void getBanner(){
		BannerAnsyncTask bannerTask = new BannerAnsyncTask();
		bannerTask.execute();
	}

}
