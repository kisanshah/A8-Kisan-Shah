package com.example.resumebuilder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {


    private static final String TAG = "DEBUG";

    public String createFolder(String path, String name) {
        File file = new File(Environment.getExternalStorageDirectory() + path, name);
        if (file.exists()) {
            Log.d(TAG, "createFolder: Exists");
        } else {
            file.mkdir();
            file.mkdirs();
            if (file.isDirectory()) {
                Log.d(TAG, "createFolder: Created");
            } else {
                Log.d(TAG, "createFolder: Failed");
            }
        }
        String fileAbsolutePath = file.getPath();
        return fileAbsolutePath;
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
    public boolean saveBitmap(Bitmap bitmap, String path) {

        File imagePath = new File(path + "/Resume.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
            return false;
        }
    }

    public void savePdf(String path, String imgPath) throws IOException, DocumentException {
        Image image = Image.getInstance(imgPath);
        Rectangle pagesize = new Rectangle(image.getScaledWidth(), image.getScaledHeight());
        image.setAbsolutePosition(0, 0);
        Document document = new Document(pagesize);
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        document.add(image);
        document.close();
    }
}
