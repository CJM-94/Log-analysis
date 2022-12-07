package project1;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

// 버튼에 대한 이벤트
public class FrameSubEvt extends WindowAdapter implements ActionListener{

	private FrameSub fs;
	private String filePath;
	private String fileName;
	private static final String SUCCESS_SIGN = "200";
	private static final String FAIL_SIGN = "404";
	private static final String ABNOMAL_SIGN = "403";
	private static final String ERROR_SIGN = "500";
	private static final int SIGNAL = 0;
	private static final int URL = 1;
	private static final int BROWSER = 2;
	private static final int ACCESS_TIME = 3;
	
	private List<String> listSignal ;
	private List<String> listKey ;
	private List<String> listBrowser ;
	private List<String> listTime ;
	private Map<String,Integer> mapSignal ;
	private Map<String,Integer> mapKeyValue ;
	private Map<String,Integer> mapBrowser ;
	private Map<String,Integer> mapTime ;
	 
	private int totalCnt ; // 비율연산목적 변수
	private int valueCnt ;  // map 횟수카운트목적 변수
	
	private String totalResult;
	public FrameSubEvt(FrameSub fs) {
		this.fs = fs;
	}
 
	@Override
	public void actionPerformed(ActionEvent e){
		
		listKey =new ArrayList<String>();
		listSignal = new ArrayList<String>();
		listBrowser = new ArrayList<String>();
		listTime = new ArrayList<String>();
		
		mapSignal = new HashMap<String,Integer>();
		mapKeyValue = new HashMap<String,Integer>();
		mapBrowser = new HashMap<String,Integer>();
		mapTime = new HashMap<String,Integer>();
		
		if(e.getSource()==fs.getJbtnView()) { // view버튼 눌렀을 때
			try {
				setFilePath();
				divideData();
				new DialogViewbtn(fs,this); // 결과창
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
		if(e.getSource()==fs.getJbtnReport()) {
		    if(fs.isReportAuth()) { // root가 아니었다면 true를 갖고옴
				try {
					setFilePath();
					divideData();
					createFile();
				} catch (IOException ie) {
					ie.printStackTrace();
				}
		    }else {
		    	JOptionPane.showMessageDialog(null, "접속하신 id는 파일생성 권한이 없습니다.", "파일 생성 실패", JOptionPane.INFORMATION_MESSAGE);
		    }
		}
		if(e.getSource()==fs.getJbtnLineView()) {
			try {
				setFilePath();
				inputLineView();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	// 선택한파일로 separateLog 호출
	public void setFilePath() throws IOException {
		
		String path= null;
		String name = null;
		FileDialog fd = new FileDialog(fs,"파일가져오기",FileDialog.LOAD);
		fd.setVisible(true);
		path = fd.getDirectory();
		fileName = name = fd.getFile();
		name = fd.getFile();
		// log파일이 아닐경우
		if(!(name.substring(name.lastIndexOf(".")+1).equals("log"))) {
			throw new FileNotFoundException("log 파일을 선택해주세요.");
		}
		filePath = path+name;
		File logFile = new File(filePath);
		separateLog(logFile);
	}
	
	// StringTokenizer 로 Log부분별 각 list, map에 넣기위한 일을 하는 메서드
	// map, list 로 값할당하도록 add method 호출
	public void separateLog (File file) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(file));
		StringTokenizer st ;
		String dataLine = null;
		String[] dataArr ;
		totalCnt =0; // 비율연산을 위한 변수
		while( (dataLine=br.readLine())!=null ) {
			st = new StringTokenizer(dataLine,"[]");
			dataArr = new String[st.countTokens()];
			
			for(int i=0;i<dataArr.length;i++) {
				dataArr[i] = st.nextToken();
				switch(i) {
				case SIGNAL: addSignal(dataArr[i]); break; 
				case URL: addKey(dataArr[i]);  break;
				case BROWSER: addBrowser(dataArr[i]); break;
				case ACCESS_TIME: addTime(dataArr[i]); break;
				}
			}
			totalCnt++;
		}
		br.close();
	}
	
	//////////   add : 각 키와 키에대한 카운트횟수를 셀 Integer타입 0을 map에 할당   ///////////////////
	// Signal list map
	public void addSignal(String signal) {
		listSignal.add(signal);
		mapSignal.put(signal, 0);
	}
	// URL(key) list map 
	public void addKey(String url) {
		if((url.contains("key"))) {
			String key =url.substring(url.indexOf("=")+1,url.indexOf("&")); 
			listKey.add(key);
			mapKeyValue.put(key,0);
		}
	}
	// browser list map
	public void addBrowser(String browser) {
		listBrowser.add(browser);
		mapBrowser.put(browser,0);
	}
	// time list map
	public void addTime(String time) {
		String hour = time.substring(time.indexOf(" ")+1,time.indexOf(":"));
		listTime.add(hour);
		mapTime.put(hour,0);
	}
	
	
	//////////   add 끝   ///////////////////////////////
	
	////////cal : 키,0 으로 이루어진 맵에 키에대한 횟수 카운트해서 map value로 할당///////////
	// Signal 키와 키에대한 횟수카운트해서 map의 value 할당
	public void calSignalCnt(List<String> listSignal) {
		for(String signal : listSignal) {
			if(mapSignal.containsKey(signal)) {
				valueCnt = mapSignal.get(signal);
				mapSignal.put(signal, valueCnt+1);
			}
		}
	}
	// key 횟수 카운트해서 map의 value 할당
	public void calKeyValueCnt(List<String> listKey) {
		calKeyValueCnt(listKey,0,listKey.size());
	}
	// 라인입력될때 오버로딩
	public void calKeyValueCnt(List<String> listKey, int start, int end) {
		String key = null;
		for(int i=start;i<end;i++) {
			key = listKey.get(i);
			if(mapKeyValue.containsKey(key)) {
				valueCnt = mapKeyValue.get(key);
				mapKeyValue.put(key, valueCnt+1);
			}
		}
	}
	// Browser 횟수 카운트 map의 value 할당
	public void calBrowserCnt(List<String> listBrowser) {
		for(String browser : listBrowser) {
			if(mapBrowser.containsKey(browser)) {
				valueCnt = mapBrowser.get(browser);
				mapBrowser.put(browser, valueCnt+1);
			}
		}
	}
	// Time 횟수 카운트 map의 value 할당
	public void calTimeCnt(List<String> listTime) {
		for(String time : listTime) {
			if(mapTime.containsKey(time)) {
				valueCnt = mapTime.get(time);
				mapTime.put(time, valueCnt+1);
			}
		}
	}
	////////cal 끝//////////////////
	
	/////////////// analyze : map에서 문제별 결과값 반환 ///////////////////
	
	// Signal 문제 : 인자에따른 신호 횟수와 비율 반환
	public String analyzeSignalLog(String sign) {
		StringBuilder sb = new StringBuilder();
		int signCnt = mapSignal.get(sign);
		double signAvg ;
		switch(sign){
		case SUCCESS_SIGN:
			sb.append(String.format("서비스를 성공적으로 수행한(%s) 횟수 : %d 회%n",SUCCESS_SIGN,signCnt));
			break;
		case FAIL_SIGN:
			sb.append(String.format("서비스를 실패한(%s) 횟수 : %d 회%n",FAIL_SIGN,signCnt));
			break;
		case ABNOMAL_SIGN:
			signAvg = ((double)signCnt/totalCnt) *100 ;
			sb.append(String.format("비정상적요청(%s)이 발생한 횟수 : %d 회, 비율 : %.2f%%%n",ABNOMAL_SIGN,signCnt,signAvg));
			break;
		case ERROR_SIGN:
			signAvg = ((double)signCnt/totalCnt) *100;
			sb.append(String.format("요청에 대한 에러(%s)가 발생한 횟수 : %d 회, 비율 : %.2f%%%n",ERROR_SIGN,signCnt,signAvg));
			break;
		}
		return sb.toString();
	}
	// key 문제 : 최다사용 키, 횟수 반환
	public String analyzeMaxKeyLog(Map<String,Integer> mapKeyValue) {
		StringBuilder sb = new StringBuilder();
		Integer maxValue = Collections.max(mapKeyValue.values());
		String maxKey = null;
		for(Entry<String,Integer> entry : mapKeyValue.entrySet()) {
			if(entry.getValue()==maxValue) {
				maxKey = entry.getKey();
			}
		}
		sb.append(String.format("최다사용 키 %s, 횟수 %d 회 %n", maxKey,maxValue));
		return sb.toString();
	}
	// Browser 문제 : 모든 브라우저의 종류와 횟수, 비율 반환
	public String analyzeBrowserLog(Map<String,Integer> mapBrowser) {
		StringBuilder sb = new StringBuilder();
		double avg;
		sb.append("브라우저별 접속횟수, 비율 \n");
		for(Entry<String,Integer> entry : mapBrowser.entrySet()) {
			avg = ((double)entry.getValue()/totalCnt)*100; 
			sb.append(String.format("%s - %d 회 (%.2f%%) %n", entry.getKey(),entry.getValue(),avg));
		}
		return sb.toString();
	}
	// Time 문제 : 가장 많이 이용한 시간 반환
	public String analyzeTimeLog(Map<String,Integer> mapTime) {
		StringBuilder sb = new StringBuilder();
		String maxHour = null;
		int maxCnt = Collections.max(mapTime.values());
		for(Entry<String,Integer> entry : mapTime.entrySet()) {
			if(entry.getValue()==maxCnt) {
				maxHour = entry.getKey();
			}
		}
//		System.out.println(mapTime); // 존재하는 시간만 받은게 맞는지 확인용
		sb.append(String.format("요청이 가장 많은 시간 : %s 시%n", maxHour));
		return sb.toString();
	}
	////////////// analyze 끝 ///////////////////
	
	
	// 파일생성 
	public void createFile() throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("report_%s.dat", String.valueOf(System.currentTimeMillis())));
		
		File file = new File("c:/dev/report");
		file.mkdirs();
		FileWriter fw = null;
		System.out.println(file+"\\"+sb.toString());
		try {
			fw = new FileWriter(new File(file+"\\"+sb.toString()));
			fw.write(totalResult);
		} finally {
			fw.close();
		}
	}
	
	// cal method 합치기
	public void divideData() throws IOException {
		calKeyValueCnt(listKey);
		calBrowserCnt(listBrowser);
		calTimeCnt(listTime);
		calSignalCnt(listSignal);
		combineResult();
	}
	
	// lineView 버튼 연산
	public void inputLineView() throws IOException {
		int startLine = Integer.parseInt(JOptionPane.showInputDialog("시작라인을 입력해주세요",0));
		int endLine = Integer.parseInt(JOptionPane.showInputDialog("끝라인을 입력해주세요",listKey.size()));
		
		// 유효성검증 실패시 유효한 값 입력할때까지 반복
		while(startLine>=endLine||startLine<0||endLine>listKey.size() ) {
			JOptionPane.showMessageDialog(fs,String.format("유효하지않은 값을 입력했습니다.\n다시 입력해주세요. 유효범위 : %d ~ %d", 0,listKey.size()));
			startLine = Integer.parseInt(JOptionPane.showInputDialog("시작라인을 입력해주세요",0));
			endLine = Integer.parseInt(JOptionPane.showInputDialog("끝라인을 입력해주세요",listKey.size()));
		}
		calKeyValueCnt(listKey,startLine,endLine);
//		System.out.println(mapKeyValue); 맵,키 횟수확인용
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%d ~ %d line의 최디사용 키,횟수정보 %n %s",startLine,endLine,analyzeMaxKeyLog(mapKeyValue)));
		JOptionPane.showMessageDialog(fs,sb.toString());
	}
	
	// 전체 문제 StringBuilder 합치기
	public String combineResult() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년-MM월-dd일 kk:mm:ss");
		String sfileData = sdf.format(new Date());
		sb.append("---------------------------------------------------------------------------------------\n");
		sb.append(String.format("파일명(%s) 생성된 날짜 %s \n",fileName,sfileData));
		sb.append("---------------------------------------------------------------------------------------\n");
		sb.append("1."+analyzeMaxKeyLog(mapKeyValue)).append("\n");
		sb.append("2."+analyzeBrowserLog(mapBrowser)).append("\n");
		sb.append("3."+analyzeSignalLog(SUCCESS_SIGN));
		sb.append("  "+analyzeSignalLog(FAIL_SIGN)).append("\n");
		sb.append("4."+analyzeTimeLog(mapTime)).append("\n");
		sb.append("5."+analyzeSignalLog(ABNOMAL_SIGN)).append("\n");
		sb.append("6."+analyzeSignalLog(ERROR_SIGN)).append("\n");
		totalResult = sb.toString();
		return sb.toString();
	}
	
	// private변수 totalResult 가져오기
	public String getTotalResult() {
		return totalResult;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		fs.dispose();
	}
}