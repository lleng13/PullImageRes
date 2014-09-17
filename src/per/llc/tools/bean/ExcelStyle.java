package per.llc.tools.bean;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class ExcelStyle {
	public CellStyle STYLE_FONT_BOLD;
	public CellStyle STYLE_FONT_COLNAME;
	public CellStyle STYLE_FONT_INVALID;
	public HSSFWorkbook workbook;
	public ExcelStyle(HSSFWorkbook workbook) {
		this.workbook = workbook;
		this.STYLE_FONT_BOLD = setStyleFontBold();
		this.STYLE_FONT_COLNAME = setStyleFontColName();
		this.STYLE_FONT_INVALID = setStyleInvalid();
	}
	public CellStyle setStyleFontColName() {
		CellStyle style = this.workbook.createCellStyle();
		Font font = this.workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)15);
		font.setColor(HSSFColor.BLUE.index);
		style.setFont(font);
		return style;
	}
	
	private CellStyle setStyleFontBold() {
		CellStyle style = this.workbook.createCellStyle();
		Font font = this.workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		return style;
	}
	private CellStyle setStyleInvalid() {
		CellStyle style = this.workbook.createCellStyle();
		Font font = this.workbook.createFont();
		font.setColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFont(font);
		return style;
	}
}
