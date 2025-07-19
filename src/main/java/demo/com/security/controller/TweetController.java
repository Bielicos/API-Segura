package demo.com.security.controller;

import demo.com.security.dto.CreateTweetDto;
import demo.com.security.entity.Tweet;
import demo.com.security.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class TweetController {
    private final TweetService tweetService;
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto createTweetDto,
                                             JwtAuthenticationToken jwtAuthenticationToken) {
        var tweetId = tweetService.createTweet(createTweetDto, jwtAuthenticationToken);
        var location = URI.create("/tweets/" + tweetId);
        return ResponseEntity.created(location).build();
    }
}
