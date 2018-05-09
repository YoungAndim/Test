package cn.smbms.dao.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;

import cn.smbms.dao.BaseDao;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
@Repository
public class ProviderDaoImpl implements ProviderDao {

	@Override
	public List<Provider> getProviderList(Connection connection,
			String proCode, String proName, int currentPageNo, int pageSize)throws Exception
	{
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Provider> userList = new ArrayList<Provider>();
		if(connection != null){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyDate,modifyBy FROM smbms_provider  WHERE 1=1");
			List<Object> list = new ArrayList<Object>();
			if(!StringUtils.isNullOrEmpty(proCode)){
				sql.append(" and  like ?");
				list.add("%"+proCode+"%");
			}
			if(!StringUtils.isNullOrEmpty(proName)){
				sql.append(" and proName like ?");
				list.add("%"+proName+"%");
			}
			sql.append(" limit ?,?");
			currentPageNo = (currentPageNo-1)*pageSize;
			list.add(currentPageNo);
			list.add(pageSize);
			Object[] params = list.toArray();
			System.out.println("sql ----> " + sql.toString());
			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			while(rs.next()){
				Provider der = new Provider();
				der.setId(rs.getInt("id"));
				der.setProCode(rs.getString("proCode"));
				der.setProName(rs.getString("proName"));
				der.setProDesc(rs.getString("proDesc"));
				der.setProContact(rs.getString("proContact"));
				der.setProPhone(rs.getString("proPhone"));
				der.setProAddress(rs.getString("proAddress"));
				der.setProFax(rs.getString("proFax"));
				der.setCreatedBy(rs.getInt("createdBy"));
				der.setCreationDate(rs.getDate("creationDate"));
				der.setModifyDate(rs.getDate("modifyDate"));
				der.setModifyBy(rs.getInt("modifyBy"));
				userList.add(der);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return userList;
	}

	@Override
	public int getProviderCount(Connection connection, String proCode,
			String proName) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = 0;
		if(connection != null){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT count(1) as count FROM smbms_provider where 1=1");
			List<Object> list = new ArrayList<Object>();
			if(!StringUtils.isNullOrEmpty(proCode)){
				sql.append(" and proCode like ?");
				list.add("%"+proCode+"%");
			}
			if(!StringUtils.isNullOrEmpty(proName)){
				sql.append(" and proName like ?");
				list.add(proName);
			}
			Object[] params = list.toArray();
			System.out.println("sql ----> " + sql.toString());
			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			if(rs.next()){
				count = rs.getInt("count");
			}
			System.out.println("count ------>"+count);
			BaseDao.closeResource(null, pstm, rs);
		}
		return count;
	}

	@Override
	public int add(Connection connection, Provider provider) throws Exception {
		int result = 0;
		PreparedStatement pstm = null;
		if(null!=connection)
		{
			String sql = "insert into smbms_provider(proCode,"
					+ "proName,proPhone,proAddress,proFax,proDesc,companyLicPicPath,orgCodePicPath) "
					+ "value(?,?,?,?,?,?,?,?)";
			Object[] params = {provider.getProCode(),provider.getProName(),
					provider.getProPhone(),provider.getProAddress(),
					provider.getProFax(),provider.getProDesc(),provider.getCompanyLicPicPath(),provider.getOrgCodePicPath()};
			System.out.println("sql ----> " + sql.toString());
			result = BaseDao.execute(connection, pstm, sql, params);
			BaseDao.closeResource(null, pstm, null);
		}
		return result;
	}

	@Override
	public Provider getProviderById(Connection connection, String id)
			throws Exception {
		Provider provider = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if(null != connection){
			System.out.println("================="+id);
			String sql = "select id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyDate,modifyBy,companyLicPicPath,orgCodePicPath from smbms_provider where id= ?";
			Object[] params = {id};
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if(rs.next()){
				provider = new Provider();
				provider.setId(rs.getInt("id"));
				provider.setProCode(rs.getString("proCode"));
				provider.setProName(rs.getString("proName"));
				provider.setProDesc(rs.getString("proDesc"));
				provider.setProContact(rs.getString("proContact"));
				provider.setProPhone(rs.getString("proPhone"));
				provider.setProAddress(rs.getString("proAddress"));
				provider.setProFax(rs.getString("proFax"));
				provider.setCreatedBy(rs.getInt("createdBy"));
				provider.setCreationDate(rs.getDate("creationDate"));
				provider.setModifyDate(rs.getDate("modifyDate"));
				provider.setModifyBy(rs.getInt("modifyBy"));
				provider.setCompanyLicPicPath(rs.getString("companyLicPicPath"));
				provider.setOrgCodePicPath(rs.getString("orgCodePicPath"));
			}
			
			BaseDao.closeResource(null, pstm, rs);
		}
		return provider;
	}

	@Override
	public int modify(Connection connection, Provider provider)
			throws Exception {
		int flag = 0;
		PreparedStatement pstm = null;
		if(null != connection){
			String sql = "update smbms_provider set proCode = ?,proName = ?,proContact = ?,proPhone = ?,proAddress = ?,proFax = ?,proDesc = ? where id = ? ";
			Object[] params = {provider.getProCode(),provider.getProName(),provider.getProContact(),provider.getProPhone()
					,provider.getProAddress(),provider.getProFax(),provider.getProDesc(),provider.getId()};
			flag = BaseDao.execute(connection, pstm, sql, params);
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

}
