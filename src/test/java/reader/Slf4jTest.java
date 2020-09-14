package reader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Slf4jTest {
    private static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void testSlf4jtoFile(){
       //级别为debug的日志
       logger.debug("Hello! debug!");
       //级别为info的日志
       logger.info("Hello! info!");
       //级别为warn的日志
       logger.warn("Hello! warn!");
       //级别为error的日志
       logger.error("Hello! error!");
    }

    @Test
    public void testClassToFile(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Student.txt"));
            Student stu = new Student(1,"吴彦祖");
            oos.writeObject(stu);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
