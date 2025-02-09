package com.fiap.hackathon.video.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ErrorDto {

	String title;
	String message;
	String code;

}
