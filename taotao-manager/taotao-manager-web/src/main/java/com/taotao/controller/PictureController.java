package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;

/**
 * 上传图片处理
 * @author lang
 *
 */
@Controller
@RequestMapping("/pic")
public class PictureController {
	
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/upload")
	@ResponseBody
	public Map uploda(MultipartFile uploadFile) throws Exception {
		//调用service上传图片
		Map map = pictureService.uploadFile(uploadFile);
		//返回上传结果
		return map;
	}
}

