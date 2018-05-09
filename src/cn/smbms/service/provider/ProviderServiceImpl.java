package cn.smbms.service.provider;

import java.sql.Connection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
@Service
public class ProviderServiceImpl implements ProviderService {
	@Resource
	private ProviderDao providerDao;
	@Override
	public List<Provider> getProviderList(
			String proCode, String proName, int currentPageNo, int pageSize) {
		Connection connection = null;
		List<Provider> userList = null;
		System.out.println("proCode ---- > " + proCode);
		System.out.println("proName ---- > " + proName);
		System.out.println("currentPageNo ---- > " + currentPageNo);
		System.out.println("pageSize ---- > " + pageSize);
		try {
			connection = BaseDao.getConnection();
			userList = providerDao.getProviderList(connection, proCode, proName, currentPageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return userList;
	}
	@Override
	public int getProviderCount( String proCode,
			String proName) throws Exception {
		Connection connection = null;
		int count = 0;
		System.out.println("proCode ---- > " + proCode);
		System.out.println("proName ---- > " + proName);
		try {
			connection = BaseDao.getConnection();
			count = providerDao.getProviderCount(connection, proCode, proName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return count;
	}
	@Override
	public int add(Provider provider) throws Exception {
		Connection connection = null;
		int count = 0;
		try
		{
			connection = BaseDao.getConnection();
			count = providerDao.add(connection, provider);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return count;
	}
	@Override
	public Provider getProviderById( String id)
			throws Exception {
		// TODO Auto-generated method stub
		Provider provider = null;
		Connection connection = null;
		try{
			connection = BaseDao.getConnection();
			provider = providerDao.getProviderById(connection, id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			provider = null;
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return provider;
	}
	@Override
	public boolean modify(Provider provider) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			int result = providerDao.modify(connection, provider);
			if( result> 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

}
