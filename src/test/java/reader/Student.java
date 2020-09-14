package reader;

public class Student implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int num;
    private String name;
    public Student(int num, String name) {
        this.num = num;
        this.name = name;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
