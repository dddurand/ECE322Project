package project322;

import com.cobra.ldtp.Ldtp;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ldtp obj = new Ldtp("frmCMPUT 291 Project");
		
		
		String[] list = obj.getWindowList();
				
		for(String item : list)
		{
			System.out.println(item);
		}
		
		System.out.println("--------------------");
		
		list = obj.getObjectList();
				
		for(String item : list)
		{
			System.out.println(item);
		}
		
		
		
		//String[] list2 = obj.getChild("ukn0", "", "");
		
//		for(String item : list2)
//		{
//			System.out.println(item);
//		}
		
	
		
//		 list = obj.getAppList();
//		
//		for(String item : list)
//		{
//			System.out.println(item);
//		}
//		
//		
//		list = obj.getWindowList();
//		
//		for(String item : list)
//		{
//			System.out.println(item);
//		}

		System.out.println("EXIT");
	}

}
