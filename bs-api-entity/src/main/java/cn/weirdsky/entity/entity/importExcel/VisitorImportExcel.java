package cn.weirdsky.entity.entity.importExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class VisitorImportExcel {

    @ExcelProperty("visitorName")
    private String visitorName;

    @ExcelProperty("visitorSex")
    private String visitorSex;

    @ExcelProperty("visitorDepartmentName")
    private String visitorDepartmentName;

    @ExcelProperty("remarks")
    private String remarks;

    @ExcelProperty("attendanceDays")
    private String attendanceDays;

}
