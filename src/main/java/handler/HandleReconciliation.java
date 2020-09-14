package handler;

import common.Color;
import common.Constant;
import dao.CompanyData;
import dao.ReconFlagList;
import dao.ReconciliFlag;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reconci.Main;
import util.StringTool;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class HandleReconciliation {
    private static final Logger logger = LoggerFactory.getLogger(HandleReconciliation.class);
    public LinkedHashMap<String, CompanyData> reconciMap;

    public void handleReconciliations(Sheet sheet, LinkedList<ReconciliFlag> reconFlagList) {
        StringTool stringTool = new StringTool();
        this.reconciMap = new LinkedHashMap();
//        BigDecimal balance = null;
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            logger.info("0:"+row.getCell(Constant.SUMMARY).getStringCellValue());
//            System.out.println("0:"+row.getCell(Constant.SUMMARY).getStringCellValue());
            StringTool st = new util.StringTool();
            if (row.getCell(Constant.SUMMARY).getStringCellValue().equals("本月合计") || row.getCell(3).getStringCellValue().equals("期初") ||
                    row.getCell(Constant.SUMMARY).getStringCellValue().equals("本年累计")) {
                // 跳过
                continue;
            }
            if (row.getCell(Constant.DEBITSTRING).getStringCellValue().equals("借")){
                int cellIdx = Constant.SUMMARY;
                String summary = row.getCell(cellIdx).getStringCellValue();
                String companyName = stringTool.parseCompanyName(summary);
                if (companyName.equals("")){
                    //空白名称不处理
                    continue;
                }
//                System.out.println("summary:"+ summary + " company:" + companyName);
                logger.info("summary:"+ summary + " company:" + companyName);
                cellIdx++;
                String debitStr = row.getCell(cellIdx).toString();
                BigDecimal debit = null;
                debit = st.StringToBigDecimal(debitStr);
                cellIdx++;
                String creditStr = row.getCell(cellIdx).toString();
                BigDecimal credit = null;
                credit = st.StringToBigDecimal(creditStr);

                CompanyData company = new CompanyData();
                company.setRowNum(row.getRowNum());
                company.setColNum(cellIdx);
                company.setSummary(summary);
                company.setCompanyName(companyName);
                company.setDebit(debit);
                company.setCredit(credit);
                company.setCreditNum(1);
                company.setDebitNum(1);
                company.setSum(debit.add(credit.negate())); // 借贷做差
                if (!this.reconciMap.containsKey(companyName)){
                    // 第一次放入map
                    this.reconciMap.put(companyName,company);
                }else {
                    CompanyData oldCompany = this.reconciMap.get(companyName);
                    BigDecimal sum2 = company.getSum();
                    BigDecimal sum1 = oldCompany.getSum();
                    BigDecimal remain =sum2.add(sum1);
                    if (remain.compareTo(new BigDecimal(0)) == 0){
                        int rowNum = row.getRowNum();
                        ReconciliFlag reconciliFlag2 = new ReconciliFlag();
                        reconciliFlag2.setRowNum(rowNum);
                        reconciliFlag2.setColor(Color.GREEN);
                        reconciliFlag2.setColNum(Constant.SUMMARY);
                        reconciliFlag2.setCompanyName(oldCompany.getCompanyName());
                        reconFlagList.add(reconciliFlag2);


                        rowNum = oldCompany.getRowNum();
                        ReconciliFlag reconciliFlag = new ReconciliFlag();
                        reconciliFlag.setRowNum(rowNum);
                        reconciliFlag.setColor(Color.GREEN);
                        reconciliFlag.setColNum(Constant.SUMMARY);
                        reconciliFlag.setCompanyName(company.getCompanyName());
                        reconFlagList.add(reconciliFlag);
                    }
                }
                continue;
            }
        }

        // 修改标记对账的单位单元格  todo 暂时不做单元格填充
        for (ReconciliFlag rf: reconFlagList) {
            int rowNum = rf.getRowNum();
//            System.out.println("rownu:"+rowNum+"  :name:" + rf.getCompanyName() + " col:"+ Constant.SUMMARY);
            logger.info("rownu:"+rowNum+"  :name:" + rf.getCompanyName() + " col:"+ Constant.SUMMARY);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.createCell(row.getLastCellNum() + 1);
            cell.setCellValue("已对账");
//            Cell cell = row.getCell(Constant.SUMMARY);
//            CellStyle cs = cell.getCellStyle();
//            cs.setBorderBottom(BorderStyle.THIN);
//            cs.setBorderLeft(BorderStyle.THIN);
//            cs.setBorderTop(BorderStyle.THIN);
//            cs.setBorderRight(BorderStyle.THIN);
//            cs.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
//            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            cell.setCellStyle(cs);
        }
    }
}
