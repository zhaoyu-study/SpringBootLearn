package com.zy.springbootlearn.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * Excel导出自适应宽度工具类
 *
 * @author LL
 * @date 2020/12/02 22:28
 */
public class AdaptiveWidthUtils {

    /**
     * 自适应宽度(中文支持)
     *
     * @param sheet sheet
     * @param size  因为for循环从0开始，size值为 列数-1
     */
    public static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum <= size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }
}
