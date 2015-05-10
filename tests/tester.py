import os
import os.path
import subprocess
import sys
from StringIO import StringIO
import re

s = StringIO()
os.chdir("..")
def fetchFiles(fName):
    f = open(fName,"r")
    aux = 0
    name = ""
    result = []
    for line in f.readlines():
        if "////" in line:
            if not run(name,'\n'.join(result)):
                print "%s file has incorrect result!"%name
                print "\nThe expected output is %s\n"%result            
            else:
                print "-----------------------------%s CHECK PASSED----------------------------"%name
                print "\nThe expected output is %s\n"%result
            aux = 0
            name = ""
            result = []
            continue
        if aux == 0:
            name = line.replace("\n", "")
            aux = 1
            continue
        if aux == 1:
            result.append(line.replace("\n", ""))

def run(fName, result):
    #output=subprocess.Popen(['./do.sh', "fib.pls"], stdout = subprocess.PIPE,stderr = subprocess.PIPE)
    output=subprocess.Popen(['sh','pls.sh', fName], stdout = subprocess.PIPE,stderr = subprocess.PIPE)    
    concline = ''.join(output.stdout.readlines() )
    if result.replace("\r","") in concline.replace("\r",""):
        return True
    return False

fetchFiles("tests/results.txt")



  
#if os.path.isfile(PATH) and os.access(PATH, os.R_OK):
#    print "Output java File exists and is readable"
   
#else:
 #   print "Either the Output java file is missing or is not readable"
  #  print "Calling function"

