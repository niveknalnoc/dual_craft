package dcu.case3.dual_craft;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/*
 *	class Online extends ListActivity
 *
 *		-> THIS CLASS PROVIDES ALL ONLINE STREAMERS TO THE USER WHEN REQUESTED
 *
 *		DATA IS PARSED FROM THE justin.tv XML PAGE ONLINE. THIS DATA IS 
 *		THEN RETURNED TO THE USER
 *
 *		<NOTE>
 *		MAXIMUM SEARCH LIMIT IS 800 STREAMERS PARSED FROM XML TO AID EFFICIENCY
 */

public class Online extends ListActivity {
	
	//INITIALISE VARIABLES
	private int flag = 1;
	private String popoutUrl;
	private String currentUrl;
	private String currentStreamer;
	private int limit = 100;
	private String limitString = "&limit=";
	private int offset = 0;
	private String offsetString = "&offset=";
	private String justinXMLurl = "http://api.justin.tv/api/stream/list.xml?";
	private String myUrl;
	
	Object newOnline;
	Search mySearch = new Search();
	Favourites myFavourite = new Favourites();
	ArrayList<Streamer> onlineStreamerList = new ArrayList<Streamer>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

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
	  //DISPLY CATEGORIES
	  setCategories();
	}
	
	//CONTROLS THE PARSING OF ONLINE STREAMERS
	private void parseActionOnline() {
		try {
				String finalUrl = myUrl+offsetString + offset + limitString + limit;
				URL url = new URL(finalUrl);
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
			
				HandlerXMLonline myHandlerXMLonline = new HandlerXMLonline();
				xr.setContentHandler(myHandlerXMLonline);
				xr.parse(new InputSource(url.openStream()));
			
				onlineStreamerList = myHandlerXMLonline.getStreamers();
			
				CustomAdapter adapter = new CustomAdapter(this, 
						R.layout.online_window, onlineStreamerList);
				setListAdapter(adapter);

			} catch (Exception e) {
				e.printStackTrace();
			}
	}//END parseActionOnline
	
	//MANAGES THE USER SCREEN TOUCH WHEN ONLINE STREAMERS DISPLAYED
	private void splashMenuOnline() {
		DialogInterface.OnClickListener dialogClickListener = 
				new DialogInterface.OnClickListener() {
			
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
			//HANDLE THE 3 OPTIONS AVAILABLE TO THE USER
	            switch (which){
	                case DialogInterface.BUTTON_NEUTRAL:	
	                	//ADD STREAMER TO FAVOUURITES
	                	myFavourite.addFavourite((Streamer) newOnline);
	                	Toast.makeText(Online.this, currentStreamer + 
	                			" added to favourites", 
								Toast.LENGTH_SHORT).show();
	                	break;
	            
	                case DialogInterface.BUTTON_POSITIVE:
	                	//VIEW STREAM
	                	Bundle bundle = new Bundle();
	       	          	popoutUrl = urlStringChecker(currentUrl);
	       	          	bundle.putString("url", popoutUrl);
	       	          	Intent dcPassUrlIntent = new 
	       	          			Intent(Online.this,ViewStream.class);
	       	          	dcPassUrlIntent.putExtras(bundle);
	       	          	startActivity(dcPassUrlIntent);
	       	          	break;
	       	          	
	                case DialogInterface.BUTTON_NEGATIVE:
	                	//GET MORE STREAMERS
	                	offset = offset + 100;
						limit = limit + 100;
						if(limit >= 800) {
							flag = 0;
						}
						else
							flag = 1;
						
						if(flag == 1 ) {
							parseActionOnline();
							actionEventOnline();
						}
	                	break;
	            }
	        }
	};
		// UI DISPLAYED TO USER WHEN A STREAMER SELECTED FROM LIST
	    AlertDialog.Builder builder = new AlertDialog.Builder(Online.this);
	    builder.setTitle(currentStreamer);
	    builder.setMessage("Add to favourites, get more or view stream");
	    builder.setPositiveButton("View Stream", dialogClickListener);
	    builder.setNeutralButton("Add Favourite", dialogClickListener);
	    builder.setNegativeButton("Search More", dialogClickListener).show();
	    
	    
	}//end splashMenufavourites
	
	//SET THE CATEGORIES AND DISPLAY TO THE USER
	private void setCategories() {
		
		Toast.makeText(Online.this,"Select a category of streamer", 
				Toast.LENGTH_SHORT).show();
		
		final String[] CATEGORIES = new String[] {"Gaming","Entertainment",
			"Sports","News","Animals","Science Tech","Education" };
		
		setListAdapter(new ArrayAdapter<String>
				(this, R.layout.categories, CATEGORIES));

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);

		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	String selectedCat = ((String) 
		    			parent.getItemAtPosition(position)).toLowerCase();
		    	setSubCategories(selectedCat);
		    }
		  });
		  
	}
	
	//SET THE SUB CATEGORIES AND DISPLAY TO THE USER
	private void setSubCategories(String s) {
		
		final String cat = s;
		
		Toast.makeText(Online.this,"Select a sub category of streamer", 
				Toast.LENGTH_SHORT).show();
		
		String[] gaming = {"StarCraft II: Wings of Liberty",
				"League of Legends",
				"Call of Duty: Modern Warfare 3",
				"Battlefield 3",
				"World of Warcraft: Cataclysm",
				"Diablo 3"};
		
		String[] entertainment = {"Arts","Celebrities",
				"Comedy","Documentary","Entertainment News","Fashion",
				"Game Shows","Movies","Music","Other Entertainment",
				"Radio Stations","Sci F","Series"}; 
		
		String[] sports = {"Auto Racing", 
				"Baseball","Soccer","Rugby","Cricket",
				"Hockey","Martial Arts","Softball",
				"Sports News","Swimming","Tennis",
				"VolleyBall","Wrestling" };
		
		String[] news = {"Festivals",
				"Government Politics","International News","Local News",
				"National News","Other Events","Other News","Weather"};
		
		String[] animals = {"Birds","Cats","Dogs",
				"Exotic wild animals","Farm Animals","Fish",
				"Lizzards Amphibians","Other Animals","Rodents"};
		
		String[] science = {"Environment","Science"};
		
		String[] education = {"Activism","Classes","How to","Nonprofit","Religious services"};
		
		if(cat.equals("gaming")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, gaming));
		}else if(cat.equals("news")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, news));
		}else if(cat.equals("animals")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, animals));
		}else if(cat.equals("science tech")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, science));
		}else if(cat.equals("education")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, education));
		}else if(cat.equals("entertainment")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, entertainment));
		}else if(cat.equals("sports")) {
			setListAdapter(new ArrayAdapter<String>
			(this, R.layout.categories, sports));
		}
			
		  ListView lv = getListView();

		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	String subCat = ((String) parent.getItemAtPosition(position));
		    	String category = "category=";
		    	String subCategory = "&subcategory=";
		    	String newCat = cat.replaceAll(" ", "_");
		    	String newSubCat = subCat.replaceAll(" ", "_").toLowerCase();
		    	if(cat.equals("gaming")) {
		    		String newSubCat2 = subCat.replaceAll(" ", "%20");
		    		String metaGame = "&meta_game=";
		    		myUrl = justinXMLurl + category + cat + metaGame + newSubCat2;
		    	}else {
		    		myUrl = justinXMLurl + category + newCat + subCategory + newSubCat;
		    		System.out.println(myUrl);
		    	}
		    	//PARSE METHOD
		    	parseActionOnline();
		   	  	//RECIEVING USER TOUCH ACTION
		   	 	actionEventOnline();
		    }
		  });
	}
	
	// HANDLE USER TOUCH 
	private void actionEventOnline() {
		ListView lv = getListView();
	    lv.setTextFilterEnabled(true);

	    lv.setOnItemClickListener(new OnItemClickListener() {
	      public void onItemClick(AdapterView<?> parent, 
	    		  View view, int position, long id) {
	    	  //GET THE STREAMER OBJECT WHERE USER SELECTED FROM THE LIST
	    	  newOnline = parent.getItemAtPosition(position);
	    	  currentStreamer = ((Streamer) newOnline).getName();
    	      currentUrl = ((Streamer) newOnline).getUrl();
    	      //CALL SPLASH UI MENU
	    	  splashMenuOnline();
	      }
	    });
	}
	//APPEND POPOUT TO END OF URL
	private String urlStringChecker(String oldUrl) {
		popoutUrl = oldUrl + "/popout";
		return popoutUrl;
    }

}