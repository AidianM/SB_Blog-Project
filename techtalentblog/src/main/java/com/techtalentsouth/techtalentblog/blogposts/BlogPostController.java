package com.techtalentsouth.techtalentblog.blogposts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogPostController {
	
	//Tell Spring Boot to set this automatically via INJECTION (Autowired)
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	private static List<BlogPost> posts = new ArrayList<>(); //intermediate hack to make this more understandable
	
	@GetMapping(value="/")
	public String index(BlogPost blogPost, Model model) { //model lets us access object via Thymeleaf key:value
		
//		BlogPost blogPost = new BlogPost(); 
		//using the variable input auto-instantiates this for us
		
//		blogPost.setAuthor("Scott Dossey"); //set default author
		
		model.addAttribute("posts", posts);
		//add in our posts variable to hold blogPosts
		//this also allows it to be able to show with th:
		
//		model.addAttribute("blogPost", blogPost);
		// re: Model model
		//with the parameter blogPost, this also gets handled for us
		return "blogpost/index";
		
	}
	
	//new mapping for /new:
	@GetMapping(value="/blogposts/new")
	public String newBlog(BlogPost post) {
		post.setAuthor("Scott Dossey"); //moved from index
		return "blogpost/new";
		
	}

	@PostMapping(value="/blogposts") //reflects changes with new.html et al
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		//"make us a blogPost, but check to see if there IS one first
		//complete with user entered info
		
		//this method's goal is to save a blogPost entered in the form to
		//the database
		
		//our db table is represented with BlogPostRepository
		BlogPost savedBlogPost = blogPostRepository.save(blogPost);
		posts.add(savedBlogPost);
		
		//we need "Model model" back in vars to handle this:
		model.addAttribute("title", savedBlogPost.getTitle());
		model.addAttribute("author", savedBlogPost.getAuthor());
		model.addAttribute("blogEntry", savedBlogPost.getBlogEntry());
		
		return "blogpost/result"; //note: this is matching the templates directory, not the variable
		
		//Slides have more, but unnecessary, code. This are the critical 
		//elements.
	}
	
}
