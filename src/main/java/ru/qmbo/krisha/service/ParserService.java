package ru.qmbo.krisha.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.qmbo.krisha.model.Room;

import java.io.IOException;
import java.util.LinkedList;


/**
 * The type Parser service.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class ParserService {
    @Value("${app.search.page}")
    private String page;
    private final KrishaService krishaService;

    /**
     * Gets page.
     */
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(fixedDelay = 60000, initialDelay = 5000)
    public void getPage() {
        try {
            this.parsPage(Jsoup.connect(this.page).get());
        } catch (IOException e) {
            log.error("Error loading page: {}", e.getMessage());
        }
    }

    private void parsPage(Document document) {
        final LinkedList<Room> list = new LinkedList<>();
        final Elements elementsByAttribute = document.body()
                .getElementsByClass("a-list")
                .get(0)
                .getElementsByAttribute("data-id");
        elementsByAttribute.forEach(element -> {
            final String[] a = element.getElementsByTag("a").get(1).toString().split("\"");
            String link = "https://krisha.kz" + a[1];
            log.debug("Link: {}", link);
            list.add(new Room().setLink(link));
        });
        if (!list.isEmpty()) {
            this.krishaService.findNewAndSend(list);
        }
    }
}
