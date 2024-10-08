package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.LevelCrossing;
import uz.optimit.railway.mapper.LevelCrossingMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.LevelCrossingDto;
import uz.optimit.railway.repository.LevelCrossingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LevelCrossingService {
    private final LevelCrossingRepository levelCrossingRepository;
    private final LevelCrossingMapper levelCrossingMapper;

    public ApiResponse create(LevelCrossingDto levelCrossingDto) {
        if (levelCrossingDto.plotId() == null)
            return new ApiResponse("plot id not found", false);

        levelCrossingRepository.save(levelCrossingMapper.toEntity(levelCrossingDto));
        return new ApiResponse("level crossing created", true);
    }


    public ApiResponse update(UUID id, LevelCrossingDto levelCrossingDto) {
        Optional<LevelCrossing> optionalLevelCrossing =
                levelCrossingRepository.findById(id);

        if (optionalLevelCrossing.isEmpty())
            throw new IllegalArgumentException("level crossing not found");

        LevelCrossing levelCrossing = optionalLevelCrossing.get();
        levelCrossingMapper.update(levelCrossingDto, levelCrossing);
        levelCrossingRepository.save(levelCrossing);

        return new ApiResponse("level crossing updated", true);
    }

    public ApiResponse getAll() {
        List<LevelCrossing> all = levelCrossingRepository.findAllByDeletedIsFalse();
        if (all.isEmpty())
            throw new IllegalArgumentException("level crossing not found");

        return new ApiResponse("found", true, all.stream()
                .map(levelCrossingMapper::toDto)
                .toList());
    }


    public ApiResponse getById(UUID id) {
        Optional<LevelCrossing> optionalLevelCrossing = levelCrossingRepository.findById(id);
        if (optionalLevelCrossing.isEmpty())
            throw new IllegalArgumentException("level crossing not found");

        return new ApiResponse("level crossing found", true, levelCrossingMapper.toDto(optionalLevelCrossing.get()));
    }

    public ApiResponse getByPlotId(UUID plotId) {
        List<LevelCrossing> all = levelCrossingRepository.findAllByPlotIdAndDeletedIsFalse(plotId);
        if (all.isEmpty())
            throw new IllegalArgumentException("level crossing not found");

        return new ApiResponse("found", true, all.stream()
                .map(levelCrossingMapper::toDto)
                .toList());
    }

    public ApiResponse delete(UUID id) {
        Optional<LevelCrossing> optionalLevelCrossing = levelCrossingRepository.findById(id);
        if (optionalLevelCrossing.isEmpty())
            return new ApiResponse("not found", false);
        levelCrossingRepository.softDelete(id);

        return new ApiResponse("deleted", true);
    }
}
