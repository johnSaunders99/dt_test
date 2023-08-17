package cn.gzsunrun.downtime.util;


import cn.gzsunrun.downtime.entity.excel.ExcelVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * EasyExcel工具类
 *
 * @author john saunders
 * @description:
 * @date: 2021/11/29
 * @time: 15:05
 */
public class ExcelUtils {

    /**
     * @param dtoList 继承ExcelVo的实际导出类List
     * @return HttpResponse流
     * @throws Exception
     */
    public static HttpServletResponse uploadExcel(List<ExcelVo> dtoList,
                                                  String sheetName,
                                                  String fileName,
                                                  HttpServletResponse response) throws Exception {
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new RuntimeException("产出失败!,数据列表为空!");
        }
        //开始转换
        ExcelWriter writer;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name()));
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        Class clzz = dtoList.get(0).getClass();
        writer = EasyExcel.write(out, clzz).build();
        WriteSheet sheet = EasyExcel.writerSheet(sheetName)
                .automaticMergeHead(true).build();
        writer.write(dtoList, sheet);
        out.flush();
        writer.finish();
        return response;
    }
}
