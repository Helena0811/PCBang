/*
 * 상품 1개의 display를 표현한 클래스
 * 영화 포스터와 구매 버튼을 가지고 있음
 * */
package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Product extends JPanel implements ActionListener{
	Canvas can;
	JButton bt_buy;
	BufferedImage image;
	URL url;		// Uniformed Resource Location, 자원의 위치에 대한 객체
	// Canvas 크기
	int width=120;
	int height=150;
	
	// ClientMain을 불러와 ClientThread접근 가능 -> send()호출 가능
	ClientMain clientMain;
	
	// Product 클래스를 사용하는 주체가 사용하기 쉽도록 URL 가져오기
	public Product(URL url, ClientMain clientMain) {
		this.url=url;
		this.clientMain=clientMain;
		
		// 이미지 생성
		// ImageIO.read(url);
		try {
			image=ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can=new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, width, height, this);
			}
		};
		bt_buy=new JButton("구매");
		
		setLayout(new BorderLayout());
		add(can);
		add(bt_buy, BorderLayout.SOUTH);
		
		// 버튼과 ActionListener 연결
		bt_buy.addActionListener(this);
		
		// 현재 패널의 크기
		setPreferredSize(new Dimension(width+5, height+45));
	}
	
	// 구매 수행
	// 서버 측에 의사 요청
	public void request(){
		// 구매하겠다는 의사 전달
		// 어떤 행위를 누가 무엇을?
		// 프로토콜/접속자/행위
		// 의사 요청을 하는 주체, 의사 요청의 종류 : 프로토콜
		// 구분자로 주체/행위를 구분
		//String msg="requestType=chat&product_id=87&id=batman";
		/*
		{
			"requestType":"chat",
			"msg":" 안녕",
			"id":"batman"
		}
		*/
		String msg="requestType=buy&product_id=87&id=batman";
		clientMain.ct.send(msg);
		
	}

	public void actionPerformed(ActionEvent e) {
		request();
	}
}
