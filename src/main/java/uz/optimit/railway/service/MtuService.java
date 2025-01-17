package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Mtu;
import uz.optimit.railway.mapper.MtuMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.MtuDto;
import uz.optimit.railway.repository.MtuRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MtuService {

    private final MtuRepository mtuRepository;
    private final MtuMapper mtuMapper;

    public ApiResponse create(MtuDto mtuDto) {
        mtuRepository.save(mtuMapper.toEntity(mtuDto));
        return new ApiResponse("Mtu created", true);
    }

    public ApiResponse edit(UUID id, MtuDto mtuDto) {
        Optional<Mtu> optionalMtu = mtuRepository.findById(id);
        if (optionalMtu.isEmpty()) {
            return new ApiResponse("Mtu not found");
        }
        Mtu mtu = optionalMtu.get();
        mtuMapper.updateEntity(mtuDto, mtu);
        mtuRepository.save(mtu);
        return new ApiResponse("Mtu updated", true);
    }


    public ApiResponse getAll() {
        List<Mtu> all = mtuRepository.findAllByDeletedIsFalse();
        if (all.isEmpty()) {
            return new ApiResponse("Mtu not found");
        }
        List<MtuDto> mtuDtoList = mtuMapper.mtuDtoList(all);
        return new ApiResponse("found", true, mtuDtoList);
    }

    public ApiResponse getById(UUID id) {
        Optional<Mtu> optionalMtu = mtuRepository.findById(id);
        if (optionalMtu.isEmpty()) {
            return new ApiResponse("Mtu not found");
        }
        Mtu mtu = optionalMtu.get();
        MtuDto mtuDto = mtuMapper.mtuDto(mtu);
        return new ApiResponse("found", true, mtuDto);
    }

    public Mtu getMtu(UUID id) {
        return mtuRepository.findById(id).orElse(null);
    }

    public ApiResponse delete(UUID id) {
        Optional<Mtu> optionalMtu = mtuRepository.findById(id);
        if (optionalMtu.isEmpty()) {
            return new ApiResponse("Mtu not found");
        }
        mtuRepository.softDelete(id);
        return new ApiResponse("Mtu deleted", true);
    }
}
