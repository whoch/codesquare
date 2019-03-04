package com.bit.codesquare.controller.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.room.Company;
import com.bit.codesquare.dto.room.Room;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.room.CompanyMapper;

@Controller
public class RoomController {
	@Autowired
	BoardMapper boardMapper;
	@Autowired
	CompanyMapper companyMapper;
	
	@Value("${companyIntro.savepath.directory}")
	String path;
	
	@RequestMapping("/companyList")
	public String companyList(Model model) throws Exception {
		try {
			List<Company> licList = companyMapper.getCompany();
			for(Company l:licList) {
				String thumbPath=path;
				thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
				l.setThumbnailPath(thumbPath);
			}
			model.addAttribute("list", licList);
		}catch (Exception e) {
			
		}
		return "room/companyList";
	}
	
	@RequestMapping("/search")
	public String search(Model model, @ModelAttribute Company company) throws Exception {
		try {
			List<Company> licList = companyMapper.search(company);
			for(Company l:licList) {
				String thumbPath=path;
				thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
				l.setThumbnailPath(thumbPath);
			}
			model.addAttribute("list", licList);
			System.out.println("여기에요!!!!!!!!!!!!!!!!!!!!!"+licList);
		}catch (Exception e) {
		}
		return "room/companyList";
	}
}
