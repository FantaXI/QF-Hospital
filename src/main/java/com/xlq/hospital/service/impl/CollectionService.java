package com.xlq.hospital.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.dao.CollectionDao;
import com.xlq.hospital.model.MyCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
	@Autowired
	private CollectionDao collectionDao;

	public int addCollection(MyCollection myCollection){
		return collectionDao.insert(myCollection);
	}

	public ResultObject queryCollection(int page, int limit, MyCollection myCollection){
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<MyCollection> list = collectionDao.queryCollection(myCollection);
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}
	public int delCollection(String id){
		return collectionDao.deleteByPrimaryKey(id);
	}
}
