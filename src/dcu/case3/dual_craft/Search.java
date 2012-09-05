package dcu.case3.dual_craft;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 *	class Search extends Activity
 *
 *		-> THIS CLASS PROVIDES SEARCH FUNCTIONS AND LOGIC FOR SEARCHING
 *		STREAMERS FROM justin.tv XML PAGE.
 *
 *		DATA IS PARSED FROM THE justin.tv XML PAGE ONLINE. THIS DATA IS 
 *		THEN USED TO SEARCH FOR STREAMERS
 *
 *		<NOTE>
 *		MAXIMUM SEARCH LIMIT IS 800 STREAMERS PARSED FROM XML TO AID EFFICIENCY
 */

public class Search extends Activity {

	// INITIALISE VARIBLES
	private EditText textEntered;
	private Button searchStreamUrlB;
	private Button searchStreamUserB;
	private String url;
	private String url2;
	private String twitchUrl = "twitch.tv";
	private String justinUrl = "justin.tv";
	private String http = "http://";
	private String newHttpUrl;
	private String userInput;
	private String updatedUrl;
	private int empty;
	private int flag = 0;
	private int count;
	private int limit = 100;
	private String limitString = "&limit=";
	private int offset = 0;
	private String offsetString = "&offset=";
	private String justinXMLurl = "http://api.justin.tv/api/stream/list.xml?";

