package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class FrameLoginEvt extends WindowAdapter implements ActionListener {
	
	Map<String,String> validUserMap ; 
	// Login의 객체 생성
	private FrameLogin lg;
	private Main main;
	boolean reportAuth = false;
	// Login 클래스와 이벤트 클래스를 has a관계로 설정하는 생성자
	public FrameLoginEvt(FrameLogin lg,Main main) {
		this.lg = lg;
		this.main = main;
		validUserMap = new HashMap<String,String>();

	}// LoginEvt

	@Override
	// 버튼이 클릭되면 유효 id,pw map 초기화
	public void actionPerformed(ActionEvent ae) {
		validUserMap.put("admin", "1234");
		validUserMap.put("administrator", "12345");
		validUserMap.put("root", "1111");
		
//		로그인 버튼 클릭시 id와 password를 가져와서 비교함
		String id = lg.getJtfId().getText();
		String pw = new String(lg.getJpfPw().getPassword());
		
		if((validUserMap.containsKey(id))) { // id가 있다면
			if(validUserMap.get(id).equals(pw)) { // 해당 id와 비밀번호가 맞다면
				reportAuth = id.equals("root") ? false: true;
				JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
				main.showFrameSub(reportAuth);
			}else {
				JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.", "로그인 실패", JOptionPane.INFORMATION_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "존재하지 않는 id입니다.", "로그인 실패", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		lg.dispose();
	}
}