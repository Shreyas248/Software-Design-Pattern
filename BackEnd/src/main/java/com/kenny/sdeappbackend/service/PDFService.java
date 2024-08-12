package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.model.Scheduler;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PDFService {

    private static final float MARGIN = 50;
    private static final float TABLE_TOP = 700;
    private static final float ROW_HEIGHT = 20;
    private static final float[] COL_WIDTHS = {50, 150, 100, 120, 120};
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ByteArrayInputStream generateSchedulePDF(List<Scheduler> schedules) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = addPage(document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            addHeader(contentStream);
            addTitle(contentStream);

            float yPosition = TABLE_TOP;
            yPosition = addTableHeader(contentStream, yPosition);

            for (Scheduler schedule : schedules) {
                if (yPosition < MARGIN + ROW_HEIGHT) {
                    contentStream.close();
                    page = addPage(document);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = TABLE_TOP;
                    yPosition = addTableHeader(contentStream, yPosition);
                }
                yPosition = addTableRow(contentStream, yPosition, schedule);
            }

            contentStream.close();
            document.save(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PDPage addPage(PDDocument document) {
        PDPage page = new PDPage();
        document.addPage(page);
        return page;
    }

    private void addHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.newLineAtOffset(470, 770);
        contentStream.showText("Generated on: " + java.time.LocalDate.now());
        contentStream.endText();
    }

    private void addTitle(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.newLineAtOffset(MARGIN, TABLE_TOP + 30);
        contentStream.showText("Schedule Report");
        contentStream.endText();
    }

    private float addTableHeader(PDPageContentStream contentStream, float yPosition) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float xPosition = MARGIN;

        contentStream.beginText();
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText("ID");
        xPosition += COL_WIDTHS[0];

        contentStream.newLineAtOffset(COL_WIDTHS[0], 0);
        contentStream.showText("Title");
        xPosition += COL_WIDTHS[1];

        contentStream.newLineAtOffset(COL_WIDTHS[1], 0);
        contentStream.showText("User");
        xPosition += COL_WIDTHS[2];

        contentStream.newLineAtOffset(COL_WIDTHS[2], 0);
        contentStream.showText("Start Time");
        xPosition += COL_WIDTHS[3];

        contentStream.newLineAtOffset(COL_WIDTHS[3], 0);
        contentStream.showText("End Time");
        contentStream.endText();

        contentStream.moveTo(MARGIN, yPosition - 5);
        contentStream.lineTo(MARGIN + COL_WIDTHS[0] + COL_WIDTHS[1] + COL_WIDTHS[2] + COL_WIDTHS[3] + COL_WIDTHS[4], yPosition - 5);
        contentStream.stroke();

        return yPosition - ROW_HEIGHT;
    }

    private float addTableRow(PDPageContentStream contentStream, float yPosition, Scheduler schedule) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        float xPosition = MARGIN;

        contentStream.beginText();
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(String.valueOf(schedule.getId()));
        xPosition += COL_WIDTHS[0];

        contentStream.newLineAtOffset(COL_WIDTHS[0], 0);
        contentStream.showText(truncateText(schedule.getTitle(), 20));
        xPosition += COL_WIDTHS[1];

        contentStream.newLineAtOffset(COL_WIDTHS[1], 0);
        contentStream.showText(schedule.getUser().getName());
        xPosition += COL_WIDTHS[2];

        contentStream.newLineAtOffset(COL_WIDTHS[2], 0);
        contentStream.showText(schedule.getStartTime().format(DATE_FORMATTER));
        xPosition += COL_WIDTHS[3];

        contentStream.newLineAtOffset(COL_WIDTHS[3], 0);
        contentStream.showText(schedule.getEndTime().format(DATE_FORMATTER));
        contentStream.endText();

        return yPosition - ROW_HEIGHT;
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}