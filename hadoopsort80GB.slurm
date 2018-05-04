#!/bin/bash
#SBATCH --nodes=1
#SBATCH --output=HadoopSort80GB.log

javac -classpath $(hadoop classpath) HadoopSort.java
jar cvf HadoopSort.jar *.class

START_TIME=$(date +%s)
hadoop jar HadoopSort.jar HadoopSort  /input/data-80GB /user/ssuresh14/output122
END_TIME=$(date +%s)
TOTAL_TIME=$(($END_TIME - $START_TIME))
hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/ssuresh14/output122 /user/ssuresh14/report122
hadoop fs -get /user/ssuresh14/report122/part-r-00000
echo "The time taken for hadoopsort80GB is $TOTAL_TIME seconds"

