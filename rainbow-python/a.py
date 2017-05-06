#!/usr/bin/python3
#coding=utf-8

# 第一个注释
'''
__author__ = 'lifubo'
import datetime
print(dir(datetime))
print(datetime.datetime.now()  )
print(datetime.datetime(2015, 5, 11, 17, 29, 14, 476759)    )
print(datetime.datetime.now().strftime('%b-%d-%y %H:%M:%S')      )


import keyword
print(dir(keyword))
print(keyword.kwlist)

if True :
    import sys; x = 'runoob'; sys.stdout.write(x + '\n')
    print('ss')
elif True :
    item_one='s1'
    item_two='s2'
    item_three='s3'
    total = item_one + \
            item_two + \
            item_three
    print(total)
else :
    print('ss2')
'''

'''
一、基本数据类型
Python 中的变量不需要声明;每个变量在使用前都必须赋值;它没有类型;

多个变量赋值
del语句删除单个或多个对象。



标准数据类型
Number（数字）   int、float、bool、complex（复数）。
1、Python可以同时为多个变量赋值，如a, b = 1, 2。
2、一个变量可以通过赋值指向不同类型的对象。
3、数值的除法（/）总是返回一个浮点数，要获取整数使用//操作符。
4、在混合计算时，Python会把整型转换成为浮点数。

a = b = c = 1
print(type(a), type(b), type(c))
a, b, c, d = 20, 5.5, True, 4+3j
print(type(a), type(b), type(c), type(d))

print(2/4)
print(2//4)
print(4.3 * 2)



String（字符串）

1、反斜杠可以用来转义，使用r可以让反斜杠不发生转义。
2、字符串可以用+运算符连接在一起，用*运算符重复。
3、Python中的字符串有两种索引方式，从左往右以0开始，从右往左以-1开始。
4、Python中的字符串不能改变。

str = 'Runoob'
print (str)          # 输出字符串
print (str[0:-1])    # 输出第一个个到倒数第二个的所有字符
print (str[0])       # 输出字符串第一个字符
print (str[2:5])     # 输出从第三个开始到第五个的字符
print (str[2:])      # 输出从第三个开始的后的所有字符
print (str * 2)      # 输出字符串两次
print (str + "TEST") # 连接字符串
print(r'Ru\noob')


List（列表）

1、List写在方括号之间，元素用逗号隔开。
2、和字符串一样，list可以被索引和切片。
3、List可以使用+操作符进行拼接。
4、List中的元素是可以改变的。


list = [ 'abcd', 786 , 2.23, 'runoob', 70.2 ]
tinylist = [123, 'runoob']

print (list)            # 输出完整列表
print (list[0])         # 输出列表第一个元素
print (list[1:3])       # 从第二个开始输出到第三个元素
print (list[2:])        # 输出从第三个元素开始的所有元素
print (tinylist * 2)    # 输出两次列表
print (list + tinylist) # 连接列表

Tuple（元组）
1、与字符串一样，元组的元素不能修改。
2、元组也可以被索引和切片，方法一样。
3、注意构造包含0或1个元素的元组的特殊语法规则。
4、元组也可以使用+操作符进行拼接。

tuple = ( 'abcd', 786 , 2.23, 'runoob', 70.2  )
tinytuple = (123, 'runoob')

print (tuple)             # 输出完整元组
print (tuple[0])          # 输出元组的第一个元素
print (tuple[1:3])        # 输出从第二个元素开始到第三个元素
print (tuple[2:])         # 输出从第三个元素开始的所有元素
print (tinytuple * 2)     # 输出两次元组
print (tuple + tinytuple) # 连接元组

string、list和tuple都属于sequence（序列）。



Sets（集合）

集合（set）是一个无序不重复元素的序列。
基本功能是进行成员关系测试和删除重复元素。
可以使用大括号({})或者 set()函数创建集合，注意：创建一个空集合必须用 set() 而不是 { }，因为 { } 是用来创建一个空字典。


student = ({'Tom', 'Jim', 'Mary', 'Tom', 'Jack', 'Rose'})

print(student)   # 输出集合，重复的元素被自动去掉

# 成员测试
if('Rose' in student) :
    print('Rose 在集合中')
else :
    print('Rose 不在集合中')
# set可以进行集合运算
a = set('abracadabra')
b = set('alacazam')
print(a)
print(b)
print('===========')

print(a - b)     # a和b的差集

print(a | b)     # a和b的并集

print(a & b)     # a和b的交集

print(a ^ b)     # a和b中不同时存在的元素


Dictionary（字典）

1、字典是一种映射类型，它的元素是键值对。
2、字典的关键字必须为不可变类型，且不能重复。
3、创建空字典使用 { }。


dict1 = {}
dict1['one'] = "1 - 菜鸟教程"
dict1[2]     = "2 - 菜鸟工具"

tinydict1 = {'name': 'runoob','code':1, 'site': 'www.runoob.com'}


print (dict1['one'])       # 输出键为 'one' 的值
print (dict1[2])           # 输出键为 2 的值
print (tinydict1)          # 输出完整的字典
print (tinydict1.keys())   # 输出所有键
print (tinydict1.values()) # 输出所有值

print (dict([('Runoob', 1), ('Google', 2), ('Taobao', 3)])) # 输出所有值
print ({x: x**2 for x in (2, 4, 6)}) # 输出所有值
print ( dict(Runoob=1, Google=2, Taobao=3)) # 输出所有值


Python数据类型转换



'''


