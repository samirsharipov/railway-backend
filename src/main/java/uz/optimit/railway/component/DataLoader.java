package uz.optimit.railway.component;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.*;
import uz.optimit.railway.enums.Permission;
import uz.optimit.railway.enums.RoleType;
import uz.optimit.railway.factory.RoleFactorySingleton;
import uz.optimit.railway.factory.UserFactorySingleton;
import uz.optimit.railway.repository.JobExampleRepository;
import uz.optimit.railway.repository.JobRepository;
import uz.optimit.railway.repository.RoleRepository;
import uz.optimit.railway.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final JobRepository jobRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JobExampleRepository jobExampleRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            List<String> texts = List.of(
                    "Stansiyalarda signalizatsiyani to‘g‘riligini tekshirish hamda kirish, chiqish svetoforlarini ruxsat beruvchidan, taqiqlovchiga o‘tishini tekshirish;",
                    "marshrut ko‘rsatkichi ko‘rinishi va signalizatsiyasini to‘g‘riligini tekshirish.",
                    "Проверка правильнос сигнализации светофор на перегоне и изменения любого из разрещающих показаний на запрещающее",
                    "Проверка действия схем зависимостей устройств электрической централизации",
                    "Chaqmoqdan himoya qiluvchi qurilmalarni sozlash va tekshirish, yashin qaytargich va to‘g‘rilagichlarning TTU sexida tekshirilgan muddatlariga mos kelishini tekshirish. Ishchi va himoya yer o‘tkazgichlarini, shuningdek o‘zgaruvchan tokli uchastkalarda kabel qobig‘ini o‘lchash.",
                    "To‘g‘rilovchi konturlarni butunligini o‘lchov asboblari yordamida tekshirish.",
                    "Barcha uchastkalarda konstruksiyalarda tashqi qurilmalarini tekshirish va baholash.",
                    "Potensiallar farqini «rels-yer» tizimida  o‘lchash. Yerga o‘tkazuvchanlikni elektr qarshiligini  va o‘zgarmas tokli elektr tortqi uchastkalarida  karkasning fundament qismi armaturasida tok kuchini oqib o‘tishini o‘lchash; 0,6-0,8m chuqurlikdagi anodli, belgisi almashadigan, tok yo‘qotishi belgilangan  me’yordan uch marta ortiq bo‘lgan yer osti konstruksiyalarini  tekshirish va baholash.",
                    "Saqlagichlarni holatini tekshirish, kuyish nazorat sxemasini ishlashini tekshirish, mustahkam qotirilganligi, texnik hujjatlarda tasdiqlangan me’yorga mos kelishini tekshirish.",
                    "Tasmalarni tortilganligi va yaxshi joylashtirilganligini tekshirish, klapandagi oraliq, moy almashtirish, qo‘yilgan detallarni mahkamligini tekshirish va qismlarni tozalash; filtrlarni yuvish; generator va starterlarni tekshirish; boshqaruv shiti va avtomatika blogini montaj va detallarini tozalash.",
                    "O‘zgaruvchan tokni o‘chirib, germitizatsiyalangan va kam xizmat ko‘rsatiladigan akkumulyatorlar parametrlarini o‘lchash.",
                    "Ichki diagnostika vositalari bilan UTQ akkumulyatorlarining sig‘imini tekshirish."
            );

            for (String text : texts) {
              JobExample example = new JobExample();
              example.setName(text);
              jobExampleRepository.save(example);
            }


            UserFactorySingleton userFactorySingleton = UserFactorySingleton.getInstance();
            RoleFactorySingleton roleFactorySingleton = RoleFactorySingleton.getInstance();

            List<Permission> permissions = Arrays.asList(Permission.values());

            Role superAdmin = roleFactorySingleton.createRole("SUPER ADMIN", RoleType.SUPER_ADMIN, permissions, "super admin");
            Role admin = roleFactorySingleton.createRole("ADMIN", RoleType.ADMIN, permissions, "admin");
            roleRepository.saveAll(Arrays.asList(superAdmin, admin));

            User superAdminUser = userFactorySingleton.createUser("Samir", "Sharipov", "superAdmin", passwordEncoder.encode("123"), superAdmin);
            User adminUser = userFactorySingleton.createUser("Samir", "Sharipov", "admin", passwordEncoder.encode("123"), admin);
            userRepository.saveAll(Arrays.asList(superAdminUser, adminUser));

            List<Role> roles = Arrays.asList(
                    new Role("SHCH", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHCHG", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHCHD", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHCHZ", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHCHU", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHCHI", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "shch"),
                    new Role("SHNS", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "SHNS"),
                    new Role("SHN", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "SHN"),
                    new Role("SHSM", RoleType.EMPLOYEE, RolePermissions.ONLY_VIEW, "SHSM")
            );

            roleRepository.saveAll(roles);

        }
    }
}
