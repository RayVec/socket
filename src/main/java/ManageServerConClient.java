/**
 * 管理客户端连接的类
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;




public class ManageServerConClient {
	public static HashMap hm=new HashMap<String,ServerConClientThread>();
	public static ArrayList<String> accounts=new ArrayList<String>();
	
	//添加一个客户端通信线程
	public static void addClientThread(String account, ServerConClientThread cc){
		hm.put(account,cc);
		accounts.add(account);
	}
	public static void delClientThread(String account){
	    hm.remove(account);
	    for(int i=0;i<accounts.size();i++){
	    	if(accounts.get(i)==account){
	    		accounts.remove(i);
			}
		}
    }
	//得到一个客户端通信线程
	public static ServerConClientThread getClientThread(String i){
		return (ServerConClientThread) hm.get(i);
	}

}
