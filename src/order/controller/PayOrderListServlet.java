package order.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import order.model.service.OrderListService;

@WebServlet("/payOrder/orderList")
public class PayOrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayOrderListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//결제후 넘어오는 orderList
		String userName = (String) request.getParameter("userName");
		String userphone = (String)request.getParameter("userPhone");
		String paddress = (String)request.getParameter("paddress");
		
		String orderQuantityList = (String)request.getParameter("orderQuantityList");
		int ordertotal = Integer.parseInt(request.getParameter("totalPrice"));
		String orderSerialcode = (String)request.getParameter("orderSerialcode");
		
		Member memberLoggedIn = (Member)request.getSession().getAttribute("memberLoggedIn");
		String memberId = memberLoggedIn.getMemberId();
		
		
		String[] orderQuantity = orderQuantityList.split(",");
		int [] orderQuantityInt = new int[orderQuantity.length];
		for(int i=0; i<orderQuantity.length; i++) {
			orderQuantityInt[i] = Integer.parseInt(orderQuantity[i]);
			//한 주문에 대한 여러상품들의 수량을 ,로 구분한 String으로 정보를 받았기 때문에 split함수로 나눈 후 int로 형변환 처리
		}

		System.out.println("orderQuantityInt = " + orderQuantityInt);
		
		String[] orderSerial = orderSerialcode.split(",");
		System.out.println("orderSerial = " + orderSerial);
		
		OrderListService orderService = new OrderListService();
		
		int resultOfOrderList = orderService.insertOrder(ordertotal, memberId, paddress, userName, userphone);
		//해당 주문에 대해 주문내역 테이블에 주문내역 추가
		
		int resultOfDetail = 0;
		
		int orderNo;
		if(resultOfOrderList == 0) {
			System.out.println("주문내역 추가 실패!");
			
		}else {
			orderNo = orderService.selectOrderNum(memberId); // 방금 insert한 주문의 주문번호 가져오기
			resultOfDetail = orderService.insertOrderDetail(orderSerial, orderQuantityInt, orderNo); 
			//가져온 주문번호와 함께 order_detail(구매내역 상세 정보 테이블)에 상품 정보 insert, 
			//배열2개와 해당 주문번호를 인자로 하였기 때문에 service에서 배열 길이만큼 dao 메소드 호출함.
		}
		
		

		String loc = "";
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		if(resultOfDetail == orderSerial.length) {
			// 한 주문에 대한 여러 상품의 개수만큼 insert가 성공해야 하기 때문에 0이나 1로 조건체크를 하는 것이 아니라 orderSerial.length로 체크함
			msg = "주문내역 추가 성공";
			loc = request.getContextPath() + "/orderList"; 
			//주문내역 테이블에 추가한 후 회원에게 주문내역을 보여주기위한 데이터 처리 servlet으로 이동하기 위함
		}else {
			msg = "주문내역 추가 실패";
			loc = request.getContextPath();
		}
		
		//dml문 사용으로 url바꾸는 겸 데이터 가공은 다른 servlet에서 하기때문에 msg.jsp로 포워딩 처리
		request.setAttribute("loc", loc);
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(view)
				.forward(request, response);
	}

}


