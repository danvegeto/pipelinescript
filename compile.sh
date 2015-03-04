DIR="$(dirname "$0")"
javac -d $DIR/PipelineScript/bin `find $DIR/PipelineScript/src -name "*.java"`