package dcu.case3.dual_craft;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * class HandlerXMLsearch
 * 
 * 	-> HANDLER USER IN PARSING 
 */ 

public class HandlerXMLsearch extends DefaultHandler{
	    
    boolean in_streams = false;
    boolean in_stream = false;
    boolean in_login = false;
    boolean in_channel_url = false;
    
    private String readName;
    private String readUrl;
    private String userInput;
    private Streamer streamer;
    String newUrl;
    String updatedUrl;
    private int count, flag;
    
    Streamer myStreamer = new Streamer();  
    Search mySearchChase = new Search();
    ArrayList<Streamer> myStreamerList = new ArrayList<Streamer>();

    public void setQuery(String qI) {
    	this.userInput = qI;
    }
    
    public Streamer getParsedStreamer() {
            return this.myStreamer;
    }

    @Override
    public void startDocument() throws SAXException {
            this.myStreamer = new Streamer();
    }

    @Override
    public void endDocument() throws SAXException {
        
    	if(myStreamerList.size() == 0) {
    		count = 0;
    		flag = 1;
    		myStreamer.setName(userInput);
    	}
    	else {
    		for(int i = 0 ; i < myStreamerList.size() ; i++) {			
       			String n = myStreamerList.get(i).getName();
        		
       			if(n.equals(userInput)) {
       				count = 1;
       				flag = 1;
        			streamer = myStreamerList.get(i);
        			returnStreamer();
        			myStreamer.setUrl(streamer.getUrl());
        			break;
       			}
        		else {
        			count = 0;
        			flag = 0;
        		}
       		}
    	}
    	myStreamerList.clear();
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                    String qName, Attributes atts) throws SAXException {
    	
            if (localName.equals("streams")) {
                    this.in_streams = true;
            }else if (localName.equals("stream")) {
                    this.in_stream = true;
            }else if (localName.equals("login")) {
                    this.in_login = true;
            }else if (localName.equals("channel_url")) {
        			this.in_channel_url = true;
            }
    }
   
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	
            if (localName.equals("streams")) {
                    this.in_streams = false;
            }else if (localName.equals("stream")) {
                    this.in_stream = false;
            }else if (localName.equals("channel_url")) {
        			this.in_channel_url = false;
            }else if (localName.equals("login")) {
                    this.in_login = false;
            }
    }
    
    public int returnCount() {
    	return count;
    }
    public int returnFlag() {
    	return flag;
    }
    
    public Streamer returnStreamer() {
    	return streamer;
    }
    
    @Override
    public void characters(char ch[], int start, int length) {
    	
    		if(this.in_login){
        		readName = new String(ch, start, length);
        		myStreamer.setName(readName);
        		myStreamerList.add(myStreamer);
        	}
    		
    		if(this.in_channel_url){
    			readUrl = new String(ch, start, length);
    			myStreamer.setUrl(readUrl);
    			myStreamerList.add(myStreamer);
    			myStreamer = new Streamer();
    		}
    		
    }
    
}
