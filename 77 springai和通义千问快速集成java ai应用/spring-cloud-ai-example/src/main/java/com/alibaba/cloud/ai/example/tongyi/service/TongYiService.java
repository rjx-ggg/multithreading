package com.alibaba.cloud.ai.example.tongyi.service;

import org.springframework.ai.image.ImageResponse;


public interface TongYiService {

	String completion(String message);

	/**
	 * Gen images.
	 * @param imgPrompt prompt info.
	 * @return {@link ImageResponse}
	 */
	ImageResponse genImg(String imgPrompt);

	/**
	 * Gen audio.
	 * @param text prompt info.
	 * @return ByteBuffer object.
	 */
	String genAudio(String text);


}
