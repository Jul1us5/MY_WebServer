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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Julius
 */
public class MY_WebServer {

    public static void main(String[] args)
            throws IOException {
        try (ServerSocket sc = new ServerSocket(9000);) {
            boolean work = true;
            while (work) {
                try (Socket socket = sc.accept();) {
                    InputStream is = socket.getInputStream();
                    Reader r = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(r);
                    OutputStream os = socket.getOutputStream();
                    Writer w = new OutputStreamWriter(os, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(w);
                    
                    
                    

                    String line = br.readLine();
                    if (line != null && !"".equals(line)) {
                        String[] parts = line.split(" ");                                            
                        if (parts.length >= 3) {
                            String fileName = parts[1];
                            System.out.println(" "+ fileName);
                                                       
                            if (fileName.endsWith(".html")) {
                                File f = new File("/Users/evuncik/Desktop/JAVA/MY_WebServer/src/" + fileName);
                                if (f.exists()) {
                                    bw.write("HTTP/1.1 200 OK");
                                    bw.newLine();
                                    bw.write("Content-Type: text/html");
                                    bw.newLine();
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
                                } else {
                                    write404(bw);
                                }
                            } else if (fileName.equals("/quit")) {
                                bw.write("HTTP/1.1 200 OK");
                                bw.newLine();
                                bw.write("Content-Type: text/html");
                                bw.newLine();
                                bw.newLine();
                                bw.write("<html>");
                                bw.write("<body>");
                                bw.write("<h1>Server is shutting down. Bye.</h1>");
                                bw.write("</body>");
                                bw.write("</html>");
                                work = false;
                            } else if (fileName.equals("/dir")) {
                                bw.write("HTTP/1.1 200 OK");
                                bw.newLine();
                                bw.write("Content-Type: text/html");
                                bw.newLine();
                                bw.newLine();
                                bw.write("<html>");
                                bw.write("<body>");
                                bw.write("<h1>/dir</h1>");
                                bw.write("</body>");
                                bw.write("</html>");
                                
                            }
                            
                            
                            else {
                                write404(bw);
                            }
                            bw.flush();
                        }
                    }
                }
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
