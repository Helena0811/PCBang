// ServerThread
package server;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel implements Runnable{
	JLabel la;
	
	// socket은 접속이 될 때 만들어지므로 client에서 가져와야 함(생성자로 받아오기)
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	boolean flag=true;
	Thread thread;
	
	public User(Socket socket) {
		this.socket=socket;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// stream 생성 후 부터 대화가 가능하기 때문에 쓰레드는 이 때 생성
		thread = new Thread(this); //Runnable인자니까!
		thread.start();
		
		la = new JLabel("123번 pc");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
	}
	
	// 서버 측에서는 msg가 어떤 프로토콜인지 유형 파악을 해야 함!
	// 따라서, 대화/구매/가입/탈퇴 등의 모든 유형 파악을 설정해야 함
	// ex) id=batman&product_id=11
	// 듣기
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			
			// 구분자를 기준으로 요청 분석
			// 채팅의 경우
			// requestType		=chat
			// msg					=클라이언트 말
			// id						=id
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");
			
			if(requestType[1].equals("chat")){
				// 대화 보내기
				String[] str=data[1].split("=");
				send(str[1]);		// 클라이언트에 다시 보내기
			}
			else if(requestType[1].equals("buy")){
				// 구매를 원하는 경우
				System.out.println("구매를 원하는군요!");
			}
			
			send(msg);		// 클라이언트에 다시 보내기
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 말하기
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(flag){
			listen();
		}
		
	}
}
