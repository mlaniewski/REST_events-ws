package pl.edu.pb.wi.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.model.db.Event;
import pl.edu.pb.wi.service.PDFCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PDFCreatorImpl implements PDFCreator {
    private static final String ROOT_DIR = "generated-pdfs";

    @Override
    public File create(java.util.List<Event> eventList) {
        final Date now = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyy");
        final String nowString = sdf.format(now);
        final String fileName = String.format("Events_%s.pdf", nowString);
        final Path location = Paths.get(ROOT_DIR, fileName);

        final Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(location.toString()));
            document.open();

            document.add(new Paragraph("Events for " + nowString));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(7);
            table.setTotalWidth(1200);
            table.setWidths(new int[]{ 7, 4, 4, 3, 3, 3, 7 });
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            appendTableHeader(table);

            for (Event event : eventList) {
                appendEvent(table, event);
            }
            document.add(table);

            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return new File(location.toString());
    }

    private void appendTableHeader(PdfPTable table) {
        PdfPCell cell;
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Date"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Name"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Type"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Week"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Month"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Year"));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Description"));
        table.addCell(cell);
    }

    private void appendEvent(PdfPTable table, Event event) {
        PdfPCell cell;
        cell = new PdfPCell();
        event.setDate(new Date(event.getDate().getTime() - 2*60*60*1000));
        cell.addElement(new Paragraph(event.getDate().toString()));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(event.getName()));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(event.getType().value()));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(String.valueOf(event.getWeek())));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(String.valueOf(event.getMonth())));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(String.valueOf(event.getYear())));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Paragraph(event.getDescription()));
        table.addCell(cell);
    }
}
