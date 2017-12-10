<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>在线支付</title>
  </head>
  
  <body>
        <form action="/buyer/pay.shtml" method="post">
      	<table width="60%">
			<tr>  
				<td bgcolor="#F7FEFF" colspan="4">
			 		订单号：<INPUT TYPE="text" NAME="orderId" value="${orderId }"> 
			 		支付金额：<INPUT TYPE="text" NAME="money" size="6" value="0.01" readonly="readonly">元
			 	</td>
			</tr>
			<tr><td><br/></td></tr>
			<tr>
				<td>请您选择在线支付银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBCHINA-NET">招商银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ICBC-NET">工商银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ABC-NET">农业银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CCB-NET">建设银行 </td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBC-NET">中国民生银行总行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CEB-NET" >光大银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOCO-NET">交通银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SDB-NET">深圳发展银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOC-NET">中国银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="PINGANBANGK-NET">平安银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CBHB-NET">渤海银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="HKBEA-NET">东亚银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BCCB-NET">北京银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CIB-NET">兴业银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SPDB-NET">上海浦东发展银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ECITIC-NET">中信银行</td>
			</tr>
			<tr><td><br/></td></tr>
			<tr>
			  <td><INPUT TYPE="submit" value="确定支付"></td>
			</tr>
     	</table>
   		</form>
  </body>
</html>
