package com.ant.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



import org.apache.log4j.Logger;


/**
 * 
 *  Class Name : CommonUtil.java
 *  Description : 
 *  
 *  Modification Information
 * 
 *   Mod Date       Modifier    Description
 *   -----------    --------    ---------------------------
 *
 *  @author 
 *  @since 2009. 04. 01
 *  @version 1.0
 */

public class CommonUtil {
	
    private static Logger logger = Logger.getRootLogger();
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
    
    /**
     * @param obj
     * @return long 
     */
    public static long toLong(Object obj) {
        long i = 0;
        if (obj != null && !obj.toString().equals("")) {
            i = Long.parseLong(obj.toString());
        }
        return i;
    }
  
    /**
     * @param obj
     * @return int
     */
    public static int toInt(Object obj) {
        int i = 0;
        if (obj != null && !obj.toString().equals("")) {
            i = Integer.parseInt(obj.toString());
        }
        return i;
    }

    /**
     * @param obj
     * @return String
     */
    public static String toString(Object obj) {
        String str = "";
        if (obj != null) {
            str = obj.toString();
        }
        return str;
    }

    /**
     * @param obj
     * @return String
     */
    public static boolean isNull(Object obj) {
        return obj != null ? false : true;
    }

   /**
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
        return obj != null ? true : false;
    }

	/**
	 * @param obj
	 * @return String
	 */
	public static String[] toStringArray(Object obj) {
        String[] strArr = null;
        try {
            if (obj != null)
                strArr = (String[]) obj;
        } catch (Exception e) {
            if (obj != null)
                strArr = new String[] { obj.toString() };
        }
        return strArr;
    }

    /**
     * @param strArr
     * @return String
     */
    public static String toConcatString(String[] strArr) {
        StringBuffer strBuf = new StringBuffer();
        if (strArr != null && strArr.length > 0) {
            int len = strArr.length;
            for (int i = 0; i < len; i++) {
                if (i > 0)
                    strBuf.append(",");
                strBuf.append("'").append(strArr[i]).append("'");
            }
        }
        return strBuf.toString();
    }

    /**
     * @param strArr
     * @return String
     */
    public static List<String> toList(String[] strArr) {
        List<String> list = Arrays.asList(strArr);
        return list;
    }

    /**
     * @param format
     * @return String
     */
    public static String getDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }

    /**
     * @param str
     * @param pStr
     * @param len
     * @return String
     */
    public static String rpad(String str, String pStr, int len) {
        String reStr = "";
        str = toString(str);
        if (str.length() < len) {
            reStr = str;
            for (int i = str.length(); i < len; i++) {
                reStr += pStr;
            }
        } else if (str.length() > len) {
            reStr = str.substring(0, len);
        } else {
            reStr = str;
        }
        return reStr;
    }

    /**
     * @param str
     * @param pStr
     * @param len
     * @return String
     */
    public static String lpad(String str, String pStr, int len) {
        String reStr = "";
        str = toString(str);
        if (str.length() < len) {
            for (int i = 0; i < len - str.length(); i++) {
                reStr += pStr;
            }
            reStr += str;
        } else if (str.length() > len) {
            reStr = str.substring(0, len);
        } else {
            reStr = str;
        }
        return reStr;
    }

    /**
     * @param req
     * @return HashMap
     */
    public static HashMap<Object, Object> getRequestForm(HttpServletRequest req) {
        String param = "";
        String[] values;
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        try {
            Enumeration paramNames = req.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    param = (String) paramNames.nextElement();
                    values = req.getParameterValues(param);

                    if (values != null) {
                        if (values.length == 1)
                            map.put(param, StringUtil.escapeHtmlString(values[0]));
                        else if (values.length > 1)
                            for (int i = 0; i < values.length; i++) {
                                map.put(param, StringUtil.escapeHtmlString(values[i]));
                            }
                        //                            map.put(param, values);                            
                        else if (values.length < 1)
                            map.put(param, "");
                    } else
                        map.put(param, "");
                }
            }

        } catch (Exception ex) {
            logger.error(" CommonUtil getFormValue() Error occurred while process action: " + ex.getMessage(), ex);
        }
        return map;
    }
    
    /**
     * convert HTML content info overload
     *
     * @param siteCd
     * @param html
     * @return String
     */
    public static String replaceHTML(String siteCd, String model_cd ,String html) {
        String reHtml = "";
        if (html != null) {
            html = html.replaceAll("\"[.]/",  "\"/"  + siteCd + "/flagship/" + model_cd + "/");
            html = html.replaceAll("'[.]/",  "'/"  + siteCd + "/flagship/" + model_cd + "/");
            reHtml = html;
        }
        return reHtml;
    }

    /**
     * @param prd_mdl_cd
     * @return String
     */
    public static String flatName(String prd_mdl_cd) {
        String reModel_cd = "";
        if(prd_mdl_cd != null){
            prd_mdl_cd = prd_mdl_cd.toLowerCase();
            
            char ch;
            reModel_cd = "";
            
            for(int count = 0; count < prd_mdl_cd.length(); count++){
                ch = prd_mdl_cd.charAt(count);
                if((ch == '_') || ((ch >= '0') && (ch <= '9')) || ((ch >= 'a') && (ch <= 'z')))
                    reModel_cd = reModel_cd + ch;
                else if(ch == '-')
                    reModel_cd = reModel_cd + '_';
            }
        }
        return reModel_cd;
    }
    
    /**
     * 작성자: 박상모
     * 내용: 엑셀파일을 만들기 위한 메소드
     */
	public static File makeExcelFile(List<Map> result,String filename, String s_name, String[] header_e, String[] header_k, int[] c_size) throws IOException{
		WritableWorkbook wb=null;  //엑셀파일을 쓸 WritableWorkbook 객체
		File f=null;
		try{
			f=new File(fileDir+filename);
			wb=Workbook.createWorkbook(f); //엑셀 파일 패스 지정
			WritableSheet sheet=wb.createSheet(s_name, 0);
						
			for(int i=1;i<=result.size();i++){
				for(int j=0; j<header_e.length;j++){
					sheet.addCell(new Label(j, 0, header_k[j]));  //헤더입력
					//System.out.println(header_k[j]);
					sheet.addCell(new Label(j, i, StringUtil.nullToStr("", String.valueOf(result.get(i-1).get(header_e[j])))));  //Lable(컬럼no,  로우no,  데이타)
					//System.out.println(result.get(i-1).get(header_e[j]).toString());
					sheet.setColumnView(j, c_size[j]);  //열 사이즈를 지정
				}
			}
			
			wb.write();  //엑셀파일에 쓰기
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(wb!=null) wb.close();
			}catch(Exception ignored){}
		}
		return f;
	}
}
