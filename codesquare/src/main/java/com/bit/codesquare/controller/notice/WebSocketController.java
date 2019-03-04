package com.bit.codesquare.controller.notice;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.notice.Notice;
import com.bit.codesquare.dto.notice.Producer;
import com.bit.codesquare.mapper.notice.NoticeMapper;
import com.bit.codesquare.util.CodesquareUtil;

import lombok.extern.java.Log;

@Controller
@Log
public class WebSocketController {

	@Autowired
	Producer producer;

	@Autowired
	NoticeMapper noticeMapper; 

	Notice notice;
	Map<String, Object> map ;
	
	@Autowired
	CodesquareUtil csu;
	
	//js pushList
	@RequestMapping("/send/{topic}")
	public String sender(@PathVariable String topic, @RequestParam(value="qq") String qq, @RequestParam(value="aline") String aline
			, @RequestParam(value="aline") Date times) throws Exception{
		
		log.info("times : " + times);
		producer.sendMessageTo(topic, qq);
		return "index";
	}
 
	//DB list
	@RequestMapping("/send/message")
	@ResponseBody
	public List<Notice> gettoSelect(@RequestParam(value="qq") String qq, @RequestParam(value="aline") String aline) throws Exception{

		noticeMapper.insert(qq, aline); 
		return null;
	}
	
	//ajax 알림리스트 DB뿌리기
	@RequestMapping(value="noticeview" , method=RequestMethod.GET)
	@ResponseBody
	private List<Notice> getNoteContent(@RequestParam(value="messages") String messages)throws Exception {
		map= new HashMap<String, Object>();
		map.put("messages: ", messages);
		List<Notice> noteContent2=noticeMapper.getLectureHandWriting2();
		return (List<Notice>) csu.getDateTimeCompareObject(noteContent2);
	}
	
	//카운트  coints
		@RequestMapping(value="coints1" , method=RequestMethod.GET)
		@ResponseBody
		public int coints3() throws Exception{
			int cc = noticeMapper.coints();
			return cc;
		}
		
}

