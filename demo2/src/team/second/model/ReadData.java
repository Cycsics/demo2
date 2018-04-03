package team.second.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel文件数据
 * @Data 20180328
 * @author WTZ
 *		
 */
public class ReadData {

	
	/** 总行数 */
    private int totalRows = 0;
    /** 总列数 */
    private int totalCells = 0;
    /** 错误信息 */
    private String errorInfo;
    /** 构造方法 */
    public ReadData() {
    }
    

    public int getTotalRows() {
        return totalRows;
    }
    public int getTotalCells() {
        return totalCells;
    }
    public String getErrorInfo() {
        return errorInfo;
    }

    public boolean validateExcel(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null
                || !(WDWUtil.isExcel2003(filePath) || WDWUtil
                        .isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }

    public List<List<String>> read(String filePath) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);
                return null;
            }
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return dataLst;
    }


    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        } catch (IOException e) {
        
            e.printStackTrace();
        }
        return dataLst;
    }
    

    private List<List<String>> read(Workbook wb) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        this.totalRows = sheet.getPhysicalNumberOfRows();
        /** 得到Excel的列数 */
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        /** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                        cellValue = cell.getNumericCellValue() + "";
                        break;
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        cellValue = cell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                        cellValue = cell.getBooleanCellValue() + "";
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        cellValue = cell.getCellFormula() + "";
                        break;
                    case HSSFCell.CELL_TYPE_BLANK: // 空值
                        cellValue = "";
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                        cellValue = "非法字符";
                        break;
                    default:
                        cellValue = "未知类型";
                        break;
                    }
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }
    public static void main(String[] args) throws Exception {
    	ReadData poi = new ReadData();
    	/**数据存储**/
    	double[][] data = new double[50][50];
    	  /**声明数据库对象**/
        Constant dataBase = new Constant();
        // List<List<String>> list = poi.read("d:/aaa.xls");
        List<List<String>> list = poi.read("C:\\Users\\Administrator\\Desktop\\暂存\\02 相似度问题\\lz01.xls");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print("第" + (i) + "行");
                List<String> cellList = list.get(i);
                for (int j = 0; j < cellList.size(); j++) {
                    // System.out.print("    第" + (j + 1) + "列值：");
                    System.out.print("    " + cellList.get(j));
                    String str = cellList.get(j);
                    if(i != 0 && i != 36&& j != 0) {
                    	double cny =Double.parseDouble(str); 
                        data[i-1][j-1] = cny;
                    }
                }
                System.out.println();
            }

        }
        for(int i = 0; i < 2; i++) {
        	for(int j = 0; j < 36; j++) {
        		System.out.print(data[j][i] +",");
        	}
        	System.out.println();
        }
        dataBase.setDataBase(data);
      
    }
	
}

class WDWUtil {
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
