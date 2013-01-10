<!--
 ~ Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 ~
 ~ WSO2 Inc. licenses this file to you under the Apache License,
 ~ Version 2.0 (the "License"); you may not use this file except
 ~ in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~    http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied.  See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 -->

<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%><html>
<head></head>
<body>

<form method="post" action="<%=session.getAttribute("replyTo")%>">
<p><input type="hidden" name="wa"
	value="<%=session.getAttribute("action")%>" /> <input type="hidden"
	name="wresult" value="<%=session.getAttribute("results")%>" />
	<input type="hidden"
	name="wctx" value="<%=session.getAttribute("context")%>" />
<button type="submit">POST</button>
</p>
</form>

<script type="text/javascript">
   document.forms[0].submit();
</script>

</body>
</html>