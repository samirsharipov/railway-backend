package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.User;
import uz.optimit.railway.jwt.JwtProvider;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.AuthenticationResponseDto;
import uz.optimit.railway.payload.LoginDto;
import uz.optimit.railway.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public ApiResponse login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDto.getUsername());
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Invalid username or password", false);
        }
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (DisabledException exception) {
            return new ApiResponse("User is disabled", false);
        } catch (AuthenticationException exception) {
            return new ApiResponse("Invalid username or password", false);
        }

        User user = optionalUser.get();
        String generatedToken = jwtProvider.generateToken(user.getUsername(), user.getRole());
        return new ApiResponse("successfully logged in", true, new AuthenticationResponseDto(generatedToken, user));
    }
}
