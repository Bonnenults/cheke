/**
 * 文件名称   : com.qigou.core.web.captcha.QrcodeServlet.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-12-3
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.common.web.captcha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swetake.util.Qrcode;

/**
 * <PRE>
 * 
 * 中文描述：二维码生成器
 * 
 * </PRE>
 * 
 * @version 1.0.0
 */
public class QrcodeServlet extends HttpServlet {

	private static final long serialVersionUID = 908455020331542512L;

	private static final int IMAGE_WIDTH = 70;
	private static final int IMAGE_HEIGHT = 70;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String content = request.getParameter("content");
		Qrcode qrcodeHandler = new Qrcode();
		qrcodeHandler.setQrcodeErrorCorrect('M');
		qrcodeHandler.setQrcodeEncodeMode('B');
		qrcodeHandler.setQrcodeVersion(3);

		BufferedImage bufImg = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		gs.setColor(Color.BLACK);

		byte[] contentBytes = content.getBytes("gb2312");
		boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
		int pixoff = 1;
		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; j++) {
				if (codeOut[j][i]) {
					gs.fillRect(j * 2 + pixoff, i * 2 + pixoff, 2, 2);
				}
			}
		}
		gs.dispose();
		bufImg.flush();
		ImageIO.write(bufImg, "png", response.getOutputStream());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
