package demo.com.security.service;

import demo.com.security.dto.CreateTweetDto;
import demo.com.security.entity.Tweet;
import demo.com.security.repository.TweetRepository;
import demo.com.security.repository.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public String createTweet(CreateTweetDto dto, JwtAuthenticationToken token) {
        var userFromDb = userRepository.findById(token.getName()); // Recebemos o userId como parâmetro, pois o getName é o subject do Token que contem como valor o Id do usuário.

        var tweet = new Tweet();
        tweet.setContent(dto.content());
        tweet.setUser(userFromDb.get());

        var newTweet = tweetRepository.save(tweet);
        return newTweet.getTweetId();
    }
}
