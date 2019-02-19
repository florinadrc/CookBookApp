package cooking.book.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cooking.book.message.request.LoginForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RecipeControllerIntegrationTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String INVALID_USERNAME = "invalid_username";
    private static final String INVALID_PASSWORD = "invalid_password";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void when_noCredentials_expect_badRequestStatus() throws Exception {
        mockMvc.perform(post("/api/auth/login")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void when_wrongUsername_expect_unauthorizedStatus() throws Exception {
        LoginForm loginForm = new LoginForm(INVALID_USERNAME, PASSWORD);
        mockMvc.perform(post("/api/auth/login")
                .content(json(loginForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void when_wrongPassword_expect_unauthorizedStatus() throws Exception {
        LoginForm loginForm = new LoginForm(USERNAME, INVALID_PASSWORD);
        mockMvc.perform(post("/api/auth/login")
                .content(json(loginForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void when_wrongUsernameAndPassword_expect_unauthorizedStatus() throws Exception {
        LoginForm loginForm = new LoginForm(INVALID_USERNAME, INVALID_PASSWORD);
        mockMvc.perform(post("/api/auth/login")
                .content(json(loginForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void when_correctLoginParameters_expect_okStatus() throws Exception {
        mockMvc.perform(get("/api/recipes/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    private String getAuthenticationToken(String username, String password) throws Exception {
        LoginForm loginForm = new LoginForm(username, password);
        MvcResult response = mockMvc.perform(post("/api/auth/login")
                .content(json(loginForm))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        return conversionResultIntoToken(response.getResponse().getContentAsString());
    }

    private String conversionResultIntoToken(String token) {
        if (token.length() == 0) {
            return "";
        }

        return token
                .replaceAll("[{:}]", "")
                .replaceAll(" ", "")
                .replaceAll("\"", "")
                .substring(5);
    }

    private String json(Object o) throws IOException {
        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return mapper.writeValueAsString(o);
    }
}
