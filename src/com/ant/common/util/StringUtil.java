/*
 * Copyright (c) 2007 Samsung Networks, Inc. All Rights Reserved.
 * 
 * @(#) StringUtil.java
 * @author: 
 * @version: 1.0
 * @since: 1.0
 * <p>Date: 2007. 2. 23
 * <p>Description: 
 * <p>Important: 
 */
package com.ant.common.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 *  Class Name : StringUtil.java
 *  Description : 
 */

public class StringUtil {

    
    /**
     * @param s
     * @return
     */
    public static String alphaNumOnly(String s) {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-' || c == ' ')
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    /**
     * @param s
     * @return
     */
    public static boolean isAlphaNumOnly(String s) {
        int i = s.length();
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') && c != '_' && c != '-' && c != ' ')
                return false;
        }

        return true;
    }
    
    /**
     * @param ch
     * @return
     */
    public static boolean isAlpha(char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
            return true;

        return false;
    }

    /**
     * @param ch
     * @return
     */
    public static boolean isNumeric(char ch) {
        if (ch >= '0' && ch <= '9')
            return true;

        return false;
    }

    /**
     * @param s
     * @return
     */
    public static String normalizeWhitespace(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        boolean flag = false;
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            switch (c) {
            case 9: // '\t'
            case 10: // '\n'
            case 13: // '\r'
            case 32: // ' '
                if (!flag) {
                    stringbuffer.append(' ');
                    flag = true;
                }
                break;

            default:
                stringbuffer.append(c);
                flag = false;
                break;
            }
        }

