package demo.com.security.controller;

import demo.com.security.dto.CreateTweetDto;
import demo.com.security.dto.FeedDto;
import demo.com.security.entity.Tweet;
import demo.com.security.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") String tweetId,
                                            JwtAuthenticationToken jwtAuthenticationToken) {

        tweetService.deleteUserById(tweetId, jwtAuthenticationToken);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var feed = tweetService.createFeed(page, pageSize);
        return ResponseEntity.ok().body(feed);
    }
}
