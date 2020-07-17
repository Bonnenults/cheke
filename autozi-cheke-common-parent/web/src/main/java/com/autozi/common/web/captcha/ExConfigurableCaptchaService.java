package com.autozi.common.web.captcha;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.service.Captcha;

public class ExConfigurableCaptchaService extends ConfigurableCaptchaService {
	private static final Random RANDOM = new Random();
	private List<ColorFactory> colorList = new ArrayList<ColorFactory>(); // 为了性能

	public ExConfigurableCaptchaService() {
		colorList.add(new SingleColorFactory(Color.blue));
		colorList.add(new SingleColorFactory(Color.black));
		colorList.add(new SingleColorFactory(Color.red));
		colorList.add(new SingleColorFactory(Color.pink));
		colorList.add(new SingleColorFactory(Color.orange));
		colorList.add(new SingleColorFactory(Color.green));
		colorList.add(new SingleColorFactory(Color.magenta));
		colorList.add(new SingleColorFactory(Color.cyan));
	}

	public ColorFactory nextColorFactory() {
		int index = RANDOM.nextInt(colorList.size());
		return colorList.get(index);
	}

	@Override
	public Captcha getCaptcha() {
		ColorFactory cf = nextColorFactory();
		CurvesRippleFilterFactory crff = (CurvesRippleFilterFactory) this
				.getFilterFactory();
		crff.setColorFactory(cf);
		this.setColorFactory(cf);
		return super.getCaptcha();
	}

//	public static void main(String[] args) throws Exception {
//		CaptchaService service = CaptchaServiceFactory.create();
//		for (int i = 0; i < 10; ++i) {
//			FileOutputStream fos = null;
//			try {
//				fos = new FileOutputStream("C:/Users/haifeng.li/Desktop/新建文件夹/" + i + ".png");
//				String captcha = EncoderHelper.getChallangeAndWriteImage(
//						service, "png", fos);
//				System.out.println(captcha);
//			} finally {
//				if (fos != null) {
//					try {
//						fos.close();
//					} catch (Exception e) {
//					}
//				}
//			}
//		}
//	}
}