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

@Service @Transactional
public class PlaceDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddPlace(Place place) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(place);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Place> QueryPlaceInfo(String placeName,String placePos,String telephone,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Place place where 1=1";
    	if(!placeName.equals("")) hql = hql + " and place.placeName like '%" + placeName + "%'";
    	if(!placePos.equals("")) hql = hql + " and place.placePos like '%" + placePos + "%'";
    	if(!telephone.equals("")) hql = hql + " and place.telephone like '%" + telephone + "%'";
    	if(!addTime.equals("")) hql = hql + " and place.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List placeList = q.list();
    	return (ArrayList<Place>) placeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Place> QueryPlaceInfo(String placeName,String placePos,String telephone,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Place place where 1=1";
    	if(!placeName.equals("")) hql = hql + " and place.placeName like '%" + placeName + "%'";
    	if(!placePos.equals("")) hql = hql + " and place.placePos like '%" + placePos + "%'";
    	if(!telephone.equals("")) hql = hql + " and place.telephone like '%" + telephone + "%'";
    	if(!addTime.equals("")) hql = hql + " and place.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List placeList = q.list();
    	return (ArrayList<Place>) placeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Place> QueryAllPlaceInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Place";
        Query q = s.createQuery(hql);
        List placeList = q.list();
        return (ArrayList<Place>) placeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String placeName,String placePos,String telephone,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Place place where 1=1";
        if(!placeName.equals("")) hql = hql + " and place.placeName like '%" + placeName + "%'";
        if(!placePos.equals("")) hql = hql + " and place.placePos like '%" + placePos + "%'";
        if(!telephone.equals("")) hql = hql + " and place.telephone like '%" + telephone + "%'";
        if(!addTime.equals("")) hql = hql + " and place.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List placeList = q.list();
        recordNumber = placeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Place GetPlaceByPlaceId(int placeId) {
        Session s = factory.getCurrentSession();
        Place place = (Place)s.get(Place.class, placeId);
        return place;
    }

    /*更新Place信息*/
    public void UpdatePlace(Place place) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(place);
    }

    /*删除Place信息*/
    public void DeletePlace (int placeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object place = s.load(Place.class, placeId);
        s.delete(place);
    }

}
