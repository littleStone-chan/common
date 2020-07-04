package com.o2osys.tools.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @Description:
 * @Author:Sine Chen
 * @Date:May 16, 2016 3:52:32 PM
 * @Copyright: All Rights Reserved. Copyright(c) 2016
 */
public class StrUtil extends StringUtils {
    /**
     * 校验手机号码
     *
     * @param telephone
     * @return
     */
    public static boolean checkPhone(String telephone) {
        telephone = telephone.replaceAll("-", "");
        if (telephone == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(telephone);
        boolean b = m.matches();
        if (!b) {
            return false;
        }
        return true;
    }

    /**
     * 提取汉字的首字母(大写)
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        if (str == null) {
            return "";
        }
        String convert = "";
        char word = str.charAt(0);
        // 提取汉字的首字母
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
        if (pinyinArray != null) {
            convert += pinyinArray[0].charAt(0);
        } else {
            convert += word;
        }
        convert = convert.trim();
        return convert.toUpperCase();
    }

    /**
     * unicode转string
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return false->手机号格式不正确，true->格式正确
     */
    public static boolean isMobileNO(String mobiles) {
        if (isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("(13[0-9]|15[^4,\\D]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * <p>
     * 生成随机码
     *
     * <p>
     * 若指定的长度为0，则返回空白字符串
     *
     * @param codeLength 随机码长度
     * @return
     */
    public static String randomCode(int codeLength) {
        if (codeLength <= 0) {
            return "";
        }

        long powResult = 1L;
        for (int i = 0; i < codeLength / 2; i++) {
            powResult *= 10;
        }
        if ((codeLength & 0x1) == 0) {
            powResult *= powResult;
        } else {
            powResult *= powResult * 10;
        }
        long randomResult = RandomUtils.nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
        if (randomResult < 0) {
            randomResult += Long.MAX_VALUE;
        }
        StringBuilder code = new StringBuilder(Long.toString(randomResult));
        if (code.length() > codeLength) {
            code.setLength(codeLength);
        } else {
            int[] source = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            int sourceHash = source.hashCode();
            int index = code.length();
            while ((index < codeLength)) {
                code.append(sourceHash % 10);
                sourceHash /= 10;
                index++;
            }
        }
        return String.valueOf(code);
    }

    /**
     * 获取uuid字符串，无横杠
     */
    public static String uuidNotLine() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取uuid字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * <pre>
     * 获取换行符
     * </pre>
     *
     * @return
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 校验vincode码
     *
     * @param str
     * @return
     */
    public static boolean isStringFormatCorrect(String str) {
        String strPattern = "[a-zA-Z0-9]+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * <p>处理从Map中取出的Null值,默认返回空字符串</p>
     * <pre>
     * Map: {name=sinba,age=11}
     * StrUtil.fixNull( map.get("noSuchKey") )  =  ""
     * StrUtil.fixNull( map.get("name") )       =  "sinba"
     * StrUtil.fixNull( map.get("age") )        =  "11"
     * @param o
     * @return
     * </pre>
     */
    public static String fixNull(Object o) {
        return o == null ? "" : o.toString().trim();
    }

    /**
     * 处理空值
     *
     * @param o
     * @param defaulStr
     * @return
     */
    public static String fixNull(Object o, String defaulStr) {
        return o == null ? defaulStr : o.toString().trim();
    }

    /**
     * 处理BigDecimal Null值
     *
     * @param value
     * @return
     */
    public static BigDecimal fixBigDecimal(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }

    /**
     * 去掉字符串前面的空格
     *
     * @param original
     * @return
     */
    public static String beforeTrim(String original) {
        if (original == null || original.trim().length() == 0) {
            return original;
        }
        int len = original.length();
        int st = 0;
        int off = 0; /* avoid getfield opcode */
        char[] originalValue = original.toCharArray();
        char[] val = Arrays.copyOfRange(originalValue, off, off + len);
        /* avoid getfield opcode */

        while ((st < len) && (val[off + st] <= ' ')) {
            st++;
        }
        return ((st > 0) || (st < len)) ? original.substring(st, len) : "";
    }

    /**
     * 去掉字符串后面的空格
     *
     * @param original
     * @return
     */
    public static String afterTrim(String original) {
        if (original == null || original.trim().length() == 0) {
            return original;
        }
        int len = original.length();
        int st = 0;
        int off = 0; /* avoid getfield opcode */
        char[] originalValue = original.toCharArray();
        char[] val = Arrays.copyOfRange(originalValue, off, off + len); /* avoid getfield opcode */

        while ((st < len) && (val[off + len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (st < len)) ? original.substring(st, len) : "";
    }

    /**
     * 字符串转list
     *
     * @param str
     * @param splitBy
     * @return
     */
    public static List<String> splitToList(String str, String splitBy) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] arrays = str.split(splitBy);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arrays.length; i++) {
            list.add(arrays[i].trim());
        }
        return list;
    }

    /**
     * @param str
     * @param splitBy
     * @return
     */
    public static List<Long> splitToLongList(String str, String splitBy) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] arrays = str.split(splitBy);
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < arrays.length; i++) {
            list.add(Long.parseLong(arrays[i].trim()));
        }
        return list;
    }

    /**
     * 将数据库表字段转化为实体类属性
     * e.g. user_info --> userInfo
     *
     * @param columnName
     * @return
     */
    public static String toCamelStyle(String columnName) {
        StringBuffer sb = new StringBuffer();
        boolean match = false;
        for (int i = 0; i < columnName.length(); i++) {
            char ch = columnName.charAt(i);
            if (match && ch >= 97 && ch <= 122) {
                ch -= 32;
            }
            if (ch != '_') {
                match = false;
                sb.append(ch);
            } else {
                match = true;
            }
        }
        return sb.toString();
    }

    /**
     * 将驼峰命名法转化为下划线的形式
     * e.g.  UserInfo --> user_info     loginId --> login_id
     *
     * @param name
     * @return
     */
    public static String addUnderscores(String name) {
        StringBuffer buf = new StringBuffer(name.replace('.', '_'));
        for (int i = 1; i < buf.length() - 1; i++) {
            if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase();
    }


    /**
     * 将搜索条件中的特殊字符屏蔽掉
     */
    public static String checkSelectInfo(String str) {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 将搜索条件中的特殊字符屏蔽掉
     */
    public static boolean checkVinCode(String str) {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    /**
     * <pre>
     * 判断是否为空，为空则返回true
     * 为空的条件：null、""、"null"
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        String str = obj.toString().trim();
        if ("".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * 判断是否不为空，不为空则返回true
     * 为空的条件：null、""、"null"
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 去除前后空格，若obj为null返回""空字串
     *
     * @param obj
     * @return
     */
    public static String trim(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

}
