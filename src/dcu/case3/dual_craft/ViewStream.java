package dcu.case3.dual_craft;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/*
 * 	Class ViewStream extends Activity
 * 
 * 		-> THIS CLASS IS RESPONSIBLE FOR STARTING THE VIEWSTREAM ACTIVITY
 * 		AND DISPLAYING THE URL PROVIDED TO THIS CLASS IN A WEBVIEW WINDOW.
 */

public class ViewStream extends Activity{

	//INITIALISE WEBVIEW AND WAKELOCK
	// WAKELOCK WILL PREVENT THE WINDOW FROM DIMMING DURING THE STREAM
	WebView streamViewer;
	WakeLock wL;
	
	//ON CREATE METHOD CALLED WHEN THE ACTIVITY IS STARTED
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//PREVENT THE SCREEN FROM GOING INTO POWER SAVING MODE
		PowerManager pM = (PowerManager)getSystemService
				(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "wake_lock");
		
		super.onCreate(savedInstanceState);
		
		//SET THE DISPLAY WINDOW AND ACQUIRE THE WAKELOCK
		setContentView(R.layout.view_stream_window);
		wL.acquire();
		
		Toast.makeText(ViewStream.this, 
				"press back button to close stream", 
				Toast.LENGTH_LONG).show();
	}//END ON CREATE
	
	@Override
	public void onStart() {
	  super.onStart();
	}
	
	@Override
	public void onPause() {
	  super.onPause();
	}
	
	@Override
	public void onResume() {
	  super.onResume();
	  	//RECIEVE THE URL VIA BUNDLE AND ASSIGN IT TO URL
	  	Bundle bundle = getIntent().getExtras();
	  	String url = bundle.getString("url");
			
		//INITIALISE THE WEBVIEW STREAMING WINDOW AND SET UP WEBSETTINGS 
		WebView streamViewer = (WebView) findViewById(R.id.webViewer);
		streamViewer.setWebViewClient(new WebViewClient());
		streamViewer.getSettings().setJavaScriptEnabled(true);
		streamViewer.getSettings().setLoadWithOverviewMode(true);
		streamViewer.getSettings().setPluginsEnabled(true);
		streamViewer.loadUrl(url);	
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    final WebView webview = (WebView)findViewById(R.id.webViewer);
	    // Calling .clearView does not stop the flash player must load new data
	    webview.loadData("", "text/html", "utf-8");
	}
	
	//HANDLE A CHANGE IN ORIENTATION
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
		    super.onConfigurationChanged(newConfig);

		    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		        Toast.makeText(this, "Double tap for full screen", 
		        		Toast.LENGTH_SHORT).show();
		    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
		        Toast.makeText(this, "Double tap for full screen", Toast.LENGTH_SHORT).show();
		    }
		}//END onConfigurationChanged
}//END class ViewStream


