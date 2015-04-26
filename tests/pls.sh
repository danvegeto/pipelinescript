#!/bin/bash
python ../pls.py $1 > Output.java 2> /dev/null
javac Output.java 2> /dev/null
java -cp . Output
rm Output.java Output.class parser.out parsetab.py parsetab.pyc 2> /dev/null
