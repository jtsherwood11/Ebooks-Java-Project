import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.*;
import javax.servlet.http.*;


public class FileServlet extends HttpServlet {


    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         String id = request.getParameter("file");

         String fileName = "";
         String fileType = "";
         // Find this file id in database to get file name, and file type

         // You must tell the browser the file type you are going to send
         // for example application/pdf, text/plain, text/html, image/jpg
         response.setContentType(fileType);

         // Make sure to show the download dialog
         response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");

         // Assume file name is retrieved from database
         // For example D:\\file\\test.pdf

         File my_file = new File(fileName);

         // This should send the file to browser
         OutputStream out = response.getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > -1){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
    }

}
