/*
 * ��ǰ 1���� display�� ǥ���� Ŭ����
 * ��ȭ �����Ϳ� ���� ��ư�� ������ ����
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
	URL url;		// Uniformed Resource Location, �ڿ��� ��ġ�� ���� ��ü
	// Canvas ũ��
	int width=120;
	int height=150;
	
	// ClientMain�� �ҷ��� ClientThread���� ���� -> send()ȣ�� ����
	ClientMain clientMain;
	
	// Product Ŭ������ ����ϴ� ��ü�� ����ϱ� ������ URL ��������
	public Product(URL url, ClientMain clientMain) {
		this.url=url;
		this.clientMain=clientMain;
		
		// �̹��� ����
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
		bt_buy=new JButton("����");
		
		setLayout(new BorderLayout());
		add(can);
		add(bt_buy, BorderLayout.SOUTH);
		
		// ��ư�� ActionListener ����
		bt_buy.addActionListener(this);
		
		// ���� �г��� ũ��
		setPreferredSize(new Dimension(width+5, height+45));
	}
	
	// ���� ����
	// ���� ���� �ǻ� ��û
	public void request(){
		// �����ϰڴٴ� �ǻ� ����
		// � ������ ���� ������?
		// ��������/������/����
		// �ǻ� ��û�� �ϴ� ��ü, �ǻ� ��û�� ���� : ��������
		// �����ڷ� ��ü/������ ����
		//String msg="requestType=chat&product_id=87&id=batman";
		/*
		{
			"requestType":"chat",
			"msg":" �ȳ�",
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
