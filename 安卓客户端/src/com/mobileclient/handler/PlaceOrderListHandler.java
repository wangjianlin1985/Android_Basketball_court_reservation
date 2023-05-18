package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.PlaceOrder;
public class PlaceOrderListHandler extends DefaultHandler {
	private List<PlaceOrder> placeOrderList = null;
	private PlaceOrder placeOrder;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (placeOrder != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderId".equals(tempString)) 
            	placeOrder.setOrderId(new Integer(valueString).intValue());
            else if ("placeObj".equals(tempString)) 
            	placeOrder.setPlaceObj(new Integer(valueString).intValue());
            else if ("orderDate".equals(tempString)) 
            	placeOrder.setOrderDate(Timestamp.valueOf(valueString));
            else if ("timeSectionObj".equals(tempString)) 
            	placeOrder.setTimeSectionObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	placeOrder.setUserObj(valueString); 
            else if ("orderTime".equals(tempString)) 
            	placeOrder.setOrderTime(valueString); 
            else if ("shenHeState".equals(tempString)) 
            	placeOrder.setShenHeState(valueString); 
            else if ("shenHeTime".equals(tempString)) 
            	placeOrder.setShenHeTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("PlaceOrder".equals(localName)&&placeOrder!=null){
			placeOrderList.add(placeOrder);
			placeOrder = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		placeOrderList = new ArrayList<PlaceOrder>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("PlaceOrder".equals(localName)) {
            placeOrder = new PlaceOrder(); 
        }
        tempString = localName; 
	}

	public List<PlaceOrder> getPlaceOrderList() {
		return this.placeOrderList;
	}
}