	// ON CREATE METHOD CALLED WHEN THE ACTIVITY IS STARTED
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_window);

		Toast.makeText(Search.this,
				"Search may take time! " + "please be patient when searching!",
				Toast.LENGTH_SHORT).show();

		// INITIALISE THE BUTTONS USED IN THE UI AND ASSIGN TO VARIABLES
		textEntered = (EditText) findViewById(R.id.searchText);
		searchStreamUserB = (Button) findViewById(R.id.searchEnterUser);
		searchStreamUrlB = (Button) findViewById(R.id.searchEnterUrl);
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
	public void onResume() {
		super.onResume();

		/*
		 * searchStreamUserB OnClickListener
		 * 
		 * SEARCH FOR A STREAMER BY A SPECIFIC USERNAME
		 */

		searchStreamUserB.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// REMOVE CASE AND ASSIGN TO USERINPUT
				userInput = textEntered.getText().toString().toLowerCase();

				// CHECK IF INPUT EMPTY
				if (userInput.length() < 1) {
					Toast.makeText(Search.this, "not a valid search query!",
							Toast.LENGTH_SHORT).show();
					textEntered.setText("");
					empty = 0;
				} else { // IF EMPTY SEARCH SET EMPTY = 1
					empty = 1;
				}

				// PARSE XML WHILE FLAG IS 0 ELSE DONT PARSE
				while (flag == 0 && empty != 0) {
					try {

						// CREATE URL WE WANT TO PARSE
						URL url = new URL(justinXMLurl + offsetString + offset
								+ limitString + limit);

						// GET SAXPARSER FROM SAXPARSER FACTORY
						SAXParserFactory spf = SAXParserFactory.newInstance();
						SAXParser sp = spf.newSAXParser();

						// GET THE XMLREADER OF THE SAX PARSER CREATED
						XMLReader xr = sp.getXMLReader();

						// CREATE A NEW CONTENTHANDLER
						// AND APPLY IT TO THE XMLREADER
						HandlerXMLsearch myHandlerXMLsearch = new HandlerXMLsearch();
						myHandlerXMLsearch.setQuery(userInput);
						xr.setContentHandler(myHandlerXMLsearch);

						// PARSE THE XML FROM GIVEN URL
						xr.parse(new InputSource(url.openStream()));
						// PARSING FINISHED

						// GET PARSED DATA FROM CONTENT HANDLER
						Streamer streamer = myHandlerXMLsearch
								.getParsedStreamer();

						// GET AND SET THE FLAG AND COUNT VALUES FROM HANDLER
						count = myHandlerXMLsearch.returnCount();
						flag = myHandlerXMLsearch.returnFlag();

						// START THE VIEWSTREAM ACTIVITY AND PASS URL
						if (count == 1) {
							Streamer s = myHandlerXMLsearch.returnStreamer();
							splashMenuSearch(s);
						} else if (count == 0) {
							// STREAMER NOT FOUND
							if (flag == 1) {
								Toast.makeText(Search.this,
										streamer.printNotFound(),
										Toast.LENGTH_LONG).show();
								textEntered.setText("");
							} else {
								// SEARCH COMPLETED AND NOT FOUND
								if (limit >= 800) {
									flag = 1;
									textEntered.setText("");
									splashMenuSearchAgain(userInput);
								}
								// CONTINUE SEARCHING -> INCREASE OFFSET AND
								// LIMIT
								else {
									offset = offset + 100;
									limit = limit + 100;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}); // end searchStreamUserB

		/*
		 * searchStreamUrlB OnClickListener
		 * 
		 * SEARCH FOR A STREAMER BY A SPECIFIC URL
		 * HTTP://WWW.TWITCH.TV/STREAMERURL
		 */

		// SET FOR WHEN SEARCH BY URL BUTTON IS CLICKED
		searchStreamUrlB.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// GET URL FROM THE EDITTEXT BOX
				url = textEntered.getText().toString().toLowerCase();

				// URL VALIDITY CHECKS PERFORMED TO ENSURE CLEAN INPUT FROM USER
				if (url.length() < 7) {
					// OUTPUT MESSAGE TO USER/RE-SET TEXT ENTERED
					Toast.makeText(Search.this, "not a valid URL!",
							Toast.LENGTH_SHORT).show();
					textEntered.setText("");
				} else {
					// CHECK IF URL DOESNT CONTAINS HTTP://
					if (!url.substring(0, 7).equals(http)) {

						// APPEND HTTP:// TO START OF USERINPUT
						newHttpUrl = http + url;

						// CHECK INPUT IS VALID
						if (newHttpUrl.contains(twitchUrl)
								|| newHttpUrl.contains(justinUrl)) {
							// VALID INPUT BY USER
							// -> PASS URL AND CALL VIEWSTREAM ACTIVITY
							Bundle bundle = new Bundle();
							newHttpUrl = urlStringChecker(newHttpUrl);
							bundle.putString("url", newHttpUrl);
							Intent dcPassUrlIntent = new Intent(Search.this,
									ViewStream.class);
							dcPassUrlIntent.putExtras(bundle);
							startActivity(dcPassUrlIntent);
						} else {
							// INVALID USER INPUT
							Toast.makeText(Search.this,
									"not a valid twitch.tv / justin.tv url",
									Toast.LENGTH_SHORT).show();
							textEntered.setText("");
						}
					} else {
						if (url.contains(twitchUrl) || url.contains(justinUrl)) {
							Bundle bundle = new Bundle();
							url = urlStringChecker(url);
							bundle.putString("url", url);
							Intent dcPassUrlIntent = new Intent(Search.this,
									ViewStream.class);
							dcPassUrlIntent.putExtras(bundle);
							startActivity(dcPassUrlIntent);
						} else {
							Toast.makeText(Search.this,
									"not a valid twitch.tv / justin.tv url",
									Toast.LENGTH_SHORT).show();
							textEntered.setText("");
						}
					}
				}
			}
		}); // end searchStreamUrlB OnClickListener
	}

	private void splashMenuSearchAgain(String userInput) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// YES
					flag = 0;
					Intent intent = new Intent(Search.this, Search.class);
					startActivity(intent);
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// NO
					onBackPressed();
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
		builder.setTitle(userInput + " not found!");
		builder.setMessage("Search Again?");
		builder.setPositiveButton("Yes", dialogClickListener);
		builder.setNegativeButton("No", dialogClickListener).show();
	}

	private void splashMenuSearch(final Streamer s) {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Favourites myFavourites = new Favourites();

				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// VIEW SELECTED STREAM
					Bundle bundle = new Bundle();
					String u = s.getUrl();
					url2 = urlStringChecker(u);
					bundle.putString("url", url2);
					Intent dcPassUrlIntent = new Intent(Search.this,
							ViewStream.class);
					dcPassUrlIntent.putExtras(bundle);
					startActivity(dcPassUrlIntent);
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// ADD TO FAVOURITES
					myFavourites.addFavourite(s);
					break;

				case DialogInterface.BUTTON_NEUTRAL:
					// seach again
					onBackPressed();
				}
			}

		};

		AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
		builder.setTitle(s.getName());
		builder.setMessage("View stream or add to favourites");
		builder.setPositiveButton("View stream", dialogClickListener);
		builder.setNegativeButton("Add to favourites", dialogClickListener);
		builder.setNeutralButton("Main menu", dialogClickListener).show();
	}// end splashMenufavourites

	// ADD POPOUT TO END OF STRING URL -> RETURN NEW URL
	private String urlStringChecker(String oldUrl) {
		updatedUrl = oldUrl + "/popout";
		return updatedUrl;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
