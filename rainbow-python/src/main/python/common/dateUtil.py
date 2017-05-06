__author__ = 'lifubo'

import datetime
import time
import calendar


date_format = '%Y-%m-%d'
date_week_format = '%Y-%W'
date_month_format = '%Y-%m'

def get_format_today(format):
    """
    获取今天日期（字符串）
    """
    today = datetime.date.today().strftime(format)
    return today

def get_format_yesterday(today = get_format_today(date_format),sub=1,format = date_format):
    """
    默认获取昨天日期（字符串）默认'%Y-%m-%d'，
    format =‘%d' 获取昨天是本月中第几天
    """
    yesterday = (datetime.datetime.strptime(today, date_format) + datetime.timedelta(-sub)).strftime(format)
    return yesterday

#获取本月第一天（字符串）,默认获取昨天所在月份的第一天
def get_current_month_begin(tx_data_str = get_format_today(date_format) ,format = date_format):
    tx_data = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_data_str, format)))
    return datetime.date(tx_data.year, tx_data.month, 1).strftime(format)

#获取上个月第一天
def get_last_month_begin(tx_data_str = get_format_today(date_format),format = date_format):
    tx_data = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_data_str, format)))
    return_tx_data_str = ''
    if tx_data.month-1 > 0:
        return_tx_data_str = datetime.date(tx_data.year,tx_data.month-1,1).strftime(format)
    else:
        return_tx_data_str = datetime.date(tx_data.year - 1,12,1).strftime(format)
    return return_tx_data_str

def get_last_month_end(tx_data_str = get_format_today(date_format),format = date_format):
    txDate = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_data_str, format)))
    if(txDate.month!=1):
        pre_month_end = datetime.date(txDate.year, txDate.month, 1) - datetime.timedelta(1)
    else:
        pre_month_end = datetime.date(txDate.year, 1, 1) - datetime.timedelta(1)
    return pre_month_end.strftime(format)

def get_current_month_end(tx_data_str = get_format_yesterday(),format = date_format):
    """
    获取本月最后（字符串）,默认获取昨天所在月份的最后一天
    """
    tx_data = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_data_str, format)))
    pre_month_end = datetime.date(tx_data.year, tx_data.month, calendar.monthrange(tx_data.year,tx_data.month)[1])
    return pre_month_end.strftime(format)

def oneday_by_date(tx_date_str, off_size, format):
    """
    获取第N天（字符串）
    """
    tx_date = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_date_str, format)))
    yesterday = (tx_date + datetime.timedelta(off_size)).strftime(format)
    return yesterday

def get_data_days_diff(time_start,time_end,format=date_format):
    """
    获取日期相隔天数
    """
    dateStart = time.strptime(time_start,format)
    dateEnd = time.strptime(time_end,format)
    time1 = time.mktime(dateEnd)
    time2 = time.mktime(dateStart)
    daysec = 24 * 60 * 60
    return int(( time2 - time1 )/daysec)

def get_month_days(tx_data_str = get_format_yesterday(),format = date_format):
    """
        获取该月天数
    """
    tx_data = datetime.datetime.fromtimestamp(time.mktime(time.strptime(tx_data_str, format)))
    return calendar.monthrange(tx_data.year,tx_data.month)[1]

def get_last_friday(format=date_format):
    today = lastFriday = datetime.date.today()
    oneday = datetime.timedelta(days = 1)

    while lastFriday.weekday() != calendar.FRIDAY or (today - lastFriday).days <= 2: # 小于等于几 要看距离周日差几天，周五差2天，周一差6天。
        lastFriday -= oneday

    return lastFriday.strftime( format)

#获取上周一
def get_last_monday(todayStr = get_format_today(date_format),format = date_format):
    today = lastMonday = datetime.datetime.fromtimestamp(time.mktime(time.strptime(todayStr, format)))
    oneday = datetime.timedelta(days = 1)

    while lastMonday.weekday() != calendar.MONDAY or (today - lastMonday).days <= 6:
        lastMonday -= oneday

    return lastMonday.strftime( format)

