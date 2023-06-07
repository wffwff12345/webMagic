package com.example.webmagic.task;

import com.example.webmagic.entity.RegionEntity;
import com.example.webmagic.helper.RegionPipeLineHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RegionJob implements PageProcessor {
    @Resource
    RegionPipeLineHelper pipeLineHelper;

    private final Site site = Site.me().setTimeOut(10000).setRetryTimes(10).setCycleRetryTimes(10).setSleepTime(1000).setRetrySleepTime(3000).setCharset("UTF-8");

    @Override
    public void process(Page page) {
        Elements elements = page.getHtml().getDocument().select("div table tbody tr[height=19] ");
        page.putField("regions", elements);
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay = 4000, fixedDelay = 1000000 * 1000)
    public void regionScheduler() {
        Spider.create(new RegionJob())
                .addUrl("https://www.mca.gov.cn/mzsj/xzqh/2022/202201xzqh.html")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(50000)))
                .thread(10)
                .addPipeline(pipeLineHelper)
                .run();
    }

}
