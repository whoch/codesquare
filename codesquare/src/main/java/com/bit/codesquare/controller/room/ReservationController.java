package com.bit.codesquare.controller.room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.dto.room.Room;
import com.bit.codesquare.mapper.room.RoomMapper;

@Controller
public class ReservationController {

	@Autowired
	RoomMapper roomMapper;
	
	
	@Value("${roomIntro.savepath.directory}")
	String path;
	
	List<Room> licList = new ArrayList<Room>();
	
	@RequestMapping("/getid")
	public ModelAndView getid(Model model, String companyid) throws Exception {
		try {
			licList=roomMapper.getid(companyid);
			for(Room l:licList) {
				String thumbPath=path;
				thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
				l.setThumbnailPath(thumbPath);
			}
		}catch (Exception e) {
			
		}
		return new ModelAndView("room/roomList", "list", licList);
	}
	
//	@RequestMapping("/getid")
//	public String getid(Model model, String companyid) throws Exception {
//		model.addAttribute("list", roomMapper.getid(companyid));
//		licList=roomMapper.getAllRoom();
//		for(Room l: licList) {
//			String thumbPath=path;
//			thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
//			System.out.println(thumbPath);
//			l.setThumbnailPath(thumbPath);
//		}
//		return "roomList";
//	}
}
//	@RequestMapping("/roomList")
//	public String getAllRoom(Model model) throws Exception {
//		
//		List<Room> licList = new ArrayList<Room>();
//		
//		model.addAttribute("list", roomMapper.getAllRoom());
//		licList=roomMapper.getAllRoom();
//		for(Room l: licList) {
//			String thumbPath=path;
//			thumbPath+="/Thumbnail."+l.getExtension();
//			l.setThumbnailPath(thumbPath);
//		}
//		return "roomList";
//	}
	

