import java.io.IOException;

import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class HadoopSort {

    
public static class HadoopMapper extends Mapper<Object, Text, Text, Text>
{

         
Text hadoop_sortword1 = new Text();
         
Text hadoop_sortword2= new Text();

        
public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            
String str= value.toString();
            
hadoop_sortword1.set(str.substring(1, 10));
            
hadoop_sortword2.set(str.substring(12) + "\r\n");
            
context.write(hadoop_sortword1 , hadoop_sortword2 );
       
 }
   
 }

    
public static class HadoopReducer extends Reducer<Text,Text,Text,Text> {

        
public void reduce(Text key, Text value, Context context) throws IOException, InterruptedException
 {

          
  context.write(key, value);
       
 }
    
}

    
public static void main(String[] args) throws Exception {

        
Configuration config = new Configuration();
        
config.set("mapreduce.output.textoutputformat.separator","  ");
        
long start = System.currentTimeMillis();
      
  Job job = Job.getInstance(config, "Sort");
       
  job.setJarByClass(HadoopSort.class);

      

  job.setMapperClass(HadoopMapper.class);
       
  job.setReducerClass(HadoopReducer.class);

        

  job.setOutputKeyClass(Text.class);
       
  job.setOutputValueClass(Text.class);

        

  FileInputFormat.addInputPath(job, new Path(args[0]));
        
  FileOutputFormat.setOutputPath(job, new Path(args[1]));
       

  long end = System.currentTimeMillis();
       
  long duration = (end - start)/1000;
       
  System.out.println("Compute time is"+ duration + "ms");
       
  System.exit(job.waitForCompletion(true) ? 0 : 1);

}

}



