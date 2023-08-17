package cn.gzsunrun.downtime.config.permission;


import cn.gzsunrun.oars.idaas.service.IDaasAuthorityService;
import cn.hutool.core.thread.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 考勤权限启动自动调用方法
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AttendancePermissionInit {
    private final IDaasAuthorityService iDaasAuthorityService;



    /***
     * 调用角色授权。自动初始化系统角色权限到idaas
     * @throws Exception
     */
    @PostConstruct
    public void initPermission() {
        //异步调用
        ThreadUtil.execAsync(()->{
            //初始化考勤权限
//            AttPermissionConstant.initPermission(iDaasAuthorityService);
//            CanteenPermissionConstant.initPermission(iDaasAuthorityService);
        });
    }
}
