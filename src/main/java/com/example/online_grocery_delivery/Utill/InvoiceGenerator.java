package com.example.online_grocery_delivery.Utill;

import com.example.online_grocery_delivery.Dto.CartDto;
import com.example.online_grocery_delivery.Dto.OrderDto;
import com.example.online_grocery_delivery.Dto.ResponseDto;
import com.example.online_grocery_delivery.Entity.Cart;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InvoiceGenerator {
    public static byte[] generateInvoice(ResponseDto<OrderDto> orders) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            OrderDto order = orders.getData(); // ✅ Extract actual OrderDto

            if (order == null) return null;

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("INVOICE"))
                    .setBold()
                    .setFontSize(20);
            document.add(new Paragraph("Order Id :" +order.getId()));
            document.add(new Paragraph("Order Date"+order.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            document.add(new Paragraph("Customer :" + order.getCustomerName()));

            document.add(new Paragraph("\nItems:\n"));

            for (CartDto item : order.getItems()){
                document.add(new Paragraph(  "-Qty:" + item.getQuantity() + " -ProductId : " + item.getProductId()));
            }
            document.add(new Paragraph("\nTotal Amount : " + order.getTotalPrice()));
            document.add(new Paragraph("Thank you for your purchase!"));

            document.close();
            return  baos.toByteArray();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
