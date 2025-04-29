package com.practice.speakup.services;

import com.practice.speakup.dtos.ComplainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGenerateService {
    private  final TemplateEngine templateEngine;

    @Autowired
    public PDFGenerateService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public  byte[] generatePdf(ComplainResponse complainResponse) throws Exception{
        Context context = new Context();
        context.setVariable("complain", complainResponse);

        String htmlContent = templateEngine.process("complain_template", context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }
 }
