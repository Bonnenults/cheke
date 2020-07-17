package com.autozi.cheke.util.web;


import com.autozi.common.utils.util1.MD5;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 378952171335139418L;

	private static final int[] WORD_LENGTH = { 4, 5 };

	private static final int IMAGE_WIDTH = 100;

	private static final int IMAGE_HEIGHT = 28;

	private static final int FONT_SIZE = 16;

	private static final String NUMERIC_CHARS = "2345678";

	private static final String UPPER_ASCII_CHARS = "ABCDEFGHJKMNPQRSTXYZ";

	// private static final String LOWER_ASCII_CHARS = "abcdefghjkmnprstxyz";

	private static final String[] FONTS = { "Lucida Handwriting", "Blackoak Std" };

	public static final String CAPTCHA_SESSION = "captcha_session";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

		Random random = new Random();
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(random, 200, 240));
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		g.setFont(new Font(null, Font.PLAIN, 30));
		StringBuilder randChars = new StringBuilder();
		int len = WORD_LENGTH[random.nextInt(WORD_LENGTH.length)];
		for (int i = 0; i < len; i++) {
			String chars = NUMERIC_CHARS + UPPER_ASCII_CHARS;
			int start = random.nextInt(chars.length());
			String randChar = chars.substring(start, start + 1);
			randChars.append(randChar);
			g.setColor(new Color(random.nextInt(200), 0, 0));
			g.setFont(new Font(FONTS[random.nextInt(FONTS.length)], Font.BOLD, FONT_SIZE));
			g.drawString(randChar, i * (100 / len) + 4, 18);
		}
		g.dispose();
		HttpSession session = request.getSession();
		session.setAttribute(CAPTCHA_SESSION, MD5.getMD5(randChars.toString().toUpperCase()));
		ImageIO.write(image, "png", response.getOutputStream());
	}

	private Color getRandColor(Random random, int min, int max) {
		int r = random.nextInt(max - min) + min + 1;
		int g = random.nextInt(max - min) + min + 1;
		int b = random.nextInt(max - min) + min + 1;
		return new Color(r, g, b);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}