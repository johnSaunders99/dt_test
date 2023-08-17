CREATE TABLE IF NOT EXISTS `sys_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100)  COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100)  COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500)  COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1)  COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64)  COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500)  COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '参数配置表';

INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '工作流请求地址', 'sys.sunrun.url', 'http://10.21.1.35/sl', 'Y', 'admin', '2023-09-02 11:39:20', 'admin', '2023-09-03 07:16:05', 'SenseLink数据上传地址');
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '工作流请求账户', 'sys.sunrun.username', 'xiaotingji', 'Y', 'admin', '2023-09-02 11:39:20', 'admin', '2023-09-03 07:16:05', 'SenseLink凭证key');
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, '工作流请求密码', 'sys.sunrun.password', 'Abcd@12345', 'Y', 'admin', '2023-09-02 11:39:20', 'admin', '2023-09-03 07:16:05', 'SenseLink凭证secret');

CREATE TABLE IF NOT EXISTS `dt_down_time_record`  (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `count` bit(1) NULL DEFAULT 0 COMMENT '是否统计',
  `remark` varchar(500)  COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '故障统计标记数据表';

