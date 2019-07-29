package techcourse.myblog.domain;

import lombok.*;
import techcourse.myblog.service.dto.UserRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uniqueEmailConstraint")}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

//    @OneToMany(mappedBy = "author")  // User가 종속적인 엔티티이고, Article이 주인 엔티티
//    private List<Article> articles = new ArrayList<>();

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

//    public void addArticle(Article persistArticle) {
//        this.articles.add(persistArticle);
//    }
}
