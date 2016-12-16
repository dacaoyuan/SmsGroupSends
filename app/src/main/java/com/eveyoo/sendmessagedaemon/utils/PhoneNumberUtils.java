package com.eveyoo.sendmessagedaemon.utils;

import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abc on 2016/12/15.
 */

public class PhoneNumberUtils {

    private static final String TAG = "PhoneNumberUtils  ";

    /**
     * 检查手机号输入的格式是否正确和手机号是否合法
     *
     * @param phoneNumber
     * @return
     */
    public static boolean PhoneNumberInspection(String phoneNumber) {

        boolean isLegal = true;
        String strPhone = phoneNumber.trim();
        if (",".equals(strPhone.charAt(strPhone.length() - 1) + "") || "，".equals(strPhone.charAt(strPhone.length() - 1) + "")) {
            return false;
        }

        for (int i = 1; i < 50; i++) {//最大可以给50个手机号发送短信
            int b = strPhone.indexOf(",") + 1;
            XLog.d(TAG + "PhoneNumberInspection " + " b=" + b);

            if (b == 0) {
                if (strPhone.length() == 11) {
                    System.out.println("MyClass.main y=" + strPhone);
                    if (isMobileNO(strPhone)) {
                        XLog.d(TAG + "PhoneNumberInspection  手机号合法");
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    XLog.d(TAG + "PhoneNumberInspection " + "手机号不合法");
                    return false;
                }
            }
            if (b == 12) {
                XLog.d(TAG + "PhoneNumberInspection " + "多个手机号格式是正确的");
                //在这里可以在判断具体手机号的格式是否合法
                String strNumber = strPhone.substring(0, 11);
                XLog.d(TAG + "PhoneNumberInspection strNumber=" + strNumber);
                if (isMobileNO(strNumber)) {
                    XLog.d(TAG + "PhoneNumberInspection  手机号合法");//在这里手机号，合法，不错返回处理，继续判断下一号码
                } else {
                    return false;
                }
            } else {
                XLog.d(TAG + "PhoneNumberInspection " + "格式是错误！");
                return false;
            }
            String c = strPhone.substring(b, strPhone.length());
            XLog.d(TAG + "PhoneNumberInspection " + "c=" + c);
            strPhone = c;


        }
        return true;
    }


    /**
     * 处理手机号字符串，放到ArrayList中
     *
     * @param phoneNumbers
     * @return
     */
    public static ArrayList<String> dealWithString(String phoneNumbers) {

        ArrayList<String> arrayList = new ArrayList<>();
        //18865526389,18865526389,18865526389,18865526389
        for (int i = 1; i < 50; i++) {//最大可以给你50个手机号发送短信
            String strPhone = phoneNumbers.substring(0, 11);
            XLog.d(TAG + "dealWithString  " + "strPhone=" + strPhone);
            arrayList.add(strPhone);
            if (phoneNumbers.length() != 11) {
                String c = phoneNumbers.substring(12, phoneNumbers.length());
                XLog.d(TAG + "dealWithString  " + "c=" + c);
                phoneNumbers = c;
            } else {
                return arrayList;
            }
        }
        return arrayList;
    }


    /**
     * 验证手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public static final boolean isRightPwd(String pwd) {
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


}
