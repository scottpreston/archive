<%@ page import="com.scottpreston.javarobot.chapter2.*" %><%

// WebClient class will throw exception if these are not set
String portId = request.getParameter("portid");
String action = request.getParameter("action");
String cmdInput = request.getParameter("commands");
String timeout = request.getParameter("timeout");
String dtr = request.getParameter("dtr");
try {

WebSerialPort com = new WebSerialPort(portId);
out.println(com.execute(action,cmdInput,timeout,dtr));

} catch (Exception e) {
out.println(e);
int term = '!';
%>

usage: /webcom.jsp?portid=[1,2,..]&action=[r,w,wr]&commands=[100,120,222,..]&timeout=[0,50,..]&dtr=true
<p>sample:
<a href="/webcom.jsp?portid=1&action=wr&commands=100,<%=term%>&timeout=0&dtr=true">sample 1</a>

<% }%>