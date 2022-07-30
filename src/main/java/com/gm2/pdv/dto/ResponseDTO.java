package com.gm2.pdv.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ResponseDTO<T> {

	@Getter
	private List<String> messages;

	public ResponseDTO( List<String> messages ) {
		this.messages = messages;
	}

	public ResponseDTO( String message ) {

		this.messages = Arrays.asList(message);
	}
}
