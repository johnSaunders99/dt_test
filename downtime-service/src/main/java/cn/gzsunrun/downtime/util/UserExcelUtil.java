package cn.gzsunrun.downtime.util;

import cn.gzsunrun.downtime.entity.excel.PurchaseGoodsDuoExportVO;
import cn.gzsunrun.downtime.entity.excel.PurchaseGoodsExportVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.google.protobuf.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author john saunders
 * @description:
 * @date: 2022/11/24
 * @time: 15:42
 */
@Slf4j
public class UserExcelUtil {

    /**
     * @param dtoList 继承ExcelVo的实际导出类List
     * @return HttpResponse流
     * @throws Exception
     */
    public static HttpServletResponse exportImageExcel(List<PurchaseGoodsExportVO> dtoList,
                                                       String sheetName,
                                                       String fileName,
                                                       HttpServletResponse response) throws Exception {
        if (CollectionUtils.isEmpty(dtoList)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return response;
        }
        //开始转换
        ExcelWriter writer;
        if (CollectionUtil.isEmpty(dtoList)){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return response;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name()));
        OutputStream out = response.getOutputStream();
        Class clzz = dtoList.get(0).getClass();
        //设置列长度所用类
        AutoColumnStyleStrategy longWidth = new AutoColumnStyleStrategy();
        List<String> cleanablePath = new ArrayList<>();
        dtoList.stream().forEach(
                dto -> {
                    //filebase_id
//                    if (128>dto.getAvatarBase64().length()){
                    String imgName = UUID.randomUUID()+".png";
                    // 生成的二维码的路径
                    String imgPath = "barcode/"+dto.getProjectName()+"/"+dto.getId()+"/";
                    if (!FileUtil.isDirectory(imgPath)){
                        FileUtil.mkdir(imgPath);
                    }
                    try {
                        if (StrUtil.isNotBlank(dto.getBarCode())){
                            BarCodeUtil.encode(dto.getBarCode(),imgPath, imgName, dto.getBarCode());
                        }
                        cleanablePath.add(imgPath);
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
//                        log.info("outing:" + dto.getDepartment());
                    try
//                            ()
                    {
                        if (FileUtil.exist(imgPath+imgName)){
                            byte[] inputStream = FileUtil.readBytes(imgPath+imgName);
                            dto.setBarcodeImg(inputStream);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
//                    }
//                    else {
//                        ImageData imageData = new ImageData();
//                        //可能会爆内存?
//                        imageData.setImage(DatatypeConverter.parseBase64Binary(dto.getAvatarBase64()));
//                        dto.setUserAvatar(imageData);
//                    }
                }
        );
        writer = EasyExcel.write(out, clzz).build();
        Map<String, List<PurchaseGoodsExportVO>> sortSheet = dtoList.parallelStream().collect(
                Collectors.groupingBy(PurchaseGoodsExportVO::getProjectName)
        );
        for (String pjn : sortSheet.keySet()){
            WriteSheet sheet = EasyExcel.writerSheet(pjn)
//                .registerWriteHandler(longWidth)
                    .automaticMergeHead(true)
                    .build();
            try {
                writer.write(sortSheet.get(pjn), sheet);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        out.flush();
        try {
            writer.finish();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {

        }
        out.close();
        if (CollectionUtil.isNotEmpty(cleanablePath)){
            for (String path:
                    cleanablePath) {
                FileUtil.del(path);
            }
        }
        return response;
    }
    /**
     * @param dtoList 继承ExcelVo的实际导出类List
     * @return HttpResponse流
     * @throws Exception
     */
    public static HttpServletResponse exportImageExcelDuo(List<PurchaseGoodsDuoExportVO> dtoList,
                                                       String sheetName,
                                                       String fileName,
                                                       HttpServletResponse response) throws Exception {
        //开始转换
        ExcelWriter writer;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name()));
        OutputStream out = response.getOutputStream();
        Class clzz = dtoList.get(0).getClass();
        //设置列长度所用类
        AutoColumnStyleStrategy longWidth = new AutoColumnStyleStrategy();
        List<String> cleanablePath = new ArrayList<>();
        dtoList.stream().forEach(
                dto -> {
                    //filebase_id
//                    if (128>dto.getAvatarBase64().length()){
                    String imgName = UUID.randomUUID()+".png";
                    String imgName2 = UUID.randomUUID()+".png";
                    // 生成的二维码的路径
                    String imgPath = "barcode/"+dto.getProjectName()+"/"+dto.getId()+"/";
                    if (!FileUtil.isDirectory(imgPath)){
                        FileUtil.mkdir(imgPath);
                    }
                    try {
                        if (StrUtil.isNotBlank(dto.getBarCode())){
                            BarCodeUtil.encode(dto.getBarCode(),imgPath, imgName, dto.getBarCode());
                        }
                        if (StrUtil.isNotBlank(dto.getBarCode2())){
                            BarCodeUtil.encode(dto.getBarCode2(),imgPath, imgName2, dto.getBarCode2());
                        }
                        cleanablePath.add(imgPath);
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
//                        log.info("outing:" + dto.getDepartment());
                    try
//                            ()
                    {
                        if (FileUtil.exist(imgPath+imgName)){
                            byte[] inputStream = FileUtil.readBytes(imgPath+imgName);
                            dto.setBarcodeImg(inputStream);
                        }
                        if (FileUtil.exist(imgPath+imgName2)){
                            byte[] inputStream = FileUtil.readBytes(imgPath+imgName2);
                            dto.setBarcodeImg2(inputStream);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
//                    }
//                    else {
//                        ImageData imageData = new ImageData();
//                        //可能会爆内存?
//                        imageData.setImage(DatatypeConverter.parseBase64Binary(dto.getAvatarBase64()));
//                        dto.setUserAvatar(imageData);
//                    }
                }
        );
        writer = EasyExcel.write(out, clzz).build();
        Map<String, List<PurchaseGoodsDuoExportVO>> sortSheet = dtoList.parallelStream().collect(
                Collectors.groupingBy(PurchaseGoodsDuoExportVO::getProjectName)
        );

        for (String pjn : sortSheet.keySet()){
            // 采用动态 表头设计
            List<List<String>> heads = new ArrayList<>(3);
            // 1、常用信息
            List<String> names = Arrays.stream(PurchaseGoodsDuoExportVO.class.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(ExcelProperty.class))
                    .map(field -> {
                        ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                        return annotation.value()[annotation.value().length - 1];
                    })
                    .collect(Collectors.toList());
            Boolean head = false;
            for (String name : names) {
                if (!head){
                    heads.add(Arrays.asList(pjn,name));
                    head =true;
                }else {
                    heads.add(Arrays.asList(pjn,name));
                }
            }
            //内容样式策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //垂直居中,水平居中
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

            //设置 自动换行
            contentWriteCellStyle.setWrapped(true);
            // 字体策略
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 20);
            contentWriteFont.setBold(Boolean.TRUE);
            contentWriteCellStyle.setWriteFont(contentWriteFont);
            //头策略使用默认 设置字体大小
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short) 20);
            headWriteCellStyle.setWriteFont(headWriteFont);


            WriteSheet sheet = EasyExcel.writerSheet(pjn)
//                .registerWriteHandler(longWidth)
//                    .head(heads)
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                    .automaticMergeHead(true)
                    .build();
            try {
                writer.write(sortSheet.get(pjn), sheet);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        out.flush();
        try {
            writer.finish();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {

        }
        out.close();
        if (CollectionUtil.isNotEmpty(cleanablePath)){
            for (String path:
                    cleanablePath) {
                FileUtil.del(path);
            }
        }
        return response;
    }




    /**
     * @param dtoList 继承ExcelVo的实际导出类List
     * @return HttpResponse流
     * @throws Exception
     */
    public static HttpServletResponse exportComplexExcel(List<List<Object>> dtoList,
                                                         List<List<String>> heads,
                                                         String sheetName,
                                                         String fileName,
                                                         HttpServletResponse response) throws Exception {
//        if (CollectionUtils.isEmpty(dtoList)) {
//            return response;
//        }
        //开始转换
        ExcelWriter writer;
//        if (CollectionUtil.isEmpty(dtoList)){
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return response;
//        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name()));
        OutputStream out = response.getOutputStream();
        //设置列长度所用类
        List<List<Object>> dataList = dtoList;

        writer = EasyExcel.write(out).build();
        // 采用动态 表头设计
        // 1、常用信息

        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中

        //背景设置白色（这里一定要设置表格内容的背景色WPS下载下来的文件没有问题，但是office下载下来的文件表格内容会变成黑色）
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置自动换行
        contentWriteCellStyle.setWrapped(true);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 头默认了 FillPatternType所以可以不指定。
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteFont.setBold(Boolean.TRUE);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //头策略使用默认 设置字体大小
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 20);
        headWriteCellStyle.setWriteFont(headWriteFont);


        WriteSheet sheet = EasyExcel.writerSheet(sheetName)
//                .registerWriteHandler(longWidth)
//                    .head(heads)
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15)) // 简单的列宽策略，列宽15
                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)25,(short)15)) // 简单的行高策略：头行高30，内容行高15
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .automaticMergeHead(true)
                .build();
        try {
            // 创建一个表格，用于 Sheet 中使用
            WriteTable   table = new WriteTable( );
            table.setTableNo(1);
            table.setHead(heads);
            writer.write(dataList, sheet, table);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        out.flush();
        try {
            writer.finish();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {

        }
        out.close();
        return response;
    }
}
