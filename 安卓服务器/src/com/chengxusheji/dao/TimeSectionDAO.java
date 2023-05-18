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
import com.chengxusheji.domain.TimeSection;

@Service @Transactional
public class TimeSectionDAO {

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
    public void AddTimeSection(TimeSection timeSection) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(timeSection);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSection> QueryTimeSectionInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSection timeSection where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List timeSectionList = q.list();
    	return (ArrayList<TimeSection>) timeSectionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSection> QueryTimeSectionInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSection timeSection where 1=1";
    	Query q = s.createQuery(hql);
    	List timeSectionList = q.list();
    	return (ArrayList<TimeSection>) timeSectionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSection> QueryAllTimeSectionInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From TimeSection";
        Query q = s.createQuery(hql);
        List timeSectionList = q.list();
        return (ArrayList<TimeSection>) timeSectionList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From TimeSection timeSection where 1=1";
        Query q = s.createQuery(hql);
        List timeSectionList = q.list();
        recordNumber = timeSectionList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TimeSection GetTimeSectionBySectionId(int sectionId) {
        Session s = factory.getCurrentSession();
        TimeSection timeSection = (TimeSection)s.get(TimeSection.class, sectionId);
        return timeSection;
    }

    /*更新TimeSection信息*/
    public void UpdateTimeSection(TimeSection timeSection) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(timeSection);
    }

    /*删除TimeSection信息*/
    public void DeleteTimeSection (int sectionId) throws Exception {
        Session s = factory.getCurrentSession();
        Object timeSection = s.load(TimeSection.class, sectionId);
        s.delete(timeSection);
    }

}
