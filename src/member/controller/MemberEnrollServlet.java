package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Utils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * 암호화	
 * - 양방향 암호화
 * - 단방향 암호화
 * 
 * 
 */
@WebServlet("/member/enroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp")
			   .forward(request, response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.사용자 입력값 처리
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		
		String memberPwd = request.getParameter("memberPwd");
		//비밀번호 암호화처리
		memberPwd = Utils.getEncryptedPassword(memberPwd);
		
		String memberName = request.getParameter("memberName");
		String memberPhone = request.getParameter("memberPhone");
		String memberAddress = request.getParameter("roadFullAddr"); 


			
		Member member = 
				new Member(memberId, memberName, memberPwd, MemberService.USER_MEMBER_ROLE, 
						memberPhone, memberAddress, null, null );
				
		System.out.println(member);
		
		
		//2.업무로직: Service단에 insert요청
		int result = new MemberService().insertMember(member);
		
		
		//3. view단 처리 : msg.jsp
		//등록성공시, 성공적으로 회원등록되었습니다. 
		//등록실패시, 회원 등록 실패했습니다.
		//url변경을 위해  /mvc 페이지로 이동시킬것. 
		
		//4. 받은 결과에 따라 뷰페이지 내보내기
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = request.getContextPath();

		if(result>0)
			msg = "성공적으로 회원가입되었습니다.";
		else 
			msg = "회원등록에 실패했습니다.";	
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		RequestDispatcher reqDispatcher = request.getRequestDispatcher(view);
		reqDispatcher.forward(request, response);

		
	}

}
