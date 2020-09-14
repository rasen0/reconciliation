package reconci;

import dao.ReconciliFlag;
import handler.HandleReconciliation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String usrpath = System.getProperty("user.dir");
        String path = usrpath + File.separator+"对账.xls";
        logger.info("path:" + path);
        File file = new File(path);
        if (!file.exists()){
            logger.info("file is not exist at path[{}]",path);
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            fis = new FileInputStream(path);
            workbook = new HSSFWorkbook(fis);
//            int numberOfSheet = workbook.getNumberOfSheets();
            sheet = workbook.getSheetAt(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HandleReconciliation rd = new HandleReconciliation();
        LinkedList<ReconciliFlag> reconFlagList = new LinkedList();
        assert sheet != null;
        rd.handleReconciliations(sheet,reconFlagList);
        // todo 添加背景色溢出单元格，暂时输出到文本中
//        FileTool fileTool = new FileTool();
//        try {
//            fileTool.writeIntoFile(usrpath + File.separator+"reconciliationResult.txt",reconFlagList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (ReconciliFlag rf: reconFlagList) {
            logger.info("flag:"+rf.toString());
//            System.out.println("flag:"+rf.toString());
        }

        //todo 暂时不做单元格填充
        // write excel
        try {
            fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
