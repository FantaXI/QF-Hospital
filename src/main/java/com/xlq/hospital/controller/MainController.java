package com.xlq.hospital.controller;

import java.sql.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xlq.hospital.common.DataResp;
import com.xlq.hospital.model.Comment;
import com.xlq.hospital.model.Notice;
import com.xlq.hospital.service.ICommentService;
import com.xlq.hospital.service.INoticeService;
import com.xlq.hospital.service.impl.CommentService;

import net.sf.jsqlparser.schema.Database;

@Controller
@RequestMapping(value = "home")
public class MainController {
	@Autowired
	private INoticeService noticeService;
	@Autowired 
	private ICommentService commentService;
	//首页
	@RequestMapping("main")
	public String test() {
		return "index";
	}
	//首页获取notice列表
	@RequestMapping(value = "notice")
	@ResponseBody
	public DataResp getNotice(int pageNum, int pageSize,String type) {
		DataResp dataResp = new DataResp();	
		List<Notice> noticeList = noticeService.getNoticeByPage(pageNum, pageSize,type);	
		dataResp.setObjectResult(noticeList);
		dataResp.setPageNum(pageNum);
		dataResp.setTotalSize(noticeService.getNoticeCount(type));
		return dataResp;
	}
	@RequestMapping(value = "noticeList")
	public String noticeList() {
		return "noticeList";
	}
	@RequestMapping(value = "newsList")
	public String newsList() {
		return "newsList";
	}
	
	
	@RequestMapping(value = "noticeInfo")
	public ModelAndView noticeInfo(String id) {
		Notice notice = noticeService.getNoticeById(id);
		System.out.println(notice);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("noticeInfo", notice);	
		modelAndView.setViewName("noticeInfo");
		return modelAndView;
	}
	
	//访问留言页面
	@RequestMapping(value = "comment")
	public String comment() {
		return "comment";
	}
	//分页查询留言数据
	@RequestMapping("commentList")
	@ResponseBody
	public DataResp queryCommentByPage(int pageNum, int pageSize) {
		DataResp dataResp = new DataResp();
		Comment comment = new Comment();
		comment.setCommentType("1");
		List<Comment> list = commentService.queryCommentByPage(pageNum, pageSize, comment);
		dataResp.setTotalSize(commentService.queryCommentByPageCount(comment));
		//获取留言下面的回复
		for (Comment commentMain : list) {
			comment.setMainId(commentMain.getId());
			comment.setCommentType("2");
			List<Comment> replyList = commentService.queryCommentByPage(1, 100, comment);
			commentMain.setReplyList(replyList);
		}
		dataResp.setObjectResult(list);
		dataResp.setPageNum(pageNum);	
		return dataResp;
	}
	//查询我的留言/回复
	@RequestMapping(value = "myComment")
	public ModelAndView myComment() {
		ModelAndView modelAndView = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		//查询本人留言列表
		Comment comment = new Comment();
		comment.setCommentType("1");
		comment.setUserId(userId);
		List<Comment> commentList = commentService.queryCommentByPage(1, 999, comment);
		//查询本人回复列表
		Comment reply = new Comment();
		reply.setCommentType("2");
		reply.setUserId(userId);
		List<Comment> replyList = commentService.queryCommentByPage(1, 999, reply);
		
		modelAndView.addObject("commentList", commentList);
		modelAndView.addObject("replyList", replyList);
		modelAndView.setViewName("mineComment");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "mine")
	public String mine() {
		return "mine";
	}
}
