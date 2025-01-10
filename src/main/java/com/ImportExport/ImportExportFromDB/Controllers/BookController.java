package com.ImportExport.ImportExportFromDB.Controllers;


import com.ImportExport.ImportExportFromDB.Models.Book;
import com.ImportExport.ImportExportFromDB.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin("*")
public class BookController {

    @Autowired
    BookService book_service;


    @PostMapping("/books/post/import")
    public String importFile(@RequestParam("file") MultipartFile file) {
        if(file!=null) {
            book_service.importBooks(file);
            return "Successful!!";
        }
        return "unsuccessful";
    }

    @GetMapping("/books/get/all")
    public List<Book> findAllBooks() {
        List<Book> books=book_service.getAllBooks();
        return books;
    }

}
