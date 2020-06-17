package hanbao.com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class dataServlet
 */
@WebServlet("/dataServlet")
public class dataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String timetype=request.getParameter("timetype");
		String type=request.getParameter("type");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String day=request.getParameter("day");
		System.out.println(type+timetype);
		if(type!=null&&timetype!=null){
			if(timetype.equals("year")) { 
				String realpath=request.getServletContext().getRealPath("")+"\\face\\record\\"+type;
				realpath=realpath.replace('\\', '/');
				System.out.println(realpath);
				List<String> names=fileUtils.getDirectoryNames(realpath);
				String yearJSONArr="[";
				for (int i = 0; i < names.size(); i++) {
		            String name=names.get(i);
		            if(i>0) {
		            	yearJSONArr+=",";
		            }
		            yearJSONArr+="\""+name.substring(name.length()-2, name.length())+"\"";
		            
		        }
				yearJSONArr+="]";
				System.out.println(yearJSONArr);
				response.getWriter().append(yearJSONArr);
			}else if(timetype.equals("month")){
				String realpath=request.getServletContext().getRealPath("")+"\\face\\record\\"+type+"\\20"+year;
				realpath=realpath.replace('\\', '/');
				System.out.println(realpath);
				List<String> names=fileUtils.getDirectoryNames(realpath);
				String yearJSONArr="[";
				for (int i = 0; i < names.size(); i++) {
		            String name=names.get(i);
		            if(i>0) {
		            	yearJSONArr+=",";
		            }
		            yearJSONArr+="\""+name+"\"";
		            
		        }
				yearJSONArr+="]";
				System.out.println(yearJSONArr);
				response.getWriter().append(yearJSONArr);
			}else if(timetype.equals("day")){
				String realpath=request.getServletContext().getRealPath("")+"\\face\\record\\"+type+"\\20"+year+"\\"+month;
				realpath=realpath.replace('\\', '/');
				System.out.println(realpath);
				List<String> names=fileUtils.getDirectoryNames(realpath);
				String yearJSONArr="[";
				for (int i = 0; i < names.size(); i++) {
		            String name=names.get(i);
		            if(i>0) {
		            	yearJSONArr+=",";
		            }
		            yearJSONArr+="\""+name+"\"";
		            
		        }
				yearJSONArr+="]";
				System.out.println(yearJSONArr);
				response.getWriter().append(yearJSONArr);
			}else if(timetype.equals("data")){
				String realpath=request.getServletContext().getRealPath("")+"\\face\\record\\"+type+"\\20"+year+"\\"+month+"\\"+day;
				realpath=realpath.replace('\\', '/');
				System.out.println("data:"+realpath);
				List<String> names=fileUtils.getFileNames(realpath);
				String yearJSONArr="[";
				for (int i = 0; i < names.size(); i++) {
		            String name=names.get(i);
		            if(i>0) {
		            	yearJSONArr+=",";
		            }
		            yearJSONArr+="\""+name+"\"";
		        }
				yearJSONArr+="]";
				System.out.println(yearJSONArr);
				response.getWriter().append(yearJSONArr);
			}else {
				System.out.println("hh");
				System.out.println(request.getServletContext().getRealPath(""));
			}
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
