package com.ant.common.util;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class LoggerDebugger {

    protected static Logger logger = Logger.getRootLogger();
    private static final boolean willYouUseLoggerDebug = true;
    private static final boolean useLoggerToPrint = false;

    @SuppressWarnings("unchecked")
    public static void loggerDebug(Object obj) {
        if (willYouUseLoggerDebug) {
        if (obj != null) {
            Class<? extends Object> c = obj.getClass();
            String className = c.getName();
            if ( c.isArray()) {
                loggerDebugOutput("[Array]");
                loggerDebugArray((Object[])obj);
            }
            else if (c.isPrimitive()) {
                loggerDebugOutput("[Primitive]");
                loggerDebugOutput(obj);
            }
            else if(className.equals("java.util.Map")
                    || className.equals("java.util.HashMap")) {
                loggerDebugOutput("[Map]");
                loggerDebugMap((Map)obj);
            }
            else if (className.equals("java.util.List")
                    || className.equals("java.util.ArrayList")) {
                loggerDebugOutput("[List]");
                loggerDebugList((List)obj);
            }
            else if ( c.getName().equals("java.lang.String")) {
                loggerDebugOutput(obj);
            }
            else {
                loggerDebugOutput("[Object] " + c.getName());
                loggerDebugObject(obj);
            }
        }
        }
    }

    public static void loggerDebugMap(Map<Object, Object> hash) {
        if (!hash.isEmpty()) {
            Iterator<Object> ir = hash.keySet().iterator();
            while (ir.hasNext()) {
                String name = (String) ir.next();

                String output = name + " : " +  CommonUtil.toString(hash.get(name));
                loggerDebug(output);
            }
        }
    }

    public static void loggerDebugArray(Object[] array) {
        int length = array.length;
        for(int i=0; i < length; i++) {
            loggerDebug(array[i]);
        }
    }

    public static void loggerDebugList(List<Object> list) {
        int length = list.size();
        for(int i=0; i < length; i++) {
            loggerDebug(list.get(i));
        }
    }

    @SuppressWarnings("unchecked")
    public static void loggerDebugObject(Object obj) {
        Class c = obj.getClass();
        Method[] methods = c.getDeclaredMethods();
        int length = methods.length;
        for(int i=0; i < length; i++) {
            if(methods[i].getName().indexOf("get") == 0) {
//                loggerDebug(i + ":" + methods[i].getName());
                try {
                     loggerDebug(methods[i].getName() + " : " + methods[i].invoke(obj, new Object[]{}));
                } catch (Exception e) {
                    //loggerDebug(methods[i].getName() + "���õ�");
                    loggerDebugOutput(obj);
                }
            }
        }
    }

    public static void loggerDebugOutput(Object obj) {
        if(useLoggerToPrint) {
            logger.debug(obj);
        }
        else {
            System.out.println(obj);
        }
    }
}