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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Leaveword;

@Service @Transactional
public class LeavewordDAO {

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
    public void AddLeaveword(Leaveword leaveword) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveword);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Leaveword> QueryLeavewordInfo(String leaveTitle,String telephone,UserInfo userObj,String leaveTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Leaveword leaveword where 1=1";
    	if(!leaveTitle.equals("")) hql = hql + " and leaveword.leaveTitle like '%" + leaveTitle + "%'";
    	if(!telephone.equals("")) hql = hql + " and leaveword.telephone like '%" + telephone + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveword.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!leaveTime.equals("")) hql = hql + " and leaveword.leaveTime like '%" + leaveTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List leavewordList = q.list();
    	return (ArrayList<Leaveword>) leavewordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Leaveword> QueryLeavewordInfo(String leaveTitle,String telephone,UserInfo userObj,String leaveTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Leaveword leaveword where 1=1";
    	if(!leaveTitle.equals("")) hql = hql + " and leaveword.leaveTitle like '%" + leaveTitle + "%'";
    	if(!telephone.equals("")) hql = hql + " and leaveword.telephone like '%" + telephone + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveword.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!leaveTime.equals("")) hql = hql + " and leaveword.leaveTime like '%" + leaveTime + "%'";
    	Query q = s.createQuery(hql);
    	List leavewordList = q.list();
    	return (ArrayList<Leaveword>) leavewordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Leaveword> QueryAllLeavewordInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Leaveword";
        Query q = s.createQuery(hql);
        List leavewordList = q.list();
        return (ArrayList<Leaveword>) leavewordList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String leaveTitle,String telephone,UserInfo userObj,String leaveTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Leaveword leaveword where 1=1";
        if(!leaveTitle.equals("")) hql = hql + " and leaveword.leaveTitle like '%" + leaveTitle + "%'";
        if(!telephone.equals("")) hql = hql + " and leaveword.telephone like '%" + telephone + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and leaveword.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!leaveTime.equals("")) hql = hql + " and leaveword.leaveTime like '%" + leaveTime + "%'";
        Query q = s.createQuery(hql);
        List leavewordList = q.list();
        recordNumber = leavewordList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Leaveword GetLeavewordByLeaveWordId(int leaveWordId) {
        Session s = factory.getCurrentSession();
        Leaveword leaveword = (Leaveword)s.get(Leaveword.class, leaveWordId);
        return leaveword;
    }

    /*更新Leaveword信息*/
    public void UpdateLeaveword(Leaveword leaveword) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveword);
    }

    /*删除Leaveword信息*/
    public void DeleteLeaveword (int leaveWordId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveword = s.load(Leaveword.class, leaveWordId);
        s.delete(leaveword);
    }

}
