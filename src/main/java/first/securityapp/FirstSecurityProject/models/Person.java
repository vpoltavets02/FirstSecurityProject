package first.securityapp.FirstSecurityProject.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Username can not be empty")
    @Size(min = 2, max = 100, message = "Username length should be between 2 and 100 characters")
    private String username;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "The year of birth should be greater than 1900")
    private int yearOfBirth;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Person(String username, int yearOfBirth) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
    }
}