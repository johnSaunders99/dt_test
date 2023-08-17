package cn.gzsunrun.downtime.entity.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 总账单列表导出类
 *
 * @author john saunders
 * @description:
 * @date: 2021/11/29
 * @time: 14:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@HeadRowHeight(value = 50)
@ColumnWidth(value = 12)
@ContentRowHeight(value = 75)
public class PurchaseGoodsDuoExportVO extends ExcelVo {
    @ExcelIgnore
    private Integer id;

    /**
     * 货物名称
     */
    @ExcelProperty(value = "名称")
//    @ColumnWidth(value = 20)
    private String name;

    @ExcelProperty(value = "条形码")
    @ColumnWidth(value = 30)
    private byte[] barcodeImg;

    @ExcelProperty(value = "名称2")
//    @ColumnWidth(value = 35)
    private String name2;

    @ExcelProperty(value = "条形码2")
    @ColumnWidth(value = 30)
    private byte[] barcodeImg2;
    @ApiModelProperty(value = "采购计划名称")
    @ExcelIgnore
    private String projectName;

    @ExcelIgnore
    private String brand;

    @ExcelIgnore
    private String orderNum;



    @ExcelIgnore
    private String barCode;


    @ExcelIgnore
    private String barCode2;

}
