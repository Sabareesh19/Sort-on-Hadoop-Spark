Username A20396634
Name Sabareesh Suresh

Follow these instructions to execute the HadoopSort.java

javac -classpath $(hadoop classpath) HadoopSort.java   //Compile the haddopsort
jar cvf HadoopSort.jar *.class  //to combine all the class and put as a single jar file
hadoop jar HadoopSort.jar HadoopSort /input/data-8GB /user/ssuresh14/output   //change the output name everytime
hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/ssuresh14/output /user/ssuresh14/report
hadoop fs -get /user/ssuresh14/report/part-r-00000

While executing the Hadoop sort for 20GB and 80GB /input/data-20GB /user/ssuresh14/output and /input/data-80GB /user/ssuresh14/output accordingly.

To ease the HadoopSort execution run the slurm files
1.sbatch hadoopsort8GB.slurm
2.sbatch hadoopsort20GB.slurm
3.sbatch hadoopsort80GB.slurm



Follow these instructions to execute the SparkSort.java

$ javac -classpath /opt/spark-2.3.0-bin-hadoop2.7/jars/spark-core_2.11-2.3.0.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/spark-sql_2.11-2.3.0.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/scala-compiler-2.11.8.jar:/opt/spark-2.3.0-bin-hadoop2.7/jars/scala-library-2.11.8.jar SparkSort.java
$ jar cvf SparkSort.jar SparkSort*.class # make sure to include only the class files for the Spark implemetation
$ spark-submit --class SparkSort --master yarn --deploy-mode client --driver-memory 1g --executor-memory 1g --executor-cores 1 --num-executors 1 SparkSort.jar /input/data-8GB /user/ssuresh14/outputspark9
$ hadoop jar /opt/hadoop-2.9.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar teravalidate /user/ssuresh14/outputspark9 /user/ssuresh14/reportspark9
$ hadoop fs -get /user/ssuresh14/reportspark9/part-r-00000

To ease the SparkSort execution run the slurm files
1.sbatch sparksort8GB.slurm
2.sbatch sparksort20GB.slurm
3.sbatch sparksort80GB.slurm

The respective output files are generated for hadoop and spark in the output folder as per the specified names in the slurm files.


