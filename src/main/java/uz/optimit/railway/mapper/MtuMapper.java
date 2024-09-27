package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.Mtu;
import uz.optimit.railway.entity.Role;
import uz.optimit.railway.payload.MtuDto;
import uz.optimit.railway.payload.RoleDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MtuMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void initMappings() {
        modelMapper.addMappings(new PropertyMap<MtuDto, Mtu>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }

    public Mtu toEntity(MtuDto mtuDto) {
        return modelMapper.map(mtuDto, Mtu.class);
    }

    public void updateEntity(MtuDto mtuDto, Mtu mtu) {
        modelMapper.map(mtuDto, mtu);
    }

    public MtuDto mtuDto(Mtu mtu) {
        return modelMapper.map(mtu, MtuDto.class);
    }

    public List<MtuDto> mtuDtoList(List<Mtu> mtuList) {
        return mtuList.stream().map(this::mtuDto).toList();
    }
}
