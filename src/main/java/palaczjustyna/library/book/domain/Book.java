package palaczjustyna.library.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import palaczjustyna.library.borrow.domain.Borrow;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column (name = "status")
    private Boolean status;

    @OneToMany (mappedBy = "book")
    List<Borrow> borrowList;

    public Book(String title, String author, Boolean status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

}
