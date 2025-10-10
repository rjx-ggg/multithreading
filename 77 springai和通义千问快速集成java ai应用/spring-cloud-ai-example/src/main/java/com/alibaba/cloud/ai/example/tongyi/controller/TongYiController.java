package com.alibaba.cloud.ai.example.tongyi.controller;

import com.alibaba.cloud.ai.example.tongyi.service.TongYiService;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ai")
@CrossOrigin
public class TongYiController {

	@Autowired
	@Qualifier("tongYiSimpleServiceImpl")
	private TongYiService tongYiSimpleService;

	@GetMapping("/example")
	public String completion(
			@RequestParam(value = "message", defaultValue = "Tell me a joke")
			String message
	) {

		return tongYiSimpleService.completion(message);
	}

	@Autowired
	@Qualifier("tongYiImagesServiceImpl")
	private TongYiService tongYiImgService;

	@GetMapping("/img")
	public ImageResponse genImg(@RequestParam(value = "prompt",
			defaultValue = "Painting a picture of blue water and blue sky.") String imgPrompt) {

		return tongYiImgService.genImg(imgPrompt);
	}

	@Autowired
	@Qualifier("tongYiAudioSimpleServiceImpl")
	private TongYiService tongYiAudioService;

	@GetMapping("/audio/speech")
	public String genAudio(@RequestParam(value = "prompt",
			defaultValue = "你好，Spring Cloud Alibaba AI 框架！") String prompt) {

		return tongYiAudioService.genAudio(prompt);
	}



}
