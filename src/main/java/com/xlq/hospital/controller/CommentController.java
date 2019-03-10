package com.xlq.hospital.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Null;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xlq.hospital.common.DataResp;
import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.model.Comment;
import com.xlq.hospital.model.User;
import com.xlq.hospital.service.ICommentService;
@Controller
@RequestMapping(value="comment")
public class CommentController {
	
	@Autowired
	private ICommentService commentService;
	
	@RequestMapping(value = "addComment")
	public String addComment(String commentContent) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//用户未登录
		if (session.getAttribute("user")==null) {
			return "redirect:/login";
		}
		User user = (User)session.getAttribute("user");
		Comment comment = new Comment();
		comment.setId(IdUtil.getRandomId());
		comment.setCommentContent(commentContent);
		comment.setCommentType("1");
		comment.setUserId(user.getId());
		comment.setUserName(user.getName());
		comment.setCheckResult("1");
		int result = commentService.addComment(comment);
		return "redirect:/home/comment";
	}
	
	@RequestMapping(value = "addReply")
	@ResponseBody
	public DataResp addReply(String commentContent,String mainId) {
		DataResp dataResp = new DataResp();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute("user");
		Comment comment = new Comment();
		comment.setId(IdUtil.getRandomId());
		comment.setCommentContent(commentContent);
		comment.setCommentType("2");
		comment.setMainId(mainId);
		comment.setUserId(user.getId());
		comment.setUserName(user.getName());
		comment.setCheckResult("1");
		int result = commentService.addComment(comment);
		return dataResp;
	}
	@RequestMapping(value = "getComment")
	@ResponseBody
	public DataResp getCommentById(String id) {
		DataResp dataResp = new DataResp();
		Comment comment = commentService.getCommentById(id);
		Comment commentReq = new Comment();
		//添加评论列表
		commentReq.setMainId(id);
		commentReq.setCommentType("2");
		List<Comment> replyList = commentService.queryCommentByPage(1, 100, commentReq);
		comment.setReplyList(replyList);
		
		
		dataResp.setObjectResult(comment);
		return dataResp;
	}
	
	
}
