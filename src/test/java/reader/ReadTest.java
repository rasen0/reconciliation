package reader;

import org.junit.Test;
import util.StringTool;

import java.math.BigDecimal;

public class ReadTest {
    @Test
    public void testRead(){
//        String cn = "收2020年4月体检费（蓝山县工伤保险中心）";
//        String cn = "收到蓝山县工伤保险中心体检费";
        String cn = "收2020年7月体检费(郴州市城市管理和综合执法局（高新区城管执法大队）)";
//        String cn = "收2020年6月体检费（郴州市农业农村局(妇检））";
        StringTool stringTool = new StringTool();
        String cName = stringTool.parseCompanyName(cn);
        System.out.println("name:"+ cName);
    }

    @Test
    public void testBigDecimal(){
        BigDecimal debit = new BigDecimal(8);
        BigDecimal credit = new BigDecimal(5);
        System.out.println("debit :"+debit.toBigInteger());
        System.out.println("credit negate:"+credit.negate().toBigInteger());
        BigDecimal remain = debit.add(credit.negate());
        System.out.println("remain:"+ remain.toBigInteger());
    }
}