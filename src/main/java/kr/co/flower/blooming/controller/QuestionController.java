package kr.co.flower.blooming.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "문제 API")
@RequestMapping("/api/v1/question")
@RestController
@RequiredArgsConstructor
public class QuestionController {
	
}
