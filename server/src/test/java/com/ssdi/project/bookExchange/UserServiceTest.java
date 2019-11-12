package com.ssdi.project.bookExchange;

import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.RoleRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import com.ssdi.project.bookExchange.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                mockRoleRepository,
                mockBCryptPasswordEncoder);
        List<User> users = new ArrayList<>();
        user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .build();
        User user1 = User.builder()
                .id(2)
                .name("Parth")
                .lastName("Mehta")
                .email("testParth@test.com")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("Rohit")
                .lastName("Sharma")
                .email("rohitSharma@test.com")
                .build();
        users.add(user);
        users.add(user1);
        users.add(user2);

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.getById(anyInt()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findAll())
                .thenReturn(users);
    }

    @Test
    public void testFindUserByEmail() {
        final String email = "test@test.com";
        final User result = userServiceUnderTest.findUserByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserById() {
        final int id = 1;
        final User result = userServiceUnderTest.getUserById(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testSaveUser() {
        final String email = "test@test.com";
        User result = userServiceUnderTest.saveUser(user);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testListAll(){
        List<User> al = userServiceUnderTest.listAll();

        assertEquals(true,al.size()>0);
        assertEquals("Gustavo",al.get(0).getName());
        assertEquals("Parth",al.get(1).getName());
        assertEquals("Rohit",al.get(2).getName());

        assertNotEquals("test@test.com", al.get(1).getEmail());
        assertNotEquals("test@test.com", al.get(2).getEmail());
    }
}
