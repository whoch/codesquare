package com.bit.codesquare.controller.file;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bit.codesquare.controller.board.BoardRestController;
import com.bit.codesquare.dto.file.CodesquareFile;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.file.FileMapper;
import com.bit.codesquare.mapper.member.MemberMapper;

@RestController
@RequestMapping(value = "cFile")
@ResponseStatus(HttpStatus.OK)
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);

	@Value("${temp.upload.path}")
	String tPath;

	// war 배포할 경우 사용하는 경로 codesquareDB까지 연결됌
	@Value("${window.upload.path}")
	String wPath;

	@Value("${linux.upload.path}")
	String lPath;

	CodesquareFile cFile;

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	FileMapper fileMapper;
	
	//강의소개글 썸네일 등록용 컨트롤러
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public CodesquareFile uploadThumbnail(@RequestPart("thumbnail") MultipartFile tFile, @RequestParam("id") int id,
			Principal principal) throws IOException {
		Member member = memberMapper.getUser(principal.getName());
		int result = 0;
		int isImage = 0;
		cFile = new CodesquareFile();
		// windows 업로드 패스
		String path = String.format("%s", System.getProperty("user.dir")) + wPath;

		// 파일의 오리지널 이름
		String oriName = tFile.getOriginalFilename();
		// 서버에 저장될 파일 이름
		String extension = FilenameUtils.getExtension(oriName).toLowerCase();
		double fileSize = tFile.getSize();

		File destinationFile;
		String changeName;

		try {
			do {
//				changeName = RandomStringUtils.randomAlphanumeric(32);
				changeName = id+"_Thumbnail";
				destinationFile = new File(lPath + "LectureIntro/" + id + "/" + changeName+"."+extension);
			} while (destinationFile.exists());
			destinationFile.getParentFile().mkdirs();
			tFile.transferTo(destinationFile);

			cFile.setBoardId(id);
			cFile.setExtension(extension);
			cFile.setOriginalName(oriName);
			cFile.setChangedName(changeName);
			cFile.setSize(fileSize);
			logger.info(cFile.toString()+"tostring한솔");
			result = fileMapper.insertFileInfo(cFile);
			if (result != 0) {
				return cFile;
			} else {
				logger.info("파일업로드 실패");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cFile;
	}
}
