package com.autozi.common.utils.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jofc2.model.Chart;
import jofc2.model.Text;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.BarChart;
import jofc2.model.elements.BarChart.Bar;
import jofc2.model.elements.BarChart.Style;
import jofc2.model.elements.PieChart;
import jofc2.model.elements.PieChart.Slice;

/**
 * 
 * @author Ropod
 * 
 */
public class ChartUtil {

	// PieChart的一些属性
	// 字体大小
	public static String PIE_FONT_SIZE_PROP = "PIE_FONT_SIZE";
	public static Integer PIE_FONT_SIZE = new Integer(12);
	// 设置角度
	public static String PIE_START_ANGLE_PROP = "PIE_START_ANGLE";
	public static Integer PIE_START_ANGLE = new Integer(100);
	// 动态显示
	public static String PIE_ANIMATE_PROP = "PIE_ANIMATE";
	public static Boolean PIE_ANIMATE = false;
	// 渐变显示
	public static String PIE_GRADIEN_FILL_PROP = "PIE_GRADIEN_FILL";
	public static Boolean PIE_GRADIEN_FILL = true;
	// 颜色
	public static String[] PIE_COLOURS = ColorUtils.getColors();
	// 鼠标移动上去后提示内容
	public static String PIE_TOOL_TIP_PROP = "PIE_TOOL_TIP";
	public static String PIE_TOOL_TIP = "#val# / #total# <br> #percent#";
	// pie图的大小
	public static String PIE_RADIUS_PROP = "PIE_RADIUS";
	public static Integer PIE_RADIUS = new Integer(100);
	// 整个图的标题
	public static String PIE_TITLE_TEXT_PROP = "PIE_TITLE_TEXT";
	public static String PIE_TITLE_TEXT = "QEEGOO PIE";
	// 标题样式
	public static String PIE_TITLE_STYLE_PROP = "PIE_TITLE_STYLE";
	public static String PIE_TITLE_STYLE = "{font-size:14px;text-algin:center;color: #d01f3c;padding:5px 20px;}";
	// 背景颜色
	public static String PIE_BACK_GROUND_COLOUR_PROP = "PIE_BACK_GROUND_COLOUR";
	public static String PIE_BACK_GROUND_COLOUR = "#FFFFFF";

	/**
	 * 生成PieChart
	 * 
	 * @author Ropod
	 * @param values
	 *            需要显示的数据
	 * @param propMap
	 *            需要修改的属性
	 * @return
	 */
	public static String getPieChart(List<Slice> values, Map<String, Object> propMap) {
		// 创建pie对象
		PieChart pie = new PieChart();

		if (propMap == null) {
			propMap = new HashMap<String, Object>();
		}

		// 设置pie的一些属性
		// 设置字体大小
		if (propMap.containsKey(PIE_FONT_SIZE_PROP)) {
			pie.setFontSize(Integer.valueOf(propMap.get(PIE_FONT_SIZE_PROP).toString()));
		} else {
			pie.setFontSize(PIE_FONT_SIZE);
		}
		// 设置角度
		if (propMap.containsKey(PIE_START_ANGLE_PROP)) {
			pie.setStartAngle(Integer.valueOf(propMap.get(PIE_START_ANGLE_PROP).toString()));
		} else {
			pie.setStartAngle(PIE_START_ANGLE);
		}
		// 渐变显示
		if (propMap.containsKey(PIE_GRADIEN_FILL_PROP)) {
			pie.setGradientFill(Boolean.valueOf(propMap.get(PIE_GRADIEN_FILL_PROP).toString()));
		} else {
			pie.setGradientFill(PIE_GRADIEN_FILL);
		}
		// 动态显示
		if (propMap.containsKey(PIE_ANIMATE_PROP)) {
			PIE_ANIMATE = Boolean.valueOf(propMap.get(PIE_ANIMATE_PROP).toString());
			if (PIE_ANIMATE) {
				pie.setAnimate(PIE_ANIMATE);
			}
		}
		// 设置颜色
		pie.setColours(PIE_COLOURS);
		// 鼠标移动上去后提示内容
		if (propMap.containsKey(PIE_TOOL_TIP_PROP)) {
			pie.setTooltip(propMap.get(PIE_TOOL_TIP_PROP).toString());
		} else {
			pie.setTooltip(PIE_TOOL_TIP);
		}
		// pie图的大小
		if (propMap.containsKey(PIE_RADIUS_PROP)) {
			pie.setRadius(Integer.valueOf(propMap.get(PIE_RADIUS_PROP).toString()));
		} else {
			pie.setRadius(PIE_RADIUS);
		}

		// 整个图标的样式
		String titleText = PIE_TITLE_TEXT;
		String titleStyle = PIE_TITLE_STYLE;
		if (propMap.containsKey(PIE_TITLE_TEXT_PROP)) {
			titleText = propMap.get(PIE_TITLE_TEXT_PROP).toString();
		}
		if (propMap.containsKey(PIE_TITLE_STYLE)) {
			titleStyle = propMap.get(PIE_TITLE_STYLE_PROP).toString();
		}
		Chart flashChart = new Chart(titleText, titleStyle); // 整个图的标题
		// 设置背景颜色
		if (propMap.containsKey(PIE_BACK_GROUND_COLOUR_PROP)) {
			flashChart.setBackgroundColour(propMap.get(PIE_BACK_GROUND_COLOUR_PROP).toString());
		} else {
			flashChart.setBackgroundColour(PIE_BACK_GROUND_COLOUR);
		}
		// 放入数据
		pie.addSlices(values);
		flashChart.addElements(pie); // 把饼图加入到图表
		return flashChart.toString();// 转成 json 格式
	}

