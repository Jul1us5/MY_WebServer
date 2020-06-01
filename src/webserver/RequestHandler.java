package webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class RequestHandler extends Thread {

    private Socket sc;

    public RequestHandler(Socket sc) {
        if (sc == null) {
            throw new NullPointerException("Socket can not be null");
        }
        this.sc = sc;
    }

    @Override
    public void run() {
        
        InputStream is = sc.getInputStream();
        Reader r = new InputStreamReader(is, "UTF-8");

        BufferedReader br = new BufferedReader(r);
        OutputStream os = sc.getOutputStream();
        Writer w = new OutputStreamWriter(os, "UTF-8");
        BufferedWriter bw = new BufferedWriter(w);
        String line = br.readLine();
        if (line != null && !"".equals(line)) {
            System.out.println(line);
            String[] parts = line.split(" ");
            if (parts.length >= 3) {
                String fileName = parts[1];
                System.out.println(fileName);
                if (fileName.equals("/quit")) {
                    bw.write("HTTP/1.1 200 OK");
                    bw.newLine();
                    bw.write("Content-Type: text/html");
                    bw.newLine();
                    bw.newLine();
                    bw.write("<html>");
                    bw.write("<body>");
                    bw.write("<h1>Server is shutting down...</h1>");
                    bw.write("</body>");
                    bw.write("</html>");
                    MY_WebServer.work = false;
                } else {
                    File f = new File("/Users/evuncik/Desktop/JAVA/MY_WebServer/src/www/" + fileName);
                    if (f.exists()) {
                        if (f.isDirectory()) {
                            bw.write("HTTP/1.1 200 OK");
                            bw.newLine();
                            bw.write("Content-Type: text/html");
                            bw.newLine();
                            bw.newLine();
                            bw.write("<html>");
                            bw.write("<body>");
                            bw.write("<a href=\"..\">..</a>");
                            bw.write("<br>");
                            for (File file : f.listFiles()) {
                                bw.write("<a href=\"" + file.getName()
                                        + ((file.isDirectory()) ? "/" : "")
                                        + "\">" + file.getName() + "</a>");
                                bw.write("<br>");
                            }
                            bw.write("</body>");
                            bw.write("</html>");
                        } else {
                            bw.write("HTTP/1.1 200 OK");
                            bw.newLine();
                            if (fileName.endsWith(".html")) {
                                bw.write("Content-Type: text/html");
                                bw.newLine();
                            } else if (fileName.endsWith(".js")) {
                                bw.write("Content-Type: text/javascript;charset=UTF-8");
                                bw.newLine();
                            } else if (fileName.endsWith(".css")) {
                                bw.write("Content-Type: text/css");
                                bw.newLine();
                            }
                            bw.newLine();
                            try (
                                    FileInputStream fis = new FileInputStream(f);
                                    Reader fr = new InputStreamReader(fis, "UTF-8");
                                    BufferedReader fbr = new BufferedReader(fr);) {
                                String fileLine;
                                while ((fileLine = fbr.readLine()) != null) {
                                    bw.write(fileLine);
                                    bw.newLine();
                                }
                            }
                        }
                    } else {
                        write404(bw);
                    }
                }
                bw.flush();
            }
        }
    }

    public static void write404(BufferedWriter bw)
            throws IOException {
        bw.write("HTTP/1.1 404 File not found");
        bw.newLine();
        bw.newLine();
    }
}
