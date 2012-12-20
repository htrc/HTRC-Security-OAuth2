package edu.indiana.d2i.htrc.oauth2.sample;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebResource extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<h2>Successfully authenticated using OAuth2</h2>");
    }

    public void doPost
            (HttpServletRequest
                     request,
             HttpServletResponse
                     response) throws IOException, ServletException {
        doGet(request, response);
    }
}
