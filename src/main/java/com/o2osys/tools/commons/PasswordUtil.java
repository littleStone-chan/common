package com.o2osys.tools.commons;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <pre>
 * 密码工具类
 * 采用BCrypt加密模式，同一个字符串，每次encode的结果都不一样，需要使用checkPassword验证正确性.
 * </pre>
 * 
 * @author jzsong@uworks.cc
 */
public class PasswordUtil {

  private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  /**
   * 加密
   * 
   * @param password
   * @return
   */
  public static String encode(String password) {
    return encoder.encode(password);
  }

  /**
   * 验证密码正确性
   * 
   * @param password：原始密码
   * @param encodedPassword：加密过后的密码
   * @return
   */
  public static boolean checkPassword(String password, String encodedPassword) {
    return encoder.matches(password, encodedPassword);
  }

  /**
   * <pre>
   * 验证格式，是否符合要求
   * 1、必须为数字，字母（大小写都行），大小为6～18位
   * 2、不包含中文
   * </pre>
   * 
   * @param password
   * @return true or false
   */
  public static boolean validatePattern(String password) {
    if (password == null) {
      return false;
    }
    // 必须为数字，字母（大小写都行），大小为6～18位
    String str = "((?=.*\\d)|(?=.*[a-zA-Z]))^.{6,18}$";
    boolean result = password.matches(str);
    if (!result) {
      return result;
    }
    if (containChinese(password)) {
      result = false;
    }
    return result;
  }

  /**
   * 根据Unicode编码判断中文汉字和符号(true为字符串中有中文字符false为无)
   * 
   * @param password
   * @return true or false
   */
  private static boolean containChinese(String password) {
    char[] passwordchar = password.toCharArray();
    for (int i = 0; i < passwordchar.length; i++) {
      char c = passwordchar[i];
      Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
      if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
          || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
          || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
          || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
          || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
          || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
          || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
        return true;
      }
    }
    return false;
  }

}
