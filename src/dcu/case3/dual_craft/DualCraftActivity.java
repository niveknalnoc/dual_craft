/*
 * TEST/DEVELOPMENT APPLICATION ENVIRONMENT
 * AUTHOR: KEVIN CONLAN, SAM HALLIGAN 20/02/12
 */
package dcu.case3.dual_craft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.widget.Button;

/*
 * class DualCraftActivity
 * 
 * 	-> MAIN PAGE DISPLAYED TO THE USER
 * 	CONTROLS THE BUTTONS IN THE MAIN UI PAGE AND PROVIDES THE ACTIVITY
 * 	FOR EACH BUTTON AVAILABLE TO THE USER
 */ 

public class DualCraftActivity extends Activity {
	/** Called when the activity is first created. */
	
	//INITIALISE BUTTONS
	Button onlineButton;
	Button searchButton;
	Button favouritesButton;
	
	//INITIALISE WAKE_LOCK
	WakeLock wL;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//WAKE_LOCK
    	PowerManager pM =(PowerManager)getSystemService(Context.POWER_SERVICE);
    	wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake_Lock");
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //startService(new Intent(this, Notifications.class));
        
        //ACQUIRE WAKE_LOCK
        wL.acquire();
        
        //ASSIGN BUTTONS
        onlineButton = (Button) findViewById(R.id.onlineB);
        searchButton = (Button) findViewById(R.id.searchB);
        favouritesButton = (Button) findViewById(R.id.favouritesB);
        
    }
    
    @Override
	public void onStart() {
	  super.onStart();
	}
	
	@Override
	public void onPause() {
	  super.onPause();
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}
	
	@Override
	public void onResume() {
	  super.onResume();
	  
	//search OnClickListener
      searchButton.setOnClickListener(new View.OnClickListener() {
  			
  	public void onClick(View v) {
  	// TODO Auto-generated method stub
  		Intent dcIntent = new Intent(DualCraftActivity.this, Search.class);
  		startActivity(dcIntent);
  		
  		}
  	});	// end search OnClickListener
      
      //online OnClickListener
      onlineButton.setOnClickListener(new View.OnClickListener() {
			
      	public void onClick(View v) {
      	// TODO Auto-generated method stub
      		Intent dcIntent = 
      				new Intent(DualCraftActivity.this, Online.class);
      		startActivity(dcIntent);
      		
      		}
      	});	// end online OnClickListener
      
      //favourites OnClickListener
      favouritesButton.setOnClickListener(new View.OnClickListener() {
  			
  	public void onClick(View v) {
  	// TODO Auto-generated method stub
  		Intent dcIntent = 
  				new Intent(DualCraftActivity.this, Favourites.class);
  		startActivity(dcIntent);
  			
  		}
  	});	// end favourites OnClickListener
      
}
    @Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}