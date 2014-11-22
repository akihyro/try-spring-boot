package akihyro.tryspringboot.hoges;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.val;

/**
 * Hoge コントローラ。
 */
@RestController
@RequestMapping("/hoges")
public class HogeController {

    /**
     * データストア。
     */
    private static List<HogeData> store = new ArrayList<HogeData>();

    /**
     * データ作成。
     *
     * @param data データ。
     * @return レスポンス。
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HogeData> post(@ModelAttribute HogeData data, UriComponentsBuilder uriComponentsBuilder) {
        store.add(data);
        val headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/hoges/{id}").buildAndExpand(store.size()).toUri());
        return new ResponseEntity<HogeData>(data, headers, HttpStatus.CREATED);
    }

    /**
     * データリスト取得。
     *
     * @return データリスト。
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<HogeData> get() {
        return store;
    }

    /**
     * データ取得。
     *
     * @param id データのID。
     * @return データ。
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public HogeData get(@PathVariable int id) {
        return store.get(id - 1);
    }

}
