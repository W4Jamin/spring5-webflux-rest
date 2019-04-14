package by9ye.springframework.spring5webfluxrest.bootstrap;

import by9ye.springframework.spring5webfluxrest.domain.Category;
import by9ye.springframework.spring5webfluxrest.domain.Vendor;
import by9ye.springframework.spring5webfluxrest.repository.CategoryRepository;
import by9ye.springframework.spring5webfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0)
            loadCategories();
        if (vendorRepository.count().block() == 0)
            loadVendors();
    }

    private void loadVendors() {
        Vendor vendor1 = Vendor.builder().firstName("Bin").lastName("Yin").build();
        vendorRepository.save(vendor1).block();

        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("Jiamin");
        vendor2.setLastName("Cheng");
        vendorRepository.save(vendor2).block();

        System.out.println("Vendors Loaded: " + vendorRepository.count().block());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setDescription("Fruits");

        Category dried = new Category();
        dried.setDescription("Dried");

        Category fresh = new Category();
        fresh.setDescription("Fresh");

        Category exotic = new Category();
        exotic.setDescription("Exotic");

        Category nuts = new Category();
        nuts.setDescription("Nuts");

        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();

        System.out.println("Categories Loaded: " + categoryRepository.count().block());
    }

}
