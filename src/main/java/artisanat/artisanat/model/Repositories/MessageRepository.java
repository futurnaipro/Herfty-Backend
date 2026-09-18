package artisanat.artisanat.model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    Message findMessageById(Long id);
    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(Long id,Long id2);
    List<Message> findBySignaledTrue();
}
