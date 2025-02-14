package ru.qmbo.krisha.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.qmbo.krisha.model.dto.Message;
import ru.qmbo.krisha.model.document.Room;
import ru.qmbo.krisha.repository.RoomRepository;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
@Log4j2
@RequiredArgsConstructor
public class KrishaService {

    private final KafkaService kafkaService;
    private final RoomRepository roomRepository;
    @Value("${app.telegram.admin}")
    private Long admin;

    public String setAdmin(Long userId) {
        this.admin = userId;
        String adminId = Long.toString(admin);
        log.info("Admin id set to " + adminId);
        return adminId;
    }

    public Map<String,?> getInfo() {
        return Map.of("admin", Long.toString(admin));
    }

    public void findNewAndSend(List<Room> rooms) {
        rooms.forEach(room ->
                this.roomRepository.findById(room.getLink())
                        .ifPresentOrElse(
                                room1 -> {
                                },
                                () -> this.saveAndSend(room)
                        )
        );
    }

    private void saveAndSend(Room room) {
        this.roomRepository.save(room);
        this.kafkaService.sendMessage(
                new Message()
                        .setMessage(format("Новое объявление: %s", room.getLink()))
                        .setChatId(this.admin)
        );
    }

}
