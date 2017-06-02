package com.bierbobo.config.tool.codegen.util;

import java.io.PrintStream;

public class Native2AsciiUtils
{
  private static String PREFIX = "\\u";

  public static String native2Ascii(String str)
  {
    char[] chars = str.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < chars.length; i++) {
      sb.append(char2Ascii(chars[i]));
    }
    return sb.toString();
  }

  private static String char2Ascii(char c)
  {
    if (c > 'ÿ') {
      StringBuilder sb = new StringBuilder();
      sb.append(PREFIX);
      int code = c >> '\b';
      String tmp = Integer.toHexString(code);
      if (tmp.length() == 1) {
        sb.append("0");
      }
      sb.append(tmp);
      code = c & 0xFF;
      tmp = Integer.toHexString(code);
      if (tmp.length() == 1) {
        sb.append("0");
      }
      sb.append(tmp);
      return sb.toString();
    }
    return Character.toString(c);
  }

  public static String ascii2Native(String str)
  {
    StringBuilder sb = new StringBuilder();
    int begin = 0;
    int index = str.indexOf(PREFIX);
    while (index != -1) {
      sb.append(str.substring(begin, index));
      sb.append(ascii2Char(str.substring(index, index + 6)));
      begin = index + 6;
      index = str.indexOf(PREFIX, begin);
    }
    sb.append(str.substring(begin));
    return sb.toString();
  }

  private static char ascii2Char(String str)
  {
    if (str.length() != 6) {
      throw new IllegalArgumentException(
        "Ascii string of a native character must be 6 character.");
    }
    if (!PREFIX.equals(str.substring(0, 2))) {
      throw new IllegalArgumentException(
        "Ascii string of a native character must start with \"\\u\".");
    }
    String tmp = str.substring(2, 4);
    int code = Integer.parseInt(tmp, 16) << 8;
    tmp = str.substring(4, 6);
    code += Integer.parseInt(tmp, 16);
    return (char)code;
  }

  public static void main(String[] args) {
    String s = "dasd.asdsadsa=佛挡杀佛斯蒂芬斯蒂芬但是";
    System.out.println(native2Ascii(s));
  }
}