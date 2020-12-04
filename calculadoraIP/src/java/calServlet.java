/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Autor: Douglas Javier Colque Mullisaca
Materia: Lab - 273 Laboratorio de telemática
Tarea de auxiliatura MODULO 4
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author douglas
 */
@WebServlet(urlPatterns = {"/calServlet"})
public class calServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // extraemos los datos de la pagina index
        String as=request.getParameter("a");
        String bs=request.getParameter("b");
        String cs=request.getParameter("c");
        String ds=request.getParameter("d");
        String ps=request.getParameter("p");
        String ns=request.getParameter("n");
        String S1=request.getParameter("s1");
        String S2=request.getParameter("s2");
        String S3=request.getParameter("s3");
        String S4=request.getParameter("s4");
        String S5=request.getParameter("s5");
        String S6=request.getParameter("s6");
        String S7=request.getParameter("s7");
        String S8=request.getParameter("s8");
        String S9=request.getParameter("s9");
        String S10=request.getParameter("s10");
        //convertimos los datos extraidos
        int a=Integer.parseInt(as);
        int b=Integer.parseInt(bs);
        int c=Integer.parseInt(cs);
        int d=Integer.parseInt(ds);
        int p=Integer.parseInt(ps);
        int n=Integer.parseInt(ns);
        int s1=Integer.parseInt(S1);
        int s2=Integer.parseInt(S2);
        int s3=Integer.parseInt(S3);
        int s4=Integer.parseInt(S4);
        int s5=Integer.parseInt(S5);
        int s6=Integer.parseInt(S6);
        int s7=Integer.parseInt(S7);
        int s8=Integer.parseInt(S8);
        int s9=Integer.parseInt(S9);
        int s10=Integer.parseInt(S10);
        // creamos un vector para almacenar los datos de cantidades extraidos
        int vec[]=new int [10];
        vec[0]=s1;vec[1]=s2;vec[2]=s3;vec[3]=s4;vec[4]=s5;vec[5]=s6;vec[6]=s7;
        vec[7]=s8;vec[8]=s9;vec[9]=s10;
        Arrays.sort(vec);
        String vec2[][]=new String [10][5];
       //En esta parte obtenemos el primer host de la direccion IP
            int am=0;
            int bm=0;
            int cm=0;
            int dm=0;
            if(p>=24 && p<=32){
                am=bm=cm=255;
                dm=256-(int) Math.pow(2,32-p);
                
            }else{
                if(p>=16 && p<=23){
                    am=bm=255;
                    cm=256-(int) Math.pow(2,32-(p-8));
                    dm=0;
                }else{
                    if(p>=8 && p<=15){
                        am=255;
                        bm=256-(int) Math.pow(2,32-(p-8-8));
                        cm=dm=0;

                    }else{
                        am=256-(int) Math.pow(2,32-(p-8-8-8));
                        bm=cm=dm=0;
                    }
                }
            }
            System.out.println("# "+am+" "+bm+" "+cm+" "+dm);
            a=a&am;
            b=b&bm;
            c=c&cm;
            d=d&dm;
       //en esta parte obtenemos las sub redes solicitadas
       for (int i = 9; i >=10-n ; i--) {
           // en esta seccion optenemos las mascara de subred
            int elevado=2;
            int potencia=1;
            while(elevado -2 <vec[i]){
                elevado= (int) Math.pow(2,potencia);
                potencia++;
            }
            potencia=potencia-1;
            int r=(32-p)-potencia;
            int mascara=p+r;
            //llenamos la IP de red
            vec2[9-i][0]=a+","+b+","+c+","+d;
            //llenamos la mascara de subred
            vec2[9-i][1]=mascara+"";
            // hallamos la primera IP asignable
            if(d<255)
                d=d+1;
            else
                if(c<255){
                    c=c+1;
                    d=0;
                }else{
                    if(b<255){
                        b=b+1;
                        d=0;
                        c=0;
                    }else{
                        a=a+1;
                        b=c=d=0;
                    }
                }
            //llenamos la primera IP asignable
            vec2[9-i][3]=a+","+b+","+c+","+d;
            //hallamos la ultima IP asignable
            int resto=elevado-2;
            while(resto>0){
                if(d+resto<=255){
                d=d+resto;
                resto=resto-255;
                }
               else
                if(c<=255){
                    c=c+1;
                    d=0;
                    resto=resto-255;
                }else{
                    if(b<255){
                        b=b+1;
                        d=0;
                        c=0;
                        resto=resto-255;
                    }else{
                        a=a+1;
                        b=c=d=0;
                        resto=resto-255;
                    }
                }
            }
            //lleanmos la ultima IP asignable
            vec2[9-i][4]=a+","+b+","+c+","+d;
            //hallamos el broadcast
            if(d<255)
                d=d+1;
            else
                if(c<255){
                    c=c+1;
                    d=0;
                }else{
                    if(b<255){
                        b=b+1;
                        d=0;
                        c=0;
                    }else{
                        a=a+1;
                        b=c=d=0;
                    }
                }
            //llenamos el broadcast
             vec2[9-i][2]=a+","+b+","+c+","+d;
       }
    
    try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            //AQUI CRAMOS LA PAGINA DE RESPUESTA
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet calServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<table border=1>");
            out.println("<tr>");
            out.println("<th>subred</th><th>IP de red</th><th>Mascara</th><th>Broadcast</th><th>Primera IP asignada</th><th>Ultima IP asignada</th>");
            out.println("</tr>");
            //AQUI MOSTRAMOS LOS RESULTADOS EN UNA TABLA
            for (int i = 0; i < n; i++) {
                out.println("<tr>");
                out.println("<th>subred "+(i+1)+"</th><th>"+vec2[i][0]+"</th><th>"+vec2[i][1]+"</th><th>"+vec2[i][2]+"</th><th>"+vec2[i][3]+"</th><th>"+vec2[i][0]+"</th>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
