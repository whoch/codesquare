package com.bit.codesquare.dto.file;

import lombok.Data;

@Data
public class CodesquareFile {
	int boardId;
	String boardKindId;
	String extension;
	int isImage;
	String originalName;
	String changedNam;
	int size;
}
