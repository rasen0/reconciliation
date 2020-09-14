package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import util.StringTool;

@Data
public class ReconciliationData {
    private String string; //标题
    private String dateField; //日期
    private String subjectTitle;
    private String subjectName;
    private int currencyType;
    private int currency;
    private int year;
    private String yearStr;
    private String voucherField;
    private String summaryField;
    private String debitField;
    private String creditField;
    private int directionField;
    private String balanceField;
    public CompanyData[] companyData;
    private String totalMonthField;
    private long debitTotalMonth;
    private long creditTotalMonth;
    private String directTotalMonth;
    private long balanceTotalMonth;
    private String totalYearField;
    private long debitTotalYear;
    private long creditTotalYear;
    private String directTotalYear;
    private long balanceTotalYear;
    private String accountingUnitField;
    private String accountingUnit;
    private String TabulatorField;
    private String Tabulator;
    private String appName;
    private String printInfo;
    private String pageInfo;




}
