package com.taekyeong.tkgram.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64ToMultipartFile implements MultipartFile {
    private final byte[] imgContent;

    public Base64ToMultipartFile(byte[] imgContent)
    {
        this.imgContent = imgContent;
    }

    @Override
    public String getName()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getOriginalFilename()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getContentType()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize()
    {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException
    {
        new FileOutputStream(dest).write(imgContent);
    }
}
