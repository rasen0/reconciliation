package reader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import util.StringTool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class ReadPoiTest {

    @Test
    public void ReadTest() {
        System.out.println("read TEST");
        String xPath = "D:\\workspace\\workme\\reconci\\src\\main\\resources\\体检对账2020年 - 副本.xls";
        try (FileInputStream fis = new FileInputStream(xPath)) {
            Workbook workbook = new HSSFWorkbook(fis);
            int numberOfSheet = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheet; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Row row = sheet.getRow(7);
//                for (int j = 0; j < ; j++) {
//
//                }
                System.out.println("row num:"+row.getRowNum());
            }
//            Row row = workbook.getSheetAt(0).getRow(7);
//            for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
//                Cell c = it.next();
//                System.out.println(c.toString());
//            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void ReadAndWriteTest() {
        System.out.println("read and write TEST");
        String xPath = "D:\\workspace\\workme\\reconci\\src\\main\\resources\\体检对账2020年 - 副本.xls";
        FileInputStream fis = null;
        Workbook workbook = null;
        try  {
            fis = new FileInputStream(xPath);
            workbook = new HSSFWorkbook(fis);
            int numberOfSheet = workbook.getNumberOfSheets();
//            System.out.println("numberOfSheet:" + numberOfSheet);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(8);
            System.out.println("row num:"+row.getRowNum());
            System.out.println("last row num:"+row.getLastCellNum());
            Cell oldCell = row.getCell(3);
            System.out.println("old cell:" + oldCell.getStringCellValue());
            CellStyle oldCellStyle = oldCell.getCellStyle();
//            Cell cell = row.getCell(8);
//            if (cell == null) {
//                cell = row.createCell(8);
//            }
//            Font sFont = workbook.createFont();
//            sFont.setFontName("宋体");
//            sFont.setFontHeight((short)10);
//            CellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.setFont(sFont);
//            oldCellStyle.setDataFormat((short)4);
//            cell.setCellStyle(oldCellStyle);
//            cell.setCellValue("- 123456789.32");

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(xPath);
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }

    }
    @Test
    public void StringIntoDecimalTest() {
        StringTool st = new util.StringTool();
        String digitalStr = "  12,234,857,374.84";
        BigDecimal bd = st.StringToBigDecimal(digitalStr);
        System.out.println("mi:" + bd);
//        boolean minus = false;
//        int digitalIdx = -1;
//        int digitalEndIdx = -1;
//        for (int i= 0; i < digitalStr.length();i++){
//            if (digitalStr.charAt(i) == '-'){
//                minus = true;
//                continue;
//            }else if (digitalIdx < 0 && Character.isDigit(digitalStr.charAt(i))){
//                digitalIdx = i;
//                continue;
//            }else if ( digitalIdx > 0 && i > digitalIdx && digitalStr.charAt(i) == ' ') {
//                digitalEndIdx = i;
//                break;
//            }
//        }
//        if (digitalEndIdx == -1) {
//            digitalEndIdx = digitalStr.length();
//        }
//        String digitalStr2 = digitalStr.substring(digitalIdx,digitalEndIdx);
//        digitalStr2 = digitalStr2.replaceAll(",","");
//        System.out.printf("|%s|\n",digitalStr2);
//        BigDecimal bigDecimal = null;
//        if (minus) {
//            digitalStr2 = "-" + digitalStr2;
//            bigDecimal = new BigDecimal(digitalStr2);
//        }
//        System.out.println("mi:" + bigDecimal);

    }
}
