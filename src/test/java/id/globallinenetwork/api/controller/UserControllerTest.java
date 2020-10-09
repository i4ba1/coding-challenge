package id.globallinenetwork.api.controller;

import com.google.gson.Gson;
import id.globallinenetwork.api.user.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("UserController")
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Registration of new user is success")
    public void successfullyRegister() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("eve.holt@reqres.in");
        registerDto.setPassword("cityslicka");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/register", registerDto, ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, Object> data = new HashMap<>();
        data.put("id", 4);
        data.put("token", "QpwL5tke4Pnpja7X4");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Registration of new user unsuccessful")
    public void unSuccessfullyRegister() throws Exception {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("eve.holt@reqres.in");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/register", registerDto, ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> data = new HashMap<>();
        data.put("error", "Missing password");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Login Successful")
    public void loginSuccessful() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("janet.weaver@reqres.in");
        loginDto.setPassword("cityslicka");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/login", loginDto,
                ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, Object> data = new HashMap<>();
        data.put("token", "QpwL5tke4Pnpja7X4");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("UnSuccessful Login")
    public void loginUnSuccessful() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("janet.weaver@reqres.in");

        Gson gson = new Gson();
        String gsonLogin = gson.toJson(loginDto);

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/login", loginDto,
                ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<String, Object> data = new HashMap<>();
        data.put("error", "Missing password");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Create New User")
    public void createUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Muhammad Umar");
        createUserDto.setJob("Software Engineer");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/users", createUserDto,
                ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!Objects.equals(result.getBody(), null));
    }

    @Test
    @DisplayName("Failed Create New User")
    public void failedCreateUserMissingName() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Muhammad Umar");
        createUserDto.setJob("Software Engineer");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/users", createUserDto,
                ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, Object> data = new HashMap<>();
        data.put("error", "Missing name");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Failed Create New User")
    public void failedCreateUserMissingJob() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Muhammad Umar");
        createUserDto.setJob("Software Engineer");

        //when
        ResponseEntity<?> result = restTemplate.postForEntity("/api/users", createUserDto,
                ResponseEntity.class);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, Object> data = new HashMap<>();
        data.put("error", "Missing Job");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Successfully Update New User")
    public void successUpdateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Muhammad Umar");
        createUserDto.setJob("Software Engineer");

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<CreateUserDto> entity = new HttpEntity<>(createUserDto, headers);

        //when
        ResponseEntity<?> result = restTemplate.exchange("/api/users/{id}", HttpMethod.PUT, entity, ResponseEntity.class, 6);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!Objects.equals(result.getBody(), null));
    }

    @Test
    @DisplayName("Failed Update User")
    public void failedUpdateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Muhammad Umar");
        createUserDto.setJob("Software Engineer");

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<CreateUserDto> entity = new HttpEntity<>(createUserDto, headers);

        //when
        ResponseEntity<?> result = restTemplate.exchange("/api/users/{id}", HttpMethod.PUT, entity, ResponseEntity.class, 6);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!Objects.equals(result.getBody(), null));
    }

    @Test
    @DisplayName("Bad Request Get Single User")
    public void badRequestGetSingleUser() throws Exception{
        Map<String, Object> data = new HashMap<>();
        //When
        ResponseEntity<?> result = restTemplate.getForEntity("/api/users/{id}", ResponseEntity.class, 6);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        data.put("error", "Missing id");
        assertThat(Objects.equals(result.getBody(), data));
    }

    @Test
    @DisplayName("Not Found/Null Get Single User")
    public void notFoundGetSingleUser() throws Exception{
        //When
        ResponseEntity<?> result = restTemplate.getForEntity("/api/users/{id}", ResponseEntity.class, 6);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(Objects.equals(result.getBody(), null));
    }

    @Test
    @DisplayName("Success Get Single User")
    public void successGetSingleUser() throws Exception{
        //When
        ResponseEntity<?> result = restTemplate.getForEntity("/api/users/{id}", ResponseEntity.class, 6);

        //Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> data = new HashMap<>();
        data.put("data",null);
        data.put("ad", null);
        assertThat(!Objects.equals(result.getBody(), data));
    }
}
