package org.atguigu.FLK.Gen;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.atguigu.FLK.POJO.Event;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClickEventSimulator implements SourceFunction<Event> {

    private static final AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void run(SourceContext<Event> sourceContext) {

        Random random = new Random();
        String[] users = {"Songsong", "Bingbing"};
        String[] urls = {"./home", "./cart", "./fav", "./prod?id=1", "./prod?id=2"};

        while (running.get()) {
            sourceContext.collect(new Event(
                    users[random.nextInt(users.length)],
                    urls[random.nextInt(urls.length)],
                    Calendar.getInstance().getTimeInMillis()
            ));
        }
    }

    @Override
    public void cancel() {
        running.set(false);
    }
}
