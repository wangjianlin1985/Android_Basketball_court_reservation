package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Place;
public class PlaceListHandler extends DefaultHandler {
	private List<Place> placeList = null;
	private Place place;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (place != null) { 
            String valueString = new String(ch, start, length); 
            if ("placeId".equals(tempString)) 
            	place.setPlaceId(new Integer(valueString).intValue());
            else if ("placeName".equals(tempString)) 
            	place.setPlaceName(valueString); 
            else if ("placePhoto".equals(tempString)) 
            	place.setPlacePhoto(valueString); 
            else if ("placePos".equals(tempString)) 
            	place.setPlacePos(valueString); 
            else if ("telephone".equals(tempString)) 
            	place.setTelephone(valueString); 
            else if ("placePrice".equals(tempString)) 
            	place.setPlacePrice(new Float(valueString).floatValue());
            else if ("placeDesc".equals(tempString)) 
            	place.setPlaceDesc(valueString); 
            else if ("onlineTime".equals(tempString)) 
            	place.setOnlineTime(valueString); 
            else if ("topFlag".equals(tempString)) 
            	place.setTopFlag(new Integer(valueString).intValue());
            else if ("addTime".equals(tempString)) 
            	place.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Place".equals(localName)&&place!=null){
			placeList.add(place);
			place = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		placeList = new ArrayList<Place>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Place".equals(localName)) {
            place = new Place(); 
        }
        tempString = localName; 
	}

	public List<Place> getPlaceList() {
		return this.placeList;
	}
}
