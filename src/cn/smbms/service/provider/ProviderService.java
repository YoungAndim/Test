package cn.smbms.service.provider;

import java.sql.Connection;
import java.util.List;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;

public interface ProviderService {
	/**
	 * ��ѯ��Ӧ���б�
	 * ��Ӧ�̱���  ģ���ѯ
	 * ��Ӧ����� ƥ���ѯ
	 * ����ҳ��ʾ
	 * proCode ��Ӧ�̱���
	 * proName ��Ӧ�����
	 * currentPageNo ��ʼҳ
	 * pageSize ��ǰҳ����
	 * @return
	 */
	public List<Provider> getProviderList( String proCode,String proName,int currentPageNo, int pageSize);
	/**
	 * ��ѯָ����Ӧ��Ʒ����
	 * @param connection
	 * @param proCode
	 * @param proName
	 * @return
	 * @throws Exception 
	 */
	public int getProviderCount(String proCode,String proName) throws Exception;
	
	/**
	 * 添加信息
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int add(Provider provider) throws Exception;
	/**
	 * 通过userId获取user
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Provider getProviderById(String id)throws Exception; 
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(Provider provider);
	
}
