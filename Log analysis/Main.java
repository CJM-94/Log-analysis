package project1;

public class Main {

	private FrameLogin frameLogin;
	private FrameSub frameSub;
	
	public static void main(String[] args) {

		Main main = new Main();
		main.frameLogin = new FrameLogin(main);
		
	}
	
//	public void showFrameSub(FrameLogin frameLogin,boolean reportAuth) { 
	public void showFrameSub(boolean reportAuth) { 
		frameLogin.dispose();
//		frameSub = new FrameSub(frameLogin,reportAuth);
		frameSub = new FrameSub(reportAuth);
	}
}
