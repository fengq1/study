package com.fenq1.study.redis;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("redis")
public class TestController {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping()
    public ResponseEntity<Article> get(@RequestParam String id) throws InterruptedException {
        Article article = (Article) redisUtil.get(id);
        if (null == article)
            article = articleMapper.selectById(id);
        log.info("1");
        Thread.sleep(1000);
        return new ResponseEntity(article, HttpStatus.ACCEPTED);
    }

    @PostConstruct
    private void init() {
        if (0 < articleMapper.selectCount(new LambdaQueryWrapper<>()))
            return;
        Article article = new Article();
        article.setId(IdUtil.fastUUID());
        article.setAuthor("ni ye fengq1");
        article.setTitle("sha bi da bian mei you mama");
        article.setContent(article.getTitle());
        article.setCreateTime(new Date());
        articleMapper.insert(article);
        redisUtil.set(article.getId(), article, 100);
    }
}
