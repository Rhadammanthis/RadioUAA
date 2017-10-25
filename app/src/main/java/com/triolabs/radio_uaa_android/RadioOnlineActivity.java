package com.triolabs.radio_uaa_android;

import com.triolabs.adapter.LastChapterAdapter;
import com.triolabs.adapter.ProgramDetailAdapter;
import com.triolabs.fragment.RadioOnlineFragment;
import com.triolabs.util.Font;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Esta clase es la actividad de tv online se agrega el fragment de radio online
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class RadioOnlineActivity extends Activity {
	
	static RadioOnlineActivity self;
	
	public RadioOnlineActivity(){
		self=this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_online);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new RadioOnlineFragment()).commit();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ProgramDetailAdapter.playVideo=false;
		LastChapterAdapter.playVideo=false;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
