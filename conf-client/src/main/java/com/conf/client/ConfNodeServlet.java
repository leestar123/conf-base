package com.conf.client;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfNodeServlet extends ConfBaseServlet
{

    private static final long serialVersionUID = 4488033381706475812L;
 
    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        super.service(req, rep);
    }
}
