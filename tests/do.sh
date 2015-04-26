#!/bin/bash
python ../pls.py $1 > sandbox/Output.java
javac sandbox/Output.java
java sandbox/Output
rm parser.out parsetab.py parsetab.pyc 2> /dev/null



