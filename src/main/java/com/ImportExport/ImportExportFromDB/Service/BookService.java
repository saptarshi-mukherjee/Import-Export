package com.ImportExport.ImportExportFromDB.Service;

import com.ImportExport.ImportExportFromDB.Models.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public void importBooks(MultipartFile file);
    public List<Book> getAllBooks();
}
