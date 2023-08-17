package cn.gzsunrun.downtime.entity.search;

import cn.gzsunrun.oars.dbutils.entity.ResultPage;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author john saunders
 * @description:
 * @date: 2023/5/16
 * @time: 9:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(
        description = "结果拓展页"
)
public class ExtendResultPage<T> extends ResultPage<T> {

    private Object extraSlot;

    private List<Object> extraSlot2;

    public ExtendResultPage(ResultPage<T> resultPage, Object extraSlot) {
        super(resultPage.getTotal(), resultPage.getPageSize(), resultPage.getPageNum(), resultPage.getPages(), resultPage.getRows());
        this.extraSlot = extraSlot;
    }
}
