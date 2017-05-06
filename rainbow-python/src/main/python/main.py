__author__ = 'lifubo'

import sys

def main():

    if(len(sys.argv)>1): # 传了系统参数
        package_module = sys.argv[1]
        __import__(package_module)

        c = sys.modules[package_module]

        module_main = getattr(c,"main")
        sys.argv = sys.argv[1:]
        module_main() # call def

    else:
        print("请输入需要执行的py脚本相对路径")

# for arg in sys.argv:
#     print(arg)

if __name__ == "__main__":
    main()