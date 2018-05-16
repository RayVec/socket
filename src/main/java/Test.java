public class Test {
    public static void main(String args[]){
        String result=HTTPUtils.doGet("http://112.74.177.29:8080/together/rtable/getUserAttendAll","JSESSIONID=CAAB15C8875801D62BF177C6F85FD760");
        System.out.println(result);
    }
}
