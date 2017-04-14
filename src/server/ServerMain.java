package server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center;				// ��ü ȭ��� �г�
	ServerSocket server;		// ������ ����
	Thread thread;					//������ ���ÿ� ������
	
	int port=7878;
	
	public ServerMain() {
		try {
			server=new ServerSocket(port);
			System.out.println("���� ����");
			
			thread=new Thread(this);
			
			// �� �������� ���ξ������ ������ ���� ������(���� ������)�� ���� �ڽ��� �ڵ带 �����ϰ� ��
			// -> ���ξ����忡 ���� �������� ȭ�鿡 �����԰� ���ÿ� ������ accept()�� ����
			// ��, ������ ������ �ϸ� ���������� ������
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p_center=new JPanel();
		p_center.setBackground(Color.WHITE);
		add(p_center);
		
		// ������
		setLayout(new FlowLayout());
		setSize(800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void run() {
		// �����ڸ� �������� ����
		while(true){
			try {
				// �����ڰ� �߰ߵǸ� PC ���� ī���Ϳ� �����ڰ� ȭ�鿡 ����
				// �����ڰ� ������ ������ ��ȯ��
				Socket socket=server.accept();
				System.out.println("����� ����");
				
				// user �ø���
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
