package model;

import java.util.Map;

/**
 * Created by Jianhua Sun on 2018/9/5.
 */
public class Line {
    private int lineNo;
    private Map<String,String> data;

    public Line() {
    }

    public Line(int lineNo, Map<String, String> data) {
        this.lineNo = lineNo;
        this.data = data;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
