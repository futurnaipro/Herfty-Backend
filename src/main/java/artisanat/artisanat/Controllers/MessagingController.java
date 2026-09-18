package artisanat.artisanat.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import artisanat.artisanat.model.DTOs.MessageDTO;
import artisanat.artisanat.model.DTOs.MessageSignaledDTO;
import artisanat.artisanat.model.Entities.AppUser;
import artisanat.artisanat.model.Entities.Message;
import artisanat.artisanat.model.Repositories.MessageRepository;
import artisanat.artisanat.model.Services.MessageService;
import artisanat.artisanat.model.Services.UserService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/user")
public class MessagingController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired 
    MessageRepository messageRepository;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        Message message=new Message();
        message.setSender(userService.loadUserById(messageDTO.getSenderId()));
        message.setReceiver(userService.loadUserById(messageDTO.getReceiverId()));
        message.setContent(messageDTO.getContent());
        message.setCreatedAt(LocalDateTime.now());

        return messageService.sendMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getMessagesByUserId(@RequestParam String username) {
        AppUser user= userService.loadUserByUsername(username);
        Long id=user.getId();
        List<Message> messages=messageService.getMessagesBySenderIdOrReceiverId(id);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(message -> {
                    MessageDTO messageDTO = new MessageDTO();
                    messageDTO.setId(message.getId());
                    messageDTO.setSenderId(message.getSender().getId());
                    messageDTO.setReceiverId(message.getReceiver().getId());
                    messageDTO.setContent(message.getContent());
                    messageDTO.setCreatedAt(message.getCreatedAt());
                    return messageDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(messageDTOs);
    }

    @PostMapping("/signal/{id}")
    public ResponseEntity<String> signalerMessage(@PathVariable Long id ,@RequestBody String signalCause){
        Message message= messageService.loadMessageById(id);
        message.setSignaled(true);
        message.setSignalCause(signalCause);
        messageRepository.save(message);
        return ResponseEntity.ok("Message signaled sucessufuly");
    }

}
