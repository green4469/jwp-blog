package techcourse.myblog.domain.User;

import org.springframework.stereotype.Service;
import techcourse.myblog.web.ArticleNotFoundException;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void update(String email, UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(ArticleNotFoundException::new);
        user.update(userRequestDto);
    }
}
