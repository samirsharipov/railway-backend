package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Category;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.CategoryDto;
import uz.optimit.railway.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public ApiResponse create(CategoryDto categoryDto) {
        Category category = fromDto(categoryDto, new Category());
        repository.save(category);
        return new ApiResponse("success", true);
    }

    private static Category fromDto(CategoryDto categoryDto, Category category) {
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setCheckDay(categoryDto.getCheckDay());
        category.setStation(categoryDto.isStation());
        category.setPeregon(categoryDto.isPeregon());
        category.setLevelCrossing(categoryDto.isLevelCrossing());
        return category;
    }

    public ApiResponse edit(UUID id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isEmpty())
            return new ApiResponse("not found", false);

        repository.save(fromDto(categoryDto, optionalCategory.get()));

        return new ApiResponse("success", true);
    }

    public ApiResponse getAll() {

        List<Category> all = repository.findAllByDeletedIsFalse();
        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("success", true, toDto(all));
    }

    public ApiResponse getById(UUID id) {

        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isEmpty())
            return new ApiResponse("not found", false);

        Category category = optionalCategory.get();

        CategoryDto dto = toDto(category);

        return new ApiResponse("success", true, dto);
    }

    private static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setCheckDay(category.getCheckDay());
        categoryDto.setStation(category.isStation());
        categoryDto.setPeregon(category.isPeregon());
        categoryDto.setLevelCrossing(category.isLevelCrossing());
        return categoryDto;
    }

    public static List<CategoryDto> toDto(List<Category> categories) {
        return categories.stream().map(CategoryService::toDto).toList();
    }

    public ApiResponse delete(UUID id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isEmpty())
            return new ApiResponse("not found", false);

        repository.softDelete(id);
        return new ApiResponse("success", true);
    }

    public ApiResponse getByIsStation(boolean isStation, boolean isLevelCrossing, boolean isPeregon) {

        List<Category> all;

        if (isStation)
            all = repository.findAllByStationIsTrueAndDeletedIsFalse();
        else if (isLevelCrossing)
            all = repository.findAllByLevelCrossingIsTrueAndDeletedIsFalse();
        else if (isPeregon)
            all = repository.findAllByPeregonIsTrueAndDeletedIsFalse();
        else
            all = new ArrayList<>();

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("success", true, toDto(all));
    }
}
