package ru.qmbo.krisha.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.qmbo.krisha.model.document.User;


/**
 * RateRepository
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 16.12.2022
 */
public interface UserRepository extends MongoRepository<User, Long> {

}