        return stringbuffer.toString();
    }

    /**
     * @param s
     * @param c
     * @return
     */
    public static int numOccurrences(String s, char c) {
        int i = 0;
        int j = 0;
        int l;
        for (int k = s.length(); j < k; j = l + 1) {
            l = s.indexOf(c, j);
            if (l < 0)
                break;
            i++;
        }

        return i;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    public static String removeCharacters(String s, String s1) {

        if (s == null || s.length() < 1)
            return "";

        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if (s1.indexOf(c) == -1)
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    /**
     * @param s
     * @return
     */
    public static String removeWhiteSpace(String s) {

        if (s == null || s.length() < 1)
            return "";

        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if (!Character.isWhitespace(c))
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    /**
     * @param target
     * @param arguments
     * @param replacements
     * @return
     */
    public static String replace(String target, String[] arguments, String[] replacements) {
        if (target == null || arguments == null || replacements == null)
            return target;

        for (int index = 0; index < arguments.length; index++) {
            target = replace(target, arguments[index], replacements[index]);
        }

        return target;
    }

    /**
     * @param target
     * @param argument
     * @param replacement
     * @return
     */
    public static String replace(String target, String argument, String replacement) {
        if (target == null || argument == null || replacement == null)
            return target;

        int i = target.indexOf(argument);

        if (i == -1)
            return target;

        StringBuffer targetSB = new StringBuffer(target);
        while (i != -1) {
            targetSB.delete(i, i + argument.length());
            targetSB.insert(i, replacement);
            // check for any more
            i = targetSB.toString().indexOf(argument, i + replacement.length());
        }

        return targetSB.toString();
    }

    /**
     * @param s
     * @param c
     * @return
     */
    public static String[] splitStringAtCharacter(String s, char c) {
        String as[] = new String[numOccurrences(s, c) + 1];
        splitStringAtCharacter(s, c, as, 0);
        return as;
    }

    /**
     * @param s
     * @param c
     * @param as
     * @param i
     * @return
     */
    protected static int splitStringAtCharacter(String s, char c, String as[], int i) {
        int j = 0;
        int k = i;
        int l = 0;
        int j1;
        for (int i1 = s.length(); l <= i1 && k < as.length; l = j1 + 1) {
            j1 = s.indexOf(c, l);
            if (j1 < 0)
                j1 = i1;
            as[k] = s.substring(l, j1);
            j++;
            k++;
        }

        return j;
    }

    /**
     * Convert a String to a boolean
     * 
     * @param data
     *            the thing to convert
     * @return the converted data
     */
    public static boolean string2Boolean(String data) {
        if (data.equalsIgnoreCase("true"))
            return true;
        if (data.equalsIgnoreCase("yes"))
            return true;
        if (data.equalsIgnoreCase("ok"))
            return true;
        if (data.equalsIgnoreCase("okay"))
            return true;
        if (data.equalsIgnoreCase("on"))
            return true;
        if (data.equalsIgnoreCase("1"))
            return true;

        return false;
    }

    /**
     * Convert a String to an int
     * 
     * @param data
     *            the thing to convert
     * @return the converted data
     */
    public static int string2Int(String data) {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * Convert a String to a Hashtable
     * <p>
     * 
     * @param data
     *            the thing to convert
     * @return the converted data
     */
    public static Map string2Hashtable(String data) {

        Map<String, String> commands = new HashMap<String, String>();

        data = normalizeWhitespace(data);
        String[] data_arr = splitStringAtCharacter(data, ' ');

        for (int i = 0; i < data_arr.length; i++) {
            int equ_pos = data_arr[i].indexOf('=');
            String key = data_arr[i].substring(0, equ_pos);
            String value = data_arr[i].substring(equ_pos + 1);

            commands.put(key, value);
        }

        return commands;
    }

    /**
     * Convert a Hashtable to a Sting
     * <p>
     * 
     * @param data
     *            the thing to convert
     * @return the converted data
     */
    public static String hashtable2String(Map commands) {
        Iterator it = commands.keySet().iterator();
        StringBuffer retcode = new StringBuffer();

        while (it.hasNext()) {
            //String key = "";
            //String value = "";

            try {
                Map.Entry me = (Map.Entry) it.next();

                //key = (String) it.next();
                //value = (String) commands.get(key);

                retcode.append(me.getKey());
                retcode.append("=");
                retcode.append(me.getValue());
                retcode.append(" ");
            } catch (ClassCastException ex) {
                //exception pass
            }
        }

        return retcode.toString().trim();
    }

    /**
     * @param s
     * @return
     */
    public static String toLowerCase(String s) {
        int i;
        int j;
        char c;
        label0: {
            i = s.length();
            for (j = 0; j < i; j++) {
                char c1 = s.charAt(j);
                c = Character.toLowerCase(c1);
                if (c1 != c)
                    break label0;
            }

            return s;
        }
        char ac[] = new char[i];
        int k;
        for (k = 0; k < j; k++)
            ac[k] = s.charAt(k);

        ac[k++] = c;
        for (; k < i; k++)
            ac[k] = Character.toLowerCase(s.charAt(k));

        String s1 = new String(ac, 0, i);
        return s1;
    }

    /**
     * @param s
     * @return
     */
    public static String toUpperCase(String s) {
        int i;
        int j;
        char c;
        label0: {
            i = s.length();
            for (j = 0; j < i; j++) {
                char c1 = s.charAt(j);
                c = Character.toUpperCase(c1);
                if (c1 != c)
                    break label0;
            }

            return s;
        }
        char ac[] = new char[i];
        int k;
        for (k = 0; k < j; k++)
            ac[k] = s.charAt(k);

        ac[k++] = c;
        for (; k < i; k++)
            ac[k] = Character.toUpperCase(s.charAt(k));

        return new String(ac, 0, i);
    }

   /**
	 * @param s
	 * @param s1
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static Vector tokenizer(String s, String s1) {
        if (s == null)
            return null;
        Vector vector = null;
        for (StringTokenizer stringtokenizer = new StringTokenizer(s, s1); stringtokenizer.hasMoreTokens(); vector.addElement(stringtokenizer.nextToken().trim()))
            if (vector == null)
                vector = new Vector();

        return vector;
    }

    /**
     * @param s
     * @return
     */
    public static String escapeHtmlString(String s) {
        String s1 = s;
        if (s1 == null)
            return null;
        if (s1.indexOf(38, 0) != -1)
            s1 = replace(s1, "&", "&amp;");
        if (s1.indexOf(60, 0) != -1)
            s1 = replace(s1, "<", "&lt;");
        if (s1.indexOf(62, 0) != -1)
            s1 = replace(s1, ">", "&gt;");
        if (s1.indexOf(34, 0) != -1)
            s1 = replace(s1, "\"", "&quot;");
        /*
        if (s1.indexOf(10, 0) != -1)
            s1 = replace(s1, "\n", "<br />");
        */
        if (s1.indexOf("\"", 0) != -1)
            s1 = replace(s1, "\"", "&quot;");
        if (s1.indexOf("\"", 0) != -1)
            s1 = replace(s1, "\'", "&prime;");

        return s1;
    }

    /**
     * @param s
     * @return
     */
    public static String toHtmlString(String s) {
        String s1 = s;
        if (s1 == null)
            return null;
        if (s1.indexOf(38, 0) != -1)
            s1 = replace(s1, "&", "&amp;");
        if (s1.indexOf(60, 0) != -1)
            s1 = replace(s1, "<", "&lt;");
        if (s1.indexOf(62, 0) != -1)
            s1 = replace(s1, ">", "&gt;");
        if (s1.indexOf(34, 0) != -1)
            s1 = replace(s1, "\"", "&quot;");
        if (s1.indexOf(10, 0) != -1)
            s1 = replace(s1, "\n", "<br />");
        if (s1.indexOf("\"", 0) != -1)
            s1 = replace(s1, "\"", "&quot;");
        if (s1.indexOf("\"", 0) != -1)
            s1 = replace(s1, "\'", "&prime;");

        return s1;
    }

   /**
	 * @param s
	 * @return
	 */
	public static String reEscapeHtmlString(String s) {
        String s1 = s;
        if (s1 == null)
            return null;
        String[] arguments = { "&amp;", "&lt;", "&gt;", "&quot;", "<br />" };
        String[] replacements = { "&", "<", ">", "\"", "\n" };
        return replace(s1, arguments, replacements);
    }
	
    /**
     * @param c
     * @param length
     * @return
     */
    public static String fill(char c, int length) {
        if (length <= 0)
            return "";

        char[] ca = new char[length];
        for (int index = 0; index < length; index++) {
            ca[index] = c;
        }

        return new String(ca);
    }

    /**
     * @param s
     * @param c
     * @param length
     * @return
     */
    public static String padRight(String s, char c, int length) {
        return s + fill(c, length - s.length());
    }

    /**
     * @param s
     * @param c
     * @param length
     * @return
     */
    public static String padLeft(String s, char c, int length) {
        return fill(c, length - s.length()) + s;
    }

    /**
     * @param src
     * @param str_length
     * @param att_str
     * @return
     */
    public static String cutString(String src, int str_length, String att_str) {
        int ret_str_length = 0;

        String ret_str = "";

        if (src == null) {
            return ret_str;
        }

        String tempMulLanChar = "..";
        int lanCharLength = tempMulLanChar.length();

        int multiLanCharIndex = 0;
        int nonMultiLanCharIndex = 0;

        for (int i = 0; i < src.length(); i++) {
            ret_str += src.charAt(i);

            if (src.charAt(i) > '~') {
                ret_str_length = ret_str_length + 2 / lanCharLength;
                multiLanCharIndex++;
            } else {
                ret_str_length = ret_str_length + 1;
                nonMultiLanCharIndex++;
            }
            if (ret_str_length >= str_length && (multiLanCharIndex % lanCharLength) == 0) {
                if (src.length() != multiLanCharIndex + nonMultiLanCharIndex)
                    ret_str += NVL(att_str);
                break;
            }
        }

        return ret_str;
    }

   /**
	 * @param args
	 * @return
	 */
	public static String toString(Object[] args) {
        return toString(args, ",");
    }

    /**
     * @param args
     * @param separator
     * @return
     */
    public static String toString(Object[] args, String separator) {
        if (args == null)
            return null;

        StringBuffer buf = new StringBuffer();

        for (int index = 0; index < args.length; index++) {
            if (index > 0)
                buf.append(separator);

            if (args[index] == null)
                buf.append("null");
            else
                buf.append(args[index].toString());
        }

        return buf.toString();
    }

    /**
     * @param list
     * @param separator
     * @return
     */
    public static String toString(List list, String separator) {
        StringBuffer buf = new StringBuffer();
        for (int index = 0; index < list.size(); index++) {
            if (index > 0)
                buf.append(separator);
            buf.append(list.get(index).toString());
        }
        return buf.toString();
    }

    /**
     * @param str
     * @param src_enc
     * @param dest_enc
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String toConvert(String str, String src_enc, String dest_enc) throws java.io.UnsupportedEncodingException {
        if (str == null)
            return "";

        return new String(str.getBytes(src_enc), dest_enc);
    }

    /**
     * @param str
     * @return
     */
    static public String NVL(String str) {
        if (str == null)
            return "";

        return str;
    }

    /**
     * @param str
     * @return
     */
    static public String NVL(Object str) {
        if (str == null)
            return "";

        return str.toString();
    }

    /**
     * @param str
     * @param replace_str
     * @return
     */
    static public String NVL(String str, String replace_str) {
        if (str == null || str.length() <= 0)
            return replace_str;

        return str;
    }

    /**
     * @param src
     * @return
     */
    static public String getStringDate(String src) {
        StringBuffer strRet = new StringBuffer();
        if (src.length() == 6) {
            strRet.append(src.substring(0, 4) + ".");
            strRet.append(src.substring(4, 6));
        } else if (src.length() == 8) {
            strRet.append(src.substring(0, 4) + ".");
            strRet.append(src.substring(4, 6) + ".");
            strRet.append(src.substring(6, 8));
        } else {
            strRet.append(src);
        }
        return strRet.toString();
    }

    /**
     * @param intNumber
     * @return
     */
    public static String numberFormt(int intNumber) {

        String numberStr = "";
        try {
            DecimalFormat df = new DecimalFormat("###,##0");
            numberStr = df.format(intNumber);
        } catch (Exception e) {
        }
        return numberStr;
    }

    /**
     * @param strNumber
     * @return
     */
    public static String numberFormt(String strNumber) {

        String numberStr = "";
        try {
            int intNumber = Integer.parseInt(strNumber);
            DecimalFormat df = new DecimalFormat("###,##0");
            numberStr = df.format(intNumber);
        } catch (Exception e) {
        }
        return numberStr;
    }

    /**
     * @param value
     * @return
     */
    public static String getFlatName(String value) {
        value = value.toLowerCase(new Locale("en"));
        StringBuffer flatName = new StringBuffer();

        char ch;

        for (int count = 0; count < value.length(); count++) {
            ch = value.charAt(count);
            if ((ch == '_') || ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'z')))
                flatName.append(ch);
            else if (ch == '-')
                flatName.append("_");
        }

        return flatName.toString();
    }

    /**
     * @param value
     * @return
     */
    public static String getFlatFileName(String value) {

        StringBuffer flatName = new StringBuffer();
        char ch;

        for (int count = 0; count < value.length(); count++) {

            ch = value.charAt(count);

            if (!Character.isJavaIdentifierPart(ch)) {

                if (ch == '-') {
                    flatName.append(ch);
                } else if (ch == '.') {
                    flatName.append(ch);
                }

            } else {
            		if (((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_')) {
                    flatName.append(ch);
                }

            }
        }
        return flatName.toString();
    }

    /**
     * 
     * @param number
     * @return String
     */
    public static String getSpecComment(int number) {
        StringBuffer str = new StringBuffer();
        try {
            for (int i = 0; i < number; i++) {
                str.append("*");
            }
        } catch (Exception e) {
        }
        return str.toString();
    }

    /**
     * 
     * 
     * @param str
     * @return String
     */
    public static String getMultiLineTrim(String str) {

        String rnStr = str;

        if (rnStr != null && !"".equals(rnStr)) {
            rnStr = "";
            String[] arrStr = str.split("\r\n");

            for (int iLoop = 0; iLoop < arrStr.length; iLoop++) {
                if (!rnStr.equals("")) {
                    if (!"".equals(arrStr[iLoop].trim())) {
                        rnStr += "\r\n" + arrStr[iLoop].trim();
                    }
                }else{
                    if (!"".equals(arrStr[iLoop].trim())) {
                        rnStr += arrStr[iLoop].trim();
                    }
                }
            }
        }

        return rnStr;
    }    
    
    /**
     * @param s
     * @return
     */
    public static boolean isNumOnly(String s) {
        int i = s.length();
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            if ((c < '0' || c > '9') )
                return false;
        }

        return true;
    }      
    
    /**
     * @param s
     * @return
     */
    public static boolean isNumOnly2(String s) {
        if(s != null && !"".equals(s)){
            int i = s.length();
            for (int j = 0; j < i; j++) {
                char c = s.charAt(j);
                if ((c < '0' || c > '9') )
                    return false;
            }
        }else{
            return false;
        }

        return true;
    } 
    
    public static String nullToStr(String gubn, String s){
        if( s == null )
            return gubn;
        else {
            if(s.length() == 0)
              return gubn;
            else
              return s;
        }
    }
}

