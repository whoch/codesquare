package com.bit.codesquare.controller.room;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.room.Reservation;
import com.bit.codesquare.dto.room.Room;
import com.bit.codesquare.mapper.room.CompanyMapper;
import com.bit.codesquare.mapper.room.ReservationMapper;
import com.bit.codesquare.mapper.room.RoomMapper;


@Controller
public class ReservationController {
	
	private static final Logger Logger = LoggerFactory.getLogger(ReservationController.class);
	@Autowired
	RoomMapper roomMapper;
	@Autowired
	ReservationMapper reservationMapper;
	@Autowired
	CompanyMapper companyMapper;
	
	@Value("${roomIntro.savepath.directory}")
	String path;
	
	
	
	@RequestMapping("/getid")
	public String getid(Model model, String companyid, Reservation reservation) throws Exception {
		
		try {
			List<Room> licList =roomMapper.getid(companyid);
			for(Room l:licList) {
				String thumbPath=path;
				thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
				l.setThumbnailPath(thumbPath);
			}
			model.addAttribute("list", licList);
			model.addAttribute("content", companyMapper.companycontent(companyid));
			System.out.println(companyid);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "room/roomList";
	}
	
	
	@RequestMapping("/roomView")
	public String roomView(Model model, String id) throws Exception {
		
			Room l=roomMapper.getroom(id);
				String thumbPath=path;
				thumbPath+=l.getId()+"/Thumbnail."+l;
				l.setThumbnailPath(thumbPath);
//		int[] test = new int[] {8,9,11,15,16};
		String[] test = new String[] {"3","08:00:00"};
		model.addAttribute("test", test);
		model.addAttribute("item", l);
		
		System.out.println(l);
		return "room/roomView";
	}
	
	@RequestMapping("/reserve")
	public String reserve(HttpServletRequest request, Model model, @ModelAttribute Reservation reservation) throws Exception {
//		int no = Integer.parseInt(request.getParameter("no"));
		Logger.info("하기전:"+reservation.getNo());

		reservationMapper.insert(reservation);
		reservationMapper.enddate(reservation.getNo());

		Logger.info("와야됨:"+reservation.getNo());
		return "redirect:companyList";
	}
}
//	@RequestMapping("/roomView")
//	public String roomView(Model model, String id) throws Exception {
//		model.addAttribute("list", roomMapper.getroom(id));
//		return "room/roomView";
//	}

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
	

