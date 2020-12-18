package com.fenq1.study.redis;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestSeckill {

    @Autowired
    public TestSeckillController testSeckillController;

    private static int totalStock = 0;

    private static int cnt = 1;

    @Test
    public void contextLoads() {
    }

    //引入 ContiPerf 进行性能测试
    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

   /* @Test
    //10个线程 执行10次
    @PerfTest(invocations = 20, threads = 20)
    public void buy_sql() {
        int stock = 7;
        totalStock += stock;
        String response = testSeckillController.buy_sql("f05edda1-2e85-4451-9997-751bde1e0054", stock);
        log.info((cnt++) + ": 抢了 " + stock + " 瓶台子！" + (response.equals("yes") ? "抢购成功！" : "抢购失败！"));
        log.info(totalStock + "");
    }*/

    @Test
    //10个线程 执行10次
    @PerfTest(invocations = 2000, threads = 2000)
    public void buy_redis() {
//        int stock = RandomUtil.randomInt(1, 10);
        int stock = 7;
        String response = testSeckillController.buy_redis("f05edda1-2e85-4451-9997-751bde1e0054", stock);
        log.info((cnt++) + ": 抢了 " + stock + " 瓶台子！" + (response.equals("yes") ? "抢购成功！" : "抢购失败！"));
    }

   /* @Test
    //10个线程 执行10次
    @PerfTest(invocations = 200, threads = 200)
    public void add_redis() {
//        int stock = RandomUtil.randomInt(1, 10);
        int stock = 1;
        String response = testSeckillController.add_redis("f05edda1-2e85-4451-9997-751bde1e0054", stock);
//        log.info((cnt++) + ": s了 " + stock + " 瓶台子！" + (response.equals("yes") ? "抢购成功！" : "抢购失败！"));
    }*/
}
