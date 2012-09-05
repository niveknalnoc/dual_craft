package dcu.case3.dual_craft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
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
 * 	class Favourites extends ListActivity
 * 
 * 		-> DISPLAY FAVOURITE STREAMERS, ADD AND DELETE SELECTED STREAMERS
 * 		FAVOURITES ARE SAVED TO A TEXT FILE IN
 * 		INTERNAL STORAE ON THE MOBILE DEVICE
 */

public class Favourites extends ListActivity {

	private Streamer streamer;
	private String url;
	private String updatedUrl;
	private String FILENAME = "/sdcard/favourites.txt";
	
	Streamer myStreamer = new Streamer();
	ArrayAdapter<Streamer> listData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_favourites_window);
		
		listData = new ArrayAdapter<Streamer>(this,
                android.R.layout.simple_list_item_1, getFavourites());
		setListAdapter(listData);
		
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
	  ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				streamer = (Streamer) parent.getItemAtPosition(position);
				splashMenuFavourites(streamer);
			}
		});
	}

	//GET THE FAVOURITES FROM THE FAVOURITES.TXT FILE IN INTERNAL STORAGE
	public ArrayList<Streamer> getFavourites() {
		ArrayList<Streamer> favouritesList = new ArrayList<Streamer>();
		try {
			Scanner in = new Scanner(new File(FILENAME));
			while(in.hasNext()) {
				String nextName = in.nextLine();
				String nextUrl = in.nextLine();
				//ADD STREAMER OBJECT TO THE ARRAYLIST
				favouritesList.add(new Streamer(nextName, nextUrl));
			}
			//CLOSE THE FILE
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//RETURN THIS POPULATED LIST OF STREAMERS
		return favouritesList;
	}
	//ADD FAVOURITE STREAMER TO THE FAVOURITES.TXT FILE
	public void addFavourite(Streamer streamer) {
		ArrayList<Streamer> favouritesList = getFavourites();
		
		if(!favouritesList.contains(streamer)) {
			favouritesList.add(streamer);
		}
		//CALL TO WRITEFAVOURITES
		writeFavourite(favouritesList);
	}
		
	//WRITE THE FAVOURITES TO A NEW FILE AND DELETE THE OLD FILE IN MEMORY
	private void writeFavourite(ArrayList<Streamer> ls) {
		try {
			PrintWriter favourites = new PrintWriter(new File(FILENAME+".tmp"));
			
			for(int i = 0 ; i < ls.size() ; i++) {
				favourites.println(ls.get(i).getName());
				favourites.println(ls.get(i).getUrl());
			}
			favourites.close();
			//RENAME NEW FILE TO THAT OF THE PREVIOUS FILE
			File oldFile = new File(FILENAME);
			File newFile = new File(FILENAME+".tmp");
			oldFile.delete();
			newFile.renameTo(oldFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//REMOVE SELECTED FAVOURITE FROM MEMORY
	private void removeFavourite(Streamer s) {
		ArrayList<Streamer> favouritesList = getFavourites();
		favouritesList.remove(s);
		writeFavourite(favouritesList);
	}

	private void splashMenuFavourites(final Streamer s) {

		DialogInterface.OnClickListener dialogClickListener = 
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					//REMOVE SELECTED STREAMER FROM MEMORY
					removeFavourite(s);
					listData.remove(s);
					Toast.makeText(Favourites.this, s.getName() + 
							" removed successfully", 
							Toast.LENGTH_SHORT).show();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					//VIEW SEECTED FAVOURITE STREAM
					Bundle bundle = new Bundle();
					url = streamer.getUrl();
					url = urlStringChecker(url);
					bundle.putString("url", url);
					Intent dcPassUrlIntent = new Intent(Favourites.this,
							ViewStream.class);
					dcPassUrlIntent.putExtras(bundle);
					startActivity(dcPassUrlIntent);
					break;
				}
			}

		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(Favourites.this);
		builder.setTitle(s.getName());
		builder.setMessage("You can remove this favourite or view the stream");
		builder.setPositiveButton("Remove favourite", dialogClickListener);
		builder.setNegativeButton("View Stream", dialogClickListener).show();
	}// end splashMenufavourites

	//APPEND '/POPOUT' TO THE URL SELECTED
	private String urlStringChecker(String oldUrl) {
		updatedUrl = oldUrl + "/popout";
		return updatedUrl;
	}

}
