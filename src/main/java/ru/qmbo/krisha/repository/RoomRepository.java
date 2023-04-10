package ru.qmbo.krisha.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.qmbo.krisha.model.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

}
