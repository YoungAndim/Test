package cn.smbms.dao.provider;

import java.sql.Connection;
import java.util.List;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;

public interface ProviderDao 
{
	/**
	 * 分页
	 * proCode ��Ӧ�̱���
	 * proName ��Ӧ�����
	 * currentPageNo ��ʼҳ
	 * pageSize ��ǰҳ����
	 * @return
	 * @throws Exception 
	 */
	public List<Provider> getProviderList(Connection connection, String proCode,String proName,int currentPageNo, int pageSize) throws Exception;
	/**
	 * @param connection
	 * @param proCode
	 * @param proName
	 * @return
	 * @throws Exception 
	 */
	public int getProviderCount(Connection connection,String proCode,String proName) throws Exception;
	/**
	 * 添加信息
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int add(Connection connection,Provider provider) throws Exception;
	/**
	 * 通过userId获取user
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Provider getProviderById(Connection connection,String id)throws Exception; 
	/**
	 * 修改用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int modify(Connection connection,Provider provider)throws Exception;

}
