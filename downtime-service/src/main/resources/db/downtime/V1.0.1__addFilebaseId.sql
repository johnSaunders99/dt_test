ALTER TABLE `dt_down_time_record`
ADD COLUMN `file_base_ids` json NOT NULL COMMENT '备注图片列表' AFTER `remark`;
