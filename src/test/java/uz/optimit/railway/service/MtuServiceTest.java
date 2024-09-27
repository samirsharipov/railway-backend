package uz.optimit.railway.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.MtuDto;
import uz.optimit.railway.repository.MtuRepository;

import static org.junit.jupiter.api.Assertions.*;

class MtuServiceTest {

    @Mock
    private MtuRepository mtuRepository;

    @InjectMocks
    private MtuService mtuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        MtuDto mtuDto = new MtuDto();

        ApiResponse apiResponse = mtuService.create(mtuDto);

    }

    @Test
    void edit() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }
}