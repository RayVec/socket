
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import common.ChatMessage;
import common.ChatMessageType;
import common.User;


public class ChatServer {
	public ChatServer() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5469);
			System.out.println("服务器已启动 in " + new Date() + " " + InetAddress.getLocalHost());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				Socket s = ss.accept();
				//接受客户端发来的信息
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				ChatMessage m = new ChatMessage();
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				if (u.getOperation().equals("login")) { //登录
					String account = u.getAccount();
					System.out.println("[" + account + "]上线了！"+new Date());

					//得到个人信息
					//String user=udao.getUser(account);
					//返回一个成功登陆的信息包，并附带个人信息
					m.setType(ChatMessageType.SUCCESS);
					m.setContent(u.getAccount());
					oos.writeObject(m);
					ServerConClientThread cct = new ServerConClientThread(s);//单开一个线程，让该线程与该客户端保持连接
					cct.setID(u.getAccount());
					ManageServerConClient.addClientThread(u.getAccount(), cct);
					System.out.println("当前连接的线程有" + ManageServerConClient.hm.size());
					cct.start();//启动与该客户端通信的线程
				}
		        else if (u.getOperation().equals("register")) {
			/**注册用户
			 UserDao udao=new UserDao();
			 if(udao.register(u)){
			 //返回一个注册成功的信息包
			 m.setType(common.ChatMessageType.SUCCESS);
			 oos.writeObject(m);
			 }
			 */
		           }
	       }
		        catch (StreamCorruptedException e ){
			         System.out.println("invalid stream header ,go on "+new Date());
			}
					catch (Exception e){
			         e.printStackTrace();
			}
		}
	}

}
