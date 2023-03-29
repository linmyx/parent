import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


@Data
public class DemoData {
    @ExcelProperty("学生的编号")
    private Integer sno;
    @ExcelProperty("学生的姓名")
    private String name;
}
