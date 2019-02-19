package com.bit.codesquare.mapper.file;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.file.CodesquareFile;

@Mapper
public interface FileMapper {
	
	public int insertFileInfo(CodesquareFile cFile) throws Exception;
	
	
}
