package com.yupi.liaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PDFGenerationToolTest {
    @Test
    public void PDFGenerationToolTest(){
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        pdfGenerationTool.generatePDF("Hello World", "output.pdf");
        assertNotNull(pdfGenerationTool);

    }
}