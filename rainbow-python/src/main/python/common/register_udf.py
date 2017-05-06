import os


register_sql = """
    add jar """ + os.getcwd() + os.sep + """common""" + os.sep + """ihs_hive_udf-1.0.jar;

    create temporary function skuType as 'com.jd.vdp.udf.ConvertSkuDataTypeUtil';
    create temporary function arraySum as 'com.jd.vdp.udf.ArraySum';
"""

# print(os.getcwd())
# print(os.sep)
# print(register_sql)