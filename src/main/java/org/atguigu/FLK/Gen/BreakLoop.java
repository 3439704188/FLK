package org.atguigu.FLK.Gen;

import java.util.concurrent.atomic.AtomicBoolean;

public class BreakLoop {
    public static void main(String[] args) throws InterruptedException {
        final AtomicBoolean isa= new AtomicBoolean(true);
        Thread div = new Thread(() ->{while(isa.get()){System.out.println("running");}});
        div.start();
        Thread.sleep(5000);
        isa.set(false);
    }
}
