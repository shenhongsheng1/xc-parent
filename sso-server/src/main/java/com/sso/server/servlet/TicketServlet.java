package com.sso.server.servlet;

import com.sso.server.cache.JVMCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ShenHongSheng
 * ClassName: TicketServlet
 * Description:
 * Date: 2020/12/22 17:45
 * @version V1.0
 */
public class TicketServlet extends HttpServlet {
    private static final long serialVersionUID = 5964206637772848290L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticket = request.getParameter("ticket");
        String username = (String) JVMCache.TICKET_AND_NAME.get(ticket);
        JVMCache.TICKET_AND_NAME.remove(ticket);
        PrintWriter writer = response.getWriter();
        writer.write(username);
    }
}
