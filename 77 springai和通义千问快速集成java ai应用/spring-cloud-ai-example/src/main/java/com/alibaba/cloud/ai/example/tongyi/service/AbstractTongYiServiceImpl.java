package com.alibaba.cloud.ai.example.tongyi.service;
import org.springframework.ai.image.ImageResponse;

public abstract class AbstractTongYiServiceImpl implements TongYiService {

	private static final String INFO_PREFIX = "please implement ";
	private static final String INFO_SUFFIX = "() method.";

	@Override
	public String completion(String message) {

		throw new RuntimeException(INFO_PREFIX + Thread.currentThread().getStackTrace()[2].getMethodName());
	}


	@Override
	public ImageResponse genImg(String imgPrompt) {

		throw new RuntimeException(INFO_PREFIX + Thread.currentThread()
				.getStackTrace()[2].getMethodName() + INFO_SUFFIX);
	}

	@Override
	public String genAudio(String text) {

		throw new RuntimeException(INFO_PREFIX + Thread.currentThread()
				.getStackTrace()[2].getMethodName() + INFO_SUFFIX);
	}



}
