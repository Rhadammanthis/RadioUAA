package com.triolabs.radio_uaa_android;

import com.triolabs.fragment.SplashFragment;
import com.triolabs.radio_uaa_android.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Esta clase es el splash de la aplicacion de la aplicacion aqui se agrega el fragment de splash,
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class SplashActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		//if the instance is null, add splash fragment
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new SplashFragment(),"SplashView").addToBackStack(null).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){

	    if (keyCode == KeyEvent.KEYCODE_MENU){
	        return true;
	    }else{
	    	return super.onKeyDown(keyCode, event); 
	    }

	}
}
