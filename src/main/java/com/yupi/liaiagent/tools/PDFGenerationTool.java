package com.yupi.liaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;


public class PDFGenerationTool {
    @Tool(description = "Generate a PDF file from content")
    public String generatePDF(@ToolParam(description = "Content to generate PDF from") String content, @ToolParam(description = "Output file path") String outputFilePath) {
        // Implement logic to generate PDF from content
       String fileDir=FileConstant.FILE_SAVE_DIR+"/pdf";
       String filePath=fileDir+"/"+outputFilePath;
        FileUtil.mkdir(fileDir);
       try(PdfWriter writer=new PdfWriter(filePath);
         PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument)) {

           // 使用内置中文字体
           PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
           document.setFont(font);

           document.add(new Paragraph(content));
       } catch (IOException e) {
           return "Error generating PDF";
       }

        return "PDF generated successfully";
    }
}
