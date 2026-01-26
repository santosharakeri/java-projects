package com.tdd.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.user.entity.User;
import com.tdd.user.exception.UserNotFoundException;
import com.tdd.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("UserController Integration Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .build();
    }

    @Nested
    @DisplayName("POST /api/users")
    class CreateUserEndpoint {

        @Test
        @DisplayName("Should create user and return 201")
        void createUser_WithValidData_ShouldReturn201() throws Exception {
            when(userService.createUser(any(User.class))).thenReturn(testUser);

            mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testUser)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("John Doe"))
                    .andExpect(jsonPath("$.email").value("john@example.com"));

            verify(userService, times(1)).createUser(any(User.class));
        }

        @Test
        @DisplayName("Should return 400 for invalid data")
        void createUser_WithInvalidData_ShouldReturn400() throws Exception {
            User invalidUser = User.builder().name("").email("invalid-email").build();

            mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /api/users")
    class GetAllUsersEndpoint {

        @Test
        @DisplayName("Should return list of users")
        void getAllUsers_ShouldReturnUserList() throws Exception {
            User user2 = User.builder().id(2L).name("Jane").email("jane@example.com").build();
            when(userService.getAllUsers()).thenReturn(Arrays.asList(testUser, user2));

            mockMvc.perform(get("/api/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name").value("John Doe"))
                    .andExpect(jsonPath("$[1].name").value("Jane"));
        }

        @Test
        @DisplayName("Should return empty list when no users")
        void getAllUsers_WhenEmpty_ShouldReturnEmptyList() throws Exception {
            when(userService.getAllUsers()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/users"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    @Nested
    @DisplayName("GET /api/users/{id}")
    class GetUserByIdEndpoint {

        @Test
        @DisplayName("Should return user when exists")
        void getUserById_WhenExists_ShouldReturnUser() throws Exception {
            when(userService.getUserById(1L)).thenReturn(testUser);

            mockMvc.perform(get("/api/users/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.email").value("john@example.com"));
        }

        @Test
        @DisplayName("Should return 404 when not found")
        void getUserById_WhenNotExists_ShouldReturn404() throws Exception {
            when(userService.getUserById(99L)).thenThrow(new UserNotFoundException(99L));

            mockMvc.perform(get("/api/users/99"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PUT /api/users/{id}")
    class UpdateUserEndpoint {

        @Test
        @DisplayName("Should update user successfully")
        void updateUser_WithValidData_ShouldReturnUpdatedUser() throws Exception {
            User updatedUser = User.builder()
                    .id(1L)
                    .name("John Updated")
                    .email("updated@example.com")
                    .password("newpass123")
                    .build();

            when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

            mockMvc.perform(put("/api/users/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedUser)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John Updated"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/users/{id}")
    class DeleteUserEndpoint {

        @Test
        @DisplayName("Should delete user and return 204")
        void deleteUser_WhenExists_ShouldReturn204() throws Exception {
            doNothing().when(userService).deleteUser(1L);

            mockMvc.perform(delete("/api/users/1"))
                    .andExpect(status().isNoContent());

            verify(userService, times(1)).deleteUser(1L);
        }
    }
}

