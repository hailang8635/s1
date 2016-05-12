package smart.s2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdRecovery {
	private static final Logger logger = LoggerFactory.getLogger(IdRecovery.class.getName());

	private AtomicInteger i = new AtomicInteger();
	
	@Test
	public void testID() throws IOException{
		///Users/hailang/Documents/代码
//		PropertyConfigurator.configure("log4j2.xml");//加载.properties文件
//		Logger log=Logger.getLogger("org.zblog.test");
	       
		String filePath = "/Users/hailang/Documents/代码/text3";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String str;
		while( (str=br.readLine())!=null){
//			System.out.println(str);
			makeID(str);
			logger.info("\n\r");
		}
		br.close();
		
	}
	public void makeID(String id){
		for(int m=1;m<=12;m++){
			String ms = m<10?("0"):("");
			for(int d=1;d<=31;d++){
				String ds = d<10?("0"):("");
				//logger.debug(id.substring(0, 10)+"    "+ms+m+ds+d+"  "+id.substring(14));
				isCertNo(id.substring(0, 10)+ms+m+ds+d+id.substring(14));
			}
		}
	}
	
	/**
	 * 二代身份证校验
	 * @param certNo 4105811958XXXX7314
	 * @return 是否是正确身份证号码
	 */
	public boolean isCertNo(String certNo) {
		if (StringUtils.isBlank(certNo) || certNo.length() != 18) {
			return false;
		}
		String ai = certNo.substring(0, 17);
		if (!Pattern.matches("[0-9]+", ai))
			return false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);
		try {
			sdf.parse(certNo.substring(6, 14));
		} catch (ParseException e) {
			return false;
		}

		String arrVerifyCode[] = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2", };
		int wi[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		int ret = 0;
		for (int i = 0; i < 17; i++) {
			ret += (ai.charAt(i) - 48) * wi[i];
		}
		ai += arrVerifyCode[ret %= 11];

		if (!certNo.toLowerCase().equals(ai)) {
			//logger.debug(certNo + "是不正确的证件号，正确的应该是：{}", ai);
		}else{
			logger.info(i.incrementAndGet()+" "+certNo);
		}
		return certNo.toLowerCase().equals(ai);
	}

}