#获取上周日
def get_last_sunday(todayStr = get_format_today(date_format),format = date_format):
    today = lastSunday = datetime.datetime.fromtimestamp(time.mktime(time.strptime(todayStr, format)))
    oneday = datetime.timedelta(days = 1)

    while lastSunday.weekday() != calendar.SUNDAY or (today - lastSunday).days == 0:
        lastSunday -= oneday

    return lastSunday.strftime( format)
#获取上周六
def get_last_saturday(todayStr = get_format_today(date_format),format = date_format):
    txDate = datetime.datetime.fromtimestamp(time.mktime(time.strptime(todayStr, format)))
    pre_week_end = txDate - datetime.timedelta(txDate.weekday())- datetime.timedelta(2)
    return pre_week_end.strftime(format)

def get_last_week(today_str = get_format_today(date_format),format = date_week_format):
    last_monday = get_last_monday(today_str)
    last_week = datetime.datetime.strptime(last_monday, date_format).strftime(format)
    return last_week


def get_last_month(today_str = get_format_today(date_format),format = date_month_format):
    last_month_begin = get_last_month_begin(today_str)
    last_month = datetime.datetime.strptime(last_month_begin, date_format).strftime(format)
    return last_month


def get_week_begin(day_str = get_format_yesterday(), format = date_format,N = 0):
    """
    获取指定天 所在周周一
    """
    calc = datetime.datetime.strptime(day_str,format)
    monday = calc + datetime.timedelta(-calc.weekday())
    return monday.strftime(format)

def get_week_end(day_str = get_format_yesterday(), format = date_format,N = 0):
    """
    获取指定天 所在周周日
    """
    calc = datetime.datetime.strptime(day_str,format)
    monday = calc + datetime.timedelta(6-calc.weekday())
    return monday.strftime(format)

def get_week_begin_by_date(count, txDateStr, format):
    """
    获取指定日期前N周第一天（字符串）
    """
    txDate = datetime.datetime.fromtimestamp(time.mktime(time.strptime(txDateStr, format)))
    pre_week_begin = txDate - datetime.timedelta(txDate.weekday()) - datetime.timedelta(count * 7)
    return pre_week_begin.strftime(format)

def get_pre_month_begin(format):
    """
    获取上个月第一天（字符串）
    """
    pre_month_end = datetime.date(datetime.date.today().year, datetime.date.today().month, 1) - datetime.timedelta(1)
    begin_date = datetime.date(pre_month_end.year, pre_month_end.month, 1)
    return begin_date.strftime(format)

def get_pre_month_end(format):
    """
    获取上个月最后一天（字符串）
    """
    pre_month_end = datetime.date(datetime.date.today().year, datetime.date.today().month, 1) - datetime.timedelta(1)
    return pre_month_end.strftime(format)

def main():
    #print("get_format_today():" + get_format_today())
    print("get_format_yesterday():" + get_format_yesterday())
    print("get_format_yesterday():" + get_format_yesterday('2016-05-01'))
    print("get_format_yesterday():" + get_format_yesterday('2016-05-01',2))
    # print("get_format_yesterday('%d'):" + get_format_yesterday('%d'))
    print("get_current_month_begin():" + get_current_month_begin())
    print("get_last_month_begin():" + get_last_month_begin())
    # print("get_current_month_end():" + get_current_month_end())
    # print(get_month_days())
    # print("get_week_begin:" + get_week_begin('2016-03-21'))
    # print("get_week_end:" + get_week_end('2016-03-21'))
    #print(get_last_sunday('%Y-%m-%d'))
    print('get_last_week:'+ get_last_week())
    print('get_last_month:' + get_last_month())
    print('get_last_month_end:' + get_last_month_end())
    print('get_last_month_end:' + get_last_month_end('2016-03-05'))
    print('get_last_month_end:' + get_last_month_end('2016-12-24'))
    print('get_last_month_end:' + get_last_month_end('2017-01-24'))

if __name__ == "__main__":
    main()