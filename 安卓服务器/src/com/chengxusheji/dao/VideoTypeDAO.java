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
import com.chengxusheji.domain.VideoType;

@Service @Transactional
public class VideoTypeDAO {

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
    public void AddVideoType(VideoType videoType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(videoType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VideoType> QueryVideoTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From VideoType videoType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List videoTypeList = q.list();
    	return (ArrayList<VideoType>) videoTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VideoType> QueryVideoTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From VideoType videoType where 1=1";
    	Query q = s.createQuery(hql);
    	List videoTypeList = q.list();
    	return (ArrayList<VideoType>) videoTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<VideoType> QueryAllVideoTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From VideoType";
        Query q = s.createQuery(hql);
        List videoTypeList = q.list();
        return (ArrayList<VideoType>) videoTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From VideoType videoType where 1=1";
        Query q = s.createQuery(hql);
        List videoTypeList = q.list();
        recordNumber = videoTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public VideoType GetVideoTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        VideoType videoType = (VideoType)s.get(VideoType.class, typeId);
        return videoType;
    }

    /*更新VideoType信息*/
    public void UpdateVideoType(VideoType videoType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(videoType);
    }

    /*删除VideoType信息*/
    public void DeleteVideoType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object videoType = s.load(VideoType.class, typeId);
        s.delete(videoType);
    }

}
