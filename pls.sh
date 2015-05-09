python pls.py $1 > PipelineScript/src/com/pipelinescript/Pipeline.java 2> /dev/null
javac -d PipelineScript/bin PipelineScript/src/com/pipelinescript/*.java 2> /dev/null
java -cp PipelineScript/bin com.pipelinescript.Pipeline
rm PipelineScript/src/com/pipelinescript/Pipeline.java
rm parser.out parsetab.py parsetab.pyc 2> /dev/null
