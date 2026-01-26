package com.tdd.user.service;

import com.tdd.user.entity.User;
import com.tdd.user.exception.UserNotFoundException;
import com.tdd.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
    @DisplayName("Create User Tests")
    class CreateUserTests {

        @Test
        @DisplayName("Should create user successfully")
        void createUser_WithValidData_ShouldReturnSavedUser() {
            // Given
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            // When
            User savedUser = userService.createUser(testUser);

            // Then
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getName()).isEqualTo("John Doe");
            assertThat(savedUser.getEmail()).isEqualTo("john@example.com");
            verify(userRepository, times(1)).save(testUser);
        }
    }

    @Nested
    @DisplayName("Read User Tests")
    class ReadUserTests {

        @Test
        @DisplayName("Should return all users")
        void getAllUsers_ShouldReturnUserList() {
            // Given
            User user2 = User.builder()
                    .id(2L)
                    .name("Jane Doe")
                    .email("jane@example.com")
                    .build();
            when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

            // When
            List<User> users = userService.getAllUsers();

            // Then
            assertThat(users).hasSize(2);
            assertThat(users).extracting(User::getName)
                    .containsExactly("John Doe", "Jane Doe");
            verify(userRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return user when exists")
        void getUserById_WhenUserExists_ShouldReturnUser() {
            // Given
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            // When
            User foundUser = userService.getUserById(1L);

            // Then
            assertThat(foundUser).isNotNull();
            assertThat(foundUser.getEmail()).isEqualTo("john@example.com");
            verify(userRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void getUserById_WhenUserNotExists_ShouldThrowException() {
            // Given
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> userService.getUserById(99L))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining("User not found with id: 99");
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class UpdateUserTests {

        @Test
        @DisplayName("Should update user successfully")
        void updateUser_WithValidData_ShouldReturnUpdatedUser() {
            // Given
            User updatedDetails = User.builder()
                    .name("John Updated")
                    .email("updated@example.com")
                    .password("newpassword")
                    .build();

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

            // When
            User updatedUser = userService.updateUser(1L, updatedDetails);

            // Then
            assertThat(updatedUser.getName()).isEqualTo("John Updated");
            assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Delete User Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete user successfully")
        void deleteUser_WhenUserExists_ShouldDeleteUser() {
            // Given
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            // When
            userService.deleteUser(1L);

            // Then
            verify(userRepository, times(1)).delete(testUser);
        }

        @Test
        @DisplayName("Should throw exception when deleting non-existent user")
        void deleteUser_WhenUserNotExists_ShouldThrowException() {
            // Given
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> userService.deleteUser(99L))
                    .isInstanceOf(UserNotFoundException.class);
        }
    }
}
