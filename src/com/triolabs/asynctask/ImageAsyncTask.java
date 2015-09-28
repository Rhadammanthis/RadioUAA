package com.triolabs.asynctask;

import com.triolabs.util.Image;
import com.triolabs.util.LoaderView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
* ImageAsyncTask realiza el request para obtener las imagenes y mostrarlas en el imageview
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
	
	ImageView imageView;
	String url;
	int width;
	int heigth;
	Context contex;
	LoaderView progress;
	ProgressBar progressBar;
	boolean circule;
	
	/**Se crea una instancia de ImageAsyncTask
	 * @param context actividad en la que se esta
	 * @param imageView en donde se pondra la imagen
	 * @param progressBar se mostrara cuando se este descargando la imagen
	 * @param url de la imagen que se quiere descargar
	 * @param width ancho que se quiere la imagen
	 * @param heigth alto que se quiere la imagen
     */
	public ImageAsyncTask(Context context,ImageView imageView,ProgressBar progressBar,String url,int width,int heigth,boolean circule){
		this.imageView=imageView;
		this.url=url;
		this.heigth=heigth;
		this.width=width;
		this.contex=context;
		this.progressBar=progressBar;
		this.progressBar.setVisibility(View.VISIBLE);
		this.circule=circule;
	} 

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		Image image=new Image();
		Bitmap bitmap=image.getBitmapFromURL(url);
		if(bitmap!=null)
			bitmap=image.setBitmapScale(bitmap, width, heigth);
		image.addBitmapToMemoryCache(bitmap, url);
		return bitmap;
	}
	
	@Override
    protected void onPostExecute(Bitmap bmp) {
		imageView.setImageBitmap(bmp);
		this.progressBar.setVisibility(View.GONE);
    }


}
