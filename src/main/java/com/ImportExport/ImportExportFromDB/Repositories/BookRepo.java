package com.ImportExport.ImportExportFromDB.Repositories;


import com.ImportExport.ImportExportFromDB.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {

    @Query(value = "select * from books", nativeQuery = true)
    public List<Book> fetchAllBooks();

}
