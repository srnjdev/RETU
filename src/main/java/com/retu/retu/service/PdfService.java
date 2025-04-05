package com.retu.retu.service;

import com.retu.retu.entity.Tarea;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generarPdfConTareas(List<Tarea> tareas) {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Lista de Tareas", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            document.add(new Paragraph(" ")); // Espacio

            // Listar cada tarea
            for (Tarea t : tareas) {
                Paragraph p = new Paragraph("• " + t.getTitulo() 
                    + " (Entrega: " + t.getFechaEntrega() + ") "
                    + "Categoría: " + t.getCategoria());
                document.add(p);
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
