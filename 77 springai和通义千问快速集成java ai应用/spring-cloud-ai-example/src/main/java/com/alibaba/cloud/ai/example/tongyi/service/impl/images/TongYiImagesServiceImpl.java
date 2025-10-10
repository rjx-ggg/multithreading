package com.alibaba.cloud.ai.example.tongyi.service.impl.images;

import com.alibaba.cloud.ai.example.tongyi.service.AbstractTongYiServiceImpl;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TongYiImagesServiceImpl extends AbstractTongYiServiceImpl {

	private final ImageClient imageModel;

	@Autowired
	public TongYiImagesServiceImpl(ImageClient imageModel) {

		this.imageModel = imageModel;
	}

	@Override
	public ImageResponse genImg(String imgPrompt) {

		var prompt = new ImagePrompt(imgPrompt);
		return imageModel.call(prompt);
	}

}
