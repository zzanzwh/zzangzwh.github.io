package member.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class CheckIdDuplicateServlet
 */
@WebServlet("/member/checkMemberIdDuplicate")
public class CheckMemberIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckMemberIdDuplicateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.사용자입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		
		//2. 업무로직
		Member member = new MemberService().selectOne(memberId);
		System.out.println("member@servlet = "+member);
		
		boolean isUsable = member == null ? true : false;  //null 리턴시, 사용가능한 아이디 
		System.out.println("isUsable@servlet = " + isUsable);

		//3. view단 처리
		request.setAttribute("isUsable", isUsable);
		request.getRequestDispatcher("/WEB-INF/views/member/checkMemberIdDuplicate.jsp")
			   .forward(request, response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
