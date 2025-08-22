package com.pizzadelivery.backend.data;

import com.pizzadelivery.backend.entity.*;
import com.pizzadelivery.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PizzaTypeRepository pizzaTypeRepo;
    private final PizzaFlavorRepository pizzaFlavorRepo;
    private final PizzaExtraRepository pizzaExtraRepo;
    private final PizzaCrustRepository pizzaCrustRepo;
    private final BeverageRepository beverageRepo;
    private final BeverageCategoryRepository beverageCategoryRepo;
    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Roda a inicialização apenas se não houver admins cadastrados
        if (adminRepo.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // 1. Usuário Administrador
        adminRepo.save(Admin.builder()
                .name("Administrador do Sistema")
                .username("admin")
                .password(passwordEncoder.encode("123456789teste"))
                .build());

        // 2. Bordas
        PizzaCrust bordaCatupiry = pizzaCrustRepo.save(PizzaCrust.builder().name("Borda de Catupiry").description("Deliciosa borda recheada com Catupiry Original").price(8.0).build());
        PizzaCrust bordaCheddar = pizzaCrustRepo.save(PizzaCrust.builder().name("Borda de Cheddar").description("Cremosa borda de queijo cheddar para sua pizza").price(8.0).build());
        PizzaCrust bordaChocolate = pizzaCrustRepo.save(PizzaCrust.builder().name("Borda de Chocolate").description("Uma tentação de chocolate ao leite na borda").price(10.0).build());
        PizzaCrust bordaPaozinho = pizzaCrustRepo.save(PizzaCrust.builder().name("Borda Pãozinho").description("Borda recheada com alho, azeite e mussarela").price(12.0).build());

        // 3. Adicionais (Extras)
        PizzaExtra extraBacon = pizzaExtraRepo.save(PizzaExtra.builder().name("Extra Bacon").description("Porção extra de bacon crocante").price(6.0).build());
        PizzaExtra extraQueijo = pizzaExtraRepo.save(PizzaExtra.builder().name("Extra Queijo").description("O dobro de queijo mussarela para os amantes de queijo").price(5.0).build());
        PizzaExtra extraCatupiry = pizzaExtraRepo.save(PizzaExtra.builder().name("Extra Catupiry").description("Uma camada generosa de Catupiry Original").price(7.0).build());
        PizzaExtra extraMilho = pizzaExtraRepo.save(PizzaExtra.builder().name("Extra Milho").description("Adicione um toque adocicado com milho selecionado").price(3.0).build());

        // 4. Tipos de Pizza
        PizzaType grande = PizzaType.builder()
                .name("Pizza Grande")
                .description("Pizza de 35cm com 8 fatias, ideal para 3-4 pessoas")
                .basePrice(35.0)
                .availableCrusts(List.of(bordaCatupiry, bordaCheddar, bordaPaozinho))
                .availableExtras(List.of(extraBacon, extraQueijo, extraCatupiry, extraMilho))
                .build();
        pizzaTypeRepo.save(grande);

        PizzaType pequena = PizzaType.builder()
                .name("Pizza Pequena")
                .description("Pizza de 25cm com 4 fatias, perfeita para 1-2 pessoas")
                .basePrice(25.0)
                .availableCrusts(List.of(bordaCatupiry, bordaCheddar))
                .availableExtras(List.of(extraBacon, extraQueijo, extraMilho))
                .build();
        pizzaTypeRepo.save(pequena);

        PizzaType doce = PizzaType.builder()
                .name("Pizza Doce")
                .description("Pizza pequena especial com massa levemente adocicada")
                .basePrice(30.0)
                .availableCrusts(List.of(bordaChocolate))
                .build();
        pizzaTypeRepo.save(doce);


        // 5. Sabores Salgados
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Portuguesa").description("Presunto, mussarela, ovo, cebola, azeitona e molho de tomate").price(15.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/978ede23-af02-4a99-8f09-352ea32a4003.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Mussarela").description("Queijo mussarela de alta qualidade e molho de tomate fresco").price(12.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/6a5aa061-b15f-48f7-aded-f95543d34ccf.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Calabresa").description("Linguiça calabresa fatiada com cebola e azeitonas").price(14.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/f72e3338-5e8b-4514-8c17-c6e68088971f.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Frango com Catupiry").description("Frango desfiado temperado coberto com Catupiry Original").price(16.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/c16e0771-17d1-4e63-a0b4-06b8d476c90a.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Quatro Queijos").description("Mussarela, provolone, parmesão e gorgonzola").price(18.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/d9657c4a-071c-46fc-ac82-ebc8e04528fd.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Brócolis com Bacon").description("Brócolis frescos, bacon crocante e mussarela").price(17.0).pizzaTypes(List.of(grande, pequena)).imageUrl("/images/06a53f78-522e-47e1-b7e0-09e9ea8337c5.jpg").build());

        // 6. Sabores Doces
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Chocolate com Morango").description("Creme de avelã, morangos frescos e chocolate granulado").price(20.0).pizzaTypes(List.of(doce)).imageUrl("/images/ad0e8b20-fdb0-4b5d-80ea-49e2cc34addb.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Banana com Canela").description("Bananas fatiadas com açúcar, canela e leite condensado").price(18.0).pizzaTypes(List.of(doce)).imageUrl("/images/8447cdb0-b034-4539-85d4-f351346cbca8.jpg").build());
        pizzaFlavorRepo.save(PizzaFlavor.builder().name("Confete").description("Chocolate ao leite com confeitos coloridos").price(18.0).pizzaTypes(List.of(doce)).imageUrl("/images/80953b18-512e-4fb6-89e4-29783db53b0f.jpg").build());


        // 7. Categorias de Bebidas
        BeverageCategory refrigerantes = beverageCategoryRepo.save(BeverageCategory.builder().name("Refrigerantes").build());
        BeverageCategory sucos = beverageCategoryRepo.save(BeverageCategory.builder().name("Sucos").build());
        BeverageCategory cervejas = beverageCategoryRepo.save(BeverageCategory.builder().name("Cervejas").build());
        BeverageCategory aguas = beverageCategoryRepo.save(BeverageCategory.builder().name("Águas").build());

        // 8. Bebidas
        beverageRepo.save(Beverage.builder().name("Coca-Cola 2L").price(12.0).category(refrigerantes).alcoholic(false).imageUrl("/images/70bf54dc-6236-451e-a99d-8119b837528a.webp").build());
        beverageRepo.save(Beverage.builder().name("Guaraná Antarctica 2L").price(10.0).category(refrigerantes).alcoholic(false).imageUrl("/images/883f7010-c431-4c1a-9dae-01eba8b7c50e.webp").build());
        beverageRepo.save(Beverage.builder().name("Sprite Lata 350ml").price(5.0).category(refrigerantes).alcoholic(false).imageUrl("/images/94c00b49-b8c8-4db7-8c3f-69abd1d48d70.webp").build());
        beverageRepo.save(Beverage.builder().name("Suco Del Valle Uva 1L").price(8.0).category(sucos).alcoholic(false).imageUrl("/images/5d5d8641-ed36-479e-8322-f79af00e497a.webp").build());
        beverageRepo.save(Beverage.builder().name("Suco Del Valle Laranja 1L").price(8.0).category(sucos).alcoholic(false).imageUrl("/images/e7fb3082-ea76-4d09-8f36-d744df484e5e.webp").build());
        beverageRepo.save(Beverage.builder().name("Heineken Long Neck 330ml").price(9.0).category(cervejas).alcoholic(true).imageUrl("/images/cb4216fa-68c3-4311-b3bb-bc6a9ea22b39.webp").build());
        beverageRepo.save(Beverage.builder().name("Skol Lata 350ml").price(5.0).category(cervejas).alcoholic(true).imageUrl("/images/c2b6f3e3-ef2c-40c3-b972-671f363a604d.webp").build());
        beverageRepo.save(Beverage.builder().name("Água Mineral com Gás 500ml").price(4.0).category(aguas).alcoholic(false).imageUrl("/images/3caf3a5d-6e59-43e0-8e68-4ba2b7743416.webp").build());
    }
}