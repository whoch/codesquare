package com.bit.codesquare.mapper.room;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.room.Company;

@Mapper
public interface CompanyMapper {
	
	public List<Company> getCompany() throws Exception;
	public List<Company> getgangnam() throws Exception;
	public List<Company> getseocho() throws Exception;
	public List<Company> getgeumcheon() throws Exception;
}
