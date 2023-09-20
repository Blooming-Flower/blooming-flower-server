package kr.co.flower.blooming.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "시험지 API")
@RequestMapping("/api/v1/exam")
@RestController
@RequiredArgsConstructor
public class ExamController {
    // 지문id와 유형에 따라 문제수와 문제id return

    // 시험지 제작

    // 시험지에 있는 문제들 불러오기

    // 시험지 삭제

    // 시험지 목록 보기

}
