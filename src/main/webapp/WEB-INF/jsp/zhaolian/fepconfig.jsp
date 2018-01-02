<%@ page import="java.util.*" %>

<%
Properties prop = new Properties();
prop.load(this.getClass().getClassLoader().getResourceAsStream("properties/const.properties"));

String POST_ACTION_URL = prop.getProperty("POST_ACTION_URL");
String POST_SDK_ACTION_URL = prop.getProperty("POST_SDK_ACTION_URL");
String INPUT_SDK_ACTION_URL = prop.getProperty("INPUT_SDK_ACTION_URL");
String LOAN_INPUT_ACTION_URL = prop.getProperty("LOAN_INPUT_ACTION_URL");
String LOAN_POST_URL=prop.getProperty("LOAN_POST_URL");
%>
