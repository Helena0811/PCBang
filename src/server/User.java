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
	
	// socket�� ������ �� �� ��������Ƿ� client���� �����;� ��(�����ڷ� �޾ƿ���)
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
		
		// stream ���� �� ���� ��ȭ�� �����ϱ� ������ ������� �� �� ����
		thread = new Thread(this); //Runnable���ڴϱ�!
		thread.start();
		
		la = new JLabel("123�� pc");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
	}
	
	// ���� �������� msg�� � ������������ ���� �ľ��� �ؾ� ��!
	// ����, ��ȭ/����/����/Ż�� ���� ��� ���� �ľ��� �����ؾ� ��
	// ex) id=batman&product_id=11
	// ���
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			
			// �����ڸ� �������� ��û �м�
			// ä���� ���
			// requestType		=chat
			// msg					=Ŭ���̾�Ʈ ��
			// id						=id
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");
			
			if(requestType[1].equals("chat")){
				// ��ȭ ������
				String[] str=data[1].split("=");
				send(str[1]);		// Ŭ���̾�Ʈ�� �ٽ� ������
			}
			else if(requestType[1].equals("buy")){
				// ���Ÿ� ���ϴ� ���
				System.out.println("���Ÿ� ���ϴ±���!");
			}
			
			send(msg);		// Ŭ���̾�Ʈ�� �ٽ� ������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ���ϱ�
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
