package com.ssdi.project.bookExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdi.project.bookExchange.controller.LoginController;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

    @Mock
    private UserService mockUserService;
    @InjectMocks
    private LoginController loginController;
    private User user;
    private User user1;
    private User user2;
    private MockMvc mockMvc;
    private ObjectMapper obj_mapper;
    private Authentication authentication;
    private SecurityContext securityContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        obj_mapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<User> users = new ArrayList<>();
        user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .password("password")
                .email("test@test.com")
                .build();
        user1 = User.builder()
                .id(2)
                .name("Parth")
                .lastName("Mehta")
                .email("testParth@test.com")
                .build();
        user2 = User.builder()
                .id(2)
                .name("Rohit")
                .lastName("Sharma")
                .email("rohitSharma@test.com")
                .build();
        users.add(user);
        users.add(user1);
        users.add(user2);

        Mockito.when(mockUserService.saveUser(any())).thenReturn(user);
        Mockito.when(mockUserService.listAll()).thenReturn(users);
        Mockito.when(mockUserService.getUserById(anyInt())).thenReturn(user);
        Mockito.when(mockUserService.findUserByEmail(anyString())).thenReturn(null,user);
    }

    @Test
    public void createNewUserTest() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/registration")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(String.valueOf(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists()).andDo(print());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void getUserTest() throws Exception{
        String authHeader = "Basic " + Base64Utils.encodeToString("test@test.com:password".getBytes());

        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/user").header(HttpHeaders.AUTHORIZATION,authHeader)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
