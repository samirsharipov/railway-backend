package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Device;
import uz.optimit.railway.repository.DeviceRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceGetService {
    private final DeviceRepository repository;

    public Device getDeviceById(UUID id) {
        Optional<Device> optionalDevice = repository.findById(id);
        return optionalDevice.orElse(null);
    }
}
