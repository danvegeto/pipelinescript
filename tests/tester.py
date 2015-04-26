import os
import os.path
import subprocess

PATH='./Output.java'
os.chdir("./sandbox/")

def test(): 
    print("Inside Testing Module")
    #subprocess.check_output(["echo", "Hello World!"])
    subprocess.check_output(["./do.sh", "input.pls"])

def run():
    output=subprocess.call(['./do.sh fib.pls'])

if os.path.isfile(PATH) and os.access(PATH, os.R_OK):
    print "Output java File exists and is readable"
    test()
else:
    print "Either the Output java file is missing or is not readable"
    run()
