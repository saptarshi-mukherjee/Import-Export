package com.ImportExport.ImportExportFromDB.Service;


import com.ImportExport.ImportExportFromDB.Helper.ExcelHelper;
import com.ImportExport.ImportExportFromDB.Models.Book;
import com.ImportExport.ImportExportFromDB.Repositories.BookRepo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    BookRepo book_repo;
    ChatClient chat_client;

    public BookServiceImpl(BookRepo book_repo, ChatClient.Builder builder) {
        this.book_repo=book_repo;
        this.chat_client=builder
                .defaultAdvisors(new PromptChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }


    @Override
    public void importBooks(MultipartFile file) {
        try {
            if(ExcelHelper.checkExcelFile(file)) {
                List<Book> books = ExcelHelper.convertToList(file.getInputStream());
                for (Book book : books) {
                    //System.out.println(book.getTitle());
                    book_repo.save(book);
                }
            }
            else
                throw new Exception("Excel file not present");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books=book_repo.fetchAllBooks();
        return books;
    }

    @Override
    public String chatBot(String msg) {
        List<Book> books=getAllBooks();
        String msg1="You are a librarian. The books in your library are given below\n";
        for(Book book : books) {
            msg1+=book.getTitle()+"\t"+book.getAuthor()+"\t"+book.getCategory()+"\n";
        }
        String msg2=msg;
        SystemMessage system_msg=new SystemMessage(msg1);
        UserMessage user_msg=new UserMessage(msg2);
        Prompt prompt=new Prompt(List.of(system_msg,user_msg));
        ChatResponse response=chat_client.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }
}
