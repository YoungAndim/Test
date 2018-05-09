package cn.smbms.controller;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import sun.misc.Hashing;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.BaseController;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	/**
	 * 进入login登陆页面
	 * @return
	 */
	@RequestMapping(value="/login.html")
	public String login(){
		logger.debug("UserController welcome SMBMS==================");
		return "login";
	}
	/**
	 * 用户登录
	 * post 提交方式
	 * 两个参数 一个为用户名，一个为用户密码
	 * 引入Servlet Apl 对象作为入参，将用户保存在session 中
	 * @return
	 */
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword,
			HttpSession session,
			HttpServletRequest request)
	{
		logger.info("doLogin=================================");
		//调用servive 方法进行用户匹配。
		User user = userService.login(userCode, userPassword);
		if(null!=user)	//不为空 登陆成功
		{
			if(!user.getUserPassword().equals(userPassword))
			{
				request.setAttribute("error","用户密码不正确！");
				//页面跳转 带提示信息转发
				return "login";
			}
			session.setAttribute(Constants.USER_SESSION, user);	//将数据存入session 
			//页面跳转 
			return "redirect:/user/main.html";
			//response.sendRedirect("jsp/frame.jsp");
		}else //如果为空
		{
			request.setAttribute("error","用户名不正确");
			//页面跳转 带提示信息转发
			return "login";
		}
	}
	/**
	 * 用户登陆的页面跳转进入此方法进行页面重定向进入首页
	 * @return
	 */
	@RequestMapping(value="/main.html")
	public String main(HttpSession session)
	{
		//判断session 中是否有存入的值，或者说键
		if(session.getAttribute(Constants.USER_SESSION)==null)
		{
			//重新发送请求
			return "redirect:/user/login.html";
		}
		//服务端的页面跳转
		return "frame";
	}
	/**
	 * 判断用户登陆是否出现异常
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	@RequestMapping(value="exlogin.html",method=RequestMethod.GET)
	public String exLogin(@RequestParam String userCode,@RequestParam String userPassword)
	{
		logger.info("exLogin================================");
		//调用service方法，进行用户匹配
		User user = userService.login(userCode, userPassword);
		if(null==user)
		{
			//如果为空抛出一个异常
			throw new RuntimeException("用户名或者密码不正确");
		}
		return "redirect:/user/main.html";
	}
//	/**
//	 * 捕获用户登陆异常抛出的异常 -------局部异常处理
//	 * @return
//	 */
//	@ExceptionHandler(value={RuntimeException.class})
//	public String handleerException(RuntimeException e,HttpServletRequest req)
//	{
//		req.setAttribute("e", e);
//		return "error";
//	}
	/**
	 * 根据多条件查询用户列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user.do")
	public String getUserList(Model model,
			@RequestParam(value="queryname",required=false)String queryUserName,
			@RequestParam(value="queryUserRole",required=false)String queryUserRole,
			@RequestParam(value="pageIndex",required=false)String pageIndex)
	{
		logger.info("getUserList---------->queryname:"+queryUserName);
		logger.info("getUserList---------->queryUserRole:"+queryUserRole);
		logger.info("getUserList---------->pageIndex:"+pageIndex);
		
		int _queryUserRole = 0;
		List<User> userList = null;
		
		//设置页面容量 5
		int pageSize = Constants.pageSize;
		//当前页码
		int currentPageNo = 1;
		if(queryUserName==null)
		{
			queryUserName ="";
		}
		if(queryUserRole!=null&&queryUserRole.equals(""))
		{
			_queryUserRole = Integer.parseInt(queryUserRole);
		}
		if(pageIndex!=null)
		{
			try
			{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch(NumberFormatException e)
			{
				return "redirect:/user/syserror.html";
			}
		}
		//总数量
		int totalCount = userService.getUserCount(queryUserName, _queryUserRole);
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);	//起始页
		pages.setPageSize(pageSize);	//当前页数
		pages.setTotalCount(totalCount);//总数量
		int totalPageCount = pages.getTotalPageCount(); //总页数
		
		//控制首页和尾页
		if(currentPageNo<1)	//其实也如果小于1
		{
			//则赋值为1
			currentPageNo =1;
		}else if(currentPageNo>totalPageCount) //起始页如果大于总页数
		{
			//则将总页数赋值为起始页
			currentPageNo = totalPageCount;
		}
		userList = userService.getUserList(queryUserName, _queryUserRole, currentPageNo, pageSize);
		model.addAttribute("userList", userList);
		
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", queryUserName);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("totalCount", totalCount);
		return "userlist";
	}
	/**
	 * 登陆添加用户页=页面
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String addUser()
	{
		return"useradd";
	}
	/**
	 * 添加用户
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/addUserSave.html",method=RequestMethod.POST)
	public String addUserSave(User user,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attachs",required=false)MultipartFile[] attachs)
	{
		String idPicPath = null;
		String workPicPath = null;
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
						idPicPath = fileName;
					}else if(i==1)
					{
						workPicPath =fileName;
					}
				}else
				{
					request.setAttribute("uploadFileError","*上传图片格式不正确！");
					flag = false;
				}
			}
		}
		if(flag)
		{
			user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
			user.setCreationDate(new Date());
			user.setIdPicPath(idPicPath);
			user.setWorkPicPath(workPicPath);
			if(userService.add(user))
			{
				logger.info("添加成功！");
				return "redirect:user.do";
			}
		}
		return "useradd";
	}
	/**
	 * 根据用户id获取用户信息
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/view2/{id}",method=RequestMethod.GET)
	public String getUserById(@PathVariable String id,Model model)
	{
		logger.debug("getUserById uid=============== "+id);
		User user = userService.getUserById(id);
		model.addAttribute(user);
		return "usermodify";
	}
	/**
	 * 跟新用户信息
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/usermodifysave.html",method=RequestMethod.POST)
	public String modifyUserSave(User user,HttpSession session)
	{
		logger.debug("modifyUserSave userid ======="+user.getId());
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		boolean falg = userService.modify(user);
		System.out.println("=============="+falg);
		if(falg)
		{
			return "redirect:/user/user.do";
		}
		return "usermodify";
	}
	/**
	 * 查看用户明细
	 */
	@RequestMapping(value="/view",method=RequestMethod.GET)
	@ResponseBody
	public User view(@RequestParam String id)
	{
		logger.debug("view id============"+id);
//		String cjson = "";
		User user = new User();
//		if(null==id||"".equals(id))
//		{
//			return "nodata";
//		}else
//		{
			try
			{
				user = userService.getUserById(id);
//				cjson = JSON.toJSONString(user);
//				logger.debug("cjson: "+cjson);
			}catch(Exception e)
			{
				e.printStackTrace();
//				return "failed";
			}
//		}
		return user;
	}
	/**
	 * 保证用户的唯一性，不同名
	 */
	@RequestMapping(value="/ucexist.xml")
	@ResponseBody
	public Object userCodeIsExit(@RequestParam String userCode)
	{
		logger.debug("userCodeIsExit userCode:"+userCode);
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(userCode))
		{
			resultMap.put("userCode", "exist");
		}else
		{
			//查询用户是否存在
			User user = userService.selectUserCodeExist(userCode);
			if(null!=user)
			{
				//非null代表存在
				resultMap.put("userCode", "exist");
			}else
			{
				//不存咋i
				resultMap.put("userCode", "noexist");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * 进入密码修改页面
	 * @return
	 */
	@RequestMapping(value="/pwdModify")
	public String pwdModify()
	{
		return "pwdmodify";
	}
	/**
	 * Ajavx异步验证输入的旧密码是否正确
	 */
	@RequestMapping(value="/getPwdByUserId")
	@ResponseBody
	public Object getPwdByUserId(@RequestParam String oldpassword,HttpSession session)
	{
		User user = new User();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		user.setId(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setUserPassword(((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword());
		//如果不为空
		if(null!=user)
		{
			logger.info("-----------密码："+user.getUserPassword());
			try
			{
				//密码是否正确
				if(user.getUserPassword().equals(oldpassword))
				{
					logger.info("密码成功-----------");
					resultMap.put("result", "true");
				}else
				{
					logger.info("密码错误-----------");
					resultMap.put("result", "false");
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				resultMap.put("result", "error");
			}
		}else
		{
			logger.info("session为null-----------");
			resultMap.put("result", "sessionerror");
		}
		return resultMap;
	}
	/**
	 * 保存修改的密码，跳入登陆页面
	 */
	@RequestMapping(value="/pwdSave",method=RequestMethod.POST)
	public String pwdSave(@RequestParam String rnewpassword,HttpSession session)
	{
		//拿到用户的id
		User user = new User();
		user.setId(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		// rnewpasswoer 则是用户的密码
		logger.info("进入保存用界面:"+rnewpassword);
		boolean falg = userService.updatePwd(user.getId(),rnewpassword);
		if(falg)
		{
			logger.info("密码修改成功！"); //成功之后进入登陆页面
			return "login";
		}
		return "pwdmodify";	//如果修改失败就当前页面刷新。
	}
	/**
	 * 动态加载所有角色信息。
	 */
	@RequestMapping(value="getRoleList",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Role> getRoleList()
	{
		
		List<Role> role = roleService.getRoleList();
		logger.info("--------------"+role);
		return role;
	}
	/**
	 * 删除用户信息
	 */
	@RequestMapping(value="delUser",method=RequestMethod.GET)
	@ResponseBody
	public Object delUser(@RequestParam String uid)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try
		{
			Integer dlid = Integer.valueOf(uid).intValue();
			logger.info("========id:"+dlid);
			
			boolean flag = userService.deleteUserById(dlid);
			if(flag)
			{
				resultMap.put("delResult", "true");	//删除成功
			}else
			{
				resultMap.put("delResult", "false");	//删除失败
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			resultMap.put("delResult", "notexist");	//删除失败
		}
		return resultMap;
	}
}
