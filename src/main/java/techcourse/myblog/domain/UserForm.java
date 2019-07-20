package techcourse.myblog.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class UserForm {
    public static final String NAME_ERROR_MSG = "2글자 이상, 10글자 이하로 입력하세요. 숫자와 특수문자는 입력할 수 없습니다.";
    public static final String PASSWORD_ERROR_MSG = "소문자, 대문자, 숫자, 특수문자가 조합된 8글자 이상을 입력하세요.";
    public static final String EMAIL_ERROR_MSG = "이메일의 형식에 맞춰 입력하세요.";

    private long id;
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$", message = NAME_ERROR_MSG)
    private String name;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-]).{8,}$", message = PASSWORD_ERROR_MSG)
    private String password;
    @Email(message = EMAIL_ERROR_MSG)
    @Column(unique=true)
    private String email;

    public UserForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public User toUser() {
        if (id != 0) {
            return new User(id, name, password, email);
        }
        return new User(name, password, email);
    }
}
