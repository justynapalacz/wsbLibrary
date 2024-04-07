package palaczjustyna.library.borrow.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import palaczjustyna.library.book.domain.Book;
import palaczjustyna.library.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
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
