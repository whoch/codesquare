package com.bit.codesquare.controller.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bit.codesquare.controller.member.MemberController;
import com.bit.codesquare.mapper.group.GroupMapper;

@Controller
public class GroupController {

	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired 
	GroupMapper gm;
	
	@GetMapping("glist")
	public String getMyGroupList (Model model, Authentication auth) {
		String userId = auth.getName();
		model.addAttribute("glist", gm.getMyGroupList(userId));
		
		return "group/groupList";
	}
	
	@GetMapping("group/{groupId}")
	public String getGroupInfo(Model model, @PathVariable String groupId) {
		model.addAttribute("ginfo", gm.getGroupInfo(groupId));
		model.addAttribute("gMember", gm.getGroupMember(groupId));
		model.addAttribute("gLeader", gm.getGroupLeader(groupId));
		return "group/groupInfo";
	}
}