	// 条状图样式
	public static String BAR_CHART_STYLE_PROP = "BAR_CHART_STYLE";
	public static Style BAR_CHART_STYLE = BarChart.Style.GLASS;
	// Y 轴最大值
	public static String BAR_Y_MAX_PROP = "BAR_Y_MAX";
	public static Double BAR_Y_MAX = new Double(900);
	// Y 轴的文字的大小
	public static String BAR_Y_LABEL_SIZE_PROP = "BAR_Y_LABEL_SIZE";
	public static Integer BAR_Y_LABEL_SIZE = 12;
	// X 轴的文字
	public static String BAR_X_LABEL_PROP = "BAR_X_LABEL";
	public static String BAR_X_LABEL = "";
	// X 轴的文字的大小
	public static String BAR_X_LABEL_SIZE_PROP = "BAR_X_LABEL_SIZE";
	public static Integer BAR_X_LABEL_SIZE = 12;
	// bar的颜色
	public static String BAR_BAR_COLOR_PROP = "BAR_BAR_COLOR";
	public static String BAR_BAR_COLOR = "0x336699";
	// 鼠标移动上去后的提示
	public static String BAR_TOOL_TIP_PROP = "BAR_TOOL_TIP";
	public static String BAR_TOOL_TIP = "";
	// 背景颜色
	public static String BAR_BACK_GROUND_COLOUR_PROP = "BAR_BACK_GROUND_COLOUR";
	public static String BAR_BACK_GROUND_COLOUR = "#FFFFFF";
	// 整个图的标题
	public static String BAR_TITLE_TEXT_PROP = "BAR_TITLE_TEXT";
	public static String BAR_TITLE_TEXT = "QEEGOO BAR";
	// 标题样式
	public static String BAR_TITLE_STYLE_PROP = "BAR_TITLE_STYLE";
	public static String BAR_TITLE_STYLE = "{font-size:14px;text-algin:center;padding:5px 20px;}";
	// 步进
	public static String BAR_Y_STEPS_PROP = "BAR_Y_STEPS";
	public static Double BAR_Y_STEPS = new Double(BAR_Y_MAX / 10);
	// Y 轴显示内容
	public static String BAR_Y_TEXT_PROP = "BAR_Y_TEXT";
	public static String BAR_Y_TEXT = "Y";
	// Y 轴显示内容样式
	public static String BAR_Y_TEXT_TYPE_PROP = "BAR_Y_TEXT_TYPE";
	public static String BAR_Y_TEXT_TYPE = Text.createStyle(20, "#736AFF", Text.TEXT_ALIGN_CENTER);
	// X 轴显示内容
	public static String BAR_X_TEXT_PROP = "BAR_X_TEXT";
	public static String BAR_X_TEXT = "X";
	// X 轴显示内容样式
	public static String BAR_X_TEXT_TYPE_PROP = "BAR_X_TEXT_TYPE";
	public static String BAR_X_TEXT_TYPE = Text.createStyle(20, "#736AFF", Text.TEXT_ALIGN_CENTER);

