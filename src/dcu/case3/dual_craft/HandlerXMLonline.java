package dcu.case3.dual_craft;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * class HandlerXMLonline
 * 
 * 	-> HANDLER USER IN PARSING 
 */ 

public class HandlerXMLonline extends DefaultHandler{
	    
    boolean in_login = false;
    boolean in_channel_url = false;
    
    private String readName;
    private String readUrl;
    String userInput;
    String url;
    String name;

    Streamer myStreamer = new Streamer();  
    ArrayList<Streamer> myStreamerList = new ArrayList<Streamer>();
    
    public ArrayList<Streamer> getStreamers() {
    	return myStreamerList;
    }
    
    @Override
    public void startDocument() throws SAXException {
            this.myStreamer = new Streamer();
    }

    @Override
    public void endDocument() throws SAXException {
    	
    }
    	
    @Override
    public void startElement(String namespaceURI, String localName,
                    String qName, Attributes atts) throws SAXException {
    	
        if (localName.equals("login")) {
                this.in_login = true;
        }else if(localName.equals("channel_url")){
        		this.in_channel_url = true;
        }
    }
   
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	
    	if (localName.equals("login")) {
            this.in_login = false;
    	}else if(localName.equals("channel_url")){
    		this.in_channel_url = false;
    	}
    }
    
    @Override
    public void characters(char ch[], int start, int length) {
    	
    	if(this.in_login) {
    		readName = new String(ch, start, length);
    		myStreamer.setName(readName);
    	}
		
		if(this.in_channel_url) {
			readUrl = new String(ch, start, length);
			myStreamer.setUrl(readUrl);
			myStreamerList.add(myStreamer);
			myStreamer = new Streamer();
		}
    		
    }
}
