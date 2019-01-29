package com.lulian.driver.utils.feature;

import android.text.TextUtils;

import com.lulian.driver.utils.IDCard;

public class CheckUtil {


    /**
     * 检查密码是否符合规范
     * @param pwd
     * @return 如果不符合规范,返回对应的提示信息,如果符合规范则返回null
     */
    public static String checkPwdIsCorrect(String pwd){

        if(TextUtils.isEmpty(pwd)){
            return "密码不能为空";
        }

        if(pwd.length() < 8 || pwd.length() > 16){
            return "请输入8到16位的密码!";
        }

        if(IDCard.isNumeric(pwd) || IDCard.isLetter(pwd)){
            return "密码不能为纯数字或纯字母!";
        }

        return null;
    }

}
