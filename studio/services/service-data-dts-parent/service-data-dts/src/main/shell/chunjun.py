#!/usr/bin/env python
# -*- coding:utf-8 -*-

import sys
import os
import subprocess
import glob


def printCopyright():
  print '''
AllDataDC (%s), From AllDataDC !
AllDataDC All Rights Reserved.

'''
  sys.stdout.flush()


if __name__ == "__main__":
  printCopyright()

  # 获取传入的参数
  abs_file = sys.path[0]
  json_file = sys.argv[1]

  # 动态生成 jar 文件路径列表
  jar_files = glob.glob(os.path.join(abs_file, 'lib', '*.jar'))
  class_path = ':'.join(jar_files)  # 使用冒号将各个 jar 文件路径连接起来

  # 构建命令
  startCommand = "java -cp '{class_path}' com.dtstack.chunjun.client.Launcher -mode local -jobType sync -job {json_file} -chunjunDistDir {abs_file}/chunjun-dist -flinkConfDir {abs_file}/flinkconf ".format(class_path=class_path, json_file=json_file, abs_file=abs_file)
      # log_name=log_name


  print(startCommand)

  # 执行命令
  child_process = subprocess.Popen(startCommand, shell=True,
                                   stdout=subprocess.PIPE,
                                   stderr=subprocess.PIPE)

  # 等待子进程完成并获取输出
  stdout, stderr = child_process.communicate()

  # 打印标准输出和错误（可选）
  print("STDOUT:", stdout)
  print("STDERR:", stderr)

  # 退出程序，返回子进程的退出码
  sys.exit(child_process.returncode)
