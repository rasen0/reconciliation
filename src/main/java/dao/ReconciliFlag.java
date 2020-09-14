package dao;

import common.Color;

import java.io.Serializable;

public class ReconciliFlag implements Serializable {
    private int rowNum;
    private int colNum;
    private Color color;

    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ReconciliationFlag{" +
                "rowNum=" + rowNum +
                ", colNum=" + colNum +
                ", color=" + color +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
