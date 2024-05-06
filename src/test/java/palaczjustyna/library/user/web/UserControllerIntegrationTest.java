package palaczjustyna.library.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.AbstractIntegrationTest;
import palaczjustyna.library.user.domain.UserDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testShouldReturnAllUsers() {
        //given
        //when
        var result = getAllUsers();

        //then
        assertNotNull(result);
        assertThat(result).hasSize(4);
        assertEquals("Jan", result[0].firstName());
        assertEquals("Nowak", result[0].lastName());
    }

    @Test
    public void testShouldFindUserByName() {
        //given
        String userLastName = "Nowak";

        //when
        var result = findUserByLastName(userLastName, port);

        //then
        assertNotNull(result);
        assertThat(result).hasSize(1);
        assertEquals(userLastName, result[0].lastName());
    }

    @Test
    public void testShouldUpdateUser() {
        //given
        String userFirstName = "Adam";
        String userLastName = "Nowak";
        Integer userIdToUpdate = findUserByLastName(userLastName, port)[0].id();
        UserDTO userDTO = new UserDTO(userIdToUpdate, userFirstName,
                null, null, null, null, null);

        //when
        var result =  WebClient.create("http://localhost:" + port)
                .method(HttpMethod.PUT)
                .uri("updateUser")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(userDTO))
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        //then
        assertNotNull(result);
        assertEquals(userFirstName, result.firstName());
    }

    @Test
    public void testShouldAddUser() {
        //given
        int usersBeforeAddingNewOne = getAllUsers().length;
        String userFirstName = "Adam";
        String userLastName = "Nowak";
        UserDTO userDTO = new UserDTO(null, userFirstName,
                userLastName, userLastName, userLastName, LocalDate.of(2000,2,3), null);

        //when
        var result =  WebClient.create("http://localhost:" + port)
                .method(HttpMethod.POST)
                .uri("addUser")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .body(BodyInserters.fromValue(userDTO))
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        //then
        assertNotNull(result);
        assertEquals(usersBeforeAddingNewOne + 1, getAllUsers().length);
    }

    @Test
    public void testShouldDeleteUser() {
        //given
        int usersBeforeDeletingUser = getAllUsers().length;
        String userLastName = "Kowalczyk";
        Integer userIdToDelete = findUserByLastName(userLastName, port)[0].id();

        //when
        WebClient.create("http://localhost:" + port)
                .method(HttpMethod.DELETE)
                .uri(builder -> builder.path("/deleteUser").queryParam("id", userIdToDelete).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        //then
        assertEquals(usersBeforeDeletingUser - 1, getAllUsers().length);
    }

    private UserDTO[] getAllUsers() {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri("getUsers")
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(UserDTO[].class)
                .block();
    }

    public static UserDTO[] findUserByLastName(String lastName, int port) {
        return WebClient.create("http://localhost:" + port)
                .method(HttpMethod.GET)
                .uri(builder -> builder.path("/getUserByLastName").queryParam("lastName", lastName).build())
                .headers(headers -> headers.setBasicAuth("root", "root"))
                .retrieve()
                .bodyToMono(UserDTO[].class)
                .block();
    }

}