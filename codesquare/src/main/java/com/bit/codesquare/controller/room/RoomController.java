package com.bit.codesquare.controller.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.room.Company;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.room.CompanyMapper;

@Controller
public class RoomController {
	@Autowired
	BoardMapper boardMapper;
	@Autowired
	CompanyMapper companyMapper;
	
	@RequestMapping("/companyList")
	public String companyList(Model model) throws Exception {
		model.addAttribute("list", companyMapper.getCompany());
		return "room/companyList";
	}
	
	@RequestMapping("/getgangnam")
	public String getgangnam(Model model) throws Exception {
		model.addAttribute("list", companyMapper.getgangnam());
		return "room/companyList";
	}
	
	@RequestMapping("/getseocho")
	public String getseocho(Model model) throws Exception {
		model.addAttribute("list", companyMapper.getseocho());
		return "room/companyList";
	}
	
	@RequestMapping("/getgeumcheon")
	public String getgeumcheon(Model model) throws Exception {
		model.addAttribute("list", companyMapper.getgeumcheon());
		return "room/companyList";
	}
	
	@RequestMapping("/search")
	public String search(Model model, @ModelAttribute Company company) throws Exception {
		model.addAttribute("list", companyMapper.search(company));
		return "room/companyList";
	}
}
