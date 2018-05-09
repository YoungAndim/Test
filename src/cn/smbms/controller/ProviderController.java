package cn.smbms.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.LookAndFeel;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

/**
 * ��Ӧ�̿�����
 * @author Dow
 *
 */
@Controller
@RequestMapping("/provider")
public class ProviderController 
{
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private ProviderService providerService;
	
	@RequestMapping(value="/provider.do.html")
	public String getProviderList(Model model,
			@RequestParam(value="queryProCode",required=false)String queryProCode,
			@RequestParam(value="queryProName",required=false)String queryProName,
			@RequestParam(value="pageIndex",required=false)String pageIndex)
	{
		logger.info("proCode---------->proCode:"+queryProCode);
		logger.info("proName---------->proName:"+queryProName);
		logger.info("pageIndex---------->pageIndex:"+pageIndex);
		
		int _queryUserRole = 0;
		List<Provider> providerList = null;
		
		//����ҳ������ 5
		int pageSize = Constants.pageSize;
		//��ǰҳ��
		int currentPageNo = 1;
		if(queryProCode==null)
		{
			queryProCode ="";
		}
		if(queryProName==null)
		{
			queryProName ="";
		}
		if(pageIndex!=null)
		{
			try
			{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch(NumberFormatException e)
			{
				return "error";
			}
		}
		//������
		int totalCount = 0;
		try
		{
			totalCount = providerService.getProviderCount(queryProCode, queryProName);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);	//��ʼҳ
		pages.setPageSize(pageSize);	//��ǰҳ��
		pages.setTotalCount(totalCount);//������
		int totalPageCount = pages.getTotalPageCount(); //��ҳ�
		
		//������ҳ��βҳ
		if(currentPageNo<1)	//��ʵҲ���С��1
		{
			//��ֵΪ1
			currentPageNo =1;
		}else if(currentPageNo>totalPageCount) //��ʼҳ��������ҳ�
		{
			//����ҳ��ֵΪ��ʼҳ
			currentPageNo = totalPageCount;
		}
		providerList = providerService.getProviderList(queryProCode, queryProName, currentPageNo, pageSize);
		model.addAttribute("providerList", providerList);
		
//		model.addAttribute("roleList", roleList);
		model.addAttribute("queryProCode", queryProCode);
		model.addAttribute("queryProName", queryProName);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "providerlist";
	}
	/**
	 * 进入供应商添加页面
	 * @return
	 */
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String addProvider()
	{
		return "provideradd";
	}
	/**
	 * 添加供应商信息
	 */
	@RequestMapping(value="/addProviderSace.html",method=RequestMethod.POST)
	public String addProviderSace(Provider provder,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attachs",required=false)MultipartFile[] attachs)
	{
		String companyLicPicPath = null;
		String orgCodePicPath = null;
		String errorInfo  = null;
		boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
		logger.info("uploadFile path========>"+path);
		
		for(int i = 0;i<attachs.length;i++){
			MultipartFile attach = attachs[i];
		//判断文件是否为空
		if(!attach.isEmpty())
		{
			//区别一===============================================
			if(i==0)
			{
				errorInfo = "uploadFileError";
			}else if(i==1)
			{
				errorInfo = "uploadWpError";
			}
			//源文件名
			String oldFileName = attach.getOriginalFilename();
			logger.info("uploadFIle oldFileName===============>"+oldFileName);
			//源文件后缀
			String prefix = FilenameUtils.getExtension(oldFileName);
			logger.debug("uploadFIle prefix===============>"+prefix);
			int filesize = 500000;
			logger.debug("uploadFIle filesize===============>"+attach.getSize());
			
			//上传文件大小不能超过500KB
			if(attach.getSize()>filesize)
			{
				request.setAttribute("uploadFileError","*上传文件大小不得超过500KB");
				flag = false;
			}else if(prefix.equalsIgnoreCase("jpg")	//上传图片格式不正确
					||prefix.equalsIgnoreCase("png")
					||prefix.equalsIgnoreCase("jpeg")
					||prefix.equalsIgnoreCase("pneg"))
			{
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"personal.jpg";
				logger.debug("new fileName=============="+attach.getName());
				File targetFile = new File(path,fileName);
				//区别二===============================================
				if(!targetFile.exists())
				{
					targetFile.mkdirs();
				}
				//保存
				try
				{
					attach.transferTo(targetFile);
				}catch(Exception e)
				{
					e.printStackTrace();
					request.setAttribute("uploadFileError", "*上传失败！");
					flag = false;
				}
				//区别三===============================================
				if(i==0)
				{
					companyLicPicPath = fileName;
				}else if(i==1)
				{
					orgCodePicPath = fileName;
				}
			}else
			{
				request.setAttribute("uploadFileError","*上传图片格式不正确！");
				flag = false;
			}
		}
	}
		if (flag) {
			provder.setCreatedBy(((User) session
					.getAttribute(Constants.USER_SESSION)).getId());
			provder.setCreationDate(new Date());
			provder.setCompanyLicPicPath(companyLicPicPath);
			provder.setOrgCodePicPath(orgCodePicPath);
			try {
				if (providerService.add(provder) > 0) {
					logger.info("添加成功！");
					return "redirect:provider.do.html";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
		}
	}
		return "provideradd";
	}
	/**
	 * 查看供应商明细。
	 */
	@RequestMapping(value="/view/{id}",method=RequestMethod.GET)
	public String view(@PathVariable String id,Model model)
	{
		logger.debug("view id============"+id);
		
		try {
			Provider provider  = providerService.getProviderById(id);
			model.addAttribute(provider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "providerview";
	}
	/**
	 * 根据id查找供应商信息
	 * @return
	 */
	@RequestMapping(value="/view2/{id}",method=RequestMethod.GET)
	public String getProviderById(@PathVariable String id,Model model)
	{
		try {
			Provider provider  = providerService.getProviderById(id);
			model.addAttribute(provider);
			logger.info(".>>>>>>>>>>>"+provider.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "providermodify";
	}
	/**
	 * 更新供应商信息
	 * @return
	 */
	@RequestMapping(value="/providermodify.html",method=RequestMethod.POST)
	public String modifyProviderSave(Provider provider)
	{
		logger.info("+>>>>>>>>>>>"+provider.getId());
		try
		{
			if(providerService.modify(provider))
			{
				logger.info("成功！！！！！！！！");
				return "redirect:/provider/provider.do.html";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "providermodify";
	}
}
