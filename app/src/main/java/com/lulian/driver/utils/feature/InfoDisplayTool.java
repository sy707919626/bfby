package com.lulian.driver.utils.feature;

import android.text.TextUtils;

import com.lulian.driver.utils.TimeUtil;

import java.util.Date;

/**
 * 用来处理界面上一些信息转换,拼接显示的工具类
 * @author hxb
 */
public class InfoDisplayTool {

    private static final String SPLIT_CHAR_1 ="/";

    /**
     * 拼接 货物名称,重量,体积的显示文字
     *
     */
    public static String assemble_cargoName_weight_volume(String cargoName, String weight, String volume){
        StringBuilder sb = new StringBuilder();

        if(!TextUtils.isEmpty(cargoName)){
            sb.append(cargoName.trim());
        }

        if(isValidNumberInfo(weight)){
            sb.append(SPLIT_CHAR_1).append(weight.trim()).append("吨");
        }

        if(isValidNumberInfo(volume)){
            sb.append(SPLIT_CHAR_1).append(volume.trim()).append("方");
        }

        return trimUnnecessarySplitChar(sb.toString(),SPLIT_CHAR_1);
    }


    /**
     * 拼接 车型,车长 显示文字
     */
    public static String assemble_truckType_truckLength(String truckTypeText, String truckLength){
        StringBuilder sb = new StringBuilder();

        if(!TextUtils.isEmpty(truckTypeText)){
            sb.append(truckTypeText.trim());
        }

        if(isValidNumberInfo(truckLength)){
            sb.append(SPLIT_CHAR_1).append(truckLength.trim()).append("米");
        }

        return trimUnnecessarySplitChar(sb.toString(),SPLIT_CHAR_1);
    }


    public static String assemble_truckType_truckLength_weight(String truckTypeText, String truckLength, String weight){
        String info = assemble_truckType_truckLength(truckTypeText, truckLength);
        StringBuilder sb = new StringBuilder(info);

        if(isValidNumberInfo(weight)){
            sb.append(SPLIT_CHAR_1).append(weight.trim()).append("吨");
        }

        return trimUnnecessarySplitChar(sb.toString(),SPLIT_CHAR_1);
    }


    public static String assemble_truckType_truckLength_weight_volume(String truckTypeText, String truckLength, String weight,String volume){
        String info = assemble_truckType_truckLength_weight(truckTypeText, truckLength, weight);
        StringBuilder sb = new StringBuilder(info);

        if(isValidNumberInfo(volume)){
            sb.append(SPLIT_CHAR_1).append(volume.trim()).append("方");
        }

        return trimUnnecessarySplitChar(sb.toString(),SPLIT_CHAR_1);
    }


    /**
     * 截取掉多余的分隔符
     * @return
     */
    private static String trimUnnecessarySplitChar(String raw, String splitChar){
        if(!TextUtils.isEmpty(raw)){
            if(raw.startsWith(splitChar)){
                return raw.substring(1, raw.length());
            }else {
                return raw;
            }
        }
        return raw;
    }


    /**
     * 检测是否是有效的数字信息
     * 车长,重量,体积 等数据都应该使用此方法进行检测
     * 如果检测结果为false,则不应该进行拼接
     * @return
     */
    private static boolean isValidNumberInfo(String numberStr){
        if(!TextUtils.isEmpty(numberStr)){
            try{
                Double number = Double.valueOf(numberStr);
                //转换成数字,只有当数字大于0时,才能算有有效的
                return number > 0;
            }catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }


    /**
     * 将时间("yyyy年MM月dd日 HH:mm)转换成对应的时间范围文字
     * 规则:
     * cTime=当前时间;
     * cTime <= 1h:刚刚;
     * 1h < cTime <= 3h:一小时前;
     * 3h < cTime <= 24h:三小时前;
     * 24h < cTime <= 72h: 一天前;
     * 72h < cTime:三天前;
     */
    public static String convertToTimeScopeText(String timeStr){
        String str = "";

        long timeMilli = TimeUtil.timeStrToSecond(timeStr);
        if(timeMilli!=-1){
            long currMilli = new Date().getTime();
            long timeDistance = currMilli - timeMilli;

            long oneHourMilli = 60 * 60 * 1000;//一小时的毫秒数
            if (timeDistance <= oneHourMilli) {
                str = "刚刚";
            } else if (timeDistance <= oneHourMilli * 3) {
                str = "一小时前";
            } else if (timeDistance <= oneHourMilli * 24) {
                str = "三小时前";
            }else if (timeDistance <= oneHourMilli * 72) {
                str = "一天前";
            }else{
                str = "三天前";
            }
        }

        return str;
    }


    /**
     * 将全名转换成指定的称呼
     * 例如 参数name是"张三",appellation队长,那么转换后的结果为"张队长"
     */
    public static String convertNameToAppellation(String name,String appellation){
        StringBuilder sb = new StringBuilder();
        if(!TextUtils.isEmpty(name)){
            sb.append(name.substring(0, 1)).append(appellation);
        }
        return sb.toString();
    }


}
