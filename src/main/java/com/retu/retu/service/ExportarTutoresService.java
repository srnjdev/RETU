package com.retu.retu.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.TareaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportarTutoresService {

    private final TareaRepository tareaRepository;

    public ExportarTutoresService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public void exportarExcel(List<Tutor> tutores, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tutores");

        // Crear el header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nombre");
        headerRow.createCell(1).setCellValue("Correo institucional");
        headerRow.createCell(2).setCellValue("Materia");
        headerRow.createCell(3).setCellValue("Número de tareas asignadas");

        // Llenar datos
        int rowIdx = 1;
        for (Tutor tutor : tutores) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(tutor.getNombre());
            row.createCell(1).setCellValue(tutor.getCorreo());
            row.createCell(2).setCellValue(tutor.getMateria());
            long tareasAsignadas = tareaRepository.countByTutor(tutor);
            row.createCell(3).setCellValue(tareasAsignadas);
        }

        for (int i = 0; i < 4; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        // Escribir en el response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public byte[] generarPdfConTutores(List<Tutor> tutores) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();
            com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Lista de Tutores", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[] { 4, 6, 4, 4 });

            com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            table.addCell(new PdfPCell(new Phrase("Nombre", headFont)));
            table.addCell(new PdfPCell(new Phrase("Correo institucional", headFont)));
            table.addCell(new PdfPCell(new Phrase("Materia", headFont)));
            table.addCell(new PdfPCell(new Phrase("N° tareas asignadas", headFont)));

            for (Tutor tutor : tutores) {
                table.addCell(tutor.getNombre());
                table.addCell(tutor.getCorreo());
                table.addCell(tutor.getMateria());
                long numTareas = tareaRepository.countByTutor(tutor);
                table.addCell(String.valueOf(numTareas));
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
