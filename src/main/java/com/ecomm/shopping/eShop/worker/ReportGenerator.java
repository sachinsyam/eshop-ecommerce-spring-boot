package com.ecomm.shopping.eShop.worker;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.service.OrderHistoryService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Service
public class ReportGenerator {
    @Autowired
    OrderHistoryService orderHistoryService;




    public String generateOrderHistoryPdf(List<OrderHistory> orderHistories, String from, String to) throws DocumentException {
        String fileName = UUID.randomUUID().toString();

        // Define the directory to save the PDF file
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads/reports/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate the file path
        String filePath = uploadDir + fileName + ".pdf";

        Document document = new Document(PageSize.A1);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Create the title
            Paragraph title = new Paragraph("Order History Report");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            Paragraph period = new Paragraph("From "+from+" to "+to);
            period.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(period);

            document.add(new Paragraph("\n"));

            // Create the table
            PdfPTable table = new PdfPTable(7); // Adjust the number of columns as per your requirement

            // Add table headers
            table.addCell("UUID");
            table.addCell("Date");
            table.addCell("Total");
            table.addCell("Tax");
            table.addCell("Gross");
            table.addCell("Off Price");
            table.addCell("Type");

            // Add table rows
            for (OrderHistory orderHistory : orderHistories) {
                table.addCell(orderHistory.getUuid().toString());
                table.addCell(orderHistory.getCreatedAt().toString());
                table.addCell(String.valueOf(orderHistory.getTotal()));
                table.addCell(String.valueOf(orderHistory.getTax()));
                table.addCell(String.valueOf(orderHistory.getGross()));
                table.addCell(String.valueOf(orderHistory.getOff()));
                table.addCell(String.valueOf(orderHistory.getOrderType()));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return filePath;
    }



    public String generateOrderHistoryCsv(List<OrderHistory> orderHistoryList) {
        // Generate a unique file name for the CSV file
        String fileName = UUID.randomUUID().toString();

        // Define the directory to save the CSV file
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads/reports/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate the full file path
        String filePath = uploadDir + fileName + ".csv";

        try (PrintWriter writer = new PrintWriter(filePath)) {
            // Write the CSV header
            writer.println("Order ID,Date,Total,Tax,Gross,Off Price,Order Status,Order Type");

            // Iterate over the list of OrderHistory objects and write each record to the CSV file
            for (OrderHistory order : orderHistoryList) {
                writer.printf("%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%s%n",
                        order.getUuid().toString(),
                        order.getCreatedAt().toString(),
                        order.getTotal(),
                        order.getTax(),
                        order.getGross(),
                        order.getOff(),
                        order.getOrderStatus().toString(),
                        order.getOrderType().toString());
            }

            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filePath;
    }


    public String generateStockReportPdf(List<Variant> variants) {
        String fileName = UUID.randomUUID().toString();

        // Define the directory to save the PDF file
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads/reports/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate the file path
        String filePath = uploadDir + fileName + ".pdf";

        Document document = new Document(PageSize.A1);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Create the title
            Paragraph title = new Paragraph("Stock Report");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);


            document.add(new Paragraph("\n"));

            // Create the table
            PdfPTable table = new PdfPTable(8); // Adjust the number of columns as per your requirement

            // Add table headers
            table.addCell("Variant ID");
            table.addCell("Variant Name");
            table.addCell("Product ID");
            table.addCell("Product Name");
            table.addCell("Price");
            table.addCell("Selling Price");
            table.addCell("Wholesale Price");
            table.addCell("Stock");

            // Add table rows
            for (Variant variant : variants) {
                table.addCell(variant.getUuid().toString());
                table.addCell(variant.getName());
                table.addCell(variant.getProductId().getName().toString());
                table.addCell(String.valueOf(variant.getProductId().getName()));
                table.addCell(String.valueOf(variant.getPrice()));
                table.addCell(String.valueOf(variant.getSellingPrice()));
                table.addCell(String.valueOf(variant.getWholesalePrice()));
                table.addCell(String.valueOf(variant.getStock()));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        System.out.println("Stock report generated for "+variants.size()+" variants");
        return filePath;
    }

    public String generateUserReportPdf(List<UserInfo> users) {

        String fileName = UUID.randomUUID().toString();

        // Define the directory to save the PDF file
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads/reports/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate the file path
        String filePath = uploadDir + fileName + ".pdf";

        Document document = new Document(PageSize.A1);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Create the title
            Paragraph title = new Paragraph("User Report - "+ users.size()+" users");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);


            document.add(new Paragraph("\n"));

            // Create the table
            PdfPTable table = new PdfPTable(10); // Adjust the number of columns as per your requirement

            // Add table headers
            table.addCell("#");
            table.addCell("User ID");
            table.addCell("username");
            table.addCell("Name");
            table.addCell("Email");
            table.addCell("Phone");
            table.addCell("Enabled");
            table.addCell("Verified");
            table.addCell("Wallet");
            table.addCell("Orders");

            // Add table rows
            int i = 1;
            for (UserInfo user : users) {
                table.addCell(String.valueOf(i));
                table.addCell(user.getUuid().toString());
                table.addCell(user.getUsername().toString());
                table.addCell(user.getFirstName() + " " + user.getLastName());
                table.addCell(user.getEmail().toString());
                table.addCell(user.getPhone().toString());
                table.addCell(String.valueOf(user.isEnabled()));
                table.addCell(String.valueOf(user.isVerified()));
                String balance = user.getWallet() == null ? "0" :user.getWallet().getBalance().toString();
                table.addCell(balance);
                table.addCell(String.valueOf(user.getOrderHistories().size()));
                i++;
            }
            document.add(table);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        System.out.println("User report generated for "+users.size()+" users");
        return filePath;
    }

}
