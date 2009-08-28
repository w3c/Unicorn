package org.w3c.unicorn;

import java.util.Locale;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String s = "${TEST} sdf zer ${TEST2}";
		System.out.println(s.matches("\\$\\{[a-zA-Z_0-9]*\\}"));
		
		Matcher matcher = Pattern.compile("\\$\\{[a-zA-Z_0-9]*\\}").matcher(s);
		
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		
		System.out.println(matcher.lookingAt());
		System.out.println(matcher.group());
		System.out.println(matcher.group(0));
		System.out.println(matcher.group(1));
		System.out.println(matcher.lookingAt());
		System.out.println(matcher.group());
	     //replaceAll(repl)*/
		
		Locale loc = new Locale("sssqn");
		
		System.out.println("z" + loc.getLanguage());
	}

}
