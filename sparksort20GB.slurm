#!/bin/bash
#BATCH --nodes=1
#SBATCH --output=SparkSort20GB.log

javac -classpath /opt/spark-2.3.0-bin-hadoop2.7/jars/spark-core_2.11-2.3.0.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/spark-sql_2.11-2.3.0.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/scala-compiler-2.11.8.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/scala-library-2.11.8.jar SparkSort.java
jar cvf SparkSort.jar SparkSort*.class

START_TIME=$(date +%s)
spark-submit --class SparkSort --master yarn --deploy-mode client --driver-memory 1g --executor-memory 1g --executor-cores 1 --num-executors 1 SparkSort.jar /input/data-20GB /user/ssuresh14/outputspark133
END_TIME=$(date +%s)
TOTAL_TIME=$(($END_TIME - $START_TIME))
hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/ssuresh14/outputspark133 /user/ssuresh14/reportspark133
hadoop fs -get /user/ssuresh14/reportspark133/part-r-00000
echo "The time taken for hadoopsort8GB is $TOTAL_TIME seconds"

