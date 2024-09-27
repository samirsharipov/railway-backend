package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.entity.Mtu;
import uz.optimit.railway.payload.EnterpriseDto;
import uz.optimit.railway.service.MtuService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnterpriseMapper {

    private final ModelMapper modelMapper;
    private final MtuService mtuService;

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<EnterpriseDto, Enterprise>() {

            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Enterprise, EnterpriseDto>() {

            @Override
            protected void configure() {
                map(source.getMtu().getId(),destination.getMtuId());
            }
        });
    }


    public Enterprise toEntity(EnterpriseDto enterpriseDto) {
        Enterprise enterprise = modelMapper.map(enterpriseDto, Enterprise.class);

        if (enterpriseDto.getMtuId() != null) {
            enterprise.setMtu(mtuService.getMtu(enterpriseDto.getMtuId()));
        }

        return enterprise;
    }

    public EnterpriseDto toDto(Enterprise enterprise) {
        return modelMapper.map(enterprise, EnterpriseDto.class);
    }

    public List<EnterpriseDto> toDto(List<Enterprise> enterprises) {
        return enterprises.stream()
                .map(this::toDto)
                .toList();
    }

    public void update(EnterpriseDto enterpriseDto, Enterprise enterprise) {

        if (enterpriseDto.getMtuId() != null) {
            enterprise.setMtu(mtuService.getMtu(enterpriseDto.getMtuId()));
        }
        modelMapper.map(enterpriseDto, enterprise);
    }
}
