/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2015-07-13 09:28:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.default_.staff;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class overtime_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("var page=1;\r\n");
      out.write("function loadOvertime(){\r\n");
      out.write("\tvar oncallback=function(datas){\r\n");
      out.write("\t\tvar size=$(\"#overtime_list\").children().length;\r\n");
      out.write("\t\tvar html='';\t\t\r\n");
      out.write("\t\tfor(var i=0;i<datas.length;i++){\r\n");
      out.write("\t\t\tvar tr='<tr>';\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\ttr=tr+'<td>'+(size+i+1)+'</td>';\r\n");
      out.write("\t\t\ttr=tr+'<td>'+datas[i].user.name+'</td>';\r\n");
      out.write("            tr=tr+'<td>'+datas[i].yearNum+'-'+datas[i].monthNum+'-'+datas[i].dayNum+'</td>';\r\n");
      out.write("            tr=tr+'<td>'+datas[i].dailyBoard.taskType.name+'</td>';\r\n");
      out.write("            tr=tr+'<td>'+datas[i].dailyBoard.task+'</td>';\r\n");
      out.write("            if(datas[i].status == 'NOT_PROCESSED'){\r\n");
      out.write("            \ttr=tr+'<td><a onclick=\\'setstatus(\"'+datas[i].id+'\",0)\\'>无效</a><a onclick=\\'setstatus(\"'+datas[i].id+'\",1)\\'>确认</a></td>';\r\n");
      out.write("            }\r\n");
      out.write("            else if(datas[i].status == 'REJECTED'){\r\n");
      out.write("            \ttr=tr+'<td><span>无效</span></td>';\r\n");
      out.write("            }\r\n");
      out.write("            else if(datas[i].status == 'CONFIRMED'){\r\n");
      out.write("            \ttr=tr+'<td><span>已确认</span></td>';\r\n");
      out.write("            }\r\n");
      out.write("            else if(datas[i].status == 'TIMEOUT'){\r\n");
      out.write("            \ttr=tr+'<td><span>已超时</span></td>';\r\n");
      out.write("            }\r\n");
      out.write("            else if(datas[i].status == 'ABANDON'){\r\n");
      out.write("            \ttr=tr+'<td><span>放弃</span></td>';\r\n");
      out.write("            }\r\n");
      out.write("            \r\n");
      out.write("\r\n");
      out.write("\t\t\ttr=tr+'</tr>';\r\n");
      out.write("\t\t\thtml=html+tr;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#overtime_list\").append(html);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\turl:'/rest/user/getWorkOvertime.html?page='+page,\r\n");
      out.write("\t\ttype:'GET',\r\n");
      out.write("\t\tsuccess:function(datas){\r\n");
      out.write("\t\t\toncallback(datas.content);\r\n");
      out.write("\t\t\tpage=datas.number+1+1;\r\n");
      out.write("\t\t},\r\n");
      out.write("\t\terror:function(xhr){\r\n");
      out.write("\t\t\talert(xhr.responseText);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}).done(function(){\r\n");
      out.write("\t\t//page=page+1;\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function setstatus(id,status){\r\n");
      out.write("\t\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\turl:'/rest/user/overtime/status.html?id='+id+\"&enabled=\"+status,\r\n");
      out.write("\t\ttype:'GET',\r\n");
      out.write("\t\tsuccess:function(){\r\n");
      out.write("\t\t\twindow.location.reload();\r\n");
      out.write("\t\t},\r\n");
      out.write("\t\terror:function(xhr){\r\n");
      out.write("\t\t\talert(xhr.responseText);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("\tloadOvertime();\r\n");
      out.write("});\n");
      out.write("</script>\r\n");
      out.write(" <div class=\"bg clearfix\">\r\n");
      out.write("    \t<h2>功勋英雄 〉 <a href=\"/staff/dailyboard.html\">公事榜</a> 〉 加班审批</h2>\r\n");
      out.write("        <!--公事榜选择-->\r\n");
      out.write("        <div class=\"keepcenter-wrap\">\r\n");
      out.write("            <div class=\"keepcenter pagingdate\">\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    \t<table class=\"apply\">\r\n");
      out.write("            <thead>\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <th width=\"6%\">&nbsp;</th>\r\n");
      out.write("                    <th width=\"8%\">申请人</th>\r\n");
      out.write("                    <th width=\"22%\">日期</th>\r\n");
      out.write("                    <th width=\"17%\">项目</th>\r\n");
      out.write("                    <th width=\"27%\">内容</th>\r\n");
      out.write("                    <th width=\"20%\">加班审批</th>\r\n");
      out.write("                </tr>\r\n");
      out.write("            </thead>\r\n");
      out.write("            <tbody id=\"overtime_list\">\r\n");
      out.write("             \r\n");
      out.write("            </tbody>\r\n");
      out.write("        </table>\r\n");
      out.write("        <div class=\"blank50\"></div>\r\n");
      out.write("        <div class=\"keepcenter-wrap\">\r\n");
      out.write("            <div class=\"keepcenter\"><button class=\"btn-blue\" onclick=\"loadOvertime();\">加载更多</button></div>\r\n");
      out.write("        </div>\r\n");
      out.write("        \r\n");
      out.write("    \t<div class=\"blank100\"></div>\r\n");
      out.write("    </div>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}