package hanbaoaaa.xyz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Servlet implementation class GetCataServlet
 */
@WebServlet("/GetArticleServlet")
public class GetArticleServlet extends HttpServlet {


    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		long id =Long.parseLong(request.getParameter("id"));
		System.out.println("id:"+id);

		String MDData=NoteManager.getInstance().readMD(id,request.getParameter("code"));
		if(MDData==null){
			response.getWriter().append("error");
		}else {
			//System.out.println("data="+MDData);
			response.getWriter().append(MDData);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
