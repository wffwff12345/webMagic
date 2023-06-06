package com.example.webmagic.task;

import com.example.webmagic.entity.RegionEntity;
import com.example.webmagic.service.RegionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
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
    private RegionService regionService ;

    private final Site site = Site.me().setTimeOut(10000).setRetryTimes(10).setCycleRetryTimes(10).setSleepTime(1000).setRetrySleepTime(3000).setCharset("UTF-8");

    @Override
    public void process(Page page) {
        Elements elements = page.getHtml().getDocument().select("div table tbody tr[height=19] ");
        page.putField("content", elements.toString());
        elements.forEach(element -> {
            // 北京 110000, 天津 120000, 上海 310000, 重庆 500000
            List<String> list = Arrays.asList("110000", "120000", "310000", "500000");
            String code = element.select("tr td:nth-child(2) ").text();
            String name = element.select("tr td:nth-child(3) ").text();
            if (list.contains(code)) {
                RegionEntity entity = new RegionEntity();
                entity.setCode(code);
                entity.setParent_code("0");
                entity.setName(name);
                entity.setType("2");
                entity.setCreateTime(new Date());
                regionService.save(entity);
                System.out.println("test");
            }
            System.out.println("code: " + code + " name: " + name);
        });

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
                .addPipeline(new FilePipeline("D:\\webMagic"))
                .run();
    }

}
