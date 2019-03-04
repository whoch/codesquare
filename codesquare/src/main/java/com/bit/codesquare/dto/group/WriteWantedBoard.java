package com.bit.codesquare.dto.group;




import com.bit.codesquare.dto.board.Board;

import lombok.Data;

@Data
public class WriteWantedBoard {

	Board board;
	String applicationForm;
	int recruitmentCount;
}
