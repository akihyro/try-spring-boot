package akihyro.tryspringboot.hoges;

import java.io.StringReader;
import java.util.Arrays;

import javax.xml.bind.JAXB;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;

import akihyro.tryspringboot.Application;

/**
 * {@link HogeController} のテスト。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HogeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void JSONでPOSTしてGETできる() throws Exception {

        // リクエスト生成
        val postReq = post("/hoges")
            .accept(MediaType.APPLICATION_JSON)
            .param("integer", "1")
            .param("string", "aaa")
            .param("strings", "bbb", "ccc");

        // POST
        val postRes = mockMvc.perform(postReq)
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("/hoges/")))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        // GET
        val getRes = mockMvc.perform(get(postRes.getResponse().getHeader("Location") + ".json"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        // レスポンス確認
        val data = new ObjectMapper().readValue(getRes.getResponse().getContentAsString(), HogeData.class);
        assertThat(data.getInteger(), is(1));
        assertThat(data.getString(), is("aaa"));
        assertThat(data.getStrings(), is(Arrays.asList("bbb", "ccc")));

    }

    @Test
    public void XMLでPOSTしてGETできる() throws Exception {

        // リクエスト生成
        val postReq = post("/hoges")
                .accept(MediaType.APPLICATION_XML)
                .param("integer", "2")
                .param("string", "xxx")
                .param("strings", "yyy", "zzz");

        // POST
        val postRes = mockMvc.perform(postReq)
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("/hoges/")))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andReturn();

        // GET
        val getRes = mockMvc.perform(get(postRes.getResponse().getHeader("Location") + ".xml"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andReturn();

        // レスポンス確認
        val data = JAXB.unmarshal(new StringReader(getRes.getResponse().getContentAsString()), HogeData.class);
        assertThat(data.getInteger(), is(2));
        assertThat(data.getString(), is("xxx"));
        assertThat(data.getStrings(), is(Arrays.asList("yyy", "zzz")));

    }

}
