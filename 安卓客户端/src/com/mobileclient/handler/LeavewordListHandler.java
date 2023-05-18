package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Leaveword;
public class LeavewordListHandler extends DefaultHandler {
	private List<Leaveword> leavewordList = null;
	private Leaveword leaveword;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveword != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveWordId".equals(tempString)) 
            	leaveword.setLeaveWordId(new Integer(valueString).intValue());
            else if ("leaveTitle".equals(tempString)) 
            	leaveword.setLeaveTitle(valueString); 
            else if ("leaveContent".equals(tempString)) 
            	leaveword.setLeaveContent(valueString); 
            else if ("telephone".equals(tempString)) 
            	leaveword.setTelephone(valueString); 
            else if ("userObj".equals(tempString)) 
            	leaveword.setUserObj(valueString); 
            else if ("leaveTime".equals(tempString)) 
            	leaveword.setLeaveTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Leaveword".equals(localName)&&leaveword!=null){
			leavewordList.add(leaveword);
			leaveword = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leavewordList = new ArrayList<Leaveword>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Leaveword".equals(localName)) {
            leaveword = new Leaveword(); 
        }
        tempString = localName; 
	}

	public List<Leaveword> getLeavewordList() {
		return this.leavewordList;
	}
}
