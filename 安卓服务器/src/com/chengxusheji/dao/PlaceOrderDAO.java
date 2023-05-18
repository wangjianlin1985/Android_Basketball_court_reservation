package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Place;
import com.chengxusheji.domain.TimeSection;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.PlaceOrder;

@Service @Transactional
public class PlaceOrderDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddPlaceOrder(PlaceOrder placeOrder) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(placeOrder);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaceOrder> QueryPlaceOrderInfo(Place placeObj,String orderDate,TimeSection timeSectionObj,UserInfo userObj,String orderTime,String shenHeState,String shenHeTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PlaceOrder placeOrder where 1=1";
    	if(null != placeObj && placeObj.getPlaceId()!=0) hql += " and placeOrder.placeObj.placeId=" + placeObj.getPlaceId();
    	if(!orderDate.equals("")) hql = hql + " and placeOrder.orderDate like '%" + orderDate + "%'";
    	if(null != timeSectionObj && timeSectionObj.getSectionId()!=0) hql += " and placeOrder.timeSectionObj.sectionId=" + timeSectionObj.getSectionId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and placeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) hql = hql + " and placeOrder.orderTime like '%" + orderTime + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and placeOrder.shenHeState like '%" + shenHeState + "%'";
    	if(!shenHeTime.equals("")) hql = hql + " and placeOrder.shenHeTime like '%" + shenHeTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List placeOrderList = q.list();
    	return (ArrayList<PlaceOrder>) placeOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaceOrder> QueryPlaceOrderInfo(Place placeObj,String orderDate,TimeSection timeSectionObj,UserInfo userObj,String orderTime,String shenHeState,String shenHeTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PlaceOrder placeOrder where 1=1";
    	if(null != placeObj && placeObj.getPlaceId()!=0) hql += " and placeOrder.placeObj.placeId=" + placeObj.getPlaceId();
    	if(!orderDate.equals("")) hql = hql + " and placeOrder.orderDate like '%" + orderDate + "%'";
    	if(null != timeSectionObj && timeSectionObj.getSectionId()!=0) hql += " and placeOrder.timeSectionObj.sectionId=" + timeSectionObj.getSectionId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and placeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) hql = hql + " and placeOrder.orderTime like '%" + orderTime + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and placeOrder.shenHeState like '%" + shenHeState + "%'";
    	if(!shenHeTime.equals("")) hql = hql + " and placeOrder.shenHeTime like '%" + shenHeTime + "%'";
    	Query q = s.createQuery(hql);
    	List placeOrderList = q.list();
    	return (ArrayList<PlaceOrder>) placeOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PlaceOrder> QueryAllPlaceOrderInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From PlaceOrder";
        Query q = s.createQuery(hql);
        List placeOrderList = q.list();
        return (ArrayList<PlaceOrder>) placeOrderList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Place placeObj,String orderDate,TimeSection timeSectionObj,UserInfo userObj,String orderTime,String shenHeState,String shenHeTime) {
        Session s = factory.getCurrentSession();
        String hql = "From PlaceOrder placeOrder where 1=1";
        if(null != placeObj && placeObj.getPlaceId()!=0) hql += " and placeOrder.placeObj.placeId=" + placeObj.getPlaceId();
        if(!orderDate.equals("")) hql = hql + " and placeOrder.orderDate like '%" + orderDate + "%'";
        if(null != timeSectionObj && timeSectionObj.getSectionId()!=0) hql += " and placeOrder.timeSectionObj.sectionId=" + timeSectionObj.getSectionId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and placeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!orderTime.equals("")) hql = hql + " and placeOrder.orderTime like '%" + orderTime + "%'";
        if(!shenHeState.equals("")) hql = hql + " and placeOrder.shenHeState like '%" + shenHeState + "%'";
        if(!shenHeTime.equals("")) hql = hql + " and placeOrder.shenHeTime like '%" + shenHeTime + "%'";
        Query q = s.createQuery(hql);
        List placeOrderList = q.list();
        recordNumber = placeOrderList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PlaceOrder GetPlaceOrderByOrderId(int orderId) {
        Session s = factory.getCurrentSession();
        PlaceOrder placeOrder = (PlaceOrder)s.get(PlaceOrder.class, orderId);
        return placeOrder;
    }

    /*����PlaceOrder��Ϣ*/
    public void UpdatePlaceOrder(PlaceOrder placeOrder) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(placeOrder);
    }

    /*ɾ��PlaceOrder��Ϣ*/
    public void DeletePlaceOrder (int orderId) throws Exception {
        Session s = factory.getCurrentSession();
        Object placeOrder = s.load(PlaceOrder.class, orderId);
        s.delete(placeOrder);
    }

}
