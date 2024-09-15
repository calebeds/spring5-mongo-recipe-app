package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by jt on 6/17/17.
 */
@Ignore
public class IndexControllerTest {

    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    RecipeService recipeService;

    @Mock
    Model model;

    @Autowired
    IndexController controller;

    @Before
    public void setUp() throws Exception {
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void testMockMVC() throws Exception {

        when(recipeService.getRecipes()).thenReturn(Flux.empty());

        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    public void getIndexPage() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), recipe));

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewName = controller.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        List<Recipe> listInController = argumentCaptor.getValue();
        assertEquals(2, listInController.size());
    }

}