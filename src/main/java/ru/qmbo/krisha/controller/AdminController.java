package ru.qmbo.krisha.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.qmbo.krisha.service.KrishaService;
import ru.qmbo.krisha.service.ParserService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {
    private final ParserService parserService;
    private final KrishaService krishaService;

    @GetMapping("/info")
    public Map<String, ?> info() {
        return Map.copyOf(new HashMap<>() {{
            putAll(parserService.getInfo());
            putAll(krishaService.getInfo());
        }});
    }

    @PostMapping("/set-page")
    public String setPage(String page) {
        return parserService.setPage(page);
    }

    @PostMapping("/set-pars")
    public String setPage(boolean pars) {
        return parserService.setPars(pars);
    }

    @PostMapping("/set-user")
    public String setUser(Long userId) {
        return krishaService.setAdmin(userId);
    }
}
