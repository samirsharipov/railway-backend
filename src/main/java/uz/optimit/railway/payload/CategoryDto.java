package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private UUID id;
    private String name;
    private String description;
    private int checkDay;
    private boolean isStation;
    private boolean isPeregon;
    private boolean isLevelCrossing;
}
