package by9ye.springframework.spring5webfluxrest.controller;

import by9ye.springframework.spring5webfluxrest.domain.Category;
import by9ye.springframework.spring5webfluxrest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAllCategories() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                                        Category.builder().description("Cat2").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class).hasSize(2);
    }

    @Test
    public void getCategoryById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("cat1").build()));

        webTestClient.get().uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryToSave = Mono.just(Category.builder().description("cat").build());

        webTestClient.post().uri("/api/v1/categories")
                .body(categoryToSave, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("cat").build());

        webTestClient.put().uri("/api/v1/categories/someId")
                .body(categoryToUpdate, Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patchCategory() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("cat").build());

        webTestClient.patch().uri("/api/v1/categories/someId")
                .body(categoryToUpdate, Category.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(categoryRepository).save(any());
    }

    @Test
    public void patchNullCategory() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().build());

        webTestClient.patch().uri("/api/v1/categories/someId")
                .body(categoryToUpdate, Category.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(categoryRepository, Mockito.never()).save(any());
    }
}