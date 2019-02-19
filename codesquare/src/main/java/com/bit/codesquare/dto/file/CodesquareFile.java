package com.bit.codesquare.dto.file;

import lombok.Data;

@Data
public class CodesquareFile {
	int boardId;
	String extension;
	int isImage;
	String originalName;
	String changedName;
	double size;
}
