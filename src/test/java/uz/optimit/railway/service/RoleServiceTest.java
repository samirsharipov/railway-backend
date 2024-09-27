package uz.optimit.railway.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.optimit.railway.entity.Role;
import uz.optimit.railway.enums.Permission;
import uz.optimit.railway.enums.RoleType;
import uz.optimit.railway.mapper.RoleMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.RoleDto;
import uz.optimit.railway.repository.RoleRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    /*•@Mock: Bu annotatsiya Mockito kutubxonasidan foydalanib ob’ektlarni mock qilish uchun ishlatiladi.
    Mock ob’ektlar asl ob’ektlarning o’rnini bosadi va
    ulardan qaytariladigan ma’lumotlarni sozlash imkonini beradi.
    */
    @Mock
    private RoleRepository roleRepository; // RoleRepository mock qilish

    @Mock
    private RoleMapper roleMapper; //RoleMapper mock qilish


    /*@InjectMocks: Bu annotatsiya mock ob’ektlarni injekt qilish uchun ishlatiladi.
     Ya’ni, mock ob’ektlar avtomatik ravishda test qilinayotgan sinfga qo‘shiladi.*/
    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateRole_SuperAdminRole() {

        RoleDto roleDto = new RoleDto(); // Test uchun yangi RoleDto ob'ekti yaratamiz.
        roleDto.setRoleType(RoleType.SUPER_ADMIN.name()); // RoleType'ni SUPER_ADMIN sifatida o'rnatamiz.

        ApiResponse response = roleService.create(roleDto); // RoleService.create metodini chaqiramiz.

        assertEquals("super admin or admin already exist", response.getMessage()); // Natijada xato xabarini tekshiramiz.
        assertFalse(response.isSuccess()); // Natija muvaffaqiyatsiz bo'lishi kerakligini tekshiramiz.
        verify(roleRepository, never()).save(any()); // roleRepository.save() metodini chaqirilmaganini tekshiramiz.
    }

    @Test
    public void testCreateRole_Success() {

        RoleDto roleDto = new RoleDto(); // Test uchun yangi RoleDto ob'ekti yaratamiz.
        roleDto.setRoleType(RoleType.EMPLOYEE.name()); // RoleType'ni USER sifatida o'rnatamiz.
        Role role = new Role(); // Yangi Role ob'ekti yaratamiz.

        when(roleMapper.toEntity(roleDto)).thenReturn(role); // RoleMapper.toEntity(roleDto) metodini chaqirganda role qaytarilishini sozlaymiz.
        when(roleRepository.save(role)).thenReturn(role); // roleRepository.save(role) metodini chaqirganda role qaytarilishini sozlaymiz.

        ApiResponse response = roleService.create(roleDto); // RoleService.create metodini chaqiramiz.

        assertEquals("role created", response.getMessage()); // Natijada muvaffaqiyatli xabarni tekshiramiz.
        assertTrue(response.isSuccess()); // Natija muvaffaqiyatli bo'lishi kerakligini tekshiramiz.
        verify(roleRepository, times(1)).save(role); // roleRepository.save(role) metodini faqat bir marta chaqirilganini tekshiramiz.
    }

    @Test
    public void testEditRole_SuperAdminRole() {
        UUID roleId = UUID.randomUUID();
        RoleDto roleDto = new RoleDto();
//        roleDto.setRoleType(RoleType.SUPER_ADMIN.name());

        ApiResponse response = roleService.edit(roleId, roleDto);

        assertEquals("role not found", response.getMessage());
        assertFalse(response.isSuccess());
        verify(roleRepository, never()).save(any());
    }

    @Test
    public void testEditRole_Success() {
        UUID roleId = UUID.randomUUID();
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleType(RoleType.EMPLOYEE.name());
        Role role = new Role();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        doNothing().when(roleMapper).updateEntity(roleDto, role); // Update entity is void
        when(roleRepository.save(role)).thenReturn(role);

        ApiResponse response = roleService.edit(roleId, roleDto);

        assertEquals("role updated", response.getMessage());
        assertTrue(response.isSuccess());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testGetAllRoles() {
        List<Role> roles = List.of(new Role("samir",RoleType.SUPER_ADMIN, Arrays.stream(Permission.values()).toList(),"fda"), new Role("admin",RoleType.ADMIN, Arrays.stream(Permission.values()).toList(),"fdjkal"));
        List<RoleDto> roleDtos = List.of(new RoleDto(), new RoleDto());

        when(roleRepository.saveAll(roles)).thenReturn(roles);
        when(roleRepository.findAllByDeletedIsFalse()).thenReturn(roles);
        when(roleMapper.roleDtoList(roles)).thenReturn(roleDtos);

        ApiResponse response = roleService.getAllRoles();

        assertEquals("found", response.getMessage());
        assertTrue(response.isSuccess());
//        assertEquals(roleDtos, response.getData());
    }



    @Test
    public void testGetById_Found() {
        UUID roleId = UUID.randomUUID();
        Role role = new Role();
        RoleDto roleDto = new RoleDto();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleMapper.roleDto(role)).thenReturn(roleDto);

        ApiResponse response = roleService.getById(roleId);

        assertEquals("role found", response.getMessage());
        assertEquals(true, response.isSuccess());
        assertEquals(roleDto, response.getData());
    }

    @Test
    public void testGetById_NotFound() {
        UUID roleId = UUID.randomUUID();

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        ApiResponse response = roleService.getById(roleId);

        assertEquals("role not found", response.getMessage());
        assertEquals(false, response.isSuccess());
        assertEquals(null, response.getData());
    }
}
