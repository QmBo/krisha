package ru.qmbo.krisha.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.qmbo.krisha.model.document.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

}
