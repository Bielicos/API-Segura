package demo.com.security.service;

import demo.com.security.dto.CreateTweetDto;
import demo.com.security.dto.FeedDto;
import demo.com.security.dto.FeedItemDto;
import demo.com.security.entity.Role;
import demo.com.security.entity.Tweet;
import demo.com.security.repository.TweetRepository;
import demo.com.security.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public void deleteUserById(String tweetId, JwtAuthenticationToken token) {
        var tweetFromDb = tweetRepository.findById(tweetId).orElseThrow( () -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found");
        });
        // Administrador pode deletar qualquer tweet de qualquer usuário
        var userId = token.getName();
        var userFromDb = userRepository.findById(userId);
        var isAdmin = userFromDb.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.toString()));

        // Os tweets só podem ser deletados pelos seus criadores, para usuários básicos.
        var authorized = tweetFromDb.getUser().getUserId().equals(token.getName());

        if (isAdmin || authorized) {
            tweetRepository.delete(tweetFromDb);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Delete tweet not authorized");
        }

    }

    public FeedDto createFeed(int page, int pageSize) {
        var tweets = tweetRepository.findAll(
                PageRequest.of(page, pageSize,Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet -> {
                    return new FeedItemDto(
                            tweet.getTweetId(),
                            tweet.getContent(),
                            tweet.getUser().getName()
                    );
                });

        return new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements());
    }
}
