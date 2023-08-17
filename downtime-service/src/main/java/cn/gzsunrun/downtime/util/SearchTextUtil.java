package cn.gzsunrun.downtime.util;

import cn.gzsunrun.downtime.entity.search.SearchBase;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author john saunders
 * @description:
 * @date: 2023/4/24
 * @time: 14:45
 */
@Data
public class SearchTextUtil {



    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SearchTextUtil.class);


    /**
     * 创建通用搜索字段, 默认按空格分割
     * @param searchBase 包含searchText字段的代码
     * @param needField 字段map,在此的字段 布尔为true的进行拼音化和首字母化/false的直接放入搜索冗余
     * @param ignoreFieldName 若needField包含不需要的字段,则过滤,不放入该字段.
     * @param <T>
     */
    public static <T extends SearchBase> void createSearchText(T searchBase, Map<Boolean, List<Field>> needField, String... ignoreFieldName) {
        createSearchText(StrUtil.SPACE, searchBase, needField, ignoreFieldName);
    }


        /**
         * 创建通用搜索字段
         * @param separator 拼音/全拼/原文之间的分割符号
         * @param searchBase 包含searchText字段的代码
         * @param needField 字段map,在此的字段 布尔为true的进行拼音化和首字母化/false的直接放入搜索冗余
         * @param ignoreFieldName 若needField包含不需要的字段,则过滤,不放入该字段.
         * @param <T> 值实体
         */
    public static <T extends SearchBase> void createSearchText(String separator, T searchBase, Map<Boolean, List<Field>> needField, String... ignoreFieldName) {
        StringBuilder text = new StringBuilder();
        boolean hasIgnore = ignoreFieldName.length > 0;
        List<String> ig = new ArrayList<>();
        if (hasIgnore){
            ig = Arrays.asList(ignoreFieldName);
        }

        if (needField.containsKey(Boolean.FALSE)){
            List<Field> directFields = needField.get(Boolean.FALSE);


            if (CollectionUtil.isNotEmpty(directFields)){
                for (Field f:
                        directFields) {
                    if (ig.contains(f.getName())){
                        continue;
                    }else {
                        try {
                            text.append(ReflectUtil.getFieldValue(searchBase, f).toString());
                        }catch (Exception e){
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }
        if(needField.containsKey(Boolean.TRUE)){
            List<Field> chineseFields = needField.get(Boolean.TRUE);
            if (CollectionUtil.isNotEmpty(chineseFields)){
                for (Field f:
                        chineseFields) {
                    if (ig.contains(f.getName())){
                        continue;
                    }else {
                        try {
                            String hanzi =  ReflectUtil.getFieldValue(searchBase, f).toString();
                            if (StrUtil.isNotEmpty(hanzi)) {
                                text.append(hanzi);
                                text.append(separator);
                                Boolean startWithChinese = false;
                                StringBuilder buf = new StringBuilder();
                                for (char ch :
                                        hanzi.toCharArray()) {
                                    if (PinyinUtil.isChinese(ch)) {
                                        startWithChinese = true;
                                        buf.append(PinyinUtil.getFirstLetter(ch));
                                    } else {
                                        buf.append(ch);
                                    }
                                }
                                String firstLetterGroup = buf.toString();
                                text.append(firstLetterGroup);
                                if (startWithChinese) {
                                    text.append(separator);
                                    text.append(PinyinUtil.getPinyin(hanzi, StrUtil.EMPTY));
                                }
                            }
                        }catch (Exception e){
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }

        searchBase.setSearchText(text.toString());
    }


    public static List<String> generateTransferList(){
        return generateTransferList(Boolean.TRUE);
    }
    public static List<String> generateTransferList(Boolean isE){
        return new ArrayList<String>(){{
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            if (isE){
                add("9");
            }
        }};
    }


    public static String emphasisWord(String word){
        return "【"+word+"】";
    }


    public static List<Long> getbetweenDays(Long startDate, Long endDate) {
        List<Long> timestamps = new ArrayList<>();
        Instant startInstant = Instant.ofEpochMilli(startDate);
        Instant endInstant = Instant.ofEpochMilli(endDate);
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());
        LocalDate start = startDateTime.toLocalDate();
        LocalDate end = endDateTime.toLocalDate();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(1, 0));
            long timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            timestamps.add(timestamp);
        }
        return timestamps;
    }


    private static String todayWeekString(DateTime someday){
        return getTodayWeekString(someday, Boolean.FALSE);
    }

    public static String getTodayWeekString(DateTime someday, Boolean needWeek) {
        String dayOfWeek = "星期几";
        if (needWeek!=null && needWeek){
            dayOfWeek = "第1周星期几";
            int i = someday.dayOfWeekInMonth();
            dayOfWeek = StrUtil.replace(dayOfWeek, "1", String.valueOf(i));
        }

        int week = someday.dayOfWeek();
        switch (week) {
            case 1:
                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "日");
                break;
            case 2:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "一");
                break;
            case 3:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "二");
                break;
            case 4:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "三");
                break;
            case 5:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "四");
                break;
            case 6:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "五");
                break;
            case 7:

                dayOfWeek = StrUtil.replace(dayOfWeek, "几", "六");
                break;
            default:
                break;

        }
        return dayOfWeek;
    }

    public static String timeArea(Long startTime, Long endTime, String pattern){
        DateTime startDa = DateUtil.date(startTime);
        DateTime endDa = DateUtil.date(endTime);
        String s = startDa.toString(pattern);
        String e = endDa.toString(pattern);
        return s+" 至 "+e;
    }

}
