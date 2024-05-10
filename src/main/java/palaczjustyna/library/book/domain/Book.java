package palaczjustyna.library.book.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import palaczjustyna.library.borrow.domain.Borrow;
import palaczjustyna.library.mutationTest.DoNotMutate;

import java.util.List;

@DoNotMutate
@Entity
@Data
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
    @Column(name = "isbn")
    private String isbn;
    @Column (name = "status")
    private Boolean status;

    @OneToMany (mappedBy = "book")
    List<Borrow> borrowList;

    public Book(String title, String author, String isbn, Boolean status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
    }
}
