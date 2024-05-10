package palaczjustyna.library.borrow.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.mutationTest.DoNotMutate;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;

@DoNotMutate
@Entity
@Data
@Table(name = "borrow")
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_of_borrow")
    private LocalDateTime dateOfBorrow;

    @Column(name = "date_of_return")
    private LocalDateTime dateOfReturn;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Borrow(User user, Book book, LocalDateTime dateOfBorrow) {
        this.user = user;
        this.book = book;
        this.dateOfBorrow = dateOfBorrow;
    }
}
