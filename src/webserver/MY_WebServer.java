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

    public static boolean work = true;
    public static void main(String[] args)
            throws IOException {
        try (ServerSocket sc = new ServerSocket(9000);) {
            while (work) {
                try (Socket socket = sc.accept();) {
                    RequestHandler rh = new RequestHandler(socket);
                    rh.start();
                }
            }
        }
    } 
}
