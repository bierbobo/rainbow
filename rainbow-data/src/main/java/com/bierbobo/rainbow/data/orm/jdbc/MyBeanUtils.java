package com.bierbobo.rainbow.data.orm.jdbc;

import org.apache.commons.beanutils.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

public class MyBeanUtils  extends BeanUtils {

  public MyBeanUtils() {
    super();
  }

  /**
   * ��Map�ڵ�key��Bean��������ͬ�����ݸ��Ƶ�BEAN��
   * @param bean Object
   * @param properties Map
   * @throws IllegalAccessException
   * @throws java.lang.reflect.InvocationTargetException
   */
  public static void copyMap2Bean(Object bean, Map properties) throws
      IllegalAccessException, InvocationTargetException {
      // Do nothing unless both arguments have been specified
      if ( (bean == null) || (properties == null)) {
          return;
      }
      // Loop through the property name/value pairs to be set
      Iterator names = properties.keySet().iterator();
      while (names.hasNext()) {
          String name = (String) names.next();
          // Identify the property name and value(s) to be assigned
          if (name == null) {
              continue;
          }
          Object value = properties.get(name);
          try {
              Class clazz = PropertyUtils.getPropertyType(bean, name);
              if (null == clazz) {
                  continue;
              }
              String className = clazz.getName();
              if (className.equalsIgnoreCase("java.sql.Timestamp")||className.equalsIgnoreCase("java.sql.Date")) {
                  if (value == null || value.equals("")) {
                      continue;
                  }
              }
              setProperty(bean, name, value);
          }
          catch (NoSuchMethodException e) {
              continue;
          }
          catch(Exception e){
        	  e.printStackTrace();
          }
      }
  }

  /**
   * Map�ڵ�key��Bean��������ͬ�����ݸ��Ƶ�BEAN��
   * ���ڴ��ڿ�ֵ��ȡĬ��ֵ
   * @param bean Object
   * @param properties Map
   * @param defaultValue String
   * @throws IllegalAccessException
   * @throws java.lang.reflect.InvocationTargetException
   */
  public static void copyMap2Bean(Object bean, Map properties, String defaultValue) throws
      IllegalAccessException, InvocationTargetException {
      // Do nothing unless both arguments have been specified
      if ( (bean == null) || (properties == null)) {
          return;
      }
      // Loop through the property name/value pairs to be set
      Iterator names = properties.keySet().iterator();
      while (names.hasNext()) {
          String name = (String) names.next();
          // Identify the property name and value(s) to be assigned
          if (name == null) {
              continue;
          }
          Object value = properties.get(name);
          try {
              Class clazz = PropertyUtils.getPropertyType(bean, name);
              if (null == clazz) {
                  continue;
              }
              String className = clazz.getName();
              if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                  if (value == null || value.equals("")) {
                      continue;
                  }
              }
              if (className.equalsIgnoreCase("java.lang.String")) {
                  if (value == null) {
                      value = defaultValue;
                  }
              }
              setProperty(bean, name, value);
          }
          catch (NoSuchMethodException e) {
              continue;
          }
      }
  }

  /**
   * ��orig��dest��ͬ���Ե�value���Ƶ�dest��
   * @param dest
   * @param orig
   * @throws IllegalAccessException
   * @throws java.lang.reflect.InvocationTargetException
   */
  public static void copyBean2Bean(Object dest, Object orig) throws
      IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new org.apache.
                commons.beanutils.converters.IntegerConverter(null), Integer.class);
		ConvertUtils.register(new org.apache.
                commons.beanutils.converters.DoubleConverter(null), Double.class);
		ConvertUtils.register(new org.apache.
                commons.beanutils.converters.DoubleConverter(null), Float.class);
      convert(dest, orig);
  }

  private static void convert(Object dest, Object orig) throws
      IllegalAccessException, InvocationTargetException {

      // Validate existence of the specified beans
      if (dest == null) {
          throw new IllegalArgumentException
              ("No destination bean specified");
      }
      if (orig == null) {
          throw new IllegalArgumentException("No origin bean specified");
      }

      // Copy the properties, converting as necessary
      if (orig instanceof DynaBean) {
          DynaProperty origDescriptors[] =
              ( (DynaBean) orig).getDynaClass().getDynaProperties();
          for (int i = 0; i < origDescriptors.length; i++) {
              String name = origDescriptors[i].getName();
              if (PropertyUtils.isWriteable(dest, name)) {
                  Object value = ( (DynaBean) orig).get(name);
                  try {
                      copyProperty(dest, name, value);
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }
      else if (orig instanceof Map) {
          Iterator names = ( (Map) orig).keySet().iterator();
          while (names.hasNext()) {
              String name = (String) names.next();
              if (PropertyUtils.isWriteable(dest, name)) {
                  Object value = ( (Map) orig).get(name);
                  try {
                      copyProperty(dest, name, value);
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }
      else
      /* if (orig is a standard JavaBean) */
      {
          PropertyDescriptor origDescriptors[] =
              PropertyUtils.getPropertyDescriptors(orig);
          for (int i = 0; i < origDescriptors.length; i++) {
              String name = origDescriptors[i].getName();
              String type = origDescriptors[i].getPropertyType().toString();
              if ("class".equals(name)) {
                  continue; // No point in trying to set an object's class
              }
              if (PropertyUtils.isReadable(orig, name) &&
                  PropertyUtils.isWriteable(dest, name)) {
                  try {
                      Object value = PropertyUtils.getSimpleProperty(orig, name);
                      copyProperty(dest, name, value);
                  }
                  catch (IllegalArgumentException ie) {
                      ; // Should not happen
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }

  }

}
