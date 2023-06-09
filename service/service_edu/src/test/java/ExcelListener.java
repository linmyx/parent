import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    //一行一行的读取内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("*****"+demoData);
    }
    //读取表头信息
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头内容:"+headMap);
    }

    //读取完成之后做的事情
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
