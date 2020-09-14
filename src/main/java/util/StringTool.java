package util;

import java.math.BigDecimal;

public class StringTool {
    public BigDecimal StringToBigDecimal(String digitalStr){
        boolean minus = false;
        int digitalIdx = -1;
        int digitalEndIdx = -1;
        BigDecimal bigDecimal = null;
        digitalStr = digitalStr.trim();
        if (digitalStr.isEmpty()){
            bigDecimal = new BigDecimal(0);
            return bigDecimal;
        }
        for (int i= 0; i < digitalStr.length();i++){
            if (digitalStr.charAt(i) == '-'){
                minus = true;
                continue;
            }else if (digitalIdx < 0 && Character.isDigit(digitalStr.charAt(i))){
                digitalIdx = i;
                continue;
            }else if ( digitalIdx > 0 && i > digitalIdx && digitalStr.charAt(i) == ' ') {
                digitalEndIdx = i;
                break;
            }
        }
        if (digitalEndIdx == -1) {
            digitalEndIdx = digitalStr.length();
        }
        String digitalStr2 = digitalStr.substring(digitalIdx,digitalEndIdx);
        digitalStr2 = digitalStr2.replaceAll(",","");
        if (minus) {
            digitalStr2 = "-" + digitalStr2;
        }
        bigDecimal = new BigDecimal(digitalStr2);
        return bigDecimal;
    }

    /**
     *拆分出公司名称
     */
    public String parseCompanyName(String name) {
        if (name.equals("0398户") || name.contains("#") || name.contains("冲销")){
            return "";
        }

        int parenthesesStart = name.indexOf("(");
        int parenthesesEnd = name.lastIndexOf(")");
        int parenthesesStartzn = name.indexOf("（");
        int parenthesesEndzn = name.lastIndexOf("）");
        int parenthesesLeft = -1;
        int parenthesesRight = -1;
        // 根据中英小括号找边界
        if (parenthesesStart > 0 && parenthesesStartzn > 0){
            if (parenthesesStart < parenthesesStartzn){
                parenthesesLeft = parenthesesStart;
            } else {
                parenthesesLeft = parenthesesStartzn;
            }
        }else {
            if (parenthesesStartzn < 0 && parenthesesStart >0 ){
                parenthesesLeft = parenthesesStart;
            }
            if (parenthesesStart < 0 && parenthesesStartzn >0 ){
                parenthesesLeft = parenthesesStartzn;
            }
        }
        if (parenthesesEnd > 0 && parenthesesEndzn > 0){
            if (parenthesesEnd > parenthesesEndzn){
                parenthesesRight = parenthesesEnd;
            } else {
                parenthesesRight = parenthesesEndzn;
            }
        }else {
            if (parenthesesEndzn < 0 && parenthesesEnd >0 ){
                parenthesesRight = parenthesesEnd;
            }
            if (parenthesesEnd < 0 && parenthesesEndzn >0 ){
                parenthesesRight = parenthesesEndzn;
            }
        }
        if (parenthesesLeft > parenthesesRight) {
            System.out.println("error: parenthesesLeft > parenthesesRight");
            return "";
        }

        int rcvIdx = name.indexOf("收");
        int rcvedIdx = name.indexOf("收到");
        int receiveIdx = -1;
        if (rcvIdx >= 0) {
            receiveIdx = rcvIdx+1;
        }
        if (rcvedIdx >= 0 ) {
            receiveIdx = rcvedIdx+2;
        }
        int examinationIdx = name.indexOf("体检费");
        String subName = "";
        // 括号判断大于“收到” “收”. 例：收体检费（xxx）
        if (parenthesesLeft> 0 && parenthesesRight > parenthesesLeft && parenthesesLeft > rcvIdx && parenthesesLeft > rcvedIdx){
            subName = name.substring(parenthesesLeft+1,parenthesesRight);
//            if (subName.equals("0398户") || subName.contains("#") || subName.contains("冲销")){
//                return "";
//            }
        }else if (receiveIdx > parenthesesLeft && parenthesesLeft >0 ) {
            // 括号判断小于“收到” “收”
            if ( examinationIdx < 0){
                // 时间(收xxx)
                subName = name.substring(receiveIdx,parenthesesRight);
                return subName;
            }else if (examinationIdx > 0 && examinationIdx < parenthesesRight) {
                // 7月3日（收xxx体检费）
                subName = name.substring(receiveIdx,examinationIdx);
                return subName;
            }
        }else if ( receiveIdx > 0 && receiveIdx < examinationIdx) {
            // 收到xxx体检费
            subName = name.substring(receiveIdx,examinationIdx);
//            if (subName.equals("0398户") || subName.contains("#") || subName.contains("冲销")){
//                return "";
//            }
            return subName;
        }
    return subName;
    }

