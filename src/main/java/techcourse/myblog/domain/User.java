package techcourse.myblog.domain;

import lombok.*;
import techcourse.myblog.service.dto.UserRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void update(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.password = userRequestDto.getPassword();
        this.email = userRequestDto.getEmail();
    }

    public boolean isMatch(UserRequestDto userRequestDto) {
        return email.equals(userRequestDto.getEmail())
                && password.equals(userRequestDto.getPassword());
    }
}
