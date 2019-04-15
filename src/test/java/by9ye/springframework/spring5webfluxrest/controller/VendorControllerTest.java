package by9ye.springframework.spring5webfluxrest.controller;

import by9ye.springframework.spring5webfluxrest.domain.Vendor;
import by9ye.springframework.spring5webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void getAllVendor() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().lastName("Yin").firstName("Bin").build(),
                        Vendor.builder().lastName("Cheng").firstName("Jiamin").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class).hasSize(2);
    }

    @Test
    public void getVendorById() {
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Bin").lastName("Yin").build()));

        webTestClient.get().uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void createVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("First Name")
                .lastName("Last Name").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void patchVendor() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToPatchMono = Mono.just(Vendor.builder().firstName("First Name")
                .lastName("Last Name").build());

        webTestClient.patch().uri("/api/v1/vendors/someId")
                .body(vendorToPatchMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(vendorRepository).save(any(Vendor.class));
    }

}