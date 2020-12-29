package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class Utils {

	/**
	 * 암호화처리
	 * 1. 암호화객체
	 * 2. 암호화처리할 문자열을 byte[]로 변경
	 * 3. 암호화처리 byte[]
	 * 4. Base64인코딩처리 : 읽을 수 있는 문자열처리
	 * 
	 * @param password
	 * @return
	 */
	public static String getEncryptedPassword(String password) {
		String encryptedPassword = null;
		//1.암호화객체 생성: 알고리즘 지정
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("sha-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//2. 문자열 -> byte[]
		byte[] bytes = null;
		try {
			bytes = password.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//3. 암호화 처리
		md.update(bytes);
		byte[] encryptedBytes = md.digest();
		System.out.println(new String(encryptedBytes));
		
		//4. 인코딩처리 Base64 
		// 영대소문자 52, 숫자 10, +, /
		// = 패딩문자(채우기텍스트)
		Encoder encoder = Base64.getEncoder();
		encryptedPassword = encoder.encodeToString(encryptedBytes);
		System.out.println(encryptedPassword);
		
		return encryptedPassword;
	}

	public static String getPageBarHtml(String id, int cPage, int numPerPage, int totalContents, String url) {
		StringBuilder pageBar = new StringBuilder();
		
		//cPage앞에 붙을 구분자를 지정
		char delim = url.indexOf("?") > -1 ? '&' : '?';  
		url = url + delim;
		
		int pageBarSize = 5; //페이지바에 나열될 페이지번호의 개수
		//115 / 10 => 12
		int totalPage = (int)Math.ceil((double)totalContents/numPerPage);
		
		// x * pageBarSize + 1
		// 1 2 3 4 5 -> 1      
		// 6 7 8 9 10 -> 6
		// 11 12 13 14 15 -> 11
		int pageStart = ((cPage - 1)/pageBarSize) * pageBarSize + 1; 
		int pageEnd = pageStart + pageBarSize - 1;
		int pageNo = pageStart;
		
		//이전 
		if(pageNo == 1) {
			
		}
		else {
			pageBar.append("<a href='" + url +  "memberId="+id +"&cPage=" + (pageNo - 1) + "'>이전</a>\n");
		}
		
		//PageNo
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			//현재페이지인 경우
			if(pageNo == cPage) {
				pageBar.append("<span class='cPage'>" + pageNo + "</span>\n");
			}
			//현재페이지가 아닌 경우
			else {
				pageBar.append("<a href='" + url + "memberId="+id +"&cPage=" + pageNo + "'>" + pageNo + "</a>\n");
			}
			pageNo++;
		}
		//다음
		if(pageNo > totalPage) {
			
		}
		else {
			pageBar.append("<a href='" + url + "memberId="+id +"&cPage=" + pageNo + "'>다음</a>\n");
		}
		
		return pageBar.toString();
	}
	
	
	public static String getPageBarHtml(int cPage, int numPerPage, int totalContents, String url) {
		StringBuilder pageBar = new StringBuilder();
		
		//cPage앞에 붙을 구분자를 지정
		char delim = url.indexOf("?") > -1 ? '&' : '?';  
		url = url + delim;
		
		int pageBarSize = 5; //페이지바에 나열될 페이지번호의 개수
		//115 / 10 => 12
		int totalPage = (int)Math.ceil((double)totalContents/numPerPage);
		
		// x * pageBarSize + 1
		// 1 2 3 4 5 -> 1      
		// 6 7 8 9 10 -> 6
		// 11 12 13 14 15 -> 11
		int pageStart = ((cPage - 1)/pageBarSize) * pageBarSize + 1; 
		int pageEnd = pageStart + pageBarSize - 1;
		int pageNo = pageStart;
		
		//이전 
		if(pageNo == 1) {
			
		}
		else {
			pageBar.append("<a href='" + url + "cPage=" + (pageNo - 1) + "'>이전</a>\n");
		}
		
		//PageNo
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			//현재페이지인 경우
			if(pageNo == cPage) {
				pageBar.append("<span class='cPage'>" + pageNo + "</span>\n");
			}
			//현재페이지가 아닌 경우
			else {
				pageBar.append("<a href='" + url + "cPage=" + pageNo + "'>" + pageNo + "</a>\n");
			}
			pageNo++;
		}
		//다음
		if(pageNo > totalPage) {
			
		}
		else {
			pageBar.append("<a href='" + url + "cPage=" + pageNo + "'>다음</a>\n");
		}
		
		return pageBar.toString();
	}
	
	/*
	 	<pre>
	 	xss 공격 댑; : html 엔터티 문자인 <, > 를 변환처리한다
	 	
	 	</pre>
	 	@param old str
	 	
	 */
	public static String getSecureString(String oldStr) {
		String newStr = oldStr.replaceAll("<", "&lt;")
								.replaceAll(">", "&gt;");
		
		
		return newStr;
		
		
	}
	/*
	 * 사용자 입력값중 \n 을<br/> 태그로 변환 처리함
	 * @param s
	 * @return
	 * 
	 * 
	 */
	public static String getStringWithBr(String s) {
		return s.replaceAll("\n", "<br/>");
	}
	
	
	
	
}
