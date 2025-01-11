package com.ImportExport.ImportExportFromDB.Service;


import com.ImportExport.ImportExportFromDB.Helper.ExcelHelper;
import com.ImportExport.ImportExportFromDB.Models.Book;
import com.ImportExport.ImportExportFromDB.Repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepo book_repo;

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
}
