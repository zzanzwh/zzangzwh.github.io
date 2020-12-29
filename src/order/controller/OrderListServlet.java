package order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import order.model.service.OrderListService;
import order.model.vo.OrderBrand;
import order.model.vo.OrderList;
import order.model.vo.OrderListInfo;
import order.model.vo.OrderListSeller;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/orderList")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		Member memberLoggedIn = (Member)request.getSession().getAttribute("memberLoggedIn");
		String memberId = memberLoggedIn.getMemberId();
		String memberRole = memberLoggedIn.getMemberRole();
		
		OrderListService orderListService = new OrderListService();
		
		if(memberRole.equals("S")){
			//판매자
			List<OrderBrand> orderNoList = orderListService.selectOrderNoList(memberId); 
			//브랜드이름을 통해 상품에 대한 모든 주문넘버와 정보를 리스트에 저장함
			
			List<OrderListInfo> orderListSeller = null;
			//한 주문에 대한 주문상품목록들과 그에 대한 정보를 저장할 리스트
			
			Map<OrderBrand, Object> orderMapSeller = new HashMap<OrderBrand, Object>();
			//주문 내역별 정보를 담을 Map , 한 주문에 대한 여러 주문상품정보 리스트를 value값을 가짐
			
			for(OrderBrand ob : orderNoList) {
				//판매자의 주문내역만큼 반복
				
				orderListSeller = orderListService.selectOrderListSeller(ob.getOrderNo(), memberId);
				//한 주문마다 그에 해당하는 주문상품목록 가져오기
				
				orderMapSeller.put(ob, orderListSeller);
				//방금 가져온 주문상품목록을 value로 map에 넣기
			}
			
			request.setAttribute("orderMapSeller", orderMapSeller);
		}
		
		
		if(memberRole.equals("B")) {
			//구매자
			List<OrderList> orderList = orderListService.selectOrderList(memberId);
			//구매자 아이디를 통해 주문내역에서 주문 넘버를 가져옴
			
			List<OrderListInfo> orderListInfo = null;
			//한 구매의 여러 상품정보목록을 저장할 리스트
			
			Map<OrderList, Object> orderMap = new HashMap<OrderList, Object>();
			//구매 내역별 정보를 담을 Map , 한 주문에 대한 여러 주문상품정보 리스트를 value값을 가짐
			
			for(OrderList ol : orderList) {
				//구매자의 구매내역만큼 반복
				
				orderListInfo = orderListService.selectOrderDetailList(ol.getOrderNo());
				//한 구매에 대한 여러 상품 정보 리스트를 가져옴
				
				orderMap.put(ol, orderListInfo);
				// Map에 저장
			}
			
			System.out.println("map@servlet = " + orderMap);
			
			request.setAttribute("orderMap", orderMap);
		}
		
		
		
		String view = "/WEB-INF/views/order/OrderListPage.jsp";
		//해당 jsp에서는 구매자, 판매자 별 분기처리를 하여 판매자는 해당 주문에 대한 구매자 정보나 배송지, 받는 사람에 대한 정보가 추가로 출력됨
		request.getRequestDispatcher(view)
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
