package com.example.competition_bot.bot;

import com.example.competition_bot.entity.UserEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelGenerator {
    public static void generateExcel(List<UserEntity> users, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"User ID", "Name", "Phone", "First Name", "Username", "Created Date"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (UserEntity user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getChatId());
                row.createCell(1).setCellValue(user.getPhoneNumber());
                row.createCell(2).setCellValue(user.getUsername());
                row.createCell(3).setCellValue(user.getFirstName());
                row.createCell(4).setCellValue(user.getNameSurname());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel file has been generated successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
