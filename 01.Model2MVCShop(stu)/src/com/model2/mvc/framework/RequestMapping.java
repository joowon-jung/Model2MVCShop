package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties; // key, value 구조 
	
	// 
	private RequestMapping(String resources) { // 생성자 private 
		map = new HashMap<String, Action>(); // ~~~.do 일때 ~~.Action이 행동해라!라는 정보 
		InputStream in = null; // meta-data 파싱하기 위해 InputStream 
		try{
			// 뭔가 init-param 에 매핑해준 파일 이용하는 것 같기는 한데.. (?)
			in = getClass().getClassLoader().getResourceAsStream(resources);
			// properties : key - value 가 String 값으로만 담김 
			// properties 파일 안의 정보들이 key - value 로 알아서 파싱됨
			// 코드는 api 참고한 것임 
			properties = new Properties(); 
			properties.load(in); // load 안에 InputStream 넣어주면 알아서 key value로 파싱해준다 
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	// 인스턴스 하나만 만들어지게 해놨음. 생성은 resources를 주면서 
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	// Action : 모든 Action 클래스의 상위 클래스 
	
	// getAction : ~~.do 일때 ~~.Action을 찾아주는구나 ! 걍 이렇게만 이해하고 넘어가자 
	// Action 찾아주는구나 (properties 만드는 논리가 들어가있음) 이러고 그냥 넘기기 나중에 다시와서 보기 
	public Action getAction(String path){ // 013Model2의 getController 메소드와 같은 개념 
		
		Action action = map.get(path); // ~~.do 라면 map 에 그거 있어? 처음오면 없어
		// 뭔가 URI 주소 가져와서 AddUser.do 면 AddUserAction 으로 넘기는 것 같긴 한데.. (?)
		if(action == null){
			String className = properties.getProperty(path); // properties 안에 key - value 들어가있
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim(); // 파싱해서 찾아왔는데 properties는 String임
			try{
				Class c = Class.forName(className); // Class.forName 하면 생성해줌 
				Object obj = c.newInstance(); // 임의의 객체를 Object형으로 가져옴
				if(obj instanceof Action){ // Action의 하위인지 체크하고 있음 
					map.put(path, (Action)obj); // Action의 하위라면 
					action = (Action)obj; 
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}