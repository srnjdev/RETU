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

            // Listado de tareas
            for (Tarea tarea : tareas) {
                // Ejemplo de texto
                Paragraph p = new Paragraph("• " + tarea.getTitulo() 
                    + " (Fecha Entrega: " + tarea.getFechaEntrega() + ")");
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
