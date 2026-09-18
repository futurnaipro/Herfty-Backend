package artisanat.artisanat.model.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.Message;
import artisanat.artisanat.model.Repositories.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message loadMessageById(Long id){
        return messageRepository.findMessageById(id);
    }
    public Message sendMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBySenderIdOrReceiverId(Long id){
        return messageRepository.findBySenderIdOrReceiverIdOrderByCreatedAtDesc(id,id);
    }

    public List<Message> getMessagesBySignaled(){
        return messageRepository.findBySignaledTrue();
    }
}
