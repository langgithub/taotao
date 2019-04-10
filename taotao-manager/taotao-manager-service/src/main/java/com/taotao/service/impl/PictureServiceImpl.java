package com.taotao.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.util.FtpUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	@Value("${FILI_UPLOAD_PATH}")
	private String FILI_UPLOAD_PATH;
	@Value("${FTP_SERVER_IP}")
	private String FTP_SERVER_IP;
	@Value("${FTP_SERVER_PORT}")
	private Integer FTP_SERVER_PORT;
	@Value("${FTP_SERVER_USERNAME}")
	private String FTP_SERVER_USERNAME;
	@Value("${FTP_SERVER_PASSWORD}")
	private String FTP_SERVER_PASSWORD;

	@Override
	public Map uploadFile(MultipartFile uploadFile) {
		Map<String, Object> map=new HashMap<String,Object>();
		try {
			// 上传文件功能实现
			// 判断文件是否为空
			if (uploadFile.isEmpty())
				return null;
			// 上传文件以日期为单位分开存放，可以提高图片的查询速度
			String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date())
					+ new SimpleDateFormat("MM").format(new Date())
					+ new SimpleDateFormat("dd").format(new Date());

			System.out.println("filePath"+filePath);
			// 取原始文件名
			String originalFilename = uploadFile.getOriginalFilename();
			// 新文件名
			String newFileName = IDUtils.genImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));
			// 转存文件，上传到ftp服务器
			boolean flag=FtpUtil.uploadFile(FTP_SERVER_IP, FTP_SERVER_PORT, FTP_SERVER_USERNAME, FTP_SERVER_PASSWORD,
					FILI_UPLOAD_PATH, filePath, newFileName, uploadFile.getInputStream());
			String resultUrl = IMAGE_BASE_URL+filePath + "/" + newFileName;
			System.out.println("resultUrl"+resultUrl);
			if(flag){
				map.put("error", 0);
				map.put("url", resultUrl);
			}else{
				map.put("error", 1);
				map.put("message", "上传错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}

