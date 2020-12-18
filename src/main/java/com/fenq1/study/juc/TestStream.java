package com.fenq1.study.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestStream {

    public static void main(String[] args) {
        List<String> strList1 = new ArrayList<>();
        for (int i = 0; i < 10000000; i++)
            strList1.add(i + "");
        log.info("开始测试！");
        new Thread(new StreamThread(strList1)).start();
        new Thread(new ForeachThread(strList1)).start();
    }
}

@Slf4j
class ForeachThread implements Runnable {

    private List<String> strList;

    public ForeachThread(List<String> stringList) {
        this.strList = stringList;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (String str : strList) {
            str.toLowerCase();
        }
        long finish = System.currentTimeMillis();
        log.info("forech: " + (finish - start) + "ms");
    }
}

@Slf4j
class StreamThread implements Runnable {

    private List<String> strList;

    public StreamThread(List<String> stringList) {
        this.strList = stringList;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        strList.stream().forEach(str -> {
            str.toLowerCase();
        });
        long finish = System.currentTimeMillis();
        log.info("stream: " + (finish - start) + "ms");
    }
}