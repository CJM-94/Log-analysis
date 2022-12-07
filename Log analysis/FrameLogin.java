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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FrameLogin extends JFrame {

	public static Font Fontsetup = new Font("맑은 고딕", Font.PLAIN, 20);
	public static Font Fontsetup2 = new Font("맑은 고딕", Font.PLAIN, 15);
	private BufferedImage img = null;

	private JTextField jtfId;
	private JPasswordField jpfPw;
	private JButton jbtnBt;
	
//	private Main main;
	
	public FrameLogin(Main main) {
		super("로그인");
//		this.main = main;
		setSize(700, 738);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false); // 창 크기 고정
		setLocationRelativeTo(null); // 창 위치 고정

		try {
			img = ImageIO.read(new File("img/Login.png"));
		} catch (IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		} // try~catch
			// 패널1
		Mypanel panel = new Mypanel();
		panel.setBounds(0, 0, 707, 738);

		// 레이아웃 설정
		JLayeredPane jlp = new JLayeredPane();
		jlp.setBounds(0, 0, 707, 738);
		jlp.setLayout(null);
		// 아이디
		jtfId = new JTextField(12);
		jtfId.setBounds(349, 250, 240, 30);
		jlp.add(jtfId);
		// 아이디 커스터마이징
		jtfId.setOpaque(false); // 투명처리
		jtfId.setForeground(Color.RED);
		jtfId.setFont(Fontsetup);
		jtfId.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // 경계선 투명화처리
		// 비밀번호
		jpfPw = new JPasswordField(12);
		jpfPw.setBounds(349, 360, 240, 30);
		jlp.add(jpfPw);
		// 비밀번호 커스터마이징
		jpfPw.setOpaque(false); // 투명처리
		jpfPw.setForeground(Color.red);

		jpfPw.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // 경계선 투명화처리
		// 로그인 버튼
		jbtnBt = new JButton();
		jbtnBt.setBounds(135, 490, 200, 48);
		// 로그인 버튼 커스터마이징
		jbtnBt.setOpaque(false); // 버튼 투명처리
		jbtnBt.setBorderPainted(false);// 버튼 투명처리
		jbtnBt.setContentAreaFilled(false);// 버튼 투명처리
		jlp.add(jbtnBt);

		// 이벤트 처리 객체 생성
		FrameLoginEvt lgevt = new FrameLoginEvt(this,main);

		// 컴포넌트에서 발생하는 이벤트 처리

		jbtnBt.addActionListener(lgevt);
		addWindowListener(lgevt);
		// 마지막 추가
		add(jlp);
		jlp.add(panel);
		setVisible(true);
	}
	class Mypanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}// paint
	}// My
	
	public static Font getFontsetup() {
		return Fontsetup;
	}

	public static Font getFontsetup2() {
		return Fontsetup2;
	}

	public BufferedImage getImg() {
		return img;
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJpfPw() {
		return jpfPw;
	}

	public JButton getJbtnBt() {
		return jbtnBt;
	}
	
	
	
}