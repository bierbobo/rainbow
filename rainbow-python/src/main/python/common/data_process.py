__author__ = 'lifubo'


import threading
import time
from common import dateUtil
from common import register_udf
from HiveTask import HiveTask

_db_name = 'app'

class DataProcess(object):


    def __init__(self,run_date):
        self.yesterday = dateUtil.get_format_yesterday(run_date)
        self.last_month_begin = dateUtil.get_last_month_begin(run_date)
        self.last_month_end = dateUtil.get_last_month_end(run_date)
        self.current_month_begin = dateUtil.get_current_month_begin(run_date)
        self.last_monday = dateUtil.get_last_monday(run_date)
        self.last_sunday = dateUtil.get_last_sunday(run_date)
        self.last_week = dateUtil.get_last_week(run_date)
        self.last_month = dateUtil.get_last_month(run_date)
        self.thread_batch_number = 30 #多线程执行时一次提交的线程数量。

        self.hidden_var = {
            'yesterday':self.yesterday,
            'last_month_begin' :  self.last_month_begin,
            'last_month_end': self.last_month_end,
            'current_month_begin' : self.current_month_begin,
            'last_monday' : self.last_monday,
            'last_sunday' : self.last_sunday,
            'last_week' : self.last_week,
            'last_month' : self.last_month
        }

        self.register_udf_sql = register_udf.register_sql


    def do_job(self):
        self.__validate_attr()

        for action in self.process_action:
            if 'action_name' in action :
                if 'dimensions' in action:
                    action_name = action.get('action_name')
                    parameters = action.get('parameters')
                    dimensions = action.get('dimensions')
                    if isinstance(action_name,list) :
                        for attr in action_name:
                            self.__exec_sql_thread(attr,parameters,dimensions)
                    else:
                        self.__exec_sql_thread(action_name,parameters,dimensions)
                else:
                    action_name = action.get('action_name')
                    parameters = action.get('parameters')
                    if isinstance(action_name,list) :
                        self._action_exec_sql_thread(action_name,parameters)
                        #for attr in action_name:
                        #   self.__exec_sql(attr,parameters)
                    else:
                        self.__exec_sql(action_name,parameters)
            else:
                raise Exception('您的配置文件中必须有参数action_name!')

    def __validate_attr(self):
        """"""
        pass




    def __exec_sql(self,action_name,parameters):

        process_sql = getattr(self, action_name)

        if parameters != None:
            for key in parameters:
                process_sql = process_sql.replace('$' + key + '$',parameters.get(key))
        for key in self.hidden_var:
            process_sql = process_sql.replace('$' + key + '$',self.hidden_var.get(key))

        process_sql = process_sql.replace('$action_name$',action_name)

        ht = HiveTask('ERROR')
        ht.exec_sql(schema_name=_db_name, sql=process_sql)



    def __exec_sql_thread(self,action_name,parameters,dimensions):
        process_sql = getattr(self, action_name)

        if parameters != None:
            for key in parameters:
                process_sql = process_sql.replace('$' + key + '$',parameters.get(key))

        for key in self.hidden_var:
            process_sql = process_sql.replace('$' + key + '$',self.hidden_var.get(key))

        process_sql = process_sql.replace('$action_name$',action_name)

        thread_list = []

        for dimension in dimensions:
            thread_sql = process_sql
            for key in dimension:
                thread_sql = thread_sql.replace('$' + key + '$',dimension.get(key))

            thread_list.append(ProcessThread(thread_sql))

        sub_thread_list = []
        for index,th in enumerate(thread_list):
            th.start()
            sub_thread_list.append(th)

            if len(sub_thread_list) >= self.thread_batch_number or index == (len(thread_list) -1): #判断启动的线程够一批次或者已到线程集合的最后一个元素
                for sub_th in sub_thread_list:
                    sub_th.join()

                del sub_thread_list[:]


    def _action_exec_sql_thread(self,action_names,parameters):
        thread_list = []
        for action_name in action_names:
            process_sql = getattr(self, action_name)

            if parameters != None:
                for key in parameters:
                    process_sql = process_sql.replace('$' + key + '$',parameters.get(key))

            for key in self.hidden_var:
                process_sql = process_sql.replace('$' + key + '$',self.hidden_var.get(key))

            thread_sql = process_sql.replace('$action_name$',action_name)
            thread_list.append(ProcessThread(thread_sql))

        sub_thread_list = []
        for index,th in enumerate(thread_list):
            th.start()
            sub_thread_list.append(th)

            if len(sub_thread_list) >= self.thread_batch_number or index == (len(thread_list) -1): #判断启动的线程够一批次或者已到线程集合的最后一个元素
                for sub_th in sub_thread_list:
                    sub_th.join()

                del sub_thread_list[:]


    def get_field_list(self,all_field,group_field):
        field_list = all_field.split(',')
        group_field_list = group_field.split(',')

        for index in range(len(field_list)):
            field_list[index] = field_list[index].rstrip()
            field_list[index] = field_list[index].lstrip()

        result_field_list = ''

        for index,field in enumerate(field_list):
            if field in group_field_list:
                result_field_list = result_field_list + field + ','
            else:
                result_field_list = result_field_list + 'null,'

        return result_field_list[:-1]


class  ProcessThread(threading.Thread):
    """多线程处理类"""

    def __init__(self,process_sql):
        threading.Thread.__init__(self)
        self.daemon = False
        self.process_sql = process_sql

    def run(self):
        try:
            time.sleep(10)
            ht = HiveTask('ERROR')
            ht.exec_sql(schema_name=_db_name, sql=self.process_sql)

            self.exeInfo = "线程正常运行,sql为:" + self.process_sql
        except Exception as e:
            self.exeInfo = "程序运行时发生异常，异常信息为:" + str(e) + ",sql为:" + self.process_sql