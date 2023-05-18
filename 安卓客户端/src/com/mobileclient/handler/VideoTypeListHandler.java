package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.VideoType;
public class VideoTypeListHandler extends DefaultHandler {
	private List<VideoType> videoTypeList = null;
	private VideoType videoType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (videoType != null) { 
            String valueString = new String(ch, start, length); 
            if ("typeId".equals(tempString)) 
            	videoType.setTypeId(new Integer(valueString).intValue());
            else if ("typeName".equals(tempString)) 
            	videoType.setTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("VideoType".equals(localName)&&videoType!=null){
			videoTypeList.add(videoType);
			videoType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		videoTypeList = new ArrayList<VideoType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("VideoType".equals(localName)) {
            videoType = new VideoType(); 
        }
        tempString = localName; 
	}

	public List<VideoType> getVideoTypeList() {
		return this.videoTypeList;
	}
}
