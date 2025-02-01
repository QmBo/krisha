package ru.qmbo.krisha.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.qmbo.krisha.model.document.Room;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;


/**
 * The type Parser service.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class ParserService {
    @Value("${app.search.page}")
    private String page;
    private boolean parse = true;
    private final KrishaService krishaService;

    /**
     * Pars page
     */
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(fixedDelay = 60000, initialDelay = 5000)
    public void parsPage() {
        if (parse) {
            try {
                this.parsPage(Jsoup.connect(this.page).get());
            } catch (IOException e) {
                log.error("Error loading page: {}", e.getMessage());
            }
        }
    }

    public Map<String, ?> getInfo() {
        return Map.of("pars", parse ? "on" : "off", "page", page);
    }

    public String setPage(String page) {
        this.page = page;
        log.info("Set page {}", page);
        return this.page;
    }

    public String setPars(boolean parse) {
        this.parse = parse;
        String parsing = this.parse ? "on" : "off";
        log.info("Parsing page: {}", parsing);
        return parsing;
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