	/**
	 * 生成 BarChart
	 * 
	 * @author Ropod
	 * @param dataMap
	 * @param propMap
	 * @return
	 */
	public static String getBarChart(Map<Object, Object> dataMap, Map<String, Object> propMap) {
		BarChart chart = null;
		if (propMap == null) {
			propMap = new HashMap<String, Object>();
		}
		// 设置条状图样式
		if (propMap.containsKey(BAR_CHART_STYLE_PROP)) {
			chart = new BarChart((Style) propMap.get(BAR_CHART_STYLE_PROP));
		} else {
			chart = new BarChart(BAR_CHART_STYLE);
		}
		// Y 轴最大值
		Double yMax = BAR_Y_MAX;
		if (propMap.containsKey(BAR_Y_MAX_PROP)) {
			yMax = (Double) propMap.get(BAR_Y_MAX_PROP);
		}
		String xAxisLable = BAR_X_LABEL;
		if (propMap.containsKey(BAR_X_LABEL_PROP)) {
			xAxisLable = propMap.get(BAR_X_LABEL_PROP).toString();
		}
		String xBarColor = ColorUtils.getColor();
		if (propMap.containsKey(BAR_BAR_COLOR_PROP)) {
			xBarColor = propMap.get(BAR_BAR_COLOR_PROP).toString();
		}
		String xBarToolTip = BAR_TOOL_TIP;
		if (propMap.containsKey(BAR_TOOL_TIP_PROP)) {
			xBarToolTip = propMap.get(BAR_TOOL_TIP_PROP).toString();
		}

		// X 轴
		XAxis x = new XAxis();
		for (Object key : dataMap.keySet()) {
			// x 轴的文字
			x.addLabels(key + xAxisLable);
			Bar bar = new Bar(Long.valueOf(dataMap.get(key).toString()));
			// 颜色
			bar.setColour(xBarColor);
			// 鼠标移动上去后的提示
			bar.setTooltip(dataMap.get(key) + xBarToolTip);
			// 条标题，显示在 x 轴上
			chart.addBars(bar);
		}
		if (propMap.containsKey(BAR_X_LABEL_SIZE_PROP)) {
			BAR_X_LABEL_SIZE =Integer.valueOf(propMap.get(BAR_X_LABEL_SIZE_PROP).toString());
		}
		x.getLabels().setSize(BAR_X_LABEL_SIZE);
		// 整个图标的样式
		String titleText = BAR_TITLE_TEXT;
		String titleStyle = BAR_TITLE_STYLE;
		if (propMap.containsKey(BAR_TITLE_TEXT_PROP)) {
			titleText = propMap.get(BAR_TITLE_TEXT_PROP).toString();
		}
		if (propMap.containsKey(BAR_TITLE_STYLE)) {
			titleStyle = propMap.get(BAR_TITLE_STYLE_PROP).toString();
		}
		Chart flashChart = new Chart(titleText, titleStyle); // 整个图的标题
		// 把柱图加入到图表
		flashChart.addElements(chart);
		// y 轴
		YAxis y = new YAxis();
		// y 轴最大值
		y.setMax(yMax);
		// 步进
		if (propMap.containsKey(BAR_Y_STEPS_PROP)) {
			y.setSteps((Double) propMap.get(BAR_Y_STEPS_PROP));
		} else {
			y.setSteps(BAR_Y_STEPS);
		}
		if (propMap.containsKey(BAR_Y_LABEL_SIZE_PROP)) {
			BAR_Y_LABEL_SIZE =Integer.valueOf(propMap.get(BAR_Y_LABEL_SIZE_PROP).toString());
		}
		y.getLabels().setSize(BAR_Y_LABEL_SIZE);
		flashChart.setYAxis(y);
		flashChart.setXAxis(x);
		// 设置y轴显示内容
		if (propMap.containsKey(BAR_Y_TEXT_PROP)) {
			String yText = propMap.get(BAR_Y_TEXT_PROP).toString();
			String yStyle = BAR_Y_TEXT_TYPE;
			if (propMap.containsKey(BAR_Y_TEXT_TYPE_PROP)) {
				yStyle = propMap.get(BAR_Y_TEXT_TYPE_PROP).toString();
			}
			Text yLegend = new Text(yText, yStyle);
			// Y 轴显示内容
			flashChart.setYLegend(yLegend);
		}
		// 设置x轴显示内容
		if (propMap.containsKey(BAR_X_TEXT_PROP)) {
			String xText = propMap.get(BAR_X_TEXT_PROP).toString();
			String xStyle = BAR_X_TEXT_TYPE;
			if (propMap.containsKey(BAR_X_TEXT_TYPE_PROP)) {
				xStyle = propMap.get(BAR_X_TEXT_TYPE_PROP).toString();
			}
			Text xLegend = new Text(xText, xStyle);
			// X 轴显示内容
			flashChart.setXLegend(xLegend);
		}
		// 设置背景颜色
		if (propMap.containsKey(BAR_BACK_GROUND_COLOUR_PROP)) {
			flashChart.setBackgroundColour(propMap.get(BAR_BACK_GROUND_COLOUR_PROP).toString());
		} else {
			flashChart.setBackgroundColour(BAR_BACK_GROUND_COLOUR);
		}
		return flashChart.toString();
	}
}
