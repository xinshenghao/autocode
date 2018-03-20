package com.java.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.mybatis.domain.PrivateMethod;
import com.java.mybatis.domain.PrivateMethodExample;
import com.java.mybatis.idao.PrivateMethodMapper;
import com.java.service.PrivateMethodCodeService;

@Service("privateMethodCodeService")
public class PrivateMethodCodeServiceImpl implements PrivateMethodCodeService {
	
	@Resource
	private PrivateMethodMapper privateMethodDao;

	@Override
	public List<PrivateMethod> getPrivateMethodByUserId(String userId) {
		PrivateMethodExample example = new PrivateMethodExample();
		example.or().andUserIdEqualTo(userId);
		return privateMethodDao.selectByExampleWithBLOBs(example);
	}

	@Override
	public String addPrivateMethod(PrivateMethod privateMethod) {
		String methodId = UUID.randomUUID().toString();
		privateMethod.setMethodId(methodId);
		privateMethodDao.insert(privateMethod);
		return methodId;
	}

	@Override
	public void updatePrivateMethod(PrivateMethod privateMethod) {
		privateMethodDao.updateByPrimaryKeyWithBLOBs(privateMethod);
		//privateMethodDao.updateByPrimaryKeySelective(privateMethod);
	}

	@Override
	public void deletePrivateMethod(String methodId) {
		privateMethodDao.deleteByPrimaryKey(methodId);
	}

}
