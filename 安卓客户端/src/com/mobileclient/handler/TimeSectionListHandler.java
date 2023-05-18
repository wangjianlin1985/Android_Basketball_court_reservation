package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.TimeSection;
public class TimeSectionListHandler extends DefaultHandler {
	private List<TimeSection> timeSectionList = null;
	private TimeSection timeSection;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (timeSection != null) { 
            String valueString = new String(ch, start, length); 
            if ("sectionId".equals(tempString)) 
            	timeSection.setSectionId(new Integer(valueString).intValue());
            else if ("sectionName".equals(tempString)) 
            	timeSection.setSectionName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TimeSection".equals(localName)&&timeSection!=null){
			timeSectionList.add(timeSection);
			timeSection = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		timeSectionList = new ArrayList<TimeSection>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("TimeSection".equals(localName)) {
            timeSection = new TimeSection(); 
        }
        tempString = localName; 
	}

	public List<TimeSection> getTimeSectionList() {
		return this.timeSectionList;
	}
}
