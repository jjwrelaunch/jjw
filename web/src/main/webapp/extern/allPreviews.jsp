
<%@page import="java.util.Map"%>
<%@page import="de.jjw.service.PreviewManager"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="de.jjw.service.modelWeb.PreviewWeb"%>
<%@page import="de.jjw.service.modelWeb.PreviewWebTatamiClasses"%>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp" %>
<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : allPreviews.jsp
  ~ Created : 12 Feb 2011
  ~ Last Modified: Sat, 12 Feb 2011 21:02:55
  ~
  ~ Ju Jutsu Web is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ Ju Jutsu Web is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
  --%>
<f:view>

<h:form id="previewTatamiAction">
  <f:verbatim>    
   <%
   ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
   PreviewManager previewManager = (PreviewManager) context.getBean( "previewManager" );
   request.setAttribute("allPreviews",previewManager.getAllPreviews(request.getLocale()));
   request.setAttribute("allPreviewsTatamiClasses",previewManager.getAllPreviewTatamiClasses(request.getLocale()));
   //Map<Long, List<PreviewWebTatamiClasses>> getAllPreviewTatamiClasses()
    if(request.getAttribute("allPreviews")!= null && ((List<List<PreviewWeb>>)request.getAttribute("allPreviews")).size() >0){
    List<List<PreviewWeb>> previewList= (List<List<PreviewWeb>>)request.getAttribute("allPreviews"); int i =0; int tatamiCounter=0;
    Map<Long, List<PreviewWebTatamiClasses>> previewMapTatami = ( Map<Long, List<PreviewWebTatamiClasses>>) request.getAttribute("allPreviewsTatamiClasses");
    boolean twoRows = false;
%>
 <script type="text/javascript">   
   var layer = <%=(int)Math.ceil(previewList.size()/1.0)%>
   function reloadMe(para){
	   try{
		    window.clearTimeout(myTimer);
		  }
		  catch(event)
		  {}
	if(para < layer){	   
	   document.getElementById("#" + (para+1)).style.visibility = "visible";
	   document.getElementById("#" + (para+1)).style.display = "block";
	if (para > 0){
	 	  document.getElementById("#" + para).style.visibility = "hidden";
	   	  document.getElementById("#" + para).style.display = "none";
	}
	    myTimer = self.setTimeout("reloadMe(" + (para+1) + ")",10000);
   		}
	   else  window.location.reload();
   }
</script> 
 <body onload="reloadMe(0);"  >
 <%int j=-1, lastDivId=0;
 for(List<PreviewWeb> tatami: previewList){ i=0;j++; %>
  
 	<div id='<%="#" + ++lastDivId %>' align="center" style="display:none; visibility:hidden; position:absolute; z-index:99; top:0px; left:0px; height: auto; width:100%; ">
 	<table border="0">
   
      <tr>
        <td valign="top">
            <table border="1" cellspacing="0" width="100%">
                <%    for(PreviewWeb preview: tatami){
                    if (i>=5) break;
                    if(i==0){//head%>
                <tr >
                    <td align="center" colspan="3" class="previewTable">
                        <font style="font-size: 42px; text-decoration: none; font-weight : bold;" >
                            <b><%=preview.getTatami()%> </b>   
                        </font>
                    </td>
                </tr>
                <%  } i++;%>
                <tr>
                    <td width="450" class="previewTable"><font style="font-size: 48px; text-decoration: none; font-weight : bold;" ><%=preview.getNameRed()%></font></td>
                    <td width="450" class="previewTable"><font style="font-size: 48px; text-decoration: none; font-weight : bold;" ><%=preview.getNameBlue()%></font></td>
                    <td width="260" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" ><%=preview.getClassDescription()%></font></td>
                </tr>
                <%} %>
            </table>
            <br/>
            <%List<PreviewWebTatamiClasses> previewWebTatamiClasses = previewMapTatami.get(tatami.get( 0 ).getUserIdOfTatami( )); i=0;
              if (previewWebTatamiClasses !=null){
               for(PreviewWebTatamiClasses item: previewWebTatamiClasses){             
            if (i>=4) break; 
                    if(i==0){  //head %>
                <table border="1" cellspacing="0" width="100%">
                <tr >
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" >class</font></td>
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" >fights</font></td>
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" >start time</font></td>
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" >remaining time</font></td>
                </tr>
                <%  } i++;%>
                <tr>
                    <td width="270" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" ><%=item.getClassName()%></font></td>
                    <td width="230" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" ><%=item.getFights()%></font></td>
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" ><%=item.getEstStart()%></font></td>
                    <td width="250" class="previewTable"><font style="font-size: 32px; text-decoration: none; font-weight : bold;" ><%=item.getRestOfNeededTime()%></font></td>
                </tr>
                <%} %>
                <%if (previewWebTatamiClasses.size()>0){ %>
                	</table> 
                <%}   } %>
        </td>
    </tr>
 	
	</table> 	</div>    <%}%>
  
  
  
  
   <div id='<%="#" + ++lastDivId %>' align="center" style="display:none; visibility:hidden; position:absolute; z-index:99; top:0px; left:0px; height: auto; width:100%; ">
    <% // JJW presents%>
   </div>
   
   
   <div id='<%="#" + ++lastDivId %>' align="center" style="display:none; visibility:hidden; position:absolute; z-index:99; top:0px; left:0px; height: auto; width:100%; ">
    <% //next classes%>
    
   </div>
   
    
    <%if (false){ %>
   <div id='<%="#" + ++lastDivId %>' align="center" style="display:none; visibility:hidden; position:absolute; z-index:99; top:0px; left:0px; height: auto; width:100%; ">
    <% // commercial%>
   </div>
    <% } %>
   </body>
   
    <% } %>

   
</f:verbatim>
</h:form>
</f:view>
