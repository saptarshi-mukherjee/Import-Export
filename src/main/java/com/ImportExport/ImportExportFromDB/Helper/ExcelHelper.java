package com.ImportExport.ImportExportFromDB.Helper;

import com.ImportExport.ImportExportFromDB.Models.Book;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public static boolean checkExcelFile(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<Book> convertToList(InputStream file_ip) {
        List<Book> list=new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file_ip);
            XSSFSheet sheet=workbook.getSheetAt(0);
            int index=0, cell_id=0;
            for(Row row : sheet) {
                cell_id=0;
                if(index++==0)
                    continue;
                Book book=new Book();
                for(Cell cell : row) {
                    switch(cell_id) {
                        case 0:
                            book.setTitle(cell.getStringCellValue());
                            break;
                        case 1:
                            book.setAuthor(cell.getStringCellValue());
                            break;
                        case 2:
                            book.setCategory(cell.getStringCellValue());
                            break;
                        case 3:
                            book.setYear((int) cell.getNumericCellValue());
                            break;
                        case 4:
                            book.setPrice(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cell_id++;
                }
                list.add(book);
                //index++;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
