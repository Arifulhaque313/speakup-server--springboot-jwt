package com.practice.speakup.services;

import com.practice.speakup.dtos.ComplainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
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

        ITextRendered rendered = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            rendered.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }
 }
