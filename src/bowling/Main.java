package bowling;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if(args.length>0 ) {
			System.out.println("result is :"+ prog(args[0]));
		}
	}
	
	public static int prog(String seq) throws Exception {
		seq = seq.replaceAll("\\s", "").replaceAll("-", "0");
		
		checkSeq(seq);
		
		int i = 1;
		double frame=0;
		int point = 0;
		
		while(frame<10 && seq.length()>=i) {
			String c = seq.substring(i-1, i);
			if(isNumeric(c)){
				point += Integer.parseInt(c);
				frame += 0.5;
			} else if("/".equals(c)) {
				point += countSpare(seq, i);
				point += countPoint(seq,i);
				frame += 0.5;
			} else if("X".equals(c)) {
				point +=10;
				point += countPoint(seq,i+1);
				point += countPoint(seq,i+2);
				frame +=1;
			}
			i+=1;
		}
		
		if(frame<10 && seq.endsWith("/")) {
			point = point -Integer.parseInt(seq.substring(i-3,i-2));
		}
		
		return point;
	}
	
	//check if sequence is valid or not
	private static void checkSeq(String seq) throws Exception {
		if(seq.length()>21 || seq.startsWith("/") || !seq.matches("^[0-9\\-X/]*$")) {
			throw new Exception("sequence not valid");
		}
		for(int i=0;i<seq.length();i+=2) {
			if((i+2) < seq.length() && isNumeric(seq.substring(i,i+1)) 
					&& isNumeric(seq.substring(i+1,i+2)) 
					&& Integer.parseInt(seq.substring(i,i+1))+Integer.parseInt(seq.substring(i+1,i+2))>10) {
				throw new Exception("sequence not valid");
			}
		}
		
	}

	private static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
	//point if numeric or strike
	private static int countPoint(String seq,int i) {
		if(i<=seq.length()) {
			String str = seq.substring(i-1,i);
			if(isNumeric(str)) {
				return Integer.parseInt(str);
			} else if("X".equals(str)) {
				return 10;
			} else if("/".equals(str)) {
				return(countSpare(seq, i));
			}
		}
		return 0;
	}
	
	//spare
	private static int countSpare(String seq, int i) {
		return 10 - Integer.parseInt(seq.substring(i-2, i-1));
	}
}
