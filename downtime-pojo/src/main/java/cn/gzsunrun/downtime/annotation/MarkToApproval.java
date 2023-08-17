package cn.gzsunrun.downtime.annotation;

/**
 * @author john saunders
 * @description:
 * @date: 2022/3/2
 * @time: 13:55
 */

import java.lang.annotation.*;

/**
 * 用于标注是否需要在更新时进行审批流程的注解
 *
 * @author john saunders
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MarkToApproval {
}
