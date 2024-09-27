package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.mapper.EnterpriseMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.EnterpriseDto;
import uz.optimit.railway.repository.EnterpriseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository repository;
    private final EnterpriseMapper mapper;

    public ApiResponse create(EnterpriseDto enterpriseDto) {
        if (enterpriseDto.getMtuId() == null) {
            return new ApiResponse("mtu id is required", false);
        }
        repository.save(mapper.toEntity(enterpriseDto));
        return new ApiResponse("success", true);
    }


    public ApiResponse edit(UUID id, EnterpriseDto enterpriseDto) {
        Optional<Enterprise> optionalEnterprise =
                repository.findById(id);

        if (optionalEnterprise.isEmpty())
            throw new IllegalArgumentException("Enterprise with id " + id + " not found");

        if (enterpriseDto.getMtuId() == null)
            throw new IllegalArgumentException("Mtu id is required");


        Enterprise enterprise = optionalEnterprise.get();
        mapper.update(enterpriseDto, enterprise);
        repository.save(enterprise);

        return new ApiResponse("success", true);
    }

    public ApiResponse getAll() {
        List<Enterprise> all = repository.findAll();
        if (all.isEmpty())
            throw new IllegalArgumentException("Enterprise list is empty");

        return new ApiResponse("success", true, mapper.toDto(all));
    }

    public ApiResponse getById(UUID id) {
        Optional<Enterprise> optionalEnterprise = repository.findById(id);
        if (optionalEnterprise.isEmpty())
            throw new IllegalArgumentException("Enterprise with id " + id + " not found");

        return new ApiResponse("success", true, mapper.toDto(optionalEnterprise.get()));
    }

    public ApiResponse getByMtu(UUID mtuId) {
        List<Enterprise> all = repository.findAllByMtuId(mtuId);
        if (all.isEmpty())
            throw new IllegalArgumentException("Enterprise list is empty");

        return new ApiResponse("success", true, mapper.toDto(all));
    }

    public Enterprise getEnterpriseById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
