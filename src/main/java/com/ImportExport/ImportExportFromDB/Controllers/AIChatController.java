package com.ImportExport.ImportExportFromDB.Controllers;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class AIChatController {

    private ChatClient chat_client;

    public AIChatController(ChatClient.Builder builder) {
        this.chat_client=builder.build();
    }


    @GetMapping("/popular")
    public String popularBooks() {
        String msg1="who do you think are the most influential authors of 21st century?";
        String msg2="Restrict your search to authors who primarily write in english";
        UserMessage user_msg=new UserMessage(msg1);
        SystemMessage system_msg=new SystemMessage(msg2);
        Prompt prompt=new Prompt(List.of(system_msg,user_msg));
        ChatResponse response=chat_client.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();

    }

    @GetMapping("/author/general")
    public String getAuthorInfo(@RequestParam("name") String name) {
        String msg1="Give me some brief information about "+name;
        String msg2="Restrict your search to only authors. Display proper message if the name is not that of an author";
        UserMessage user_msg=new UserMessage(msg1);
        SystemMessage system_msg=new SystemMessage(msg2);
        Prompt prompt=new Prompt(List.of(system_msg,user_msg));
        ChatResponse response=chat_client.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }
}
