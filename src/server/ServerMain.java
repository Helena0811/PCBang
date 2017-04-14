package server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center;				// 전체 화면용 패널
	ServerSocket server;		// 접속자 감시
	Thread thread;					//접속자 감시용 쓰레드
	
	int port=7878;
	
	public ServerMain() {
		try {
			server=new ServerSocket(port);
			System.out.println("서버 생성");
			
			thread=new Thread(this);
			
			// 이 시점에는 메인쓰레드와 개발자 정의 쓰레드(서브 쓰레드)는 각자 자신의 코드를 수행하게 됨
			// -> 메인쓰레드에 의해 프레임이 화면에 등장함과 동시에 서버의 accept()도 동작
			// 즉, 별도의 실행을 하며 독립적으로 가동됨
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p_center=new JPanel();
		p_center.setBackground(Color.WHITE);
		add(p_center);
		
		// 디자인
		setLayout(new FlowLayout());
		setSize(800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void run() {
		// 접속자를 무한으로 받음
		while(true){
			try {
				// 접속자가 발견되면 PC 메인 카운터에 접속자가 화면에 등장
				// 접속자가 들어오면 소켓이 반환됨
				Socket socket=server.accept();
				System.out.println("사용자 접속");
				
				// user 올리기
				User user=new User(socket);
				
				p_center.add(user);
				p_center.updateUI();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}

}
