package com.fenq1.study.redis;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("redis")
public class TestSeckillController {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisUtil redisUtil;

    private final static String key = "taizi1";

    @PostConstruct
    private void init() {
        redisUtil.set(key + ":f05edda1-2e85-4451-9997-751bde1e0054", 2000, -1);
        if (0 < goodsMapper.selectCount(new LambdaQueryWrapper<>()))
            return;
        Goods goods = new Goods();
        goods.setId(IdUtil.fastUUID());
        goods.setCreateTime(new Date());
        goods.setName("台子");
        goods.setStock(100);
        goodsMapper.insert(goods);

    }

    @GetMapping("buy_sql")
    public String buy_sql(@RequestParam String goodsId, @RequestParam Integer number) {
        try {
            Goods goods = goodsMapper.selectById(goodsId);
            if (goods.getStock() <= 0)
                return "no";
            goods.setStock(goods.getStock() - number);
            goodsMapper.updateById(goods);
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            return "no";
        }
    }

    @GetMapping("buy_redis")
    public String buy_redis(@RequestParam String goodsId, @RequestParam Integer number) {
        try {
            String key = this.key + ":" + goodsId;
            Integer stock = (Integer) redisUtil.get(key);
            log.info("库存:" + stock);
            if (stock <= 0 || stock < number) {
                return "no";
            } else {
                for (int i = 0; i < number; i++)
                    redisUtil.decr(key, 1);
                return "yes";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "no";
        }
    }

    @GetMapping("buy_redis1")
    public String buy_redis1(@RequestParam String goodsId, @RequestParam Integer number) {
        try {
            String key = this.key + ":" + goodsId;
            redisUtil.watch(key);
            Integer stock = (Integer) redisUtil.get(key);
            log.info("库存:" + stock);
            if (stock <= 0 || stock < number) {
                redisUtil.unwatch();
                return "no";
            } else {
                /*redisUtil.multi();
                for (int i = 0; i < number; i++)
                    redisUtil.decr(key, 1);
                redisUtil.exec();*/
                SessionCallback sessionCallback = new SessionCallback() {
                    @Override
                    public Object execute(RedisOperations redisOperations) throws DataAccessException {
                        redisOperations.multi();
                        for (int i = 0; i < number; i++)
                            redisOperations.opsForValue().decrement(key, 1);
                        return redisOperations.exec();
                    }
                };
                redisUtil.execute(sessionCallback);
                return "yes";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redisUtil.discard();
            redisUtil.unwatch();
            return "no";
        }
    }

    @GetMapping("add_redis")
    public String add_redis(@RequestParam String goodsId, @RequestParam Integer number) {
        try {
            String key = this.key + ":" + goodsId;
            redisUtil.watch(key);
            redisUtil.multi();
            redisUtil.incr(key, number);
            redisUtil.exec();
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
            return "no";
        }
    }

}
