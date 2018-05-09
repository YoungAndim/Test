package cn.smbms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.smbms.pojo.Role;
import cn.smbms.service.role.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController 
{
	@Resource
	private RoleService roleService;
	@RequestMapping(value="/rolelist.html")
	public String getRoleList(Model model)
	{
		List<Role> roleList = new ArrayList<Role>();
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);
		
		return "rolelist";
	}
}