    public String parseCompanyName2(String name) {
        int parenthesesStart = name.indexOf("(");
        int parenthesesEnd = name.lastIndexOf(")");
        int parenthesesStartzn = name.indexOf("（");
        int parenthesesEndzn = name.lastIndexOf("）");
        int rcvIdx = name.indexOf("收");
        int examinationIdx = name.indexOf("体检费");
        int rcvedIdx = name.indexOf("收到");
        // 中文括号内处理
        if (parenthesesStartzn > 0) {
            String inSideName = "";
            if (parenthesesEnd > 0){
                inSideName = name.substring(parenthesesStartzn+1,parenthesesEnd);
            }else {
                inSideName = name.substring(parenthesesStartzn+1,parenthesesEndzn);
            }
            // "收"在括号前，括号内则是公司名称例：收到体检费（xxx）
            if (parenthesesStartzn > rcvIdx){
                // 冲销使用不记录
                if (inSideName.equals("0398户")) {
                    return "";
                }
                return inSideName;
            }else if (parenthesesStartzn < rcvIdx && rcvIdx < parenthesesEndzn) { // 小括号内“收”后公司
                if(rcvedIdx > 0) {// 小括号内“收到”后公司
                    // 小括号“收到”与“体检费”之间名称例：（收到xxx体检费）
                    if (examinationIdx > parenthesesStartzn && examinationIdx < parenthesesEndzn) {
                        return name.substring(rcvIdx+1,examinationIdx);
                    } else {
                        // 小括号“收到”与括号尾例：（收到xxx）
                        return name.substring(rcvIdx+1,parenthesesEndzn);
                    }
                }
                // 小括号“收”与“体检费”之间名称例：（收xxx体检费）
                if (examinationIdx > parenthesesStartzn && examinationIdx < parenthesesEndzn) {
                    return name.substring(rcvIdx+1,examinationIdx);
                } else {
                    // 小括号“收”与括号尾例：（收xxx）
                    return name.substring(rcvIdx+1,parenthesesEndzn);
                }
            }
        }else if(rcvedIdx > 0 && parenthesesStartzn < 0){ // 没有括号例：收到xxx体检费
            if (examinationIdx > 0) {
                return name.substring(rcvedIdx+1,examinationIdx);
            }else {
                return name.substring(rcvedIdx+1);
            }
        }else if (rcvIdx > 0 && parenthesesStartzn < 0){// 没有括号例：收xxx体检费
            if (examinationIdx > 0) {
                return name.substring(rcvIdx+1,examinationIdx);
            }else {
                return name.substring(rcvIdx+1);
            }
        }
        // 英文括号内处理
        if (parenthesesStart > 0) {
            String inSideName = "";
            if (parenthesesEndzn > 0){
                inSideName = name.substring(parenthesesStartzn+1,parenthesesEnd);
            }else {
                inSideName = name.substring(parenthesesStart+1,parenthesesEnd);
            }
            // "收"在括号前，括号内则是公司名称例：收到体检费（xxx）
            if (parenthesesStart > rcvIdx){
                // 冲销使用不记录
                if (inSideName.equals("0398户")) {
                    return "";
                }
                return inSideName;
            }else if (parenthesesStart < rcvIdx && rcvIdx < parenthesesEnd) { // 小括号内“收”后公司
                if(rcvedIdx > 0) {// 小括号内“收到”后公司
                    // 小括号“收到”与“体检费”之间名称例：（收到xxx体检费）
                    if (examinationIdx > parenthesesStart && examinationIdx < parenthesesEnd) {
                        return name.substring(rcvIdx+1,examinationIdx);
                    } else {
                        // 小括号“收到”与括号尾例：（收到xxx）
                        return name.substring(rcvIdx+1,parenthesesEnd);
                    }
                }
                // 小括号“收”与“体检费”之间名称例：（收xxx体检费）
                if (examinationIdx > parenthesesStart && examinationIdx < parenthesesEnd) {
                    return name.substring(rcvIdx+1,examinationIdx);
                } else {
                    // 小括号“收”与括号尾例：（收xxx）
                    return name.substring(rcvIdx+1,parenthesesEnd);
                }
            }
        }else if(rcvedIdx > 0 && parenthesesStart < 0){ // 没有括号例：收到xxx体检费
            if (examinationIdx > 0) {
                return name.substring(rcvedIdx+1,examinationIdx);
            }else {
                return name.substring(rcvedIdx+1,name.length());
            }
        }else if (rcvIdx > 0 && parenthesesStart < 0){// 没有括号例：收xxx体检费
            if (examinationIdx > 0) {
                return name.substring(rcvIdx+1,examinationIdx);
            }else {
                return name.substring(rcvIdx+1,name.length());
            }
        }
        // 未知不能处理
        return "";
    }
}
