package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogViewbtn extends JDialog implements ActionListener{
	
	FrameSub fs ;
	FrameSubEvt fse ;
	public DialogViewbtn(FrameSub fs, FrameSubEvt fse) {
		super(fs, "Analysis LogData",true);
		this.fs = fs;
		this.fse = fse;
		JTextArea jtaResult = new JTextArea(20,30);
		JScrollPane jspResult = new JScrollPane(jtaResult);
		JButton jbtnClose = new JButton("닫기");
		
		jtaResult.setText(fse.getTotalResult());
		add("Center",jspResult);
		add("South",jbtnClose);
		jbtnClose.addActionListener(this);
		setBounds(fs.getX()+100,fs.getY()+100,500,500);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
	
}
