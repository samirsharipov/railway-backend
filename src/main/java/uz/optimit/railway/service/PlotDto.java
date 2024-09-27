package uz.optimit.railway.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotDto {

    private UUID id;
    private String name;
    private String description;
    private UUID enterpriseId;
}
