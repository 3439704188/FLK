package org.atguigu.FLK.Gen;


import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


import java.util.UUID;

public class DataGen {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        streamExecutionEnvironment.setParallelism(1);
        DataGeneratorSource<String> dataGeneratorSource = new DataGeneratorSource<>(
                new GeneratorFunction<Long, String>() {
                    @Override
                    public String map(Long aLong) throws Exception {
                        return UUID.randomUUID().toString() + "->" + aLong;
                    }
                },
                10,
                RateLimiterStrategy.perSecond(1),
                Types.STRING
        );
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.fromSource(dataGeneratorSource, WatermarkStrategy.noWatermarks(), "data_gen");

        dataStreamSource.print();

        streamExecutionEnvironment.execute();
    }
}
