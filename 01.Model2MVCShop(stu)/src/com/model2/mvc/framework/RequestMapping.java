package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties; // key, value ���� 
	
	// 
	private RequestMapping(String resources) { // ������ private 
		map = new HashMap<String, Action>(); // ~~~.do �϶� ~~.Action�� �ൿ�ض�!��� ���� 
		InputStream in = null; // meta-data �Ľ��ϱ� ���� InputStream 
		try{
			// ���� init-param �� �������� ���� �̿��ϴ� �� ����� �ѵ�.. (?)
			in = getClass().getClassLoader().getResourceAsStream(resources);
			// properties : key - value �� String �����θ� ��� 
			// properties ���� ���� �������� key - value �� �˾Ƽ� �Ľ̵�
			// �ڵ�� api ������ ���� 
			properties = new Properties(); 
			properties.load(in); // load �ȿ� InputStream �־��ָ� �˾Ƽ� key value�� �Ľ����ش� 
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	// �ν��Ͻ� �ϳ��� ��������� �س���. ������ resources�� �ָ鼭 
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	// Action : ��� Action Ŭ������ ���� Ŭ���� 
	
	// getAction : ~~.do �϶� ~~.Action�� ã���ִ±��� ! �� �̷��Ը� �����ϰ� �Ѿ�� 
	// Action ã���ִ±��� (properties ����� ���� ������) �̷��� �׳� �ѱ�� ���߿� �ٽÿͼ� ���� 
	public Action getAction(String path){ // 013Model2�� getController �޼ҵ�� ���� ���� 
		
		Action action = map.get(path); // ~~.do ��� map �� �װ� �־�? ó������ ����
		// ���� URI �ּ� �����ͼ� AddUser.do �� AddUserAction ���� �ѱ�� �� ���� �ѵ�.. (?)
		if(action == null){
			String className = properties.getProperty(path); // properties �ȿ� key - value ����
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim(); // �Ľ��ؼ� ã�ƿԴµ� properties�� String��
			try{
				Class c = Class.forName(className); // Class.forName �ϸ� �������� 
				Object obj = c.newInstance(); // ������ ��ü�� Object������ ������
				if(obj instanceof Action){ // Action�� �������� üũ�ϰ� ���� 
					map.put(path, (Action)obj); // Action�� ������� 
					action = (Action)obj; 
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}