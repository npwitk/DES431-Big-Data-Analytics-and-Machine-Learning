package s6622772422;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TaxiMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    // Column index of the ID in taxi.csv (0 = first column). Change after checking head -3 taxi.csv
    private static final int ID_COLUMN = 0;

    private final Text outputKey = new Text();
    private final IntWritable one = new IntWritable(1);

    @Override
    public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException {
        String line = value.toString().trim();
        if (line.isEmpty()) {
            return;
        }
        String[] fields = line.split(",");
        if (fields.length <= ID_COLUMN) {
            return;
        }
        String id = fields[ID_COLUMN].trim();
        if (id.isEmpty()) {
            return;
        }
        outputKey.set(id);
        con.write(outputKey, one);
    }
}