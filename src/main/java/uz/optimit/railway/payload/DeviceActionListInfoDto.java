package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceActionListInfoDto {
    private DeviceDto deviceDto;
    private List<ActionGetDto> actionDtoList;
}
