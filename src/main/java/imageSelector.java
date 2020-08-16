
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class imageSelector {
    static Thread sent;
    static Thread receive;
    static Socket socket;


    public static void main(String args[]) throws IOException, InterruptedException {
        wakeUpPython();
        getImageCoordinats("automated.png");

    }

    public static String[] getImgCoordinats(String img) throws IOException, InterruptedException {
        String [] coordinats=null;
        wakeUpPython(); // opencv kütüphanesini içeren py dosyasını ayaga kaldırıyoruz.
        // sayfada istedigimiz kısımlar hemen yüklenmeyebilir bu kısımda 10 kere  resmi kontrol ediyoruz
        for(int i=0;i<10;i++){
            File screenshot = ((TakesScreenshot)main.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("Screenshot.png"));
            coordinats=getImageCoordinats(img);
            if(coordinats!=null);{
                return coordinats;
            }
        }
        return null;
    }

    public static String[] getImageCoordinats(String imageName) throws IOException, InterruptedException {

        try {
            socket = new Socket("localhost",1994);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF(imageName); // resim dosyasının ismini buradan py'ye gönderiyoruz.
        String in=null;
        try {
            BufferedReader stdIn =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                in = stdIn.readLine(); // gelen yanıtı okuyoruz
                System.out.println(in);
                out.print("Try"+"\r\n");
                out.flush();
                if(in!=null){
                   break;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(in.equals("Null")){
            //py dosyası %99 un üstünde bir eşleşme bulamassa Null texti gönderiyor
            return null;
        } else{
            return makeItArray(in);
        }

    }
    public static void wakeUpPython() throws IOException, InterruptedException {
        //Terminal kullanılarak py ayaga kaldırılıyor.

            String[] args2 = new String[] {"/bin/bash", "-c", "python imageMatcher.py ", "with", "args"};
            Process proc = new ProcessBuilder(args2).start();
            Thread.sleep(2000);

            // Windows terminal komutu b uraya gelicek
    }

    public static String[] makeItArray(String in){
        //gelen String kordinatları diziye çeviriyoruz.
        in=in.replaceAll("\\(","");
        in=in.replaceAll("\\)","");
        in=in.replaceAll(" ","");
        String [] coordinats = in.split(",");
        return coordinats;
    }

}