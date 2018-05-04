import java.io.IOException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class SparkSort {

  public static void main(String[] args) throws IOException {
     if (args.length < 2) {
      System.out.println("Specify the correct input and output");
      System.exit(1);
    }

    //System.out.println("The sorted time of Spark is " + System.currentTimeMillis() + " ms.");
    long start_time = System.currentTimeMillis();
    SparkConf Config = new SparkConf().setAppName("sort");
    Config.set("spark.hadoop.validateOutputSpecs", "false");
    JavaSparkContext sparkctx= new JavaSparkContext(Config);
    JavaRDD<String> contents = sparkctx.textFile(args[0], 1);

    JavaPairRDD<String, String> words = contents.mapToPair(new PairFunction<String, String, String>() {
                private static final long serialVersionUID = 1L;
                @Override
        public Tuple2<String, String> call(String s) throws IOException {
            return new Tuple2<String, String>(s.substring(0, 10), s.substring(10)+"\r");
        }
    });

    JavaPairRDD<String, String> sorted = words.sortByKey(true, 1);

    JavaRDD<String> result = sorted.map(new Function<Tuple2<String, String>, String>() {
        private static final long serialVersionUID = 1L;

                @Override
        public String call(Tuple2<String, String> tuple) throws IOException {
            return tuple._1() + tuple._2();
        }
    });

    result.saveAsTextFile(args[1]);
    long end_time = System.currentTimeMillis();
    long compute_time = end_time - start_time;
    System.out.println("The compute time for the Spark Sort is " + compute_time + "milliseconds");
 }
}
