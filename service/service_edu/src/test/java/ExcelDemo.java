import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelDemo {
    public static void main(String[] args) {
        //实现excel的写操作
        //1.设置写入文件夹地址和excel的文件名字
        //String fileName = "E:\\guli_log\\02.xlsx";
        //2.调用spi
        //EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());

        //实现excel的读操作
        String fileName = "E:\\guli_log\\02.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();

    }
    //创建方法
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        long l = System.currentTimeMillis();
        for (int i = 1; i <=10;i++ ) {
            DemoData demoData = new DemoData();
           demoData.setSno(i);
           demoData.setName("小明"+i);
           list.add(demoData);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("一共耗时:"+(l1-l));
        return list;
    }

}
