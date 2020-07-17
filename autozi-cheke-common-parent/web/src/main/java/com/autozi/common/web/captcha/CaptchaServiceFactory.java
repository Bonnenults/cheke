package com.autozi.common.web.captcha;

import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.CaptchaService;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

public class CaptchaServiceFactory {
	private static final String DEFAULT_CHARACTERS = "123456789ABCDEFGHJKMNPQRSTXYZ"; // 自己设置！
	private static int DEFAULT_FONT_SIZE = 25;
	private static int DEFAULT_WORD_LENGTH = 4;
	private static int DEFAULT_WIDTH = 100;
	private static int DEFAULT_HEIGHT = 28;

	private CaptchaServiceFactory() {
	}

	public static CaptchaService create(int fontSize, int wordLength,
			String characters, int width, int height) {
		ConfigurableCaptchaService service = null;
		// 字体大小设置
		RandomFontFactory ff = new RandomFontFactory();
		ff.setMinSize(fontSize);
		ff.setMaxSize(fontSize);
		// 生成的单词设置
		RandomWordFactory rwf = new RandomWordFactory();
		rwf.setCharacters(characters);
		rwf.setMinLength(wordLength);
		rwf.setMaxLength(wordLength);
		// 干扰线波纹
		CurvesRippleFilterFactory crff = new CurvesRippleFilterFactory();
		// 处理器
		service = new ExConfigurableCaptchaService();
		service.setFontFactory(ff);
		service.setWordFactory(rwf);
		service.setFilterFactory(crff);
		// 生成图片大小（像素）
		service.setWidth(width);
		service.setHeight(height);
		return service;
	}
	
	public static CaptchaService create() {
		return create(DEFAULT_FONT_SIZE, DEFAULT_WORD_LENGTH, DEFAULT_CHARACTERS, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
