package cn.gzsunrun.downtime.entity.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

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
@ColumnWidth(value = 24)
@ContentRowHeight(value = 80)
public class PurchaseGoodsExportVO extends ExcelVo {
    @ExcelIgnore
    private Integer id;

    /**
     * 货物名称
     */
    @ExcelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "条形码")
    @ColumnWidth(value = 35)
    private byte[] barcodeImg;

    @ApiModelProperty(value = "采购计划名称")
    @ExcelProperty(value = "计划")
    private String projectName;

    @ExcelProperty(value = "品牌/规格")
    private String brand;

    @ExcelProperty(value = "编号")
    private String orderNum;



    @ApiModelProperty(value = "条形码编号")
    @ExcelProperty(value = "条形码编号")
    private String barCode;


}
