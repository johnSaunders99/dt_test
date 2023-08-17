package cn.gzsunrun.downtime.service.impl;

import cn.gzsunrun.downtime.constant.UserConstants;
import cn.gzsunrun.downtime.entity.sys.SysConfig;
import cn.gzsunrun.downtime.mapper.SysConfigMapper;
import cn.gzsunrun.downtime.service.ISysConfigService;
import cn.gzsunrun.oars.dbutils.page.OarsPageUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public IPage<SysConfig> selectPageConfigList(SysConfig config) {
        Map<String, Object> params = config.getParams();
        LambdaQueryWrapper<SysConfig> lqw = new LambdaQueryWrapper<SysConfig>()
                .like(StrUtil.isNotBlank(config.getConfigName()), SysConfig::getConfigName, config.getConfigName())
                .eq(StrUtil.isNotBlank(config.getConfigType()), SysConfig::getConfigType, config.getConfigType())
                .like(StrUtil.isNotBlank(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
                .apply(Validator.isNotEmpty(params.get("beginTime")),
                        "date_format(create_time,'%y%m%d') >= date_format({0},'%y%m%d')",
                        params.get("beginTime"))
                .apply(Validator.isNotEmpty(params.get("endTime")),
                        "date_format(create_time,'%y%m%d') <= date_format({0},'%y%m%d')",
                        params.get("endTime"));
        return page(OarsPageUtil.startPage(), lqw);
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfig selectConfigById(Long configId) {
        return baseMapper.selectById(configId);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig retConfig = baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getConfigKey, configKey));
        if (Validator.isNotNull(retConfig)) {
            return retConfig.getConfigValue();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
//	@Override
//	public List<SysConfig> selectConfigList(SysConfig config) {
//		Map<String, Object> params = config.getParams();
//		LambdaQueryWrapper<SysConfig> lqw = new LambdaQueryWrapper<SysConfig>()
//			.like(StrUtil.isNotBlank(config.getConfigName()), SysConfig::getConfigName, config.getConfigName())
//			.eq(StrUtil.isNotBlank(config.getConfigType()), SysConfig::getConfigType, config.getConfigType())
//			.like(StrUtil.isNotBlank(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
//			.apply(Validator.isNotEmpty(params.get("beginTime")),
//				"date_format(create_time,'%y%m%d') >= date_format({0},'%y%m%d')",
//				params.get("beginTime"))
//			.apply(Validator.isNotEmpty(params.get("endTime")),
//				"date_format(create_time,'%y%m%d') <= date_format({0},'%y%m%d')",
//				params.get("endTime"));
//		return baseMapper.selectList(lqw);
//	}

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        return baseMapper.insert(config);
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        return baseMapper.updateById(config);
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    @Override
    public void deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfig config = selectConfigById(configId);
            if (StrUtil.equals(UserConstants.YES, config.getConfigType())) {
                throw new RuntimeException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
        }
        baseMapper.deleteBatchIds(Arrays.asList(configIds));
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = Validator.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, config.getConfigKey()));
        if (Validator.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
