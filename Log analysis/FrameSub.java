package project1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

public class FrameSub extends JFrame {

	public static Font Fontsetup = new Font("맑은 고딕", Font.PLAIN, 20);
	public static Font Fontsetup2 = new Font("맑은 고딕", Font.PLAIN, 15);
	private BufferedImage img = null;

	private JButton jbtnView, jbtnReport, jbtnLineView;

	private boolean reportAuth ;
	public FrameSub(boolean reportAuth) {
		super("ViewReport");
//		this.lg = lg;
		this.reportAuth = reportAuth;
		setSize(680, 738);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false); // 창 크기 고정
		setLocationRelativeTo(null); // 창 위치 고정

		try {
			img = ImageIO.read(new File("img/frame.png"));
		} catch (IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		} // try~catch
			// 패널1
		Mypanel2 panel2 = new Mypanel2();
		panel2.setBounds(0, 0, 707, 738);

		// 레이아웃 설정
		JLayeredPane jlp2 = new JLayeredPane();
		jlp2.setBounds(0, 0, 707, 738);
		jlp2.setLayout(null);
	    // 뷰 버튼
	    jbtnView = new JButton();
	    jbtnView.setBounds(60, 300, 170, 100);
	    // 뷰 버튼 커스터마이징
	    jbtnView.setOpaque(false); // 버튼 투명처리
	    jbtnView.setBorderPainted(false);// 버튼 투명처리
	    jbtnView.setContentAreaFilled(false);// 버튼 투명처리
	    jbtnView.setName("view");
	    jlp2.add(jbtnView);
	    // 리포트 버튼
	    jbtnReport = new JButton();
	    jbtnReport.setBounds(270, 300, 170, 100);
	    // 리포트 버튼 커스터마이징
	    jbtnReport.setOpaque(false); // 버튼 투명처리
	    jbtnReport.setBorderPainted(false);// 버튼 투명처리
	    jbtnReport.setContentAreaFilled(false);// 버튼 투명처리
	    jbtnReport.setName("report");
	    jlp2.add(jbtnReport);
		//라인뷰 버튼
		jbtnLineView=new JButton();
		jbtnLineView.setBounds(480,300,170,100);
		//라인뷰 커스터마이징
		jbtnLineView.setOpaque(false); // 버튼 투명처리
		jbtnLineView.setBorderPainted(false);// 버튼 투명처리
		jbtnLineView.setContentAreaFilled(false);// 버튼 투명처리
	    jlp2.add(jbtnLineView);

	    FrameSubEvt fse = new FrameSubEvt(this);
		jbtnView.addActionListener(fse);
		jbtnReport.addActionListener(fse);
		jbtnLineView.addActionListener(fse);

		// 마지막 추가
		add(jlp2);
		jlp2.add(panel2);
		setVisible(true);
	}

	// 패널에 사진첨부하기
	class Mypanel2 extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}// paint
	}// Mypanel

	public BufferedImage getImg() {
		return img;
	}

	public JButton getJbtnView() {
		return jbtnView;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JButton getJbtnLineView() {
		return jbtnLineView;
	}

	public boolean isReportAuth() {
		return reportAuth;
	}
    
}// class

