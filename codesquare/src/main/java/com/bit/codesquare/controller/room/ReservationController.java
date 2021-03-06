package com.bit.codesquare.controller.room;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		reservationMapper.reservedate(reservation.getNo());
		Logger.info("와야됨:"+reservation.getNo());
		return "redirect:companyList";
	}
	
	@PostMapping("/room/date")
	@ResponseBody
	public List<Reservation> getReserve(@RequestBody Map<String, String> data) throws Exception {
		List<Reservation> reservation = reservationMapper.getReserve(data);
		for(Reservation list : reservation) {
			Logger.info("여기에용11111"+list.toString()); // 날짜 선택 시 선택한 방의 예약된 날짜(reservationdate)를 뿌려준다 
		}
		return reservationMapper.getReserve(data);
	}
}
	

