package cn.gzsunrun.downtime.util;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SynchrolockUtil {

    public SynchrolockUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    private RedisUtil redisUtil;

    public final String RETRYTYPE_WAIT = "1";  //加锁方法当对象已加锁时，设置为等待并轮询
    public final String RETRYTYPE_NOWAIT = "0";  //加锁方法当对象已加锁时，设置为直接返回
    private Long requestTimeOutName = 1000 *60*60*12L;  //投资同步锁请求超时时间
    private Long retryIntervalName=200L;   //投资同步锁轮询间隔
    private Integer keyTimeoutName = 1000 *60*60*12;  //缓存中key的失效时间
    private String investProductSn = "";   //产品Sn
    private String uuid;    //对象唯一标识
    private Long startTime = System.currentTimeMillis(); //首次调用时间
    public Long getStartTime() {
        return startTime;
    }
    List<String> keyList = new ArrayList<String>(); //缓存key的保存集合
    public List<String> getKeyList() {
        return keyList;
    }
    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
    }
    @PreDestroy
    public void destroy() {
        this.unlock();
    }

    public boolean islocked(String key,String retryType){
        boolean flag = true;
        log.info("====投资同步锁设置轮询间隔、请求超时时长、缓存key失效时长====");
        //投资同步锁轮询间隔 毫秒
        Long retryInterval = retryIntervalName;
        //投资同步锁请求超时时间 毫秒
        Long requestTimeOut = requestTimeOutName;
        //缓存中key的失效时间 秒
        Integer keyTimeout = keyTimeoutName;
        //调用缓存获取当前产品锁
        log.info("====当前产品key为："+key+"====");
        if(isLockedInRedis(key,keyTimeout)){
            if("1".equals(retryType)){
                //采用轮询方式等待
                while (true) {
                    log.info("====产品已被占用，开始轮询====");
                    try {
                        Thread.sleep(retryInterval);
                    } catch (InterruptedException e) {
                        log.error("线程睡眠异常:"+e.getMessage(), e);
                        return flag;
                    }
                    log.info("====判断请求是否超时====");
                    Long currentTime = System.currentTimeMillis(); //当前调用时间
                    long Interval = currentTime - startTime;
                    if (Interval > requestTimeOut) {
                        log.info("====请求超时====");
                        return flag;
                    }
                    if(!isLockedInRedis(key,keyTimeout)){
                        log.info("====轮询结束，添加同步锁====");
                        flag = false;
                        keyList.add(key);
                        break;
                    }
                }
            }else{
                //不等待，直接返回
                log.info("====产品已被占用，直接返回====");
                return flag;
            }
        }else{
            log.info("====产品未被占用，添加同步锁====");
            flag = false;
            keyList.add(key);
        }
        return flag;
    }

    boolean isLockedInRedis(String key,int keyTimeout){
        log.info("====在缓存中查询key是否存在====");
        boolean isExist = false;
        //与redis交互，查询对象是否上锁
        boolean result = this.redisUtil.setNx(key, uuid,keyTimeout, TimeUnit.SECONDS);
        log.info("====上锁 result = "+result+"====");
//        if(null != result && 1 == Integer.parseInt(result.toString())){
        if(result){
            log.info("====设置缓存失效时长 = "+keyTimeout+"秒====");
            this.redisUtil.expire(key, keyTimeout);
            log.info("====上锁成功====");
            isExist = false;
        }else{
            log.info("====上锁失败====");
            isExist = true;
        }
        return isExist;
    }

    public void unlock(){
        //与redis交互，对产品解锁
        if(keyList.size()>0){
            for(String key : this.keyList){
                Object value = this.redisUtil.get(key);
                if(null != value && !"".equals(value.toString())){
                    if(uuid.equals(value.toString())){
                        log.info("====解锁key:"+key+" value="+value+"====");
                        this.redisUtil.del(key);
                    }else{
                        log.info("====待解锁集合中key:"+key+" value="+value+"与uuid不匹配====");
                    }
                }else{
                    log.info("====待解锁集合中key="+key+"的value为空====");
                }
            }
        }else{
            log.info("====待解锁集合为空====");
        }
    }
}
